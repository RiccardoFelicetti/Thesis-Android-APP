package com.example.kapuaapiaccess;

import com.example.kapuaapiaccess.login.Token;
import com.example.kapuaapiaccess.login.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    String baseUrl= "http://192.168.43.74:8081/";


    User user;
    Retrofit retrofit;
    Retrofit retrofitString;
    final Token token;//= new Token("");



    public RetrofitService(Token token){

        this.token=token;

    }


    public User getUser() {
        return user;
    }


    public Retrofit retrofitInstance(){


        retrofit  = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;


    }


    public KapuaAPI retrofitStringInstance(){


        System.out.println("CONTROLLER: "+token.getTokenId());
        HttpLoggingInterceptor loggingInterceptor= new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token.getTokenId())
                        .build();
                return chain.proceed(newRequest);
            }
        }).addInterceptor(loggingInterceptor).build();//.build();


        retrofitString  = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofitString.create(KapuaAPI.class);


    }

}
