package com.github.alex_moon;

import java.util.Map;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class BasicAuthenticationSession extends AuthenticatedWebSession {
    String envUsername = "admin";
    String envPassword = "fmY7umcnXN1IxF0lt1wH";

    public BasicAuthenticationSession(Request request) {
        super(request);
        Map<String, String> env = System.getenv();
        if (env.containsKey("JAWS_USER")) {
            envUsername = env.get("JAWS_USER");
        }
        if (env.containsKey("JAWS_PASSWORD")) {
            envPassword = env.get("JAWS_PASSWORD");
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        return username.equals(envUsername) && password.equals(envPassword);
    }

    @Override
    public Roles getRoles() {
        return null;
    }
}