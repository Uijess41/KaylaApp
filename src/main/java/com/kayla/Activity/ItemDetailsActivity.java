package com.kayla.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Adapter.ProductDetailsSliderAdapter;
import com.kayla.Model.ShowProductDetailsModel;
import com.kayla.Model.SliderPojo;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemDetailsActivity extends AppCompatActivity {
    ArrayList<ShowProductDetailsModel.Data> showProductDetailsModelArrayList;
    TextView buynow,turtleneck,doller,discount,decription,txtsize,brown,txt_cat,yearnum,txt_addcart;
    ImageView backbtn;
    CircleImageView profile_image;
    String pro_id="",user_id="";

    ProductDetailsSliderAdapter itemBainerAdapter;
    SliderView imageSlider;
    ArrayList<SliderPojo> sliderArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        buynow = findViewById(R.id.buynow);
        backbtn = findViewById(R.id.backbtn);
        turtleneck = findViewById(R.id.turtleneck);
        doller = findViewById(R.id.doller);
        discount = findViewById(R.id.discount);
        decription = findViewById(R.id.decription);
        txtsize = findViewById(R.id.txtsize);
        brown = findViewById(R.id.brown);
        txt_cat = findViewById(R.id.txt_cat);
        yearnum = findViewById(R.id.yearnum);
        profile_image = findViewById(R.id.profile_image);
        imageSlider =findViewById(R.id.imageSlider);
        txt_addcart =findViewById(R.id.txt_addcart);

       AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);

        pro_id = AppConstats.sharedpreferences.getString(AppConstats.Product_id, "");
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");

        Log.e("fdgd", "onCreate: " + user_id);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_cart(user_id,pro_id);
            }
        });
        //Calling api
        showProductDetail();

        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.BLUE);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(3);
        imageSlider.startAutoCycle();
    }
//implement showProductDetail api

    public void showProductDetail() {
        //data come
        Log.e("ItemDetailsActivity", "showProductDetail: " +pro_id);
        AndroidNetworking.post(API_BaseUrl.product_details)
               .addBodyParameter("product_id",pro_id)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        sliderArrayList=new ArrayList<>();
                        Log.e("response", "onResponse: " +response);
                        try {
                            if (response.getString("status").equals("true")){

                                showProductDetailsModelArrayList= new ArrayList<>();

                                Gson gson = new Gson();
                                ShowProductDetailsModel showProductDetailsModel=gson.fromJson(response.toString(),ShowProductDetailsModel.class);

                                showProductDetailsModel.getData().getId();
                                showProductDetailsModel.getData().getBrand();
                                turtleneck.setText(showProductDetailsModel.getData().getProduct_title());
                                doller.setText(showProductDetailsModel.getData().getMRP());
                                discount.setText(showProductDetailsModel.getData().getDiscount());
                                decription.setText(showProductDetailsModel.getData().getProduct_description());
                                txtsize.setText(showProductDetailsModel.getData().getSize());
                                brown.setText(showProductDetailsModel.getData().getColor());



                                for (int i = 0; i <showProductDetailsModel.getData().getMulti_image().size() ; i++) {
                                   String Image=showProductDetailsModel.getData().getMulti_image().get(i).getImage();
                                    Log.e("dzfggggfggsdsgd",  Image+"Image");
                                    SliderPojo sliderPojo=new SliderPojo();
                                    sliderPojo.setPath(showProductDetailsModel.getData().getPath());
                                    sliderPojo.setImage(Image);
                                    sliderArrayList.add(sliderPojo);
                                }

                                itemBainerAdapter = new ProductDetailsSliderAdapter(sliderArrayList,ItemDetailsActivity.this);
                                imageSlider.setSliderAdapter(itemBainerAdapter);


                            }
                        } catch (JSONException e) {
                            Log.e("Message", "getMessage: " +e.getMessage());
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("Error", "anError: " +anError.getMessage());
                    }
                });
    }
//add cart api
    public void add_cart(String user_id,String pro_id){
        Log.e("tufgvjv", "user_id: " +user_id);
        Log.e("tufgvjv", "user_id: " +pro_id);
        AndroidNetworking.post(API_BaseUrl.add_to_cart)
                .addBodyParameter("product_id",pro_id)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("product_quantity","1")
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("dfhchchgchvjvh", "onResponse: " +response);
                        try {
                            if (response.getString("result").equals("successfully")){

                                Toast.makeText(ItemDetailsActivity.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                                  startActivity(new Intent(ItemDetailsActivity.this,CartActivity.class));
                            }
                            else {
                                Toast.makeText(ItemDetailsActivity.this, response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("sdss", "onError: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("cvsdvs", "onError: " + anError.getMessage());
                    }
                });
    }

}