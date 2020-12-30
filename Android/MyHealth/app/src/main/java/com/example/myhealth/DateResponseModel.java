package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DateResponseModel {

    @SerializedName( "clientData" )
    ClientData clientData;
    @SerializedName( "Specialization" )
    ArrayList<Object> Specialization = new ArrayList<Object>();
    @SerializedName( "roles" )
    ArrayList<Object> roles = new ArrayList<Object>();
    @SerializedName( "_id" )
    private String _id;
    @SerializedName( "name" )
    private String name;
    @SerializedName( "email" )
    private String email;
    @SerializedName( "password" )
    private String password;
    @SerializedName( "date" )
    private String date;
    @SerializedName( "__v" )
    private String __v;

    public ClientData getClientData() {
        return clientData;
    }

    public ArrayList<Object> getSpecialization() {
        return Specialization;
    }

    public ArrayList<Object> getRoles() {
        return roles;
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDate() {
        return date;
    }

    public String get__v() {
        return __v;
    }

    public DateResponseModel(ClientData clientData, ArrayList<Object> specialization, ArrayList<Object> roles, String _id, String name, String email, String password, String date, String __v) {
        this.clientData = clientData;
        Specialization = specialization;
        this.roles = roles;
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.date = date;
        this.__v = __v;
    }


    public class ClientData{
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

        public ClientData(String startDate, String endDate, String userId) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.userId = userId;
        }
    }

}
