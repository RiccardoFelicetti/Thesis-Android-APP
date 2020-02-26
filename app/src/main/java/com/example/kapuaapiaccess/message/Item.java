package com.example.kapuaapiaccess.message;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName(value="payload")
    public Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }



}
