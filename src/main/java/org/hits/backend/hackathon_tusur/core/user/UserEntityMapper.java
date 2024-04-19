package org.hits.backend.hackathon_tusur.core.user;

import com.example.hackathon.public_.tables.records.UsersRecord;
import org.jooq.RecordMapper;

import java.util.Optional;

public class UserEntityMapper implements RecordMapper<UsersRecord, UserEntity> {
    @Override
    public UserEntity map(UsersRecord record) {
        return new UserEntity(
                record.getId(),
                null,
                null,
                null,
                null,
                record.getDateBirth(),
                Optional.ofNullable(record.getAffiliateId()),
                record.getDeliveryDateBefore(),
                record.getOnlineStatus()
        );
    }
}
