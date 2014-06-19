package com.github.alex_moon;


public class Jaws extends WebPage {
	private static final long serialVersionUID = 1L;

	public Jaws(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
    }
}
