package com.github.alex_moon.jaws;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class HomePage extends WebPage {
    @Override
    protected void onConfigure() {
        AuthenticatedWebApplication app = (AuthenticatedWebApplication) Application.get();
        // if user is not signed in, redirect her to sign in page
        if (!BasicAuthenticationSession.get().isSignedIn()) {
            app.restartResponseAtSignInPage();
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new WatchForm("watchForm"));

        add(new FeedbackPanel("info"));
        
        List<String> watchTerms = ((BasicAuthenticationSession) getSession()).getWatching();
        
        add(new ListView<String>("watching", watchTerms) {
            public void populateItem(final ListItem<String> item) {
                final String termString = item.getModelObject();
                item.add(new Label("termString", termString));
            }
        });

        add(new Dashboard("dashboard", watchTerms));

        add(new TextForm("textForm"));
    }

    public final class Dashboard extends DataView<Term> {        
        public Dashboard(String id, final List<String> watchTerms) {
            super(id, new ListDataProvider<Term>() {
                @Override
                protected List<Term> getData() {
                    return Term.getPersistedTerms(watchTerms);
                }
            });
        }

        @Override
        protected void populateItem(final Item<Term> item) {
            Term term = item.getModelObject();
            item.add(new Label("termString", term.getTermString()));
            item.add(new Label("first", term.getFirst()));
            item.add(new Label("second", term.getSecond()));
            item.add(new Label("third", term.getThird()));
            item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return (item.getIndex() % 2 == 1) ? "even" : "odd";
                }
            }));
        }
    }

    public final class WatchForm extends Form<ValueMap> {
        public WatchForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextField<String>("watchTerm").setType(String.class));
        }

        @Override
        public final void onSubmit() {
            ValueMap values = getModelObject();

            String watchTerm = (String) values.get("watchTerm");

            ((BasicAuthenticationSession) getSession()).addWatching(watchTerm);

            values.put("watchTerm", "");
        }
    }

    public final class TextForm extends Form<ValueMap> {
        public TextForm(final String id) {
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
            add(new TextArea<String>("textString").setType(String.class));
        }

        @Override
        public final void onSubmit() {
            ValueMap values = getModelObject();

            Text text = new Text();

            UUID uuid = UUID.randomUUID();

            text.setUuid(uuid);
            text.setTextString((String) values.get("textString"));

            new Trim().putText(text);

            values.put("textString", "");
        }
    }
}
