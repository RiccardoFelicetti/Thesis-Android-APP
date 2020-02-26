package com.example.kapuaapiaccess;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitLogin {

    String baseUrl= "http://192.168.43.74:8081/";

    Retrofit retrofit;

    public Retrofit retrofitInstance(){

        retrofit  = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }
}
