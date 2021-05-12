package com.kayla.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kayla.Fragment.HomeFragment;
import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static DrawerLayout drawer;
    LinearLayout ll_home,ll_profile,ll_cart,ll_favorite,ll_order,ll_post,ll_message,ll_setting,ll_logout;
    String User_id = "";
    SharedPreferences.Editor editor;
    NavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        User_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");

        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        ll_home = findViewById(R.id.ll_home);
        ll_profile = findViewById(R.id.ll_profile);
        ll_cart = findViewById(R.id.ll_cart);
        ll_favorite = findViewById(R.id.ll_favorite);
        ll_order = findViewById(R.id.ll_order);
        ll_post = findViewById(R.id.ll_post);
        ll_message = findViewById(R.id.ll_message);
        ll_setting = findViewById(R.id.ll_setting);
        ll_logout = findViewById(R.id.ll_logout);



        ll_home.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_cart.setOnClickListener(this);
        ll_favorite.setOnClickListener(this);
        ll_order.setOnClickListener(this);
        ll_post.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_logout.setOnClickListener(this);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_favorite:

                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.ll_order:

                startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_post:

                startActivity(new Intent(MainActivity.this, PostActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                break;


            case R.id.ll_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to close this application ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                Log.e("jkhjsa", User_id);

                                Log.e("TAfdgfG", User_id);
                                AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                                editor.remove(AppConstats.User_id);
                                editor.apply();
                                Toast.makeText(MainActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Logout");
                alert.show();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}