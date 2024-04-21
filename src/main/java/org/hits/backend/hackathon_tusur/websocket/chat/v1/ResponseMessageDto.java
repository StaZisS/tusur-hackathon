package org.hits.backend.hackathon_tusur.websocket.chat.v1;

import java.util.UUID;

public record ResponseMessageDto(
        UUID messageId,
        String senderId,
        String receiverId,
        String content,
        boolean isNotification,
        String fullName,
        String photoUrl
) {}
