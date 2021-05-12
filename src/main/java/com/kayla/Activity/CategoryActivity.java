package com.kayla.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Adapter.BrandAdapter;
import com.kayla.Adapter.CatWomanAdapter;
import com.kayla.Adapter.CategoryAdapter;
import com.kayla.Model.BrandModel;
import com.kayla.Model.CategoryModel;
import com.kayla.Model.SubCatData;
import com.kayla.Model.SubcattitleModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    RelativeLayout relative;
    ImageView backbtn;
    TextView cat_seeall;
    String user_id = "";
    CategoryAdapter categoryAdapter;
    BrandAdapter brandAdapter;
    ArrayList<CategoryModel.Data> categoryModelArrayList;
    ArrayList<BrandModel.Data> brandDataArrayList;
    RecyclerView cate_recycleview, brand_recycleview;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        relative = findViewById(R.id.relative);
        backbtn = findViewById(R.id.backbtn);
        cat_seeall = findViewById(R.id.cat_seeall);
        cate_recycleview = findViewById(R.id.cate_recycleview);
        brand_recycleview = findViewById(R.id.brand_recycleview);

        cate_recycleview.setLayoutManager(new GridLayoutManager(this, 2));
        brand_recycleview.setLayoutManager(new GridLayoutManager(this, 4));

        SharedPreferences sharedPreferences = getSharedPreferences("myprf", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Log.e("CategoryActivity", "onCreate: " +user_id);
        user_id = getSharedPreferences("myprf", MODE_PRIVATE).getString(AppConstats.User_id, "");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showcategory();
        brand();
    }
    //implement showcategory api
    public void showcategory() {

        AndroidNetworking.post(API_BaseUrl.show_catetory)

                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        CategoryModel category1Model = gson.fromJson(response.toString(), CategoryModel.class);
                        categoryModelArrayList = new ArrayList<>();
                        try {
                            if (response.getString("status").equals("true")) {

                                categoryModelArrayList.addAll(category1Model.getData());
                                categoryAdapter = new CategoryAdapter(categoryModelArrayList, CategoryActivity.this);
                                cate_recycleview.setAdapter(categoryAdapter);
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

    //implement show brand api
    public void brand() {
        AndroidNetworking.post(API_BaseUrl.show_brand)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        BrandModel brandModel = gson.fromJson(response.toString(), BrandModel.class);
                        brandDataArrayList = new ArrayList<>();
                        brandDataArrayList.addAll(brandModel.getData());

                        brandAdapter = new BrandAdapter(brandDataArrayList, CategoryActivity.this);
                        brand_recycleview.setAdapter(brandAdapter);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


}
