package org.hits.backend.hackathon_tusur.core.chat.repository;

public record ChatRoomEntity(
        String chatRoomId,
        String name,
        String description
) {
}
