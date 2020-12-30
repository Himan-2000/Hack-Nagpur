package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserResponse {
    @SerializedName( "token" )
    private String token;
    @SerializedName( "user" )
    User userobj;

    public UserResponse(String token, User userobj) {
        this.token = token;
        this.userobj = userobj;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
    public User getUser() {
        return userobj;
    }

    public class User {

        @SerializedName( "emergencyContact" )
        ArrayList < Object > emergencyContact = new ArrayList < Object > ();
        @SerializedName( "doctors" )
        ArrayList < Object > doctors = new ArrayList < Object > ();
        @SerializedName( "roles" )
        ArrayList < Object > roles = new ArrayList< Object >();
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
        @SerializedName( "password" )
        private String password;
        @SerializedName( "date" )
        private String date;
        @SerializedName( "__v" )
        private float __v;

        public User(ArrayList<Object> emergencyContact, ArrayList<Object> doctors, ArrayList<Object> roles, boolean diary, String _id, String name, String age, String email, String contact, String password, String date, float __v) {
            this.emergencyContact = emergencyContact;
            this.doctors = doctors;
            this.roles = roles;
            this.diary = diary;
            this._id = _id;
            this.name = name;
            this.age = age;
            this.email = email;
            this.contact = contact;
            this.password = password;
            this.date = date;
            this.__v = __v;
        }

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

        public String getPassword() {
            return password;
        }

        public String getDate() {
            return date;
        }

        public float get__v() {
            return __v;
        }

        // Setter Methods

        public void setDiary(boolean diary) {
            this.diary = diary;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void set__v(float __v) {
            this.__v = __v;
        }
    }

}
