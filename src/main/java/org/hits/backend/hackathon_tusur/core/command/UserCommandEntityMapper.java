package org.hits.backend.hackathon_tusur.core.command;

import com.example.hackathon.public_.tables.records.UserCommandRecord;
import org.jooq.RecordMapper;

public class UserCommandEntityMapper implements RecordMapper<UserCommandRecord, UserCommandEntity> {
    @Override
    public UserCommandEntity map(UserCommandRecord record) {
        return new UserCommandEntity(
                record.getCommandId(),
                record.getUserId()
        );
    }
}
