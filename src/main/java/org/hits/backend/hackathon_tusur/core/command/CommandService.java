package org.hits.backend.hackathon_tusur.core.command;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;
import org.hits.backend.hackathon_tusur.public_interface.command.CreateCommandDto;
import org.hits.backend.hackathon_tusur.public_interface.command.UpdateCommandDto;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackathon_tusur.public_interface.exception.ExceptionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandService {
    private final CommandRepository commandRepository;

    @Transactional
    public String createCommand(CreateCommandDto dto) {
        if (commandRepository.getCommandByName(dto.name()).isPresent()) {
            throw new ExceptionInApplication("Command with name " + dto.name() + " already exists", ExceptionType.ALREADY_EXISTS);
        }

        var entity = new CommandEntity(
                null,
                dto.name(),
                dto.description()
        );
        return commandRepository.createCommand(entity);
    }

    @Transactional
    public void updateCommand(UpdateCommandDto dto) {
        var command = commandRepository.getCommandByName(dto.name())
                .orElseThrow(() -> new ExceptionInApplication("Command with name " + dto.name() + " not found", ExceptionType.NOT_FOUND));

        commandRepository.updateCommand(new CommandEntity(
                command.id(),
                dto.name(),
                dto.description()
        ));
    }

    public List<CommandDto> getCommands(String commandName) {
        return commandRepository.getCommands(commandName)
                .map(this::mapToCommandDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCommand(String commandId) {
        commandRepository.deleteCommand(commandId);
    }

    @Transactional
    public void assignCommandToUser(String commandId, String userId) {
        commandRepository.assignCommandToUser(commandId, userId);
    }

    @Transactional
    public void unassignCommandFromUser(String commandId, String userId) {
        commandRepository.unassignCommandFromUser(commandId, userId);
    }

    public List<CommandDto> getUserCommands(String userId) {
        return commandRepository.getCommandsByUserId(userId)
                .map(this::mapToCommandDto)
                .collect(Collectors.toList());
    }

    private CommandDto mapToCommandDto(CommandEntity entity) {
        return new CommandDto(
                entity.id(),
                entity.name(),
                entity.description()
        );
    }
}
