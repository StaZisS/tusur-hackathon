package org.hits.backend.hackathon_tusur.core.message.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.chat.service.ChatRoomService;
import org.hits.backend.hackathon_tusur.core.message.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private MessageRepository messageRepository;
    private ChatRoomService chatRoomService;
    // private final UserRepository userRepository;


}
