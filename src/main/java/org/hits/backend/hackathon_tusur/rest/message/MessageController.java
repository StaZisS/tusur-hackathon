package org.hits.backend.hackathon_tusur.rest.message;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.message.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{chatRoomId}")
    public List<MessageDto> getMessages(@PathVariable String chatRoomId) {
        return messageService.findChatMessages(chatRoomId);
    }
}
