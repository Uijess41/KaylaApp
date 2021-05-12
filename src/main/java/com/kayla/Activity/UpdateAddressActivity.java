package com.kayla.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Model.ShowAddress;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateAddressActivity extends AppCompatActivity {
    String name = "", address = "", city = "", postalcaode = "", phoneno = "", user_id = "", address_id = "";
    ImageView imageback;
    EditText et_name, et_address, et_city, et_postalcaode, et_phoneNo;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        imageback = findViewById(R.id.imageback);
        btn_update = findViewById(R.id.btn_update);
        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        et_city = findViewById(R.id.et_city);
        et_postalcaode = findViewById(R.id.et_postalcaode);
        et_phoneNo = findViewById(R.id.et_phoneNo);


        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        address_id = AppConstats.sharedpreferences.getString(AppConstats.Address_Id, "");
        Log.e("update", "vdsfdfsgsd: " + address_id);


        show_address(user_id, address_id);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString();
                address = et_address.getText().toString();
                city = et_city.getText().toString();
                postalcaode = et_postalcaode.getText().toString();
                phoneno = et_phoneNo.getText().toString();
                updateAddress();

            }
        });
        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void show_address(String user_id, String address_id) {
        Log.e("CreateAddress", "dfgdfd: " + address_id);
        AndroidNetworking.post(API_BaseUrl.show_address)
                .addBodyParameter("user_id", user_id)
                .addBodyParameter("address_id", address_id)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ShowAddress showAddress = gson.fromJson(response.toString(), ShowAddress.class);
                        Log.e("kjkffdgdgdgkljbhn ", "hjhjvdfgh: " + response.toString());
                        try {

                            if (response.getBoolean("result")) {

                                for (int i = 0; i < showAddress.getData().getAll_address().size(); i++) {
                                    //setText
                                    et_name.setText(showAddress.getData().getAll_address().get(i).getName());
                                    et_address.setText(showAddress.getData().getAll_address().get(i).getAddress());
                                    et_city.setText(showAddress.getData().getAll_address().get(i).getCity());
                                    et_phoneNo.setText(showAddress.getData().getAll_address().get(i).getPincode());
                                    et_postalcaode.setText(showAddress.getData().getAll_address().get(i).getPincode());
                                }

                            } else {
                                Toast.makeText(UpdateAddressActivity.this, "dnhjkgbdkjkjdk", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("dgdghdd", "getMessage: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dsgsdsd", "onError: " + anError.getMessage());

                    }
                });

    }

    public void updateAddress() {

        Log.e("UpdateAddressActivity", "address: " + address_id);
        AndroidNetworking.post(API_BaseUrl.update_address)
                .addBodyParameter("id", address_id)
                .addBodyParameter("name", name)
                .addBodyParameter("address", address)
                .addBodyParameter("pincode", postalcaode)
                .addBodyParameter("city", city)
                .addBodyParameter("phone", phoneno)
                .setPriority(Priority.HIGH)
                .setTag("Text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("dfdfbvcvbc", "response: " + response);
                        try {
                            if (response.getString("result").equals("successfully")) {
                                Toast.makeText(UpdateAddressActivity.this, "Address update successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),ShowAddressActivity.class));
                            } else {
                                Toast.makeText(UpdateAddressActivity.this, "Address Not update successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            Log.e("UpdateAddressActivity", "onResponse: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("UpdateAddressActivity", "onError: " + anError.getMessage());
                    }
                });
    }
}