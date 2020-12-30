package com.example.myhealthdoctor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserIDAPIInterface {
    @POST("api/doctor/login")
    @Headers("Content-Type:application/json")
    Call<UserResponse> getUserIDToken(@Body UserIDInput body);
}
