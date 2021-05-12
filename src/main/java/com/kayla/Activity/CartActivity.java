package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.Adapter.CartAdapter;
import com.kayla.Model.CartModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    ImageView backbtn,notification;
    Button btn_continue;
    CartAdapter cartAdapter;


    RecyclerView recycleview_cart;
    String pro_id="",user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        Log.e("fgfytcycfctfcfccf",user_id );


        btn_continue=findViewById(R.id.btn_continue);
        backbtn=findViewById(R.id.backbtn);
        notification=findViewById(R.id.notification);
        recycleview_cart=findViewById(R.id.recycleview_cart);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MessageActivity.class));
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        show_Cart();
    }

//implment show_cart api
  public void show_Cart(){
      Log.e("CartActivity", "userid: " +user_id);
        AndroidNetworking.post(API_BaseUrl.show_cart)
                .addBodyParameter("user_id",user_id)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //no response
                        Log.e("fhgghhg ", "jkfdsgh: " + response.toString());
                        try {

                            ArrayList<CartModel> arrayList=new ArrayList<>();

                            JSONArray jsonArray=new JSONArray(response.getString("data"));
                            Log.e("dgsg", "onResponse: "+ (response.getString("data")));

                            if(jsonArray.length()>0){

                                for (int i = 0; i <jsonArray.length() ; i++) {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    CartModel cartDemoModel=new CartModel();

                                    cartDemoModel.setId(jsonObject.getString("id"));
                                    cartDemoModel.setQntity(jsonObject.getString("qntity"));
                                    cartDemoModel.setProduct_description((jsonObject.getString("product_description")));
                                    cartDemoModel.setProduct_title((jsonObject.getString("product_title")));
                                    cartDemoModel.setMRP((jsonObject.getString("MRP")));
                                    cartDemoModel.setImage(jsonObject.getString("image"));
                                    Log.e("hdghafhgf", "onResponse: "+ jsonObject.getString("image"));
                                    cartDemoModel.setPath(jsonObject.getString("path"));
                                    Log.e("dfbdfb", "onResponse:" + (jsonObject.getString("qntity")));
                                    arrayList.add(cartDemoModel);

                                }
                                btn_continue.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Toast.makeText(CartActivity.this, "dfhfdjjjf", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),CheckoutActivity.class));

                                    }
                                });
                            }else {

                            }

                            recycleview_cart.setHasFixedSize(true);
                            recycleview_cart.setLayoutManager(new LinearLayoutManager(CartActivity.this,LinearLayoutManager.VERTICAL,false));
                            cartAdapter=new CartAdapter(arrayList,CartActivity.this);
                            recycleview_cart.setAdapter(cartAdapter);

                        } catch (JSONException e) {
                            Log.e("dsgsdsd", "onError: " + e.getMessage());
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.e("dsgsddfgdgdgsd", "onError: " + anError.getMessage());
                    }
                });
    }

}