package org.hits.backend.hackathon_tusur.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;

@Component
@RequiredArgsConstructor
public class WebSocketAuthenticationFilter implements ChannelInterceptor {

    @Override
    public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object simpUser = accessor.getMessageHeaders().get("simpUser");

            if (simpUser == null) {
                throw new ExceptionInApplication("User must be authenticated", ExceptionType.UNAUTHORIZED);
            }

            JwtAuthenticationToken userToken =
                    (JwtAuthenticationToken) accessor.getMessageHeaders().get("simpUser");

            Authentication authRequest = new UsernamePasswordAuthenticationToken(userToken, null, null);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authRequest);

            accessor.setUser(new UsernamePasswordAuthenticationToken(userToken, null, null));
        }

        return message;
    }
}
