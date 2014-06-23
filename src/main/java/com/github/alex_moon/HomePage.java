package com.github.alex_moon;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		DynamoDBMapper mapper = WicketApplication.getMapper();

		Term term = mapper.load(Term.class, "radical");

		if (term == null) {
			add(new Label("killer", "term not found :("));
		} else {
			add(new Label("killer", term.toString()));
		}
    }
}
