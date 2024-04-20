package org.hits.backend.hackathon_tusur.rest.subscribe;

import java.time.LocalDate;

public record SubscribeDto(
        String userId,
        String name,
        LocalDate dateBirth
) {
}
