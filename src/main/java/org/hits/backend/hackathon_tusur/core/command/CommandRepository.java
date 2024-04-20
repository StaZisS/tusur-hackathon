package org.hits.backend.hackathon_tusur.core.command;

import java.util.Optional;
import java.util.stream.Stream;

public interface CommandRepository {
    String createCommand(CommandEntity entity);
    void updateCommand(CommandEntity entity);
    Stream<CommandEntity> getCommands(String commandName);
    void deleteCommand(String commandId);
    void assignCommandToUser(String commandId, String userId);
    void unassignCommandFromUser(String commandId, String userId);
    Optional<CommandEntity> getCommandByName(String commandName);
    Stream<CommandEntity> getCommandsByUserId(String userId);
}
