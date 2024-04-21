package org.hits.backend.hackathon_tusur.core.subscribe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hits.backend.hackathon_tusur.client.keycloak.UserClient;
import org.hits.backend.hackathon_tusur.core.affiliate.AffiliateService;
import org.hits.backend.hackathon_tusur.core.command.CommandRepository;
import org.hits.backend.hackathon_tusur.core.subscribe.repository.SubscribeEntity;
import org.hits.backend.hackathon_tusur.core.subscribe.repository.SubscribeRepository;
import org.hits.backend.hackathon_tusur.core.user.UserEntity;
import org.hits.backend.hackathon_tusur.core.user.UserRepository;
import org.hits.backend.hackathon_tusur.core.user.UserService;
import org.hits.backend.hackathon_tusur.public_interface.affiliate.AffiliateDto;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.hits.backend.hackathon_tusur.public_interface.user.UserDto;
import org.hits.backend.hackathon_tusur.rest.subscribe.AffiliateSubscribeDto;
import org.hits.backend.hackathon_tusur.rest.subscribe.CommandSubscribeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;
    private final CommandRepository commandRepository;
    private final AffiliateService affiliateService;
    private final UserClient userClient;
    private final UserService userService;

    @Transactional
    public List<UserDto> getAll(String ownerId) {
        return subscribeRepository.findSubscribers(ownerId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public List<UserEntity> getAllSubscribedUser(String ownerId) {
        return subscribeRepository.findSubscribers(ownerId)
                .stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void subscribePerson(String ownerId, String subscriberId) {
        userClient.getUser(subscriberId)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
        log.info("Subscribing user {} to user {}", ownerId, subscriberId);

        var subscribeEntity = new SubscribeEntity(ownerId, subscriberId);
        subscribeRepository.save(subscribeEntity);
        log.info("User {} subscribed to user {}", ownerId, subscriberId);
    }

    @Transactional
    public void unsubscribePerson(String ownerId, String subscriberId) {
        userClient.getUser(subscriberId)
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

        var subscribeEntity = new SubscribeEntity(ownerId, subscriberId);
        subscribeRepository.delete(subscribeEntity);
    }

    @Transactional
    public void subscribeCommand(String ownerId, String commandId) {
        var command = commandRepository.getCommandById(commandId)
                .orElseThrow(() -> new ExceptionInApplication("Command not found", ExceptionType.NOT_FOUND));

        var users = commandRepository.getUserIdsByCommandId(command.id());
        users.forEach(userId -> subscribePerson(ownerId, userId));
    }

    @Transactional
    public List<CommandSubscribeDto> getCommandSubscribers(String ownerId) {
        List<SubscribeEntity> subscribeEntities = subscribeRepository.findSubscribers(ownerId);

        List<String> commandIds = subscribeEntities.stream()
                .flatMap(subscribeEntity -> commandRepository.getCommandIdsByUserId(subscribeEntity.subscriberId()).stream())
                .distinct()
                .toList();

        return commandIds.stream()
                .map(commandId -> {
                    var command = commandRepository.getCommandById(commandId)
                            .orElseThrow(() -> new ExceptionInApplication("Command not found", ExceptionType.NOT_FOUND));
                    return new CommandSubscribeDto(
                            command.id(),
                            command.name(),
                            command.description()
                    );
                })
                .toList();

    }

    @Transactional
    public void unsubscribeCommand(String ownerId, String commandId) {
        var command = commandRepository.getCommandById(commandId)
                .orElseThrow(() -> new ExceptionInApplication("Command not found", ExceptionType.NOT_FOUND));

        var users = commandRepository.getUserIdsByCommandId(command.id());
        users.forEach(userId -> unsubscribePerson(ownerId, userId));
    }

    @Transactional
    public void subscribeAffiliate(String ownerId, String affiliateId) {
        var affiliate = affiliateService.getAffiliate(affiliateId)
                .orElseThrow(() -> new ExceptionInApplication("Affiliate not found", ExceptionType.NOT_FOUND));

        var users = userRepository.getAllUsersByAffiliateId(affiliate.id());
        users.forEach(user -> subscribePerson(ownerId, user.id()));
    }

    @Transactional
    public List<AffiliateSubscribeDto> getAffiliateSubscribers(String ownerId) {
        List<SubscribeEntity> subscribeEntities = subscribeRepository.findSubscribers(ownerId);
        List<UserEntity> users = subscribeEntities.stream()
                .map(subscribeEntity -> userRepository.getUserById(subscribeEntity.subscriberId()).orElseThrow())
                .toList();
        return users.stream()
                .map(user -> {
                    if (user.affiliateId().isPresent()) {
                        AffiliateDto affiliate = affiliateService.getAffiliate(user.affiliateId().get())
                                .orElseThrow(() -> new ExceptionInApplication("Affiliate not found", ExceptionType.NOT_FOUND));
                        return new AffiliateSubscribeDto(
                                affiliate.id(),
                                affiliate.name(),
                                affiliate.address()
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional
    public void unsubscribeAffiliate(String ownerId, String affiliateId) {
        var affiliate = affiliateService.getAffiliate(affiliateId)
                .orElseThrow(() -> new ExceptionInApplication("Affiliate not found", ExceptionType.NOT_FOUND));

        var users = userRepository.getAllUsersByAffiliateId(affiliate.id());
        users.forEach(user -> unsubscribePerson(ownerId, user.id()));
    }

    private UserDto toDto(SubscribeEntity subscribeEntity) {
        return userService.getUser(subscribeEntity.subscriberId());
    }

    private UserEntity toEntity(SubscribeEntity subscribeEntity) {
        return userRepository.getUserById(subscribeEntity.subscriberId())
                .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));
    }
}
