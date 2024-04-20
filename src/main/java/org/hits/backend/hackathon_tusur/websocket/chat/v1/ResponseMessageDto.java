package org.hits.backend.hackathon_tusur.websocket.chat.v1;

import java.time.OffsetDateTime;

public record ResponseMessageDto(
        String message,
        String dateTime
) {
}
