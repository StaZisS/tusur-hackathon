package org.hits.backend.hackathon_tusur.core.subscribe.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.hackathon.public_.Tables.SUBSCRIBE;

@Repository
@RequiredArgsConstructor
public class SubscribeRepositoryImpl implements SubscribeRepository {
    private final SubscribeEntityMapper SUBSCRIBE_ENTITY_MAPPER = new SubscribeEntityMapper();
    private final DSLContext create;


    @Override
    public SubscribeEntity save(SubscribeEntity subscribeEntity) {
        return create.insertInto(SUBSCRIBE)
                .set(SUBSCRIBE.OWNER_ID, subscribeEntity.ownerId())
                .set(SUBSCRIBE.SUBSCRIBER_ID, subscribeEntity.subscriberId())
                .returning(SUBSCRIBE.OWNER_ID, SUBSCRIBE.SUBSCRIBER_ID)
                .fetchOne(SUBSCRIBE_ENTITY_MAPPER);
    }

    @Override
    public void delete(SubscribeEntity subscribeEntity) {
        create.deleteFrom(SUBSCRIBE)
                .where(SUBSCRIBE.OWNER_ID.eq(subscribeEntity.ownerId())
                        .and(SUBSCRIBE.SUBSCRIBER_ID.eq(subscribeEntity.subscriberId())))
                .execute();
    }

    @Override
    public List<SubscribeEntity> findSubscribers(String ownerId) {
        return create.selectFrom(SUBSCRIBE)
                .where(SUBSCRIBE.OWNER_ID.eq(ownerId))
                .fetch(SUBSCRIBE_ENTITY_MAPPER);
    }
}
