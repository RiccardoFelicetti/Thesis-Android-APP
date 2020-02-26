package com.example.kapuaapiaccess.message;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {


    /*String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
*/

    @SerializedName(value="items")
    public List<Item> items;


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }



}
