package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

import static com.kayla.other.API_BaseUrl.add_address;

public class CreateAddress extends AppCompatActivity {


    Button btn_add;
    EditText et_name,et_address,et_city,et_postalcaode,et_phoneNo;
    String name="",address="",city="",postalcaode="",phoneno="",user_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);
        btn_add=findViewById(R.id.btn_add);
        et_name=findViewById(R.id.et_name);
        et_address=findViewById(R.id.et_address);
        et_city=findViewById(R.id.et_city);
        et_postalcaode=findViewById(R.id.et_postalcaode);
        et_phoneNo=findViewById(R.id.et_phoneNo);

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);

        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        Log.e("CreateAddress", "onCreate: " +user_id);
        // this is local variable

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                name=et_name.getText().toString();
                address=et_address.getText().toString();
                city=et_city.getText().toString();
                postalcaode=et_postalcaode.getText().toString();
                phoneno=et_phoneNo.getText().toString();
                //calling api
                add_address();
                //updateAddress();
            }
        });

    }
    //implement add_address api
    public void add_address(){
        Log.e("CreateAddress", "add_address: " +user_id);
        AndroidNetworking.post(add_address)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("name",name)
                .addBodyParameter("address",address)
                .addBodyParameter("pincode",postalcaode)
                .addBodyParameter("city",city)
                .addBodyParameter("phone",phoneno)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("tgjgtfg ", "dfgdedrghdh: " +response);
                        try {
                            if (response.getString("result").equals("successfully")){
                                startActivity(new Intent(getApplicationContext(), ShowAddressActivity.class));
                                Toast.makeText(CreateAddress.this, " add successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CreateAddress.this, "else", Toast.LENGTH_SHORT).show();
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