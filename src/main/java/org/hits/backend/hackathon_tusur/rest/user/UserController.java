package org.hits.backend.hackathon_tusur.rest.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UserDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@SecurityRequirement(name = "oauth2")
@Tag(name = "User", description = "Test controller for user")
public class UserController {
    private final UserService userService;

    @PutMapping
    public void updateUser(@RequestParam("username") Optional<String> username,
                           @RequestParam("email") Optional<String> email,
                           @RequestParam("password") Optional<String> password,
                           @RequestParam("full_name") Optional<String> fullName,
                           @RequestParam("birth_date") @DateTimeFormat(pattern = "dd/MM/yyyy") Optional<LocalDate> birthDate,
                           @RequestParam("delivery_date_before") Optional<Integer> deliveryDateBefore,
                           @RequestParam(required = false) Optional<MultipartFile> photo,
                           JwtAuthenticationToken token) {
        var dto = new UpdateUserDto(
                token.getTokenAttributes().get("sub").toString(),
                username,
                email,
                password,
                fullName,
                birthDate,
                Optional.empty(),
                List.of(),
                deliveryDateBefore,
                photo
        );
        userService.updateUser(dto);
    }

    @GetMapping("/profile")
    public FullUserResponse getProfile(JwtAuthenticationToken token) {
        var response = userService.getUser(token.getTokenAttributes().get("sub").toString());
        return mapToFullUserResponse(response);
    }

    @GetMapping
    public List<CommonUserResponse> getUsers(JwtAuthenticationToken token, @RequestParam("user_full_name") String userFullName) {
        var myId = token.getTokenAttributes().get("sub").toString();
        List<UserDto> response = userService.getUsersByName(userFullName);
        return response.stream()
                .filter(user -> !user.id().equals(myId))
                .map(this::mapToCommonUserResponse)
                .toList();
    }

    @GetMapping("/profile/{userId}")
    public CommonUserOneCommandDto getUsersById(@PathVariable String userId) {
        UserDto response = userService.getUser(userId);
        return new CommonUserOneCommandDto(
                response.id(),
                response.username(),
                response.email(),
                response.fullName(),
                response.birthDate(),
                response.affiliate(),
                response.onlineStatus(),
                response.commands() != null && !response.commands().isEmpty() ? response.commands().getFirst() : null,
                response.photoUrl()
        );
    }


    private FullUserResponse mapToFullUserResponse(UserDto dto) {
        return new FullUserResponse(
                dto.id(),
                dto.username(),
                dto.email(),
                dto.fullName(),
                dto.birthDate(),
                dto.commands(),
                dto.affiliate(),
                dto.deliveryDateBefore(),
                dto.onlineStatus(),
                dto.photoUrl()
        );
    }

    private CommonUserResponse mapToCommonUserResponse(UserDto dto) {
        var affiliate = dto.affiliate();
        return new CommonUserResponse(
                dto.id(),
                dto.username(),
                dto.email(),
                dto.fullName(),
                dto.birthDate(),
                affiliate.map(AffiliateDto::id).orElse(null),
                affiliate.map(AffiliateDto::name).orElse(null),
                affiliate.map(AffiliateDto::address).orElse(null),
                dto.onlineStatus(),
                dto.commands().stream().map(CommandDto::name).toList(),
                dto.photoUrl()
        );
    }
}
