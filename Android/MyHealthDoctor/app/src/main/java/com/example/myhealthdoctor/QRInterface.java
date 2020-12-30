package com.example.myhealthdoctor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface QRInterface {
    @POST("api/user/qrauth")
    @Headers("Content-Type:application/json")
    Call<QRResponseModel> getConfirm(@Body QRModel body);
}
