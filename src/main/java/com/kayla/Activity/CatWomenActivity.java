package com.kayla.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.kayla.Adapter.CatWomanAdapter;
import com.kayla.Adapter.CatWomanAdapter2;
import com.kayla.Model.SubCatModel;
import com.kayla.Model.SubcatImageModel;
import com.kayla.Model.Subcattestmodel;
import com.kayla.Model.SubcattitleModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatWomenActivity extends AppCompatActivity {
    ArrayList<SubcattitleModel.Data> subcatTittleArrayList;

    ArrayList<SubcatImageModel> arrayList = new ArrayList<>();
    RecyclerView recycleview_new, recycleview_dress;
    RelativeLayout relative1;
    MaterialCardView card;
    CatWomanAdapter catWomanAdapter;
    CatWomanAdapter2 catWomanAdapter2;
    ImageView back_btn, imgwoman;
    String st_CatId = "", Image = "", Name = "", subcategory_id = "";
    TextView txt_woman, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_women);
        relative1 = findViewById(R.id.relative1);
        card = findViewById(R.id.card);
        imgwoman = findViewById(R.id.imgwoman);
        txt_woman = findViewById(R.id.txt_woman);
        title = findViewById(R.id.title);

        back_btn = findViewById(R.id.back_btn);
        recycleview_new = findViewById(R.id.recycleview_new);
        recycleview_dress = findViewById(R.id.recycleview_dress);

        recycleview_dress.setLayoutManager(new LinearLayoutManager(CatWomenActivity.this, RecyclerView.VERTICAL, false));

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        Log.e("CatWomenActivity", "onCreate: " + st_CatId);
        st_CatId = AppConstats.sharedpreferences.getString(AppConstats.CATEGORYId, "");

        subcategory_id = AppConstats.sharedpreferences.getString(AppConstats.subcategory_id, "");

        Image = AppConstats.sharedpreferences.getString(AppConstats.IMAGE, "");
        Name = AppConstats.sharedpreferences.getString(AppConstats.NAME, "");
        Glide.with(this).load(Image).into(imgwoman);
        txt_woman.setText(Name);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cat_woman();
        show_SubCatItems();
        //  cat_dress();
    }

    public void cat_woman() {

        AndroidNetworking.post(API_BaseUrl.show_products_bysubcat)
                .setPriority(Priority.HIGH)
                .addBodyParameter("subcategory_id", st_CatId)

                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("CatWomenActivity", "onResponse: " + response);

                        try {
                            if (response.getString("status").equals("true")) {
                                Gson gson = new Gson();

                                SubCatData subCatModel = gson.fromJson(response.toString(), SubCatData.class);
                                ArrayList subcatModelArrayList = new ArrayList<SubCatModel.Data>();

                                if (!subCatModel.equals(null)) {
                                    subcatModelArrayList.addAll(subCatModel.getData());

                                    catWomanAdapter = new CatWomanAdapter(subcatModelArrayList, CatWomenActivity.this);
                                    recycleview_new.setAdapter(catWomanAdapter);
                                }

                            }
                        } catch (JSONException e) {
                            Log.e("CatWomenActivity", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("CatWomenActivity", "onResponse: " + anError.getMessage());
                    }
                });
    }

    public void cat_dress() {

        AndroidNetworking.post(API_BaseUrl.show_products_bysubcat)
                .setPriority(Priority.HIGH)
                .addBodyParameter("subcategory_id", st_CatId)
                //  .addBodyParameter("subcategory_id", subcategory_id)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("CatWomenActivity", "onResponse: " + response);

                        try {
                            if (response.getString("status").equals("true")) {
                                Gson gson = new Gson();

                                SubCatData subCatModel = gson.fromJson(response.toString(), SubCatData.class);
                                //Subcattestmodel subCatModel = gson.fromJson(response.toString(), Subcattestmodel.class);
                                ArrayList subcatModelArrayList = new ArrayList<Subcattestmodel.Data>();

                                if (!subCatModel.equals(null)) {
                                    subcatModelArrayList.addAll(subCatModel.getData());

                                    catWomanAdapter2 = new CatWomanAdapter2(subcatModelArrayList, CatWomenActivity.this);
                                    recycleview_dress.setAdapter(catWomanAdapter2);
                                }

                            }
                        } catch (JSONException e) {
                            Log.e("CatWomenActivity", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("CatWomenActivity", "onResponse: " + anError.getMessage());
                    }
                });
    }


    private void show_SubCatItems() {
        Log.e("jghjg", "ghg: " + st_CatId);
        AndroidNetworking.post(API_BaseUrl.show_subcategory_by_catid)
                .addBodyParameter("category_id", st_CatId)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("dsfsfvdsv", "onResponse: " + response);
                        try {
                            if (response.getString("status").equals("true")) {
                                subcatTittleArrayList = new ArrayList<>();
                                Gson gson = new Gson();
                                SubcattitleModel subCatModel = gson.fromJson(response.toString(), SubcattitleModel.class);
                                subcatTittleArrayList.addAll(subCatModel.getData());

                                catWomanAdapter = new CatWomanAdapter(subcatTittleArrayList, CatWomenActivity.this);
                                recycleview_dress.setAdapter(catWomanAdapter);
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