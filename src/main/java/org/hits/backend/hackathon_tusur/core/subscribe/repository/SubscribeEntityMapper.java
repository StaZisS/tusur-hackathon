package org.hits.backend.hackathon_tusur.core.subscribe.repository;

import com.example.hackathon.public_.tables.records.SubscribeRecord;
import org.jooq.RecordMapper;

public class SubscribeEntityMapper implements RecordMapper<SubscribeRecord, SubscribeEntity> {

    @Override
    public SubscribeEntity map(SubscribeRecord subscribeRecord) {
        return new SubscribeEntity(
                subscribeRecord.getOwnerId(),
                subscribeRecord.getSubscriberId()
        );
    }
}
