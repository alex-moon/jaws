package com.github.alex_moon;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class WicketApplication extends WebApplication
{
	private static DynamoDBMapper mapper;
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();

		// add your configuration here
	}

	public static DynamoDBMapper getMapper() {
		if (mapper == null) {
			AWSCredentialsProvider credentials = new ProfileCredentialsProvider();
			AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
			Region ireland = Region.getRegion(Regions.EU_WEST_1);
			client.setRegion(ireland);

			mapper = new DynamoDBMapper(client);
		}

		return mapper;
	}
}
