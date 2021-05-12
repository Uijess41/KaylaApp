package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

public class SplashActivity extends AppCompatActivity {
    String User_id = "";
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        User_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (User_id.equals("")){
                    startActivity(new Intent(getApplicationContext(), WelcomeScreenActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }

            }
        },2000);
    }
}