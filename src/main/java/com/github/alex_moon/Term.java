package com.github.alex_moon;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Jaws")
public class Term {
    private String termString;
    private int count;

    @DynamoDBHashKey(attributeName = "Term")
    public String getTerm() {
        return termString;
    }

    public void setTerm(String term) {
        this.termString = term;
    }

    @DynamoDBAttribute(attributeName = "Count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString() {
        return termString + ":" + count;
    }
}
