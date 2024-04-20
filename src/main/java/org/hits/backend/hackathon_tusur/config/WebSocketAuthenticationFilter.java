package org.hits.backend.hackathon_tusur.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WebSocketAuthenticationFilter implements ChannelInterceptor {
    private static final String BEARER_PREFIX = "Authorization";

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Optional.ofNullable(accessor.getNativeHeader("Authorization")).ifPresent(ah -> {
                        System.out.println(ah);
                    });
        }
        /*StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        StompHeaderAccessor accessor1 = StompHeaderAccessor.wrap(message);
        Map<String, List<String>> headers = accessor1.toNativeHeaderMap();


        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object simpUser = accessor.getMessageHeaders().get("simpUser");
            final String bearer =  accessor.getMessageHeaders().toString();
            System.out.println(bearer);
        }*/


        /*StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object simpUser = accessor.getMessageHeaders().get("simpUser");

            if (simpUser == null || !(simpUser instanceof UsernamePasswordAuthenticationToken)) {
                throw new ExceptionInApplication("User not authenticated", ExceptionType.UNAUTHORIZED);
            }

            UsernamePasswordAuthenticationToken userToken =
                    (UsernamePasswordAuthenticationToken) accessor.getMessageHeaders().get("simpUser");

            if (userToken == null || !(userToken.getPrincipal() instanceof UserEntity)) {
                throw new ExceptionInApplication("User not authenticated", ExceptionType.UNAUTHORIZED);
            }

            var userId = ((UserEntity) userToken.getPrincipal()).id();

            var userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new ExceptionInApplication("User not found", ExceptionType.NOT_FOUND));

            final UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                    userEntity,
                    null,
                    userRepository.findAuthoritiesByUserId(userEntity.id())
                            .stream()
                            .map(UserAuthoritiesEntity::authority)
                            .map(SimpleGrantedAuthority::new)
                            .toList()
            );

            accessor.setUser(user);
        }*/

        return message;
    }
}
