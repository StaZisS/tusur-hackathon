package org.hits.backend.hackathon_tusur.public_interface.user;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record UpdateUserDto(
        String userId,
        Optional<String> username,
        Optional<String> email,
        Optional<String> password,
        Optional<String> fullName,
        Optional<LocalDate> birthDate,
        Optional<String> affiliationId,
        List<String> commandIds,
        Optional<Integer> deliveryDateBefore,
        Optional<MultipartFile> photo
) {
}
