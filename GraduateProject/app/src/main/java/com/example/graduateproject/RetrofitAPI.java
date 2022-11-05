package com.example.graduateproject;
import com.example.graduateproject.marker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("api/marker")
    Call<List<marker>> getData(@Query("markerId") Integer markerId);
}
