package org.hits.backend.hackathon_tusur.core.chat.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.hackathon.public_.Tables.CHAT_ROOM;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
    private static final ChatEntityMapper CHAT_ENTITY_MAPPER = new ChatEntityMapper();
    private final DSLContext create;

    @Override
    public Optional<ChatRoomEntity> getChatRoom(String chatRoomId) {
        return create.selectFrom(CHAT_ROOM)
                .where(CHAT_ROOM.CHAT_ROOM_ID.eq(chatRoomId))
                .fetchOptional(CHAT_ENTITY_MAPPER);
    }

    @Override
    public ChatRoomEntity createChatRoom(String receiverId, String name, String description) {
        return create.insertInto(CHAT_ROOM)
                .set(CHAT_ROOM.CHAT_ROOM_ID, receiverId)
                .set(CHAT_ROOM.NAME, name)
                .set(CHAT_ROOM.DESCRIPTION, description)
                .returning(CHAT_ROOM.CHAT_ROOM_ID, CHAT_ROOM.NAME, CHAT_ROOM.DESCRIPTION)
                .fetchOne(CHAT_ENTITY_MAPPER);
    }
}
