package org.hits.backend.hackathon_tusur.core.message.service;

import org.hits.backend.hackathon_tusur.core.chat.service.ChatRoomService;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageEntity;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageRepository;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.rest.message.MessageDto;
import org.hits.backend.hackathon_tusur.public_interface.message.CreateMessageDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MessageService {
    private final ChatRoomService chatRoomService;
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(@Lazy ChatRoomService chatRoomService, MessageRepository messageRepository, @Lazy UserService userService) {
        this.chatRoomService = chatRoomService;
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public MessageEntity save(CreateMessageDto messageDto) {
        var recipientUser = userService.getUser(messageDto.recipientId());
        var senderUser = userService.getUser(messageDto.senderId());

        var chatRoomEntity = chatRoomService.getChatRoomId(recipientUser.id());
        String chatRoomId;

        if (chatRoomEntity.isEmpty()) {
            var createdChatRoomEntity = chatRoomService.createChatRoom(recipientUser.id(),
                    "Чат для пользователя " + recipientUser.fullName(), "Собираем подарок " + recipientUser.fullName());
            chatRoomId = createdChatRoomEntity.chatRoomId();
        } else {
            chatRoomId = chatRoomEntity.get().chatRoomId();
        }

        var messageEntity = MessageEntity.builder()
                .createdAt(OffsetDateTime.now())
                .chatRoomId(chatRoomId)
                .senderId(senderUser.id())
                .isNotification(messageDto.isNotification())
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
        var user = userService.getUser(entity.senderId());

        return new MessageDto(entity.messageId(),
                entity.chatRoomId(),
                entity.senderId(),
                entity.content(),
                entity.createdAt(),
                entity.isNotification(),
                user.fullName(),
                user.photoUrl());
    }
}
