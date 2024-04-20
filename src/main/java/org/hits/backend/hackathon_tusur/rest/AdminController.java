package org.hits.backend.hackathon_tusur.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.affiliate.AffiliateService;
import org.hits.backend.hackathon_tusur.core.command.CommandService;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.CreateAffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.UpdateAffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CreateCommandDto;
import org.hits.backend.hackathon_tusur.public_interface.command.UpdateCommandDto;
import org.hits.backend.hackathon_tusur.public_interface.user.CreateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Test Controller", description = "Test controller for admin")
public class AdminController {
    private final UserService userService;
    private final AffiliateService affiliateService;
    private final CommandService commandService;

    @PostMapping("/user")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("full_name") String fullName,
                             @RequestParam("birth_date") String birthDate,
                             @RequestParam("affiliate_id") String affiliateId,
                             @RequestParam("command_id") List<String> commandId) {
        var dto = new CreateUserDto(
                username,
                email,
                password,
                fullName,
                LocalDate.parse(birthDate),
                affiliateId,
                commandId
        );

        return userService.createUser(dto);
    }

    @PutMapping("/user")
    public void updateUser(@RequestParam("user_id") String userId,
                           @RequestParam("username") Optional<String> username,
                           @RequestParam("email") Optional<String> email,
                           @RequestParam("password") Optional<String> password,
                           @RequestParam("full_name") Optional<String> fullName,
                           @RequestParam("birth_date") Optional<LocalDate> birthDate,
                           @RequestParam("affiliate_id") Optional<String> affiliateId,
                           @RequestParam("command_id") List<String> commandId,
                           @RequestParam("delivery_date_before") Optional<Integer> deliveryDateBefore) {
        var dto = new UpdateUserDto(
                userId,
                username,
                email,
                password,
                fullName,
                birthDate,
                affiliateId,
                commandId,
                deliveryDateBefore
        );
        userService.updateUser(dto);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam("user_id") String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/affiliate")
    public String createAffiliate(@RequestParam("name") String name,
                                  @RequestParam("description") String description) {
        var dto = new CreateAffiliateDto(
                name,
                description
        );
        return affiliateService.createAffiliate(dto);
    }

    @PutMapping("/affiliate")
    public void updateAffiliate(@RequestParam("affiliate_id") String affiliateId,
                                  @RequestParam("name") String name,
                                  @RequestParam("description") String description) {
        var dto = new UpdateAffiliateDto(
                affiliateId,
                name,
                description
        );
        affiliateService.updateAffiliate(dto);
    }

    @DeleteMapping("/affiliate")
    public void deleteAffiliate(@RequestParam("affiliate_id") String affiliateId) {
        affiliateService.deleteAffiliate(affiliateId);
    }

    @PostMapping("/command")
    public String createCommand(@RequestParam("name") String name,
                                @RequestParam("description") String description) {
        var dto = new CreateCommandDto(
                name,
                description
        );
        return commandService.createCommand(dto);
    }

    @PutMapping("/command")
    public void updateCommand(@RequestParam("command_id") String commandId,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description) {
        var dto = new UpdateCommandDto(
                commandId,
                name,
                description
        );
        commandService.updateCommand(dto);
    }

    @DeleteMapping("/command")
    public void deleteCommand(@RequestParam("command_id") String commandId) {
        commandService.deleteCommand(commandId);
    }

    @PostMapping("/role")
    public void assignRole(@RequestParam("user_id") String userId,
                           @RequestParam("role") String role) {
        userService.assignRole(userId, role);
    }

    @DeleteMapping("/role")
    public void removeRole(@RequestParam("user_id") String userId,
                           @RequestParam("role") String role) {
        userService.removeRole(userId, role);
    }

}
