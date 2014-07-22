package com.github.alex_moon.jaws;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class BasicAuthenticationSession extends AuthenticatedWebSession {
    String envUsername = System.getProperty("jaws.username", "admin");
    String envPassword = System.getProperty("jaws.password", "fmY7umcnXN1IxF0lt1wH");
    List<String> watchTerms;

    public BasicAuthenticationSession(Request request) {
        super(request);
        watchTerms = new ArrayList<String>();
    }

    @Override
    public boolean authenticate(String username, String password) {
        return username.equals(envUsername) && password.equals(envPassword);
    }

    public void addWatching(String watchTerm) {
        if (!watchTerms.contains(watchTerm)) {
            watchTerms.add(watchTerm);
            this.info("Now watching " + watchTerm);
        }
    }

    public List<String> getWatching() {
        return watchTerms;
    }

    public void signIn() {
        signIn(true);
    }

    @Override
    public Roles getRoles() {
        return null;
    }
}