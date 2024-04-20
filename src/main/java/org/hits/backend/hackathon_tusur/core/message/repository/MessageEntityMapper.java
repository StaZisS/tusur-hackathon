package org.hits.backend.hackathon_tusur.core.message.repository;

import com.example.hackathon.public_.tables.records.MessageRecord;
import org.jooq.RecordMapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class MessageEntityMapper implements RecordMapper<MessageRecord, MessageEntity> {
    @Override
    public MessageEntity map(MessageRecord messageRecord) {
        return new MessageEntity(
                messageRecord.getMessageId(),
                messageRecord.getChatRoomId(),
                messageRecord.getSenderId(),
                messageRecord.getContent(),
                ZonedDateTime.of(messageRecord.getCreatedAt(), ZoneId.systemDefault()).toOffsetDateTime()
        );
    }
}
