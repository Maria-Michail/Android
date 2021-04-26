package com.example.navigation.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static NameAPI nameApi;

    public static NameAPI getNameApi() {
        if (nameApi == null) {
            nameApi = new Retrofit.Builder()
                    .baseUrl("https://api.abalin.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(NameAPI.class);
        }
        return nameApi;
    }
}
