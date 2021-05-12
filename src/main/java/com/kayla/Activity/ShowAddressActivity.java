package com.kayla.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Adapter.ShowAddressAdapter;
import com.kayla.Model.ShowAddress;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowAddressActivity extends AppCompatActivity {

    ImageView imgback,img_add;
    RelativeLayout relative_add;
    ShowAddressAdapter showAddressAdapter;
    ArrayList<ShowAddress.Data.AllAddres> addDataArrayList;
    RecyclerView recycleview_address;
    EditText et_name, et_address, et_city, et_postalcaode, et_phoneNo;
    String name = "", address = "", city = "", postalcaode = "", phoneno = "", user_id = "",address_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        imgback = findViewById(R.id.imgback);
        img_add = findViewById(R.id.img_add);
        relative_add = findViewById(R.id.relative_add);
        recycleview_address = findViewById(R.id.recycleview_address);

        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        et_city = findViewById(R.id.et_city);
        et_postalcaode = findViewById(R.id.et_postalcaode);
        et_phoneNo = findViewById(R.id.et_phoneNo);

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        address_id = AppConstats.sharedpreferences.getString(AppConstats.Address_Id, "");
        address = AppConstats.sharedpreferences.getString(AppConstats.Address, "");
        Log.e("CreateAddress", "onCreate: " + user_id);
        Log.e("dfgdf", "fgdfd: " + address_id);
        Log.e("fgdfhhgd", "sgsgs: " + address);
        recycleview_address.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        relative_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreateAddress.class));
                finish();
            }
        });

        show_address();

    }

    //implement show_address api
    public void show_address() {
        Log.e("CreateAddress", "show_address: " + user_id);
        AndroidNetworking.post(API_BaseUrl.show_address)
                .addBodyParameter("user_id", user_id)

                .addBodyParameter("name", name)
                .addBodyParameter("address", address)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ShowAddress showAddress = gson.fromJson(response.toString(), ShowAddress.class);
                        addDataArrayList = new ArrayList<>();
                        Log.e("kjkfgkljbhn ", "hjhjvdfgh: " + response.toString());
                        try {

                                if (response.getBoolean("result")) {

                                    addDataArrayList.addAll(showAddress.getData().getAll_address());
                                    showAddressAdapter = new ShowAddressAdapter(addDataArrayList, ShowAddressActivity.this);
                                    recycleview_address.setAdapter(showAddressAdapter);

                                }else {
                                    Toast.makeText(ShowAddressActivity.this, "dnhjkgbdkjkjdk", Toast.LENGTH_SHORT).show();
                                }

                        } catch (JSONException e) {
                            Log.e("fjhfjhrf", "getMessage: " +e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.e("AddressActivity", "onError: " +anError.getMessage());

                    }
                });

    }

}