package com.example.kapuaapiaccess.asset;

import com.example.kapuaapiaccess.asset.Channel;

import java.util.List;

public class DeviceAsset {

    String name;
    List<Channel>  channels;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Channel> getChannelList() {
        return channels;
    }

    public void setChannelList(List<Channel> channelList) {
        this.channels = channelList;
    }




}
