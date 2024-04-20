package org.hits.backend.hackathon_tusur.core.user;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public record UserEntity(
        String id,
        String username,
        String email,
        String password,
        String fullName,
        LocalDate birthDate,
        Optional<String> affiliateId,
        Integer deliveryDateBefore,
        Boolean onlineStatus
) {
}
