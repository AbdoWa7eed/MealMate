package com.iti.mealmate.data.source.remote.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit instance = null;
    private final static String BASE_URL = "www.themealdb.com/api/json/v1/1/";

    private ApiClient(){}


    public static Retrofit getInstance(){
        if(instance == null){
            synchronized (ApiClient.class) {
                instance = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            }
        }

        return  instance;
    }
}
