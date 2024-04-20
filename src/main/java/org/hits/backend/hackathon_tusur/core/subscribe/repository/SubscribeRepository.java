package org.hits.backend.hackathon_tusur.core.subscribe.repository;

import java.util.List;

public interface SubscribeRepository {
    SubscribeEntity save(SubscribeEntity subscribeEntity);
    void delete(SubscribeEntity subscribeEntity);
    List<SubscribeEntity> findSubscribers(String ownerId);
}
