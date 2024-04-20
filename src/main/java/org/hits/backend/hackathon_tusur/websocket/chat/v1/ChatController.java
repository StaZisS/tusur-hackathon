package org.hits.backend.hackathon_tusur.websocket.chat.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload RequestMessageDto message) {
        messagingTemplate.convertAndSend("/topic/" + "77bf6495-39f2-460b-8678-698c6e7446ab" + "/messages",
                new ResponseMessageDto(message.content(), OffsetDateTime.now().toString()));
    }
}
