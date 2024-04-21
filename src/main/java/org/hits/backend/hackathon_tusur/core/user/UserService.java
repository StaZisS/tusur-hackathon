package org.hits.backend.hackathon_tusur.core.user;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.client.keycloak.RoleClient;
import org.hits.backend.hackathon_tusur.client.keycloak.UserClient;
import org.hits.backend.hackathon_tusur.core.affiliate.AffiliateService;
import org.hits.backend.hackathon_tusur.core.command.CommandService;
import org.hits.backend.hackathon_tusur.core.file.FileMetadata;
import org.hits.backend.hackathon_tusur.core.file.StorageService;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistService;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.hits.backend.hackathon_tusur.public_interface.file.UploadFileDto;
import org.hits.backend.hackathon_tusur.public_interface.user.CreateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UserDto;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.CreateWishlistDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;
    private final RoleClient roleClient;
    private final UserRepository userRepository;
    private final CommandService commandService;
    private final AffiliateService affiliateService;
    private final WishlistService wishlistService;
    private final StorageService storageService;

    @Transactional
    public String createUser(CreateUserDto dto) {
        checkUserWithUsernameExists(dto.username());
        checkUserWithEmailExists(dto.email());

        var userEntity = new UserEntity(
                null,
                dto.username(),
                dto.email(),
                dto.password(),
                dto.fullName(),
                dto.birthDate(),
                Optional.ofNullable(dto.affiliateId()),
                1209600,
                true
        );
        var oauthId = userClient.registerUser(userEntity);
        userEntity = new UserEntity(
                oauthId,
                dto.username(),
                dto.email(),
                dto.password(),
                dto.fullName(),
                dto.birthDate(),
                Optional.ofNullable(dto.affiliateId()),
                1209600,
                true
        );
        userRepository.createUser(userEntity);
        roleClient.assignRole(oauthId, "ROLE_USER");
        var createWishlistDto = new CreateWishlistDto(oauthId);
        wishlistService.createWishlist(createWishlistDto);
        savePhoto(dto.photo(), oauthId);

        return oauthId;
    }

    @Transactional
    public UserDto getUser(String userId) {
        var user = getUserEntity(userId);
        return new UserDto(
                user.id(),
                user.username(),
                user.email(),
                user.fullName(),
                user.birthDate(),
                commandService.getUserCommands(userId),
                user.affiliateId().flatMap(affiliateService::getAffiliate),
                user.deliveryDateBefore(),
                user.onlineStatus(),
                storageService.getDownloadLinkByName(String.format("user_%s_photo", userId))
        );
    }

    @Transactional
    public void deleteUser(String userId) {
        var user = getUserEntity(userId);

        userRepository.deleteUser(user.id());
        userClient.deleteUser(user.id());
    }

    @Transactional
    public void blockUser(String userId) {
        var user = getUserEntity(userId);

        roleClient.assignRole(user.id(), "ROLE_BLOCK");
    }

    @Transactional
    public void unblockUser(String userId) {
        var user = getUserEntity(userId);

        roleClient.removeRole(user.id(), "ROLE_BLOCK");
    }

    @Transactional
    public void assignRole(String userId, String role) {
        var user = getUserEntity(userId);

        roleClient.assignRole(user.id(), role);
    }

    @Transactional
    public void removeRole(String userId, String role) {
        var user = getUserEntity(userId);

        roleClient.removeRole(user.id(), role);
    }

    @Transactional
    public void updateUser(UpdateUserDto dto) {
        var user = getUserEntity(dto.userId());

        var username = dto.username().filter(newUsername -> !newUsername.equals(user.username()));
        var email = dto.email().filter(newEmail -> !newEmail.equals(user.email()));

        var newUser = new UserEntity(
                user.id(),
                username.orElse(user.username()),
                email.orElse(user.email()),
                dto.password().orElse(user.password()),
                dto.fullName().orElse(user.fullName()),
                dto.birthDate().orElse(user.birthDate()),
                dto.affiliationId().isPresent() ? dto.affiliationId() : user.affiliateId(),
                dto.deliveryDateBefore().orElse(user.deliveryDateBefore()),
                user.onlineStatus()
        );

        if (!dto.commandIds().isEmpty()) {
            for (String commandId : dto.commandIds()) {
                commandService.assignCommandToUser(commandId, user.id());
            }
        }

        if (dto.photo().isPresent()) {
            deletePhoto(user.id());
            savePhoto(dto.photo().get(), user.id());
        }

        userRepository.updateUser(newUser);

        var newUpdateDto = new UpdateUserDto(
                user.id(),
                username,
                email,
                dto.password(),
                dto.fullName(),
                dto.birthDate(),
                dto.affiliationId(),
                dto.commandIds(),
                dto.deliveryDateBefore(),
                dto.photo()
        );
        userClient.updateUser(dto, user.id());
    }

    public List<UserDto> getUsersByName(String userName) {
        return userClient.getUsersByName(userName)
                .stream()
                .map(user -> new UserDto(
                        user.id(),
                        user.username(),
                        user.email(),
                        user.fullName(),
                        user.birthDate(),
                        commandService.getUserCommands(user.id()),
                        user.affiliateId().flatMap(affiliateService::getAffiliate),
                        user.deliveryDateBefore(),
                        user.onlineStatus(),
                        storageService.getDownloadLinkByName(String.format("user_%s_photo", user.id()))
                ))
                .toList();
    }

    private UserEntity getUserEntity(String userId) {
        return userClient.getUser(userId)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
    }

    private void checkUserWithUsernameExists(String username) {
        if (userClient.getUserByUsername(username).isPresent()) {
            throw new ExceptionInApplication("User with this username does not exist", ExceptionType.NOT_FOUND);
        }
    }

    private void checkUserWithEmailExists(String email) {
        if (userClient.getUserByEmail(email).isPresent()) {
            throw new ExceptionInApplication("User with this email does not exist", ExceptionType.NOT_FOUND);
        }
    }

    private void savePhoto(MultipartFile photo, String userID) {
        var fileMetadata = new FileMetadata(
                String.format("user_%s_photo", userID),
                photo.getContentType(),
                photo.getSize()
        );
        var uploadFileDto = new UploadFileDto(
                fileMetadata,
                photo
        );
        storageService.uploadFile(uploadFileDto).subscribe();
    }

    private void deletePhoto(String userID) {
        storageService.deleteFile(String.format("user_%s_photo", userID)).subscribe();
    }

}
