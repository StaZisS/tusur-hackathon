package org.hits.backend.hackathon_tusur.rest.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public record FullUserResponse(
        String id,
        String username,
        String email,
        @JsonProperty("full_name")
        String fullName,
        @JsonProperty("birth_date")
        LocalDate birthDate,
        List<CommandDto> commands,
        Optional<AffiliateDto> affiliate,
        @JsonProperty("delivery_date_before")
        Integer deliveryDateBefore,
        @JsonProperty("online_status")
        Boolean onlineStatus,
        @JsonProperty("photo_url")
        String photoUrl
) {
}
