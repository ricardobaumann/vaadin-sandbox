package com.example.application.domain.entities;

public class BlogEntry {
    private final String id;
    private final String title;
    private final String content;

    public BlogEntry(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
