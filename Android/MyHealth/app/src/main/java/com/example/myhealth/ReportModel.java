package com.example.myhealth;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class ReportModel {
    @SerializedName( "title" )
            private String title;
    @SerializedName( "file" )
            private String file;
    @SerializedName( "contact" )
            private String contact;

    public ReportModel(String title, String file, String contact) {
        this.title = title;
        this.file = file;
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }

    public String getFile() {
        return file;
    }

    public String getContact() {
        return contact;
    }
}
