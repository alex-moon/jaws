package com.github.alex_moon;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class WicketApplication extends WebApplication {
    private static DynamoDBMapper mapper;
    private static AmazonDynamoDBClient client;

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    public static AmazonDynamoDBClient getClient() {
        if (client == null) {
            try {
                // Dev uses profile credentials
                AWSCredentials credentials = new ProfileCredentialsProvider()
                        .getCredentials();
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
