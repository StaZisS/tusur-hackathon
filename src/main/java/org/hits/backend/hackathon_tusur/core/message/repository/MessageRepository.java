package org.hits.backend.hackathon_tusur.core.message.repository;

import java.util.List;

public interface MessageRepository {
    MessageEntity save(MessageEntity messageEntity);
    List<MessageEntity> findChatMessages(String chatRoomId);
    int countChatMessages(String chatRoomId);
}
