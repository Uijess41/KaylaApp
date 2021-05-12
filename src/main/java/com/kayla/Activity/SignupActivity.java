package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    Button btnsign;
    ImageView back;
    TextView txt_signin,social_signup;
    EditText etname,etemail,etpass,etrepass;
    ProgressBar progress;
    String name="",email="",password="",id="",repassword="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnsign=findViewById(R.id.btnsign);
        etname=findViewById(R.id.etname);
        etemail=findViewById(R.id.etemail);
        etpass=findViewById(R.id.etpass);
        etrepass=findViewById(R.id.etrepass);
        progress=findViewById(R.id.progress);
        back=findViewById(R.id.back);
        txt_signin=findViewById(R.id.txt_signin);
        social_signup=findViewById(R.id.social_signup);

        social_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "Signup Social", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=etname.getText().toString();
                email=etemail.getText().toString();
                password=etpass.getText().toString();
                repassword=etrepass.getText().toString();

                if (name.isEmpty()){
                    etname.setError("name is empty");
                }else if (email.isEmpty()){
                    etemail.setError("emailid is empty");
                }else if (password.isEmpty()){
                    etpass.setError("passowrd is empty");
                }
                else if (!repassword.equals(password)){
                    etrepass.setError("Password Not Matched");
                }else {
                    SignUp();
                }
            }
        });

    }

    public void SignUp(){

        Log.e("SignupActivity", "SignUp: " +name);
/*
        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage("please wait..");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();*/
        progress.setVisibility(View.VISIBLE);
        AndroidNetworking.post(API_BaseUrl.sign_up)
                .setPriority(Priority.HIGH)
                .addBodyParameter("name",name)
                .addBodyParameter("email",email)
                .addBodyParameter("password",password)
                .addBodyParameter("confirm_password",repassword)
                .setTag("Text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("SignupActivity", "onResponse: " +response);
                        progress.setVisibility(View.GONE);
                        try {
                            if (response.getString("result").equals("sign_in Successfully")){

                             Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(i);
                                Toast.makeText(SignupActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            }else {

                                Toast.makeText(SignupActivity.this, ""+response.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
                            Log.e("SignupActivity", "onMessage: " +e.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        progress.setVisibility(View.GONE);
                        Log.e("SignupActivity", "onError: " +anError.getMessage());
                    }
                });
    }
}