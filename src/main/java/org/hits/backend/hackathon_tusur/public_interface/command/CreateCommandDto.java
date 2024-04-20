package org.hits.backend.hackathon_tusur.public_interface.command;

public record CreateCommandDto(
        String name,
        String description
) {
}
