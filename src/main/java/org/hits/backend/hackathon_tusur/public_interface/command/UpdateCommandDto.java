package org.hits.backend.hackathon_tusur.public_interface.command;

public record UpdateCommandDto(
        String id,
        String name,
        String description
) {
}
