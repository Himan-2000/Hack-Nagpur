package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

public class MedicineResponse {
    @SerializedName( "success" )
    public boolean success;
    @SerializedName( "Message" )
    public String message;

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public MedicineResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
