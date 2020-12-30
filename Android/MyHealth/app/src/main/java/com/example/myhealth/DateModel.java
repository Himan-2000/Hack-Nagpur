package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

public class DateModel {
    @SerializedName( "startDate" )
            private String startDate;
    @SerializedName( "endDate" )
    private String endDate;
    @SerializedName( "userId" )
    private String userId;

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getUserId() {
        return userId;
    }

    public DateModel(String startDate, String endDate, String userId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }
}
