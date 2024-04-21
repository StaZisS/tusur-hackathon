package org.hits.backend.hackathon_tusur.config;

import org.hits.backend.hackathon_tusur.client.gpt.YandexGptClient;
import org.hits.backend.hackathon_tusur.client.keycloak.KeycloakRoleClient;
import org.hits.backend.hackathon_tusur.client.keycloak.KeycloakUserClient;
import org.hits.backend.hackathon_tusur.core.user.UserRepository;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientBeans {
    @Bean
    public Keycloak keycloak(
            @Value("${hits-project.services.users.uri}") String catalogueBaseUri,
            @Value("${spring.security.oauth2.client.registration.keycloak.client-id}") String registrationId,
            @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}") String secret,
            @Value("${hits-project.users-realm}") String realm) {
        return KeycloakBuilder.builder()
                .serverUrl(catalogueBaseUri)
                .clientId(registrationId)
                .clientSecret(secret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(realm)
                .build();
    }

    @Bean
    public KeycloakUserClient usersRestClient(
            Keycloak keycloak,
            UserRepository userRepository,
            @Value("${hits-project.users-realm}") String realm) {
        return new KeycloakUserClient(
                userRepository,
                keycloak,
                realm
        );
    }

    @Bean
    public KeycloakRoleClient rolesRestClient(
            Keycloak keycloak,
            @Value("${hits-project.users-realm}") String realm) {
        return new KeycloakRoleClient(
                keycloak,
                realm
        );
    }

    @Bean
    public YandexGptClient yandexGptClient(
            @Value("${yandexApi.foundationModelsUri}") String baseUrl,
            @Value("${yandexApi.token}") String token,
            @Value("${yandexApi.modelUri}") String modelUri) {
        return new YandexGptClient(WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Api-Key " + token)
                .build(),
                modelUri
        );
    }
}