package com.example.application.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Backoffice")
@Route(value = "/backoffice")
public class BackofficeView extends VerticalLayout {

    private final TextField title = new TextField("Title2");
    private final TextArea content = new TextArea("Content2");

    public BackofficeView() {
        add(title, content);
    }
}
