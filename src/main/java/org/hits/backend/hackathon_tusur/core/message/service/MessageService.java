package org.hits.backend.hackathon_tusur.core.message.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.chat.service.ChatRoomService;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageEntity;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageRepository;
import org.hits.backend.hackathon_tusur.core.user.UserRepository;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.hits.backend.hackathon_tusur.rest.message.MessageDto;
import org.hits.backend.hackathon_tusur.websocket.chat.v1.RequestMessageDto;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageEntity save(RequestMessageDto messageDto) {
        var recipientUser = userRepository.getUserById(messageDto.recipientId())
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

        var senderUser = userRepository.getUserById(messageDto.senderId())
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

        var chatRoomEntity = chatRoomService.getChatRoomId(recipientUser.id());

        var messageEntity = MessageEntity.builder()
                .createdAt(OffsetDateTime.now())
                .chatRoomId(chatRoomEntity.chatRoomId())
                .senderId(senderUser.id())
                .content(messageDto.content())
                .build();

        return messageRepository.save(messageEntity);
    }

    public List<MessageDto> findChatMessages(String chatRoomId) {
        return messageRepository.findChatMessages(chatRoomId)
                .stream()
                .map(this::fromEntity)
                .toList();
    }

    private MessageDto fromEntity(MessageEntity entity) {
        return new MessageDto(entity.messageId(),
                entity.chatRoomId(),
                entity.senderId(),
                entity.content(),
                entity.createdAt());

    }
}
