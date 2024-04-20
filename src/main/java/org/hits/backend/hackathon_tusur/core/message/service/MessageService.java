package org.hits.backend.hackathon_tusur.core.message.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.chat.service.ChatRoomService;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageEntity;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageRepository;
import org.hits.backend.hackathon_tusur.core.user.UserRepository;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.hits.backend.hackathon_tusur.rest.message.MessageDto;
import org.hits.backend.hackathon_tusur.public_interface.message.CreateMessageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageEntity save(CreateMessageDto messageDto) {
        var recipientUser = userRepository.getUserById(messageDto.recipientId())
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

        var senderUser = userRepository.getUserById(messageDto.senderId())
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

        var chatRoomEntity = chatRoomService.getChatRoomId(recipientUser.id());
        String chatRoomId;

        if (chatRoomEntity.isEmpty()) {
            var createdChatRoomEntity = chatRoomService.createChatRoom(recipientUser.id(),"Chat with " + recipientUser.username(), "Chat with " + recipientUser.username());
            chatRoomId = createdChatRoomEntity.chatRoomId();
        } else {
            chatRoomId = chatRoomEntity.get().chatRoomId();
        }

        var messageEntity = MessageEntity.builder()
                .createdAt(OffsetDateTime.now())
                .chatRoomId(chatRoomId)
                .senderId(senderUser.id())
                .content(messageDto.content())
                .build();

        return messageRepository.save(messageEntity);
    }

    @Transactional
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
