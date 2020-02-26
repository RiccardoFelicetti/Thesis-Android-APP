package com.example.kapuaapiaccess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.kapuaapiaccess.message.Message;
import com.example.kapuaapiaccess.message.Metric;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Token accessToken=new Token();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //RetrofitService controller= new RetrofitService("user123", "Kapu@12345678");
        /*
        Call<Token> call = controller.retrofitInstance().create(KapuaAPI.class).login(controller.getUser());

        System.out.println("HERE 1");
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                accessToken.setTokenId(response.body().tokenId);


            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        */

        /*
        DeviceAsset asset= new DeviceAsset();
        asset.name="Sim_Slave1";

        Call<DeviceAsset> callAsset = controller.retrofitInstance().create(KapuaAPI.class).read(" Bearer "+ accessToken.getTokenId(), asset );
        callAsset.enqueue(new Callback<DeviceAsset>() {
            @Override
            public void onResponse(Call<DeviceAsset> call, Response<DeviceAsset> response) {
                DeviceAsset responseAsset = response.body();
                if(responseAsset!=null) {
                    System.out.println("Asset Name: " + responseAsset.getName());
                }else{

                }
            }

            @Override
            public void onFailure(Call<DeviceAsset> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        */



     /*
        //PRINT MESSAGES
        Call<Message> callMessages = controller.retrofitStringInstance().create(KapuaAPI.class).getMessages();
        callMessages.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                System.out.println("RESPONSE MESSAGES API");

                Message messagesList = response.body();
                System.out.println(response.message());
                if(messagesList!=null) {

                    for(int i=0; i<messagesList.getItems().size(); i++) {

                        System.out.println("----- Message number: "+i+ " -----");
                        for(Metric m: messagesList.getItems().get(i).getPayload().getMetrics())
                        System.out.println(m.name+": " + m.value);
                    }

                }else{
                    System.out.println("message list empty");

                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
*/

    }
}
