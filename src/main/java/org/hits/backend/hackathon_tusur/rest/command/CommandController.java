package org.hits.backend.hackathon_tusur.rest.command;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackathon_tusur.core.command.CommandService;
import org.hits.backend.hackathon_tusur.public_interface.command.CommandDto;
import org.hits.backend.hackathon_tusur.rest.admin.CommandResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/command")
@Tag(name = "Command", description = "Test controller for working with command")
public class CommandController {
    private final CommandService commandService;

    @GetMapping
    public List<CommandResponse> getCommands(@RequestParam("command_name") String commandName) {
        var response = commandService.getCommands(commandName);
        return response.stream()
                .map(this::mapToCommandResponse)
                .toList();
    }

    private CommandResponse mapToCommandResponse(CommandDto dto) {
        return new CommandResponse(
                dto.id(),
                dto.name(),
                dto.description()
        );
    }
}
