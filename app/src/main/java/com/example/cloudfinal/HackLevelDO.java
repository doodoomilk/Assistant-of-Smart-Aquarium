package com.example.cloudfinal;

/*
從dynamoDB取出水位資料
存入特定的變數
*/

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "test-mobilehub-136480124-hack_level")

public class HackLevelDO {
    private String primary;
    private String timeStamp;
    private Double level;

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
    @DynamoDBAttribute(attributeName = "level")
    public Double getLevel() {
        return level;
    }

    public void setLevel(final Double _level) {
        this.level = _level;
    }

}
