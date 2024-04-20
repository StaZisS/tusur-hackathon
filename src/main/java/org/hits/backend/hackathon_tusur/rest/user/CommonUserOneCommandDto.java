package org.hits.backend.hackathon_tusur.rest.user;

import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;

import java.time.LocalDate;
import java.util.Optional;

public record CommonUserOneCommandDto(
        String id,
        String username,
        String email,
        String fullName,
        LocalDate birthDate,
        Optional<AffiliateDto> affiliate,
        Boolean onlineStatus,
        CommandDto command,
        String photoUrl
) {
}
