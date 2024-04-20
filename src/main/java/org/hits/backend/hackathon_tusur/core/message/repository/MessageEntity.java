package org.hits.backend.hackathon_tusur.core.message.repository;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record MessageEntity(
        UUID messageId,
        String chatRoomId,
        String senderId,
        String content,
        OffsetDateTime createdAt
) {
}
