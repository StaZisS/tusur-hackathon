package org.hits.backend.hackathon_tusur.rest.subscribe;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.subscribe.service.SubscribeService;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscriptions")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("/person/{userId}")
    public void subscribePerson(@PathVariable String userId, JwtAuthenticationToken token) {
        subscribeService.subscribePerson(parseUserId(token), userId);
    }

    @GetMapping("/person/all")
    public List<UserSubscribeDto> getPersonSubscribers(JwtAuthenticationToken token) {
        return subscribeService.getAll(parseUserId(token));
    }

    @DeleteMapping("/person/{userId}")
    public void unsubscribePerson(@PathVariable String userId, JwtAuthenticationToken token) {
        subscribeService.unsubscribePerson(parseUserId(token), userId);
    }

    @PostMapping("/command/{commandId}")
    public void subscribeCommand(@PathVariable String commandId, JwtAuthenticationToken token) {
        subscribeService.subscribeCommand(parseUserId(token), commandId);
    }

    @GetMapping("/command/all")
    public List<CommandSubscribeDto> getCommandSubscribers(JwtAuthenticationToken token) {
        return subscribeService.getCommandSubscribers(parseUserId(token));
    }

    @DeleteMapping("/command/{commandId}")
    public void unsubscribeCommand(@PathVariable String commandId, JwtAuthenticationToken token) {
        subscribeService.unsubscribeCommand(parseUserId(token), commandId);
    }

    @PostMapping("/affiliate/{affiliateId}")
    public void subscribeAffiliate(@PathVariable String affiliateId, JwtAuthenticationToken token) {
        subscribeService.subscribeAffiliate(parseUserId(token), affiliateId);
    }

    @GetMapping("/affiliate/all")
    public List<AffiliateSubscribeDto> getAffiliateSubscribers(JwtAuthenticationToken token) {
        return subscribeService.getAffiliateSubscribers(parseUserId(token));
    }

    @DeleteMapping("/affiliate/{affiliateId}")
    public void unsubscribeAffiliate(@PathVariable String affiliateId, JwtAuthenticationToken token) {
        subscribeService.unsubscribeAffiliate(parseUserId(token), affiliateId);
    }

    private String parseUserId(JwtAuthenticationToken token) {
        return token.getTokenAttributes().get("sub").toString();
    }

}
