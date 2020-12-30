package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

public class ReportResponseModel {
    @SerializedName( "_id" )
    private String _id;
    @SerializedName( "title" )
    private String title;
    @SerializedName( "file" )
    private String file;
    @SerializedName( "patient" )
    private String patient;
    @SerializedName( "date" )
    private String date;
    @SerializedName( "__v" )
    private int __v;

    public ReportResponseModel(String _id, String title, String file, String patient, String date, int __v) {
        this._id = _id;
        this.title = title;
        this.file = file;
        this.patient = patient;
        this.date = date;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getFile() {
        return file;
    }

    public String getPatient() {
        return patient;
    }

    public String getDate() {
        return date;
    }

    public int get__v() {
        return __v;
    }


}
