package com.example.myhealth;



import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MedicalInterface {
    @POST("api/user/mihir/userDetails")
    @Headers("Content-Type:application/json")
    Call<MedicalResponse> getRecords(@Body MedicalInput body);
}
