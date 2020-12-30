package com.example.myhealth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserIDAPIInterface {
    @POST("api/user/login")
    @Headers("Content-Type:application/json")
    Call<UserResponse> getUserIDToken(@Body UserIDInput body);
}
