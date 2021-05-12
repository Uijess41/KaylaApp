package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Adapter.DemoAdapter;
import com.kayla.Adapter.HomeArrivalAdapter;
import com.kayla.Adapter.NewArrivalAdapter;
import com.kayla.Model.ArrivalHomeModel1;
import com.kayla.Model.DemoModel;
import com.kayla.Model.NewArrivalModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewArrivalActivity extends AppCompatActivity {

    NewArrivalAdapter newArrivalAdapter;
    ArrayList<ArrivalHomeModel1.Data> dataArrayList;

    RecyclerView recycleviewnew;
    ImageView imageback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_arrival);
        recycleviewnew=findViewById(R.id.recycleviewnew);
        imageback=findViewById(R.id.imageback);
        recycleviewnew.setLayoutManager(new GridLayoutManager(this, 2));

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arival();
    }

    public void arival(){
        AndroidNetworking.post(API_BaseUrl.show_new_arrival)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ArrivalHomeModel1 featureModel = gson.fromJson(response.toString(), ArrivalHomeModel1.class);
                        dataArrayList=new ArrayList<>();

                        try {
                            if (response.getString("status").equals("true")) {
                                dataArrayList.addAll(featureModel.getData());
                                newArrivalAdapter = new NewArrivalAdapter(dataArrayList, NewArrivalActivity.this);
                                recycleviewnew.setAdapter(newArrivalAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

}