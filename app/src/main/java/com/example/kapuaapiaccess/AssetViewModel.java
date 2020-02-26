package com.example.kapuaapiaccess;

import android.content.Context;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kapuaapiaccess.asset.BodyAsset;
import com.example.kapuaapiaccess.asset.Channel;

import com.example.kapuaapiaccess.login.Token;
import com.example.kapuaapiaccess.message.Message;

import java.util.HashMap;
import java.util.List;

public class AssetViewModel extends ViewModel {

    private MutableLiveData<Message> messagesLivedata;
    private MutableLiveData<HashMap<String,List<Channel>>> assetsLivedata;
    private DataRepository dataRepository;

    private BodyAsset bodyAsset;


    public void init(Token token, Context context){

        if(messagesLivedata!=null){
            return;
        }

        dataRepository=dataRepository.getInstance(token, context);
        messagesLivedata=dataRepository.getMessage();

    }


    public LiveData<Message> getMessages(){
        return messagesLivedata;
    }


    public LiveData<HashMap<String, List<Channel>>> getAssets(){

        assetsLivedata=dataRepository.getAssets(bodyAsset);

        return assetsLivedata;
    }


    public void refresh(){

        messagesLivedata=dataRepository.getMessage();
        if(bodyAsset!=null) dataRepository.getAssets(bodyAsset);

    }

    public void setBodyAsset(BodyAsset bodyAsset) {
        this.bodyAsset = bodyAsset;
    }


    public void write(BodyAsset bodyAsset) {


        MutableLiveData<HashMap<String, List<Channel>>> assets = dataRepository.write(bodyAsset);
        if (assets != null && assets.getValue()!=null && !assets.getValue().isEmpty()) {
            HashMap<String, List<Channel>> old = assetsLivedata.getValue();

            for (String name : assets.getValue().keySet()) {
                old.put(name, assets.getValue().get(name));
            }
            assetsLivedata.postValue(old);

        }
    }
}
