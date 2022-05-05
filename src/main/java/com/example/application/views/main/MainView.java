package com.example.application.views.main;

import com.example.application.BlogService;
import com.example.application.commands.CreateEntryCommand;
import com.example.application.domain.entities.BlogEntry;
import com.example.application.domain.entities.UpdateEntryCommand;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.Optional;

@PageTitle("Blog")
@Route(value = "")
public class MainView extends VerticalLayout implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final Grid<BlogEntry> grid = new Grid<>(BlogEntry.class, false);
    private final TextField title = new TextField("Title");
    private final TextArea content = new TextArea("Content");
    private BlogEntry editBlogEntry;

    private void render() {

        MenuBar menuBar = new MenuBar();
        ComponentEventListener<ClickEvent<MenuItem>> listener =
                event -> getUI().ifPresent(ui -> ui.getPage().setLocation("/backoffice"));

        menuBar.addItem("View", listener);
        menuBar.addItem("Edit", listener);


        MenuItem share = menuBar.addItem("Share");
        SubMenu shareSubMenu = share.getSubMenu();
        MenuItem onSocialMedia = shareSubMenu.addItem("On social media");
        SubMenu socialMediaSubMenu = onSocialMedia.getSubMenu();
        socialMediaSubMenu.addItem("Facebook", listener);
        socialMediaSubMenu.addItem("Twitter", listener);
        socialMediaSubMenu.addItem("Instagram", listener);
        shareSubMenu.addItem("By email", listener);
        shareSubMenu.addItem("Get Link", listener);

        MenuItem move = menuBar.addItem("Move");
        SubMenu moveSubMenu = move.getSubMenu();
        moveSubMenu.addItem("To folder", listener);
        moveSubMenu.addItem("To trash", listener);

        menuBar.addItem("Duplicate", listener);

        menuBar.addItem("Logoff", (ComponentEventListener<ClickEvent<MenuItem>>) event -> {
            applicationContext.getBean(SecurityService.class)
                    .logout();
        });

        add(menuBar);

        BlogService blogService = applicationContext.getBean(BlogService.class);
        setMargin(true);
        setWidth("100%");

        title.setWidth("25%");
        title.setMaxLength(100);

        content.setMaxLength(200);
        content.setWidth("25%");

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {

            Optional.ofNullable(editBlogEntry)
                    .ifPresentOrElse(blogEntry -> {
                        blogService.update(new UpdateEntryCommand(blogEntry.getId(),
                                new CreateEntryCommand(title.getValue(), content.getValue())));

                    }, () -> {
                        blogService.create(
                                new CreateEntryCommand(title.getValue(), content.getValue())
                        );
                    });

            Notification.show("Entry persisted successfully");
            title.setValue("");
            content.setValue("");
            grid.setItems(blogService.list());
            title.focus();
            editBlogEntry = null;
        });
        add(title, content, saveButton);

        grid.addColumn(BlogEntry::getTitle).setHeader("Title").setWidth("20%");
        grid.addColumn(BlogEntry::getContent).setHeader("Content").setWidth("60%");
        grid.addComponentColumn(this::buildDeleteButton).setWidth("10%");
        grid.addComponentColumn(this::buildEditButton).setWidth("10%");
        grid.setWidth("80%");

        GridContextMenu<BlogEntry> contextMenu = grid.addContextMenu();
        contextMenu.addItem("Delete",
                event -> Optional.ofNullable(event.getItem())
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .ifPresent(this::delete));

        Collection<BlogEntry> blogEntries = blogService.list();
        grid.setItems(blogEntries);
        add(grid);
    }

    private Button buildEditButton(BlogEntry blogEntry) {
        Button button = new Button("Edit");
        button.addClickListener(buttonClickEvent -> edit(blogEntry));
        return button;
    }

    private void edit(BlogEntry blogEntry) {
        editBlogEntry = blogEntry;
        title.setValue(blogEntry.getTitle());
        content.setValue(blogEntry.getContent());
    }

    private Button buildDeleteButton(BlogEntry blogEntry) {
        Button button = new Button("Delete");
        button.addClickListener(buttonClickEvent -> delete(blogEntry));
        return button;
    }

    private void delete(BlogEntry blogEntry) {

        ConfirmDialog confirmDialog = new ConfirmDialog("Delete blog entry",
                "Do you want to delete this blog entry?",
                "Delete",
                event -> {
                    BlogService blogService = applicationContext.getBean(BlogService.class);
                    blogService.delete(blogEntry);
                    grid.setItems(blogService.list());
                });
        confirmDialog.setCancelable(true);
        confirmDialog.open();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        render();
    }
}
