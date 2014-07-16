package com.github.alex_moon.jaws;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;

import com.amazonaws.services.identitymanagement.model.User;

public class SessionHandler {
    private final int expiryTimeInDays = 365;
    private final String cookieName = "id";
    
    public WebSession createSession(Request request, Response response) {
        BasicAuthenticationSession session = new BasicAuthenticationSession(request);

        Cookie cookie = loadCookie(request);

        if(cookie == null) {
            UUID uuid = UUID.randomUUID();
            saveCookie(response, uuid.toString());
        }
        
        // @todo watch list
        session.setAttribute("lol", "pretty cool");

        return session;
    }

    public Cookie loadCookie(Request request) {
        List<Cookie> cookies = ((WebRequest) request).getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }

        return null;
    }

    public void saveCookie(Response response, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(expiryTimeInDays));
        ((WebResponse)response).addCookie(cookie);
    }

}
