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

@DynamoDBTable(tableName = "test-mobilehub-136480124-hack_temp")

public class HackTempDO {
    private String primary;
    private String timeStamp;
    private Double temperature;

    @DynamoDBHashKey(attributeName = "primary")
    @DynamoDBAttribute(attributeName = "primary")
    public String getPrimary() {
        return primary;
    }

    public void setPrimary(final String _primary) {
        this.primary = _primary;
    }
    @DynamoDBRangeKey(attributeName = "timeStamp")
    @DynamoDBAttribute(attributeName = "timeStamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(final String _timeStamp) {
        this.timeStamp = _timeStamp;
    }
    @DynamoDBAttribute(attributeName = "temperature")
    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(final Double _temperature) {
        this.temperature = _temperature;
    }

}
