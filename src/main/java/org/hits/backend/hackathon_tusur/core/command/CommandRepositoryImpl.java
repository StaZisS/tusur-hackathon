package org.hits.backend.hackathon_tusur.core.command;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

import static com.example.hackathon.public_.tables.Command.COMMAND;
import static com.example.hackathon.public_.tables.UserCommand.USER_COMMAND;

@Repository
@RequiredArgsConstructor
public class CommandRepositoryImpl implements CommandRepository {
    private static final CommandEntityMapper COMMAND_ENTITY_MAPPER = new CommandEntityMapper();
    private static final UserCommandEntityMapper USER_COMMAND_ENTITY_MAPPER = new UserCommandEntityMapper();

    private final DSLContext create;

    @Override
    public String createCommand(CommandEntity entity) {
        return create.insertInto(COMMAND)
                .set(COMMAND.ID, entity.id())
                .set(COMMAND.NAME, entity.name())
                .set(COMMAND.DESCRIPTION, entity.description())
                .returning(COMMAND.ID)
                .fetchOne(COMMAND.ID);
    }

    @Override
    public void updateCommand(CommandEntity entity) {
        create.update(COMMAND)
                .set(COMMAND.NAME, entity.name())
                .set(COMMAND.DESCRIPTION, entity.description())
                .where(COMMAND.ID.eq(entity.id()))
                .execute();
    }

    @Override
    public Stream<CommandEntity> getCommands(String commandName) {
        return create.selectFrom(COMMAND)
                .where(COMMAND.NAME.contains(commandName))
                .fetchStream()
                .map(COMMAND_ENTITY_MAPPER::map);
    }

    @Override
    public void deleteCommand(String commandId) {
        create.deleteFrom(COMMAND)
                .where(COMMAND.ID.eq(commandId))
                .execute();
    }

    @Override
    public void assignCommandToUser(String commandId, String userId) {
        create.insertInto(USER_COMMAND)
                .set(USER_COMMAND.COMMAND_ID, commandId)
                .set(USER_COMMAND.USER_ID, userId)
                .execute();
    }

    @Override
    public void unassignCommandFromUser(String commandId, String userId) {
        create.deleteFrom(USER_COMMAND)
                .where(USER_COMMAND.COMMAND_ID.eq(commandId))
                .and(USER_COMMAND.USER_ID.eq(userId))
                .execute();
    }

    @Override
    public Optional<CommandEntity> getCommandByName(String commandName) {
        Condition condition = DSL.trueCondition();
        if (!commandName.isBlank()) {
            condition = condition.and(COMMAND.NAME.eq(commandName));
        }
        return create.selectFrom(COMMAND)
                .where(condition)
                .fetchOptional(COMMAND_ENTITY_MAPPER);
    }

    @Override
    public Stream<CommandEntity> getCommandsByUserId(String userId) {
        return create.select(COMMAND.fields())
                .from(COMMAND)
                .join(USER_COMMAND)
                .on(COMMAND.ID.eq(USER_COMMAND.COMMAND_ID))
                .where(USER_COMMAND.USER_ID.eq(userId))
                .fetchStream()
                .map(record -> COMMAND_ENTITY_MAPPER.map(record.into(COMMAND)));
    }
}
