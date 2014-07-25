package com.github.alex_moon.jaws;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.util.io.IClusterable;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

public class Term implements IClusterable {
    private String termString;
    private Correlation first, second, third;

    private static final String tableName = "Jaws";
    private static final String id = "Term";

    public static List<Term> getPersistedTerms(List<String> termStrings) {
        List<Term> result = new LinkedList<Term>();
        Map<String, AttributeValue> persistedTerm = null;
        for (String termString : termStrings) {
            persistedTerm = fetchPersistedTerm(termString);
            result.add(new Term(termString, persistedTerm));
        }
        return result;
    }

    public Term(String termString, Map<String, AttributeValue> persistedTerm) {
        this.termString = termString;
        for (String key : persistedTerm.keySet()) {
            AttributeValue persistedTermValue = persistedTerm.get(key);
            if (persistedTermValue != null && persistedTermValue.getN() != null) {
                addCorrelation(key, Double.parseDouble(persistedTermValue.getN()));
            }
        }
    }

    public String getTermString() {
        return termString;
    }

    public String getFirst() {
        if (first == null) {
            return "";
        }
        return first.toString();
    }

    public String getSecond() {
        if (second == null) {
            return "";
        }
        return second.toString();
    }

    public String getThird() {
        if (third == null) {
            return "";
        }
        return third.toString();
    }

    private static Map<String, AttributeValue> fetchPersistedTerm(String termString) {
        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put(id, new AttributeValue().withS(termString));
        GetItemRequest getItemRequest = new GetItemRequest().withTableName(tableName).withKey(key);
        GetItemResult result = WicketApplication.getClient().getItem(getItemRequest);
        return result.getItem();
    }

    // has to be public to serialise Term :
    // @todo replace with firstTermString, firstCoefficient etc. on Term
    // @todo map and persist this way instead of long rows
    public class Correlation implements Serializable {
        public String termString;
        public Double coefficient;

        public Correlation(String termString, Double coefficient) {
            this.termString = termString;
            this.coefficient = coefficient;
        }

        public String toString() {
            return this.termString + ":" + String.format("%.3f", this.coefficient);
        }
    }

    public void addCorrelation(String termString, Double coefficient) {
        if (first == null || coefficient > first.coefficient) {
            third = second;
            second = first;
            first = new Correlation(termString, coefficient);
            return;
        }
        if (second == null || coefficient > second.coefficient) {
            third = second;
            second = new Correlation(termString, coefficient);
            return;
        }
        if (third == null || coefficient > third.coefficient) {
            third = new Correlation(termString, coefficient);
            return;
        }
    }
}
