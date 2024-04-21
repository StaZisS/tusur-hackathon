package org.hits.backend.hackathon_tusur.rest.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record MessageDto(
        @JsonProperty("message_id")
        UUID messageId,

        @JsonProperty("chat_room_id")
        String chatRoomId,

        @JsonProperty("sender_id")
        String senderId,

        String content,

        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        @JsonProperty("is_notification")
        boolean isNotification,

        @JsonProperty("full_name")
        String fullName,

        @JsonProperty("photo_url")
        String photoUrl
) {
}
