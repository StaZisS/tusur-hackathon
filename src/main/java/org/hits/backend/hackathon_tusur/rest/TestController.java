package org.hits.backend.hackathon_tusur.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
@Tag(name = "Test Controller", description = "Test controller for checking roles")
public class TestController {

    @Operation(summary = "Admin role")
    @GetMapping("/admin")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @SecurityRequirement(name = "oauth2")
    public String admin() {
        return "Hello, admin!";
    }

    @Operation(summary = "User role")
    @GetMapping("/user")
    //@PreAuthorize("hasRole('ROLE_USER')")
    //@SecurityRequirement(name = "oauth2")
    public String user() {
        return "Hello, user!";
    }
}
