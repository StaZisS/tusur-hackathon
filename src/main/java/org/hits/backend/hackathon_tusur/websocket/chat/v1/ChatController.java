package org.hits.backend.hackathon_tusur.websocket.chat.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageEntity;
import org.hits.backend.hackathon_tusur.core.message.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload RequestMessageDto message) {
        MessageEntity messageEntity = messageService.save(message);

        var response = new ResponseMessageDto(messageEntity.messageId(), messageEntity.senderId(), messageEntity.chatRoomId(), messageEntity.content());

        messagingTemplate.convertAndSend("/topic/" + messageEntity.chatRoomId() + "/messages", response);
    }
}
