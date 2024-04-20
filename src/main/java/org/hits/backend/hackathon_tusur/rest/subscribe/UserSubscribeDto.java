package org.hits.backend.hackathon_tusur.rest.subscribe;

import java.time.LocalDate;

public record UserSubscribeDto(
        String id,
        String username,
        String email,
        String fullName,
        LocalDate birthDate,
        Integer deliveryDateBefore
) {
}
