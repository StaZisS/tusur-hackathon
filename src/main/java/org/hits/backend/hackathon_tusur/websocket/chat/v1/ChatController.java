package org.hits.backend.hackathon_tusur.websocket.chat.v1;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageEntity;
import org.hits.backend.hackathon_tusur.core.message.service.MessageService;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.public_interface.message.CreateMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload RequestMessageDto message, @AuthenticationPrincipal UsernamePasswordAuthenticationToken authenticationToken) {
        var senderId = parseToken(authenticationToken);
        checkYourSelf(message, senderId);

        var createMessageDto = new CreateMessageDto(senderId, message.recipientId(), message.content(), false);

        MessageEntity messageEntity = messageService.save(createMessageDto);

        var sender = userService.getUser(senderId);

        var response = new ResponseMessageDto(messageEntity.messageId(), messageEntity.senderId(), messageEntity.chatRoomId(),
                messageEntity.content(), false, sender.fullName(), sender.photoUrl());

        messagingTemplate.convertAndSend("/topic/" + messageEntity.chatRoomId() + "/messages", response);
    }

    public String parseToken(UsernamePasswordAuthenticationToken authenticationToken) {
        var token = (JwtAuthenticationToken) authenticationToken.getPrincipal();
        return token.getTokenAttributes().get("sub").toString();
    }

    private void checkYourSelf(RequestMessageDto message, String senderId) {
        if (senderId.equals(message.recipientId())) {
            throw new IllegalArgumentException("You can't send a message to yourself");
        }
    }
}
