package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.kayla.Adapter.CartAdapter;
import com.kayla.Adapter.CheckoutAdapter;
import com.kayla.Model.CartModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.kayla.other.AppConstats.AppConstats.Address;
import static com.kayla.other.AppConstats.AppConstats.Address_Id;
import static com.kayla.other.AppConstats.AppConstats.Paymenttype;

public class CheckoutActivity extends AppCompatActivity {

    CheckoutAdapter checkoutAdapter;
            CartAdapter  cartAdapter;
    Button btnbuy;RadioButton radio_btn;
    ImageView backbtn;

    public static TextView subprice,feeprice,priceshiping,totalprice;

    TextView txtview,txtaddress,txtRoad;
    RecyclerView recycleview_cart;
    String pro_id="",user_id="",address_id="",address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
      AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        address_id = AppConstats.sharedpreferences.getString(AppConstats.Address_Id, "");
        address = AppConstats.sharedpreferences.getString(AppConstats.Address, "");

        Log.e("bdvcva", "onCreate: "+ user_id+","+address_id+","+address);

        btnbuy=findViewById(R.id.btnbuy);
        backbtn=findViewById(R.id.backbtn);
        txtview=findViewById(R.id.txtview);
        radio_btn=findViewById(R.id.radio_btn);
        subprice=findViewById(R.id.subprice);
        feeprice=findViewById(R.id.feeprice);
        priceshiping=findViewById(R.id.priceshiping);
        totalprice=findViewById(R.id.totalprice);


        txtview.setText(address);
        txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ShowAddressActivity.class));
            }
        });
        //validation
        radio_btn.setChecked(true);


        recycleview_cart=findViewById(R.id.recycleview_cart);
        recycleview_cart.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!address_id.isEmpty()) {
                   // selecteType = radio_btn.getText().toString();
                    startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
                    finish();

                } else {
                    Toast.makeText(CheckoutActivity.this, "No Address Selected ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        show_Cart();
    }
    //show cart api
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
                        Log.e("fhgghhghsghf", "jkfdsgh: " + response.toString());
                        try {
                            ArrayList<CartModel> arrayList=new ArrayList<>();
                            JSONArray jsonArray=new JSONArray(response.getString("data"));
                            Log.e("dgsg", "onResponse: "+ (response.getString("data")));

                            totalprice.setText(response.getString("total_amount"));
                            Log.e("dfghafhg", "onResponse: "+ response.getString("total_amount"));
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                CartModel cartDemoModel=new CartModel();
                                cartDemoModel.setId(jsonObject.getString("id"));
                                cartDemoModel.setQntity(jsonObject.getString("qntity"));
                                cartDemoModel.setProduct_description((jsonObject.getString("product_description")));
                                cartDemoModel.setProduct_title((jsonObject.getString("product_title")));
                                cartDemoModel.setMRP((jsonObject.getString("MRP")));
                                cartDemoModel.setImage(jsonObject.getString("image"));
                                cartDemoModel.setPath(jsonObject.getString("path"));
                                cartDemoModel.setSub_total(jsonObject.getString("sub_total"));
                                subprice.setText(jsonObject.getString("sub_total"));
                                feeprice.setText(jsonObject.getString("kayla_fee"));

                                priceshiping.setText(jsonObject.getString("shipping_total"));

                                Log.e("dfbdfb", "onResponse:" + (jsonObject.getString("qntity")));
                                arrayList.add(cartDemoModel);
                            }

                            recycleview_cart.setHasFixedSize(true);
                            checkoutAdapter =new CheckoutAdapter(arrayList,CheckoutActivity.this);
                            recycleview_cart.setAdapter(checkoutAdapter);

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