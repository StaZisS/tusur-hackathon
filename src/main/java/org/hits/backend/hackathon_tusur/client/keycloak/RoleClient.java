package org.hits.backend.hackathon_tusur.client.keycloak;

public interface RoleClient {
    void assignRole(String userId, String roleName);
    void removeRole(String userId, String roleName);
}
