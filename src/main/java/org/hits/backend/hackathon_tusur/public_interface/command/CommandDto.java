package org.hits.backend.hackathon_tusur.public_interface.command;

public record CommandDto(
        String id,
        String name,
        String description
) {
}
