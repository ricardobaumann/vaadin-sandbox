package com.example.application.views.main;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@Route("login")
@PageTitle("Login | Blog")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        add(new H1("Blog"), loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional.ofNullable(event.getLocation())
                .map(Location::getQueryParameters)
                .map(QueryParameters::getParameters)
                .filter(stringListMap -> stringListMap.containsKey("error"))
                .ifPresent(stringListMap -> loginForm.setError(true));
    }
}
