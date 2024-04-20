package org.hits.backend.hackathon_tusur.core.command;

import com.example.hackathon.public_.tables.records.CommandRecord;
import org.jooq.RecordMapper;

public class CommandEntityMapper implements RecordMapper<CommandRecord, CommandEntity> {
    @Override
    public CommandEntity map(CommandRecord commandRecord) {
        return new CommandEntity(
                commandRecord.getId(),
                commandRecord.getName(),
                commandRecord.getDescription()
        );
    }
}
