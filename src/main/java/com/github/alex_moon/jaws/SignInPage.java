package com.github.alex_moon.jaws;

import java.util.UUID;

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

        StatelessForm form = new StatelessForm("signinForm") {
            @Override
            protected void onSubmit() {
                if(Strings.isEmpty(username)) {
                    return;
                }

                boolean authResult = AuthenticatedWebSession.get().signIn(username, password);

                //if authentication succeeds set the cookie and redirect user to the requested page
                if(authResult) {
                    new SessionHandler().createSession(getRequest(), getResponse());
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