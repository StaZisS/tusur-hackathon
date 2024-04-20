package org.hits.backend.hackathon_tusur.core.message.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.hackathon.public_.Tables.MESSAGE;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {
    private final MessageEntityMapper MESSAGE_ENTITY_MAPPER = new MessageEntityMapper();
    private final DSLContext create;

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return create.insertInto(MESSAGE)
                .set(MESSAGE.CHAT_ROOM_ID, messageEntity.chatRoomId())
                .set(MESSAGE.SENDER_ID, messageEntity.senderId())
                .set(MESSAGE.CONTENT, messageEntity.content())
                .set(MESSAGE.CREATED_AT, messageEntity.createdAt().toLocalDateTime())
                .returning(MESSAGE.MESSAGE_ID, MESSAGE.CHAT_ROOM_ID, MESSAGE.SENDER_ID, MESSAGE.CONTENT, MESSAGE.CREATED_AT)
                .fetchOne(MESSAGE_ENTITY_MAPPER);
    }

    @Override
    public List<MessageEntity> findChatMessages(String chatRoomId) {
        return create.selectFrom(MESSAGE)
                .where(MESSAGE.CHAT_ROOM_ID.eq(chatRoomId))
                .fetch(MESSAGE_ENTITY_MAPPER);
    }

    @Override
    public int countChatMessages(String chatRoomId) {
        return create.fetchCount(MESSAGE, MESSAGE.CHAT_ROOM_ID.eq(chatRoomId));
    }
}
