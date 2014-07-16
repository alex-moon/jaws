package com.github.alex_moon.jaws;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

public class SignInPage extends WebPage {
   private String username;
   private String password;

   @Override
   protected void onInitialize() {
        super.onInitialize();
        
        BasicAuthenticationSession session = WicketApplication.getSessionHandler().getSession(getRequest(), getResponse());
        if (session.isSignedIn()) {
            continueToOriginalDestination();
        }

        StatelessForm form = new StatelessForm("signinForm") {
            @Override
            protected void onSubmit() {
                if(Strings.isEmpty(username)) {
                    return;
                }

                boolean authResult = AuthenticatedWebSession.get().signIn(username, password);

                //if authentication succeeds set the cookie and redirect user to the requested page
                if(authResult) {
                    WicketApplication.getSessionHandler().createSession(getRequest(), getResponse());
                    continueToOriginalDestination();
                }
            }
        };
        
        
        form.setDefaultModel(new CompoundPropertyModel(this));
        
        form.add(new TextField("username"));
        form.add(new PasswordTextField("password"));

        add(form); 
    } 
}