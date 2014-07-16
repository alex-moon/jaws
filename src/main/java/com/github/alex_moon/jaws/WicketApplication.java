package com.github.alex_moon.jaws;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class WicketApplication extends AuthenticatedWebApplication {
    private static DynamoDBMapper mapper;
    private static AmazonDynamoDBClient client;
    private static SessionHandler sessionHandler;

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }
    
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass(){
        return BasicAuthenticationSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignInPage.class;
    }
    
    public static SessionHandler getSessionHandler() {
        if (sessionHandler == null) {
            sessionHandler = new SessionHandler();
        }

        return sessionHandler;
    }

    public static AmazonDynamoDBClient getClient() {
        if (client == null) {
            try {
                // Dev uses profile credentials
                AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
                client = new AmazonDynamoDBClient(credentials);
            } catch (IllegalArgumentException e) {
                // Live uses EC2 service role
                client = new AmazonDynamoDBClient();
            }

            Region ireland = Region.getRegion(Regions.EU_WEST_1);
            client.setRegion(ireland);
        }

        return client;
    }

    public static DynamoDBMapper getMapper() {
        if (mapper == null) {
            mapper = new DynamoDBMapper(getClient());
        }

        return mapper;
    }
}
