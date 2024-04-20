package org.hits.backend.hackathon_tusur.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UserDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @PutMapping("/user")
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

    @GetMapping("/user")
    public UserDto getUser(JwtAuthenticationToken token) {
        return userService.getUser(token.getTokenAttributes().get("sub").toString());
    }
}
