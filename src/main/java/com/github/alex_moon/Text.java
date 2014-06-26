package com.github.alex_moon;

import java.util.Date;

import org.apache.wicket.util.io.IClusterable;

public class Text implements IClusterable {
    private String textString;
    private Date date = new Date();

    public Text() {
    }

    public Text(Text text) {
        this.textString = text.textString;
        this.date = text.date;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString() {
        return "[Text " + date + "]";
    }
}
