package com.example.application;

import com.example.application.commands.CreateEntryCommand;
import com.example.application.domain.entities.BlogEntry;
import com.example.application.domain.entities.UpdateEntryCommand;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BlogService {

    private static final Map<String, BlogEntry> content = new HashMap<>();

    public void create(CreateEntryCommand createEntryCommand) {
        BlogEntry blogEntry = new BlogEntry(
                UUID.randomUUID().toString(),
                createEntryCommand.getTitle(),
                createEntryCommand.getContent()
        );
        content.put(blogEntry.getId(), blogEntry);
    }

    public Collection<BlogEntry> list() {
        return content.values();
    }

    public void delete(BlogEntry blogEntry) {
        content.remove(blogEntry.getId());
    }

    public void update(UpdateEntryCommand updateEntryCommand) {
        CreateEntryCommand entryCommand = updateEntryCommand.getEntryCommand();
        content.put(updateEntryCommand.getId(), new BlogEntry(
                updateEntryCommand.getId(),
                entryCommand.getTitle(),
                entryCommand.getContent()
        ));
    }
}
