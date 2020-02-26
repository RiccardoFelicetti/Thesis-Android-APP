package com.example.kapuaapiaccess;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.kapuaapiaccess.asset.BodyAsset;
import com.example.kapuaapiaccess.asset.Channel;
import com.example.kapuaapiaccess.asset.DeviceAsset;
import com.example.kapuaapiaccess.login.Token;
import com.example.kapuaapiaccess.message.Message;
import com.example.kapuaapiaccess.message.Metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;

public class DataRepository {

    private static DataRepository dataRepository;
    private KapuaAPI clientAPI;
    private Context context;

    public static DataRepository getInstance(Token token, Context context){
        if (dataRepository==null)
            dataRepository=new DataRepository(token, context);
        return dataRepository;
    }


    public DataRepository(Token token, Context context){
        RetrofitService retrofitService = new RetrofitService(token);
        clientAPI= retrofitService.retrofitStringInstance();
        this.context=context;
    }


    public MutableLiveData<HashMap<String,List<Channel>>> write(BodyAsset bodyAsset){

        MutableLiveData<HashMap<String,List<Channel>>> assets= new MutableLiveData<>();
        Call<BodyAsset> callAsset = clientAPI.writeAssets(bodyAsset);

        callAsset.enqueue(new Callback<BodyAsset>() {
            @Override
            public void onResponse(Call<BodyAsset> call, Response<BodyAsset> response) {


                System.out.println("RESPONSE ASSETS API");

                System.out.println(response.code());
                BodyAsset assetList = response.body();
                System.out.println(response.message());

                if(assetList!=null) {

                    assets.setValue(getHashMap(assetList));
                    for(int i=0; i<assetList.getDeviceAsset().size(); i++) {

                        System.out.println("----- Asset name: "+assetList.getDeviceAsset().get(i).getName()+ " -----");
                        for(Channel channel:assetList.getDeviceAsset().get(i).getChannelList())
                            System.out.println(channel.getName()+": " + channel.getValue());
                    }

                }else{

                    Toast.makeText(context, "Error on get assets: "+ response.message(), Toast.LENGTH_SHORT).show();
                    System.out.println("asset list empty");

                }
            }

            @Override
            public void onFailure(Call<BodyAsset> call, Throwable t) {

                Toast.makeText(context, "Something went wrong on obtain assets...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        return assets;
    }

    public MutableLiveData<HashMap<String,List<Channel>>> getAssets(BodyAsset bodyAsset){

        MutableLiveData<HashMap<String,List<Channel>>> assets= new MutableLiveData<>();

        //TEST ASSET
        /*
        HashMap<String, List<Channel>> testMap= new HashMap<>();
        List<Channel> channelTest = new ArrayList<>();
        Channel channel= new Channel();
        channel.setName("Channel-1-test");
        channel.setValue("value-test");
        channelTest.add(channel);
        testMap.put("TEST_DEVICE_NAME",channelTest);
        */

        System.out.println("STARTING API CALL FOR ASSETS");

        System.out.println("LOOKING FOR: "+ bodyAsset.getDeviceAsset().get(0).getName());
        Call<BodyAsset> callAsset = clientAPI.getAssets(bodyAsset);
        callAsset.enqueue(new Callback<BodyAsset>() {
            @Override
            public void onResponse(Call<BodyAsset> call, Response<BodyAsset> response) {


                System.out.println("RESPONSE ASSETS API");

                System.out.println(response.code());
                BodyAsset assetList = response.body();
                System.out.println(response.message());


                if(assetList!=null) {


                    if(assetList.getDeviceAsset().get(0).getChannelList().isEmpty()){
                        Toast.makeText(context, "No channel defined for the asset or device not connected", Toast.LENGTH_SHORT).show();
                    }
                    assets.setValue(getHashMap(assetList));
                    for(int i=0; i<assetList.getDeviceAsset().size(); i++) {


                        System.out.println("----- Asset name: "+assetList.getDeviceAsset().get(i).getName()+ " -----");
                        for(Channel channel:assetList.getDeviceAsset().get(i).getChannelList())
                            System.out.println(channel.getName()+": " + channel.getValue());
                    }

                }else{

                    Toast.makeText(context, "Error on get assets: "+ response.message(), Toast.LENGTH_SHORT).show();
                    System.out.println("asset list empty");

                }
            }

            @Override
            public void onFailure(Call<BodyAsset> call, Throwable t) {

                Toast.makeText(context, "Something went wrong on obtain assets...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        //assets.setValue(testMap);
        return assets;

    }






    public MutableLiveData<Message> getMessage(){

        System.out.println("GET MESSAGE");
        MutableLiveData<Message> messages= new MutableLiveData<>();

        Call<Message> callMessages = clientAPI.getMessages();
        callMessages.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                System.out.println("RESPONSE MESSAGES API");

                Message messagesList = response.body();
                System.out.println(response.message());
                messages.setValue(messagesList);

                if(messagesList!=null) {

                    for(int i=0; i<messagesList.getItems().size(); i++) {

                        System.out.println("----- Message number: "+i+ " -----");
                        for(Metric m: messagesList.getItems().get(i).getPayload().getMetrics())
                            System.out.println(m.name+": " + m.value);
                    }

                }else{
                    Toast.makeText(context, "Error on get messages: "+response.message(), Toast.LENGTH_SHORT).show();
                    System.out.println("message list empty");

                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                Toast.makeText(context, "Something went wrong on obtain messages...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


        return messages;

    }


    private HashMap<String, List<Channel>> getHashMap(BodyAsset assets){

        HashMap<String, List<Channel>> result= new HashMap<>();

        for(DeviceAsset asset: assets.getDeviceAsset()){
            result.put(asset.getName(), asset.getChannelList());
        }

        return result;
    }
}
