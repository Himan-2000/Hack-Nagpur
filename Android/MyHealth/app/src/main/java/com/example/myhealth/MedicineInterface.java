package com.example.myhealth;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface MedicineInterface {
    @GET("order")
    @Headers("Content-Type:application/json")
    Call<MedicineResponse> getConfirmation();
}
