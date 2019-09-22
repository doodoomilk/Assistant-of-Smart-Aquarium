package com.example.cloudfinal;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "test-mobilehub-136480124-test_db")

public class TestDbDO {
    private String userId;
    private Double income;
    private Double house;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(final String _userId) {
        this.userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "income")
    @DynamoDBAttribute(attributeName = "income")
    public Double getIncome() {
        return income;
    }

    public void setIncome(final Double _income) {
        this.income = _income;
    }
    @DynamoDBAttribute(attributeName = "house")
    public Double getHouse() {
        return house;
    }

    public void setHouse(final Double _house) {
        this.house = _house;
    }

}
