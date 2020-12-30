package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

public class MedicalResponse {

    @SerializedName( "userPrescriptions" )
    ArrayList< Object > userPrescriptions = new ArrayList < Object > ();
    @SerializedName( "userReports" )
    ArrayList < Object > userReports = new ArrayList < Object > ();
    @SerializedName( "UserObject" )
    User UserObject;
    @SerializedName( "noOfReports" )
    private int noOfReports;
    @SerializedName( "noOfPrescriptions" )
    private int noOfPrescriptions;

    public MedicalResponse(ArrayList<Object> userPrescriptions, ArrayList<Object> userReports, User userObject, int noOfReports, int noOfPrescriptions) {
        this.userPrescriptions = userPrescriptions;
        this.userReports = userReports;
        UserObject = userObject;
        this.noOfReports = noOfReports;
        this.noOfPrescriptions = noOfPrescriptions;
    }
    // Getter Methods

        public User getUser() {
            return UserObject;
        }

        public int getNoOfReports() {
            return noOfReports;
        }

        public int getNoOfPrescriptions() {
            return noOfPrescriptions;
        }

        // Setter Methods

    public class User {
        @SerializedName( "emergencyContact" )
        ArrayList<Object> emergencyContact = new ArrayList<Object>();
        @SerializedName( "doctors" )
        ArrayList<Object> doctors = new ArrayList<Object>();
        @SerializedName( "roles" )
        ArrayList<Object> roles = new ArrayList<Object>();
        @SerializedName( "diary" )
        private boolean diary;
        @SerializedName( "_id" )
        private String _id;
        @SerializedName( "name" )
        private String name;
        @SerializedName( "age" )
        private String age;
        @SerializedName( "email" )
        private String email;
        @SerializedName( "contact" )
        private String contact;
        @SerializedName( "date" )
        private String date;
        @SerializedName( "__v" )
        private float __v;
        @SerializedName( "currentDoctor" )
        private String currentDoctor;


        // Getter Methods

        public boolean getDiary() {
            return diary;
        }

        public String get_id() {
            return _id;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public String getEmail() {
            return email;
        }

        public String getContact() {
            return contact;
        }

        public String getDate() {
            return date;
        }

        public float get__v() {
            return __v;
        }

        public String getCurrentDoctor() {
            return currentDoctor;
        }

        // Setter Methods

        public User(ArrayList<Object> emergencyContact, ArrayList<Object> doctors, ArrayList<Object> roles, boolean diary, String _id, String name, String age, String email, String contact, String date, float __v, String currentDoctor) {
            this.emergencyContact = emergencyContact;
            this.doctors = doctors;
            this.roles = roles;
            this.diary = diary;
            this._id = _id;
            this.name = name;
            this.age = age;
            this.email = email;
            this.contact = contact;
            this.date = date;
            this.__v = __v;
            this.currentDoctor = currentDoctor;
        }

    }

}
