package org.hits.backend.hackathon_tusur.public_interface.user;

import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record UserDto(
        String id,
        String username,
        String email,
        String fullName,
        LocalDate birthDate,
        List<CommandDto> commands,
        Optional<AffiliateDto> affiliate,
        Integer deliveryDateBefore,
        Boolean onlineStatus
) {
}
