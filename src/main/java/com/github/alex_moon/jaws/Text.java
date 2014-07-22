package com.github.alex_moon.jaws;

import java.util.UUID;

import org.apache.wicket.util.io.IClusterable;
import org.json.JSONObject;

public class Text implements IClusterable {
    private String textString;
    private UUID uuid = UUID.randomUUID();

    public Text() {
    }

    public Text(Text text) {
        this.textString = text.textString;
        this.uuid = text.uuid;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return "[Text " + uuid + "]";
    }

    public JSONObject serialise() {
        JSONObject result = new JSONObject();
        result.put("text", textString);
        result.put("uuid", uuid.toString());
        return result;
    }
}
