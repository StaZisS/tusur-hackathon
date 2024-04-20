package org.hits.backend.hackathon_tusur.client;


import org.hits.backend.hackathon_tusur.core.user.UserEntity;
import org.hits.backend.hackathon_tusur.public_interface.user.UpdateUserDto;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserClient {
    String registerUser(UserEntity entity);
    void deleteUser(String oauthId);
    void updateUser(UpdateUserDto dto, String oauthId);
    Optional<UserEntity> getUser(String oauthId);
    Optional<UserEntity> getUserByUsername(String username);
    Optional<UserEntity> getUserByEmail(String email);
    Stream<UserEntity> getUsersByName(String userName);
}
