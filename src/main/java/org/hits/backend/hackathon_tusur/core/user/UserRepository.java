package org.hits.backend.hackathon_tusur.core.user;

import java.util.Optional;

public interface UserRepository {
    void createUser(UserEntity entity);
    void updateUser(UserEntity entity);
    void deleteUser(String userId);
    Optional<UserEntity> getUserById(String userId);
}
