package org.hits.backend.hackathon_tusur.rest.admin;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.affiliate.AffiliateService;
import org.hits.backend.hackathon_tusur.core.command.CommandService;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.CreateAffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.UpdateAffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CreateCommandDto;
import org.hits.backend.hackathon_tusur.public_interface.command.UpdateCommandDto;
import org.hits.backend.hackathon_tusur.public_interface.user.CreateUserDto;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "oauth2")
@Tag(name = "Admin", description = "Test controller for admin")
public class AdminController {
    private final UserService userService;
    private final AffiliateService affiliateService;
    private final CommandService commandService;

    @PostMapping("/user")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("full_name") String fullName,
                             @RequestParam("birth_date") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate birthDate,
                             @RequestParam("affiliate_id") String affiliateId,
                             @RequestParam("command_id") List<String> commandId,
                             @RequestParam(required = false) MultipartFile photo) {
        var dto = new CreateUserDto(
                username,
                email,
                password,
                fullName,
                birthDate,
                affiliateId,
                commandId,
                photo
        );

        return userService.createUser(dto);
    }

    @PutMapping("/user")
    public void updateUser(@RequestParam("user_id") String userId,
                           @RequestParam("username") Optional<String> username,
                           @RequestParam("email") Optional<String> email,
                           @RequestParam("password") Optional<String> password,
                           @RequestParam("full_name") Optional<String> fullName,
                           @RequestParam("birth_date") @DateTimeFormat(pattern = "dd/MM/yyyy") Optional<LocalDate> birthDate,
                           @RequestParam("affiliate_id") Optional<String> affiliateId,
                           @RequestParam("command_id") List<String> commandId,
                           @RequestParam("delivery_date_before") Optional<Integer> deliveryDateBefore,
                           @RequestParam(required = false) Optional<MultipartFile> photo) {
        var dto = new UpdateUserDto(
                userId,
                username,
                email,
                password,
                fullName,
                birthDate,
                affiliateId,
                commandId,
                deliveryDateBefore,
                photo
        );
        userService.updateUser(dto);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam("user_id") String userId) {
        userService.deleteUser(userId);
    }

    @PostMapping("/affiliate")
    public String createAffiliate(@RequestParam("name") String name,
                                  @RequestParam("address") String address) {
        var dto = new CreateAffiliateDto(
                name,
                address
        );
        return affiliateService.createAffiliate(dto);
    }

    @PutMapping("/affiliate")
    public void updateAffiliate(@RequestParam("affiliate_id") String affiliateId,
                                  @RequestParam("name") String name,
                                  @RequestParam("address") String address) {
        var dto = new UpdateAffiliateDto(
                affiliateId,
                name,
                address
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

    @PutMapping("/block")
    public void blockUser(@RequestParam("user_id") String userId) {
        userService.blockUser(userId);
    }

    @PutMapping("/unblock")
    public void unblockUser(@RequestParam("user_id") String userId) {
        userService.unblockUser(userId);
    }

    @PutMapping("/command/{commandId}/assign")
    public void assignCommandToUser(@RequestParam("user_id") String userId,
                                    @PathVariable("commandId") String commandId) {
        commandService.assignCommandToUser(commandId, userId);
    }

    @PutMapping("/command/{commandId}/unassign")
    public void unassignCommandFromUser(@RequestParam("user_id") String userId,
                                        @PathVariable("commandId") String commandId) {
        commandService.unassignCommandFromUser(commandId, userId);
    }

    @PutMapping("/affiliate/{affiliateId}/assign")
    public void assignAffiliateToUser(@RequestParam("user_id") String userId,
                                    @PathVariable("affiliateId") String affiliateId) {
        affiliateService.assignAffiliateToUser(affiliateId, userId);
    }

    @PutMapping("/affiliate/{affiliateId}/unassign")
    public void unassignAffiliateFromUser(@RequestParam("user_id") String userId,
                                        @PathVariable("affiliateId") String affiliateId) {
        affiliateService.unassignAffiliateFromUser(affiliateId, userId);
    }
}
