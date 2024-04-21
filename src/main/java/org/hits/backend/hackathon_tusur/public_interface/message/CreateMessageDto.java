package org.hits.backend.hackathon_tusur.public_interface.message;

public record CreateMessageDto(
        String senderId,
        String recipientId,
        String content,
        boolean isNotification
) {
}
