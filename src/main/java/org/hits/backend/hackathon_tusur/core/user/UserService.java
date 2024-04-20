package org.hits.backend.hackathon_tusur.core.user;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.client.RoleClient;
import org.hits.backend.hackathon_tusur.client.UserClient;
import org.hits.backend.hackathon_tusur.core.affiliate.AffiliateService;
import org.hits.backend.hackathon_tusur.core.command.CommandService;
import org.hits.backend.hackathon_tusur.core.wishlist.WishlistService;
import org.hits.backend.hackathon_tusur.public_interface.user.CreateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UserDto;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.hits.backend.hackathon_tusur.public_interface.wishlist.CreateWishlistDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return oauthId;
    }

    @Transactional(readOnly = true)
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
                user.onlineStatus()
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

        var newUser = new UserEntity(
                user.id(),
                dto.username().orElse(user.username()),
                dto.email().orElse(user.email()),
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

        userRepository.updateUser(newUser);
        userClient.updateUser(dto, user.id());
    }

    private UserEntity getUserEntity(String userId) {
        return userClient.getUser(userId)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
    }

    private void checkUserWithUsernameExists(String username) {
        if (userClient.getUserByUsername(username).isEmpty()) {
            throw new ExceptionInApplication("User with this username does not exist", ExceptionType.NOT_FOUND);
        }
    }

    private void checkUserWithEmailExists(String email) {
        if (userClient.getUserByEmail(email).isEmpty()) {
            throw new ExceptionInApplication("User with this email does not exist", ExceptionType.NOT_FOUND);
        }
    }
}
