package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Adapter.FeatureAdapter;
import com.kayla.Model.FeatureModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FeatureActivity extends AppCompatActivity {


    FeatureAdapter featureAdapter;
    ArrayList<FeatureModel.Data> featureModelArrayList;
    RecyclerView recycleview_feature;
    ImageView imageback,notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
        imageback=findViewById(R.id.imageback);

        recycleview_feature =findViewById(R.id.recycleview_feature);
        recycleview_feature.setLayoutManager(new GridLayoutManager(this, 2));


        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        feature();
    }
    public void feature() {
        AndroidNetworking.post(API_BaseUrl.show_featured)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FeatureModel featureModel = gson.fromJson(response.toString(), FeatureModel.class);
                        featureModelArrayList=new ArrayList<>();
                        try {
                            if (response.getString("status").equals("true")) {
                                featureModelArrayList.addAll(featureModel.getData());
                                featureAdapter = new FeatureAdapter(featureModelArrayList, FeatureActivity.this);
                                recycleview_feature.setAdapter(featureAdapter);

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