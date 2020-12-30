package com.example.myhealth;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserInput
{
    @SerializedName( "name" )
    private String name;
    @SerializedName( "age" )
    private String age;
    @SerializedName( "email" )
    private String email;
    @SerializedName( "password" )
    private String password;
    @SerializedName( "contact" )
    private String contact;
    @SerializedName( "emergencyContact" )
    ArrayList< String > emergencyContact = new ArrayList < String > ();

    public UserInput(String name, String age, String email, String password,String contact,ArrayList< String > emergencyContact) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.contact =contact;
        this.emergencyContact = emergencyContact;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return contact;
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

    public String getPassword() {
        return password;
    }
}
