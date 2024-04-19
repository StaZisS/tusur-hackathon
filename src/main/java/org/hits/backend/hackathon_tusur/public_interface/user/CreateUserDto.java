package org.hits.backend.hackathon_tusur.public_interface.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CreateUserDto(
        String username,
        String email,
        String password,
        String fullName,
        LocalDate birthDate,
        String affiliateId,
        List<String> commandId
) { }
