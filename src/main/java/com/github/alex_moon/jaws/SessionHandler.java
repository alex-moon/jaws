package com.github.alex_moon.jaws;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

public class SessionHandler {
    private final int expiryTimeInDays = 365;
    private final String cookieName = "cookie";
    private List<BasicAuthenticationSession> sessions = new ArrayList<BasicAuthenticationSession>();

    public BasicAuthenticationSession getSessionFromCookie(Request request, Response response) {
        Cookie cookie = loadCookie(request);
        if (cookie != null) {
            for (BasicAuthenticationSession session : sessions) {
                if (session.getAttribute(cookieName) == cookie.getValue()) {
                    session.signIn();
                    return session;
                }
            }
        }
        return createSession(request, response);
    }

    public BasicAuthenticationSession createSession(Request request, Response response) {
        UUID uuid = UUID.randomUUID();
        saveCookie(response, uuid.toString());

        BasicAuthenticationSession session = new BasicAuthenticationSession(request);
        session.setAttribute(cookieName, uuid);
        sessions.add(session);
        return session;
    }

    public Cookie loadCookie(Request request) {
        List<Cookie> cookies = ((WebRequest) request).getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }

        return null;
    }

    public void saveCookie(Response response, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(expiryTimeInDays));
        ((WebResponse) response).addCookie(cookie);
    }
}
