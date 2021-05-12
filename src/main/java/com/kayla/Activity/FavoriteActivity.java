package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.kayla.Adapter.DemoAdapter;
import com.kayla.Adapter.FavoriteAdapter;
import com.kayla.Adapter.PostAdapter;
import com.kayla.Model.DemoModel;
import com.kayla.Model.FavoriteModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    String user_id ="",product_id;
    FavoriteAdapter favoriteAdapter;
    ArrayList<FavoriteModel> favoriteModelArrayList;
    RecyclerView recycleview_favorite;

    RelativeLayout relative;
    ImageView imageback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        relative=findViewById(R.id.relative);
        imageback=findViewById(R.id.imageback);
        recycleview_favorite=findViewById(R.id.recycleview_favorite);
        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        product_id = AppConstats.sharedpreferences.getString(AppConstats.PRODUCT_ID, "");
        recycleview_favorite.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        favorit();


    }
    public void favorit(){
        Log.e("jsdghj", "favorit: "+user_id );
        AndroidNetworking.post(API_BaseUrl.show_favorite)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("product_id",product_id)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        favoriteModelArrayList=new ArrayList<>();
                        for (int i = 0; i <response.length() ; i++) {
                            try {

                                JSONObject jsonObject =response.getJSONObject(i);
                                FavoriteModel favoriteModel = new FavoriteModel();

                                favoriteModel.setPath(jsonObject.getString("path"));
                                String product=jsonObject.getString("product");

                                JSONObject object=new JSONObject(product);
                                favoriteModel.setName(object.getString("product_title"));

                                favoriteModel.setPrice(object.getString("MRP"));
                                favoriteModel.setImage(object.getString("image"));

                                favoriteModelArrayList.add(favoriteModel);

                            } catch (JSONException e) {
                                Log.e("jdsjsui", "onResponse: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                        recycleview_favorite.setHasFixedSize(true);
                        favoriteAdapter = new FavoriteAdapter(favoriteModelArrayList, FavoriteActivity.this);
                        recycleview_favorite.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this, LinearLayoutManager.VERTICAL, false));
                        recycleview_favorite.setAdapter(favoriteAdapter);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("jdsjsui", "anError: " + anError.getMessage());
                    }
                });
    }
}