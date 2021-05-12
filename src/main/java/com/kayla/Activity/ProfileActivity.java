package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.google.gson.Gson;
import com.kayla.Model.ProfileModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    Button btn_save;
    EditText etname,et_address,et_city,et_gender,et_email,et_phone;
    String name="",address="",city="",gender="",email="",phoneno="",user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        Log.e("ProfileActivity", "onCreate: " +user_id);

        et_city=findViewById(R.id.et_city);
        et_phone=findViewById(R.id.et_phone);
        et_email=findViewById(R.id.et_email);
        et_gender=findViewById(R.id.et_gender);
        et_address=findViewById(R.id.et_address);
        btn_save=findViewById(R.id.btn_save);
        etname=findViewById(R.id.etname);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

startActivity(new Intent(getApplicationContext(),ShowAddressActivity.class));
                name = etname.getText().toString();
                email = et_email.getText().toString();
                address = et_address.getText().toString();
                city = et_city.getText().toString();
                gender = et_gender.getText().toString();
                phoneno = et_phone.getText().toString();
                updateprofile();

            }

        });
        showProfile();
    }
    public void updateprofile(){
        Log.e("hhfg", "hfg: " +user_id);
        AndroidNetworking.post(API_BaseUrl.update_profile)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("name",name)
                .addBodyParameter("email",email)
                .addBodyParameter("address",address)
                .addBodyParameter("city",city)
                .addBodyParameter("gender",gender)
                .addBodyParameter("phone_number",phoneno)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("result").equals("successfully")){
                                Gson gson=new Gson();
                                ProfileModel profileModel=gson.fromJson(response.toString(),ProfileModel.class);
                                profileModel.getName();
                                etname.setText(profileModel.getName());
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

    public void showProfile(){
        Log.e("hhfg", "hfg: " +user_id);
        AndroidNetworking.post(API_BaseUrl.show_profile)
                .addBodyParameter("user_id",user_id)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            etname.setText(response.getString("name"));
                            et_email.setText(response.getString("email"));
                            et_address.setText(response.getString("address"));
                            et_city.setText(response.getString("city"));
                            et_gender.setText(response.getString("gender"));
                            et_phone.setText(response.getString("phoneno"));

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