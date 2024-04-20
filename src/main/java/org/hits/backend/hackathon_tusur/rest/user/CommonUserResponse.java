package org.hits.backend.hackathon_tusur.rest.user;

import java.time.LocalDate;
import java.util.List;

public record CommonUserResponse(
        String id,
        String username,
        String email,
        String fullName,
        LocalDate birthDate,
        String affiliateId,
        String affiliateName,
        String affiliateAddress,
        Boolean onlineStatus,
        List<String> commandNames,
        String photoUrl
) {
}
