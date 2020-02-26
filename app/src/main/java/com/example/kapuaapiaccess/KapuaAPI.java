package com.example.kapuaapiaccess;

import com.example.kapuaapiaccess.asset.BodyAsset;
import com.example.kapuaapiaccess.asset.DeviceAsset;
import com.example.kapuaapiaccess.login.Token;
import com.example.kapuaapiaccess.login.User;
import com.example.kapuaapiaccess.message.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface KapuaAPI {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("v1/authentication/user")
    Call<Token> login(@Body User user);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("v1/_/devices/KBsyoTV7Y6U/assets/_read")
    Call<DeviceAsset> read(@Header("Authorization") String tokenHeader, @Body DeviceAsset asset);

    @Headers({ "accept: application/json;charset=UTF-8"})
    @GET("v1/_/data/messages")
    Call<Message> getMessages();

    @Headers({ "accept: application/json;charset=UTF-8"})
    @POST("v1/_/devices/KBsyoTV7Y6U/assets/_read")
    Call<BodyAsset> getAssets(@Body BodyAsset assets);

    @Headers({ "accept: application/json;charset=UTF-8"})
    @POST("v1/_/devices/KBsyoTV7Y6U/assets/_write")
    Call<BodyAsset> writeAssets(@Body BodyAsset assets);


}
