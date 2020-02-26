package com.example.kapuaapiaccess;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.kapuaapiaccess.asset.BodyAsset;
import com.example.kapuaapiaccess.asset.Channel;
import com.example.kapuaapiaccess.asset.DeviceAsset;
import com.example.kapuaapiaccess.login.Token;
import com.example.kapuaapiaccess.login.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowFragment extends Fragment {


    View view;

    Button login_button;
    Button refresh_button;
    Button button_search;
    EditText input_username;
    EditText input_password;
    EditText input_assetName;
    EditText input_channelValue;
    TextView channelName;
    TextView assetName;
    ExpandableListView resultList;
    ListView messageResultList;

    AssetViewModel assetViewModel;
    ShowFragment showFragment;
    Button write_Button;

    LayoutInflater inflater;
    ViewGroup container;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view =inflater.inflate(R.layout.show_fragment, container, true);

        login_button=view.findViewById(R.id.button_login);
        input_username= view.findViewById(R.id.input_username);
        input_password=view.findViewById(R.id.input_password);
        resultList=view.findViewById(R.id.expandableResultList);
        messageResultList=view.findViewById(R.id.resultList);
        refresh_button=view.findViewById(R.id.button_refresh);
        button_search=view.findViewById(R.id.button_search);
        input_assetName=view.findViewById(R.id.input_assetName);
        write_Button=view.findViewById(R.id.button_write);


        showFragment=this;

        refresh_button.setVisibility(View.GONE);
        resultList.setVisibility(View.GONE);
        messageResultList.setVisibility(View.GONE);
        button_search.setVisibility(View.GONE);
        input_assetName.setVisibility(View.GONE);
        write_Button.setVisibility(View.GONE);

        button_login_click();
        button_refresh_click();
        button_search_click();
        button_write_click();

        assetViewModel= ViewModelProviders.of(this).get(AssetViewModel.class);

        return view;
    }



    private void button_login_click(){
        login_button.setOnClickListener((v)->{


            String user=input_username.getText().toString();
            String password=input_password.getText().toString();
            if(user.isEmpty() || password.isEmpty()){

                Toast.makeText(this.getContext(),"Empty field", Toast.LENGTH_SHORT).show();

            }else {

                //API FOR LOGIN
                this.login(new User(user,password));
                refresh_button.setVisibility(View.VISIBLE);
                //resultList.setVisibility(View.VISIBLE);
                messageResultList.setVisibility(View.VISIBLE);
                button_search.setVisibility(View.VISIBLE);
                input_assetName.setVisibility(View.VISIBLE);

            }
        } );
    }



    private void button_refresh_click(){
        refresh_button.setOnClickListener((v)->{

            assetViewModel.refresh();

        });
    }



    private void login(User user){

        RetrofitLogin retrofitLogin=new RetrofitLogin();
        Call<Token> call = retrofitLogin.retrofitInstance().create(KapuaAPI.class).login(user);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                Token token= response.body();

                if(token!=null) {

                    //Init ViewModel and call API to get messages
                    assetViewModel.init(token, showFragment.getContext());

                    //Observe Data Messages
                    assetViewModel.getMessages().observe(showFragment, messages -> {

                        System.out.println("LIVEDATA changed");
                        //Modify result List

                        for (int i = 0; i < messages.getItems().size(); i++) {
                            CustomAdapterMessage adapter = new CustomAdapterMessage(showFragment.getContext(), R.layout.row_layout, messages.getItems().get(i).getPayload().getMetrics());
                            messageResultList.setAdapter(adapter);
                        }

                    });

                }else{

                    Toast.makeText(showFragment.getContext(), "Check credentials: "+ response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

                Toast.makeText(showFragment.getContext(), "Something went wrong on login...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void button_search_click(){

        button_search.setOnClickListener((v)->{


            String assetName=input_assetName.getText().toString();
            if(assetName.isEmpty()){
                Toast.makeText(showFragment.getContext(), "Empty field", Toast.LENGTH_SHORT).show();
            }else {

                BodyAsset requestAsset = new BodyAsset();
                DeviceAsset asset = new DeviceAsset();
                asset.setName(assetName);
                List<DeviceAsset> assetList = new ArrayList<>();
                assetList.add(asset);
                requestAsset.setDeviceAsset(assetList);


                assetViewModel.setBodyAsset(requestAsset);

                messageResultList.setVisibility(View.GONE);
                resultList.setVisibility(View.VISIBLE);
                write_Button.setVisibility(View.VISIBLE);

                //Observe Data Asset
                assetViewModel.getAssets().observe(showFragment, assets -> {

                    System.out.println("LIVEDATA changed");
                    //Modify result List
                    List<String> listAsset = new ArrayList<>(assets.keySet());
                    CustomExpandableAdapterAsset adapterAsset = new CustomExpandableAdapterAsset(showFragment.getContext(), listAsset, assets);
                    resultList.setAdapter(adapterAsset);


                });
            }
        });
    }

    private void button_write_click(){
        write_Button.setOnClickListener((v)->{

            List<Channel> channelList= new ArrayList<>();
            boolean check=false;

            String deviceName = (String) resultList.getExpandableListAdapter().getGroup(0);


            for(int i=0; i<resultList.getExpandableListAdapter().getChildrenCount(0);i++) {
                Channel channel = (Channel) resultList.getExpandableListAdapter().getChild(0, i);
                if(!channel.getValue().isEmpty()) {
                    check=true;
                    channelList.add(channel);
                }else{
                    Toast.makeText(showFragment.getContext(), "Cannot write an empty value", Toast.LENGTH_SHORT).show();
                }

            }

            if(check) {
                BodyAsset request = new BodyAsset();
                DeviceAsset asset = new DeviceAsset();
                asset.setName(deviceName);
                asset.setChannelList(channelList);
                List<DeviceAsset> assetList = new ArrayList<>();
                assetList.add(asset);
                request.setDeviceAsset(assetList);

                assetViewModel.write(request);
            }
        });
    }

}
