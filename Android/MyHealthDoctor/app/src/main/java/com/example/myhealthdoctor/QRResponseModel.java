package com.example.myhealthdoctor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QRResponseModel {

    @SerializedName( "updatedUser" )
            UpdatedUser updatedUser;

    public UpdatedUser getUpdatedUser() {
        return updatedUser;
    }

    public QRResponseModel(UpdatedUser updatedUser) {
        this.updatedUser = updatedUser;
    }

    public class UpdatedUser{
        @SerializedName( "emergencyContact" )
        ArrayList<Object> emergencyContact = new ArrayList<Object>();
        @SerializedName( "doctors" )
        ArrayList<Object> doctors = new ArrayList<Object>();
        @SerializedName( "roles" )
        ArrayList<Object> roles = new ArrayList<Object>();
        @SerializedName( "diary" )
        public boolean diary;
        @SerializedName( "_id" )
        public String _id;
        @SerializedName( "name" )
        public String name;
        @SerializedName( "age" )
        public String age;
        @SerializedName( "email" )
        public String email;
        @SerializedName( "contact" )
        public String contact;
        @SerializedName( "date" )
        public String date;
        @SerializedName( "__v" )
        public String __v;
        @SerializedName( "currentDoctor" )
        public String currentDoctor;

        public ArrayList<Object> getEmergencyContact() {
            return emergencyContact;
        }

        public ArrayList<Object> getDoctors() {
            return doctors;
        }

        public ArrayList<Object> getRoles() {
            return roles;
        }

        public boolean isDiary() {
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

        public String get__v() {
            return __v;
        }

        public String getCurrentDoctor() {
            return currentDoctor;
        }

        public UpdatedUser(ArrayList<Object> emergencyContact, ArrayList<Object> doctors, ArrayList<Object> roles, boolean diary, String _id, String name, String age, String email, String contact, String date, String __v, String currentDoctor) {
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
