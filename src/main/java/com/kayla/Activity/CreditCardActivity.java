package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.kayla.Model.PaymentModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

public class CreditCardActivity extends AppCompatActivity {

    MaterialButton add_btn,btn_cancel;
    EditText et_card_number,et_expire,et_cvv;
String card_no="",expiry_date="",cvv_no="",user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        add_btn=findViewById(R.id.add_btn);
        btn_cancel=findViewById(R.id.btn_cancel);
        et_card_number=findViewById(R.id.et_card_number);
        et_expire=findViewById(R.id.et_expire);
        et_cvv=findViewById(R.id.et_cvv);

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        Log.e("hsfghsdfg",user_id );
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card_no=et_card_number.getText().toString();
                expiry_date=et_expire.getText().toString();
                cvv_no=et_cvv.getText().toString();

                if (card_no.isEmpty()){
                    et_card_number.setError("card_number is empty");
                }else if (expiry_date.isEmpty()){
                    et_expire.setError("expire_number is empty");
                }else if (cvv_no.isEmpty()){
                    et_cvv.setError("cvv is empty");
                }else {
                    add_card();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_card_number.getText().clear();
                et_expire.getText().clear();
                et_cvv.getText().clear();
            }
        });
    }

    public void add_card(){
        Log.e("klsdhjkhsjk", "add_card: "+card_no+","+ expiry_date+","+cvv_no+","+ user_id);
        AndroidNetworking.post(API_BaseUrl.add_card)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("card_no",card_no)
                .addBodyParameter("expiry_date",expiry_date)
                .addBodyParameter("cvv_no",cvv_no)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        PaymentModel paymentModel = gson.fromJson(response.toString(), PaymentModel.class);
                        try {
                            if (response.getString("result").equals("successfully")){
                                Toast.makeText(CreditCardActivity.this, "card added successfully", Toast.LENGTH_SHORT).show();
                                paymentModel.getData().getId();
                                paymentModel.getData().getCard_no();
                                paymentModel.getData().getExpiry_date();
                                paymentModel.getData().getCvv_no();
                            }else {
                                Toast.makeText(CreditCardActivity.this, "else", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("jkdgsgj", "onResponse: "+e.getMessage());
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dghsh", "onError: "+anError.getMessage() );
                    }
                });

    }
}