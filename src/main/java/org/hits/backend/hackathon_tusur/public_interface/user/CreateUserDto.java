package org.hits.backend.hackathon_tusur.public_interface.user;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record CreateUserDto(
        String username,
        String email,
        String password,
        String fullName,
        LocalDate birthDate,
        String affiliateId,
        List<String> commandId,
        MultipartFile photo
) {
}
