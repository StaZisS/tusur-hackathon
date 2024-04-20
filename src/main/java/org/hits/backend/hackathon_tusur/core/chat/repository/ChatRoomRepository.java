package org.hits.backend.hackathon_tusur.core.chat.repository;

import java.util.Optional;

public interface ChatRoomRepository {
    Optional<ChatRoomEntity> getChatRoom(String receiverId);
    ChatRoomEntity createChatRoom(String receiverId, String name, String description);
}
