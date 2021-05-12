package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.Adapter.DemoAdapter;
import com.kayla.Adapter.MyOrderAdapter;
import com.kayla.Adapter.PostAdapter;
import com.kayla.Model.DemoModel;
import com.kayla.Model.MyOrderModel;
import com.kayla.Model.PostModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {

    ImageView imageback,imgadd;

    String user_id ="";
    PostAdapter postAdapter;
    ArrayList<PostModel> postModelArrayList;
    RecyclerView recycleview_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        imageback = findViewById(R.id.imageback);
        imgadd = findViewById(R.id.imgadd);

        recycleview_post = findViewById(R.id.recycleview_post);

        recycleview_post.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        Log.e("ghhfghfh", "onCreate: " + user_id);
        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(),NewPostActivity.class));
            }
        });

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show_my_post();
    }

    public void show_my_post() {
        Log.e("iijii", "seller_id: " + user_id);

        AndroidNetworking.post(API_BaseUrl.show_my_post)
                .addBodyParameter("seller_id", user_id)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            if (response.getString("result").equals("successfully")) {

                                Log.e("jfghgh", "onResponse: "+response );
                                postModelArrayList=new ArrayList<>();

                                JSONArray jsonArray = new JSONArray(response.getString("data"));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    PostModel postModel = new PostModel();
                                    String id = jsonObject.getString("id");

                                    Log.e("sgfgdfshj", "onResponse: "+ (jsonObject.getString("seller_id")));
                                    postModel.setSeller_id(jsonObject.getString("seller_id"));
                                    postModel.setCategory_id(jsonObject.getString("category_id"));
                                    postModel.setSub_category_id(jsonObject.getString("sub_category_id"));
                                    postModel.setProduct_title(jsonObject.getString("product_title"));
                                    postModel.setProduct_description(jsonObject.getString("product_description"));
                                    postModel.setMrp(jsonObject.getString("MRP"));
                                    postModel.setDiscount(jsonObject.getString("discount"));
                                    postModel.setSelling_price(jsonObject.getString("selling_price"));
                                    postModel.setStock(jsonObject.getString("stock"));
                                    postModel.setImage(jsonObject.getString("image"));
                                    postModel.setColor(jsonObject.getString("color"));
                                    postModel.setSize(jsonObject.getString("size"));
                                    postModel.setBrand(jsonObject.getString("brand"));
                                    postModel.setVerify_status(jsonObject.getString("verify_status"));
                                    Log.e("ggfg", "onResponse: "+jsonObject.getString("path") );
                                    postModel.setPath(jsonObject.getString("path"));

                                    postModelArrayList.add(postModel);
                                }

                                recycleview_post.setHasFixedSize(true);
                                postAdapter = new PostAdapter(postModelArrayList, PostActivity.this);
                                recycleview_post.setLayoutManager(new LinearLayoutManager(PostActivity.this, LinearLayoutManager.VERTICAL, false));
                                recycleview_post.setAdapter(postAdapter);

                            }
                        } catch (JSONException e) {
                            Log.e("jdsjsui", "onResponse: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("jhasfty", "onError: " + anError.getMessage());
                    }
                });
    }

}