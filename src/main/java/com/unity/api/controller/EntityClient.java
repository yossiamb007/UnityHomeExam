package com.unity.api.controller;

import com.unity.api.service.EntityService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EntityClient {
    private static final String BASE_URL = "http://localhost:3000/";
    private static EntityService apiService;

    public static EntityService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    // Add ScalarsConverterFactory FIRST (for plain text / HTML)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    // Then GsonConverterFactory (for JSON)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(EntityService.class);
        }
        return apiService;
    }
}
