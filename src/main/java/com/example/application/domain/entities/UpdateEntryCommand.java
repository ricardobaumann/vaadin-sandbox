package com.example.application.domain.entities;

import com.example.application.commands.CreateEntryCommand;

public class UpdateEntryCommand {
    private final String id;
    private final CreateEntryCommand entryCommand;

    public UpdateEntryCommand(String id, CreateEntryCommand entryCommand) {
        this.id = id;
        this.entryCommand = entryCommand;
    }

    public String getId() {
        return id;
    }

    public CreateEntryCommand getEntryCommand() {
        return entryCommand;
    }
}
