package com.github.alex_moon;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;

public class Trim {
    // private String url = "http://trim.elasticbeanstalk.com/text/json/";  // @todo config
    private String url = "http://localhost:8080/trim/text/json/";

    public String putText(Text text) {
        try {
            JSONObject json = text.serialise();
            String response = Request.Post(url)
                            .bodyString(json.toString(), ContentType.APPLICATION_JSON)
                            .execute().returnContent()
                            .asString();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
