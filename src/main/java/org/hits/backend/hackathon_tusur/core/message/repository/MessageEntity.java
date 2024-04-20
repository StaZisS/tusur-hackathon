package org.hits.backend.hackathon_tusur.core.message.repository;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MessageEntity(
        UUID messageId,
        String chatRoomId,
        String senderId,
        String content,
        OffsetDateTime createdAt
) {
}
