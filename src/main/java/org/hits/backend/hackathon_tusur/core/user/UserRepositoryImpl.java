package org.hits.backend.hackathon_tusur.core.user;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.hackathon.public_.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private static final UserEntityMapper USER_ENTITY_MAPPER = new UserEntityMapper();
    private final DSLContext create;

    @Override
    public void createUser(UserEntity entity) {
        create.insertInto(USERS)
                .set(USERS.ID, entity.id())
                .set(USERS.DATE_BIRTH, entity.birthDate())
                .set(USERS.DELIVERY_DATE_BEFORE, entity.deliveryDateBefore())
                .set(USERS.AFFILIATE_ID, entity.affiliateId().orElse(null))
                .set(USERS.ONLINE_STATUS, entity.onlineStatus())
                .execute();
    }

    @Override
    public void updateUser(UserEntity entity) {
        create.update(USERS)
                .set(USERS.DATE_BIRTH, entity.birthDate())
                .set(USERS.DELIVERY_DATE_BEFORE, entity.deliveryDateBefore())
                .set(USERS.AFFILIATE_ID, entity.affiliateId().orElse(null))
                .set(USERS.ONLINE_STATUS, entity.onlineStatus())
                .where(USERS.ID.eq(entity.id()))
                .execute();
    }

    @Override
    public void deleteUser(String userId) {
        create.deleteFrom(USERS)
                .where(USERS.ID.eq(userId))
                .execute();
    }

    @Override
    public Optional<UserEntity> getUserById(String userId) {
        return create.selectFrom(USERS)
                .where(USERS.ID.eq(userId))
                .fetchOptional(USER_ENTITY_MAPPER);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return create.selectFrom(USERS)
                .fetch(USER_ENTITY_MAPPER);
    }

    @Override
    public List<UserEntity> getAllUsersByAffiliateId(String affiliateId) {
        return create.selectFrom(USERS)
                .where(USERS.AFFILIATE_ID.eq(affiliateId))
                .fetch(USER_ENTITY_MAPPER);
    }
}
