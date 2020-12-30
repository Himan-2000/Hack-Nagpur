package com.example.myhealth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DateInterface {
    @POST("api/diary/sendData")
    @Headers("Content-Type:application/json")
    Call<DateResponseModel> getRecords(@Body DateModel body);
}
