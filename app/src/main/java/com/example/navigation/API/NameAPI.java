package com.example.navigation.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NameAPI {
    @GET("namedays")
    Call<NameResponse> getName(@Query("country") String country, @Query("month") String month, @Query("day") String day);
}
