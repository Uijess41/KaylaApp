package com.kayla.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    ImageView back;
    TextView txtsignup,sociallogin;
    Button btnlogin;
    EditText etemail, etpass;
    ProgressBar progress;
    String email = "", password = "",UserId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back = findViewById(R.id.back);

        btnlogin = findViewById(R.id.btnlogin);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        progress = findViewById(R.id.progress);
        txtsignup = findViewById(R.id.txtsignup);
        sociallogin = findViewById(R.id.sociallogin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        sociallogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login social", Toast.LENGTH_SHORT).show();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etemail.getText().toString();
                password = etpass.getText().toString();

                if (email.isEmpty()) {
                    etemail.setError("Emailid is empty");
                } else if (password.isEmpty()) {
                    etpass.setError("Password is empty");
                } else {
                    Login();
                }
            }
        });

    }

    //Impl Login api
    public void Login() {

        progress.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API_BaseUrl.login)
                .setPriority(Priority.HIGH)
                .addBodyParameter("email", email)
                .addBodyParameter("password", password)
                .setTag("Text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progress.setVisibility(View.GONE);
                        try {
                            if (response.getString("result").equals("Login SuccessFully")) {
                                String id = response.getString("id");
                                Log.e("LoginActivity", "onResponse: " +id);
                                AppConstats.sharedpreferences =getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                                editor.putString(AppConstats.User_id,id);
                                editor.putString(AppConstats.User_Email,response.getString("email"));
                                editor.apply();
                                Toast.makeText(LoginActivity.this, "login successfully",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();

                            }else {
                                Toast.makeText(LoginActivity.this, "" + response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
                            Log.e("LoginActivity", "onMessage: " +e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progress.setVisibility(View.GONE);
                        Log.e("LoginActivity", "onError: " +anError.getMessage());
                    }
                });

    }
}