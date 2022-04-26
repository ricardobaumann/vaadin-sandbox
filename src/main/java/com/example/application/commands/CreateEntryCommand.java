package com.example.application.commands;

public class CreateEntryCommand {
    private final String title;
    private final String content;

    public CreateEntryCommand(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
