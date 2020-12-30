package com.example.myhealth;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserAPIInterface
{
    @POST("api/user/register")
    @Headers("Content-Type:application/json")
    Call<UserResponse> getUserToken(@Body UserInput body);
}
