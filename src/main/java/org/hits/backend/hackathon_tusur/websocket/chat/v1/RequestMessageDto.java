package org.hits.backend.hackathon_tusur.websocket.chat.v1;

public record RequestMessageDto(
        String recipientId,
        String content
) {}
