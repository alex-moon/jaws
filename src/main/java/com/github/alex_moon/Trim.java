package com.github.alex_moon;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

public class Trim {
    private String url = "http://trim.elasticbeanstalk.com/text/json/";  // @todo config

    public String putText(Text text) {
        try {
            JSONObject json = text.serialise();
            StringEntity jsonBody = new StringEntity(json.toString());
            String response = Request.Post(url).body(jsonBody).execute().returnContent().asString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
