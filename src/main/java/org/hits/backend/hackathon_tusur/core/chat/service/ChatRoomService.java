package org.hits.backend.hackathon_tusur.core.chat.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.chat.repository.ChatRoomEntity;
import org.hits.backend.hackathon_tusur.core.chat.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoomEntity> getChatRoomId(String recipientId) {
        return chatRoomRepository.getChatRoom(recipientId);
    }

    public ChatRoomEntity createChatRoom(String receiverId, String name, String description) {
        return chatRoomRepository.createChatRoom(receiverId, name, description);
    }
}
