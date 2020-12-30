package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

public class MedicalInput {
    @SerializedName( "contact" )
    private String contact;
    public MedicalInput(String contact){
        this.contact = contact;
    }
}
