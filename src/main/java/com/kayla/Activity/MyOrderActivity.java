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
import com.google.android.material.textview.MaterialTextView;
import com.kayla.Adapter.CartAdapter;
import com.kayla.Adapter.CatWomanAdapter;
import com.kayla.Adapter.CheckoutAdapter;
import com.kayla.Adapter.DemoAdapter;
import com.kayla.Adapter.MyOrderAdapter;
import com.kayla.Model.CartModel;
import com.kayla.Model.DemoModel;
import com.kayla.Model.MyOrderModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrderActivity extends AppCompatActivity {


    String user_id = "";
    ImageView backbtn;
    MyOrderAdapter myOrderAdapter;
    ArrayList<MyOrderModel> myOrderModelArrayList = new ArrayList<>();
    RecyclerView recycleview_myorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        backbtn = findViewById(R.id.backbtn);
        recycleview_myorder = findViewById(R.id.recycleview_myorder);

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        Log.e("ghhfghfh", "onCreate: " + user_id);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //call api
        order();
    }

    //implement order Api
    public void order() {

        Log.e("fghfgh", "user_id: " + user_id);
        AndroidNetworking.post(API_BaseUrl.show_orders)
                .addBodyParameter("user_id", user_id)

                .setPriority(Priority.HIGH)
                .setTag("Test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("usyyudstd", "onResponse: " + response);

                        try {

                            if (response.getString("result").equals("successfully")) {

                                JSONArray jsonArray = new JSONArray(response.getString("data"));
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    MyOrderModel myOrderModel = new MyOrderModel();
                                    String id = jsonObject.getString("id");
                                    myOrderModel.setProduct_price(jsonObject.getString("product_price"));
                                    myOrderModel.setTotal_amount(jsonObject.getString("total_amount"));
                                    myOrderModel.setOrder_id(jsonObject.getString("order_id"));
                                    myOrderModel.setStatus(jsonObject.getString("status"));

                                    myOrderModel.setTotal_amount(jsonObject.getString("total_amount"));

                                    String product_detail = jsonObject.getString("product_detail");
                                    JSONArray prArray = new JSONArray(product_detail);

                                    if (prArray.length() > 0) {

                                        for (int j = 0; j < prArray.length(); j++) {
                                            JSONObject obj = prArray.getJSONObject(j);
                                            //set data
                                            myOrderModel.setProduct_title(obj.getString("product_title"));
                                            myOrderModel.setProduct_description(obj.getString("product_description"));
                                            myOrderModel.setProduct_price(obj.getString("selling_price"));
                                            myOrderModel.setImage(obj.getString("image"));
                                            myOrderModel.setPath(obj.getString("path"));

                                        }
                                    }
                                    myOrderModelArrayList.add(myOrderModel);
                                }

                                recycleview_myorder.setHasFixedSize(true);
                                myOrderAdapter = new MyOrderAdapter(myOrderModelArrayList, MyOrderActivity.this);
                                recycleview_myorder.setLayoutManager(new LinearLayoutManager(MyOrderActivity.this, LinearLayoutManager.VERTICAL, false));
                                recycleview_myorder.setAdapter(myOrderAdapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("sadsda", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("sadsda", "onResponse: " + anError.getMessage());
                    }
                });
    }
}