package org.hits.backend.hackathon_tusur.client;

public interface RoleClient {
    void assignRole(String userId, String roleName);
    void removeRole(String userId, String roleName);
}
