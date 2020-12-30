package com.example.myhealth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ReportAPIInterface {
    @POST("api/user/userReports")
    @Headers("Content-Type:application/json")
    Call<ReportResponseModel> getConfirmation(@Body ReportModel body);
}
