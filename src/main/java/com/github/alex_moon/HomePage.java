package com.github.alex_moon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class HomePage extends WebPage {
    private static final List<Text> textList = Collections.synchronizedList(new ArrayList<Text>());

    public HomePage(final PageParameters parameters) {
        super(parameters);

        DynamoDBMapper mapper = WicketApplication.getMapper();

        /*
         * Term term = mapper.load(Term.class, "radical");
         * 
         * if (term == null) { add(new Label("killer", "term not found :(")); }
         * else { add(new Label("killer", term.toString())); }
         */
        add(new Label("killer", "db disabled"));

        add(new TextForm("TextForm"));

        add(new PropertyListView<Text>("Texts", textList) {
            @Override
            public void populateItem(final ListItem<Text> listItem) {
                listItem.add(new Label("date"));
                listItem.add(new MultiLineLabel("textString"));
            }
        }).setVersioned(false);
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
            textList.add(0, text);

            new Trim().putText(text);

            values.put("textString", "");
        }
    }
}
