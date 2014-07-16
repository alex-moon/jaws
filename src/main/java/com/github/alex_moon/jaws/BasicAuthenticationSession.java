package com.github.alex_moon.jaws;

import java.util.Map;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class BasicAuthenticationSession extends AuthenticatedWebSession {
    String envUsername = System.getProperty("jaws.username", "admin");
    String envPassword = System.getProperty("jaws.password", "fmY7umcnXN1IxF0lt1wH");

    public BasicAuthenticationSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return username.equals(envUsername) && password.equals(envPassword);
    }
    
    public void signIn() {
        signIn(true);
    }

    @Override
    public Roles getRoles() {
        return null;
    }
}