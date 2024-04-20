package org.hits.backend.hackathon_tusur.core.chat.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.chat.repository.ChatRoomEntity;
import org.hits.backend.hackathon_tusur.core.chat.repository.ChatRoomRepository;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomEntity getChatRoomId(String recipientId) {
        return chatRoomRepository.getChatRoom(recipientId)
                .orElseThrow(() -> new ExceptionInApplication("Chat room not found", ExceptionType.NOT_FOUND));
    }
}
