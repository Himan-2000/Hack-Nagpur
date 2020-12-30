package com.example.myhealthdoctor;

import com.google.gson.annotations.SerializedName;

public class QRModel {
    @SerializedName( "doctorEmail" )
            private String doctorEmail;
    @SerializedName( "contact" )
            private String contact;

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public String getContact() {
        return contact;
    }

    public QRModel(String doctorEmail, String contact) {
        this.doctorEmail = doctorEmail;
        this.contact = contact;
    }
}
