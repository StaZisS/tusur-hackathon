package org.hits.backend.hackathon_tusur.core.chat.repository;

import com.example.hackathon.public_.tables.records.ChatRoomRecord;
import org.jooq.RecordMapper;

public class ChatEntityMapper implements RecordMapper<ChatRoomRecord, ChatRoomEntity> {

    @Override
    public ChatRoomEntity map(ChatRoomRecord chatRoomRecord) {
        return new ChatRoomEntity(
                chatRoomRecord.getChatRoomId(),
                chatRoomRecord.getName(),
                chatRoomRecord.getDescription()
        );
    }
}
