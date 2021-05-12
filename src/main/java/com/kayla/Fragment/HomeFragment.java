package com.kayla.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.kayla.Activity.CategoryActivity;

import com.kayla.Activity.FeatureActivity;
import com.kayla.Activity.HomeActivity;
import com.kayla.Activity.MainActivity;
import com.kayla.Activity.NewArrivalActivity;
import com.kayla.Model.SliderImageData;

import com.kayla.Adapter.FeatureAdapter;
import com.kayla.Adapter.HomeAdapter;
import com.kayla.Adapter.HomeArrivalAdapter;
import com.kayla.Adapter.SliderAdapterExample;
import com.kayla.Model.ArrivalHomeModel1;
import com.kayla.Model.CategoryModel;

import com.kayla.Model.FeatureModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {
    ArrayList<ArrivalHomeModel1.Data> dataArrayList;

    ArrayList<CategoryModel.Data> homeModelArrayList;
    RecyclerView recycleview;


    ArrayList<SliderImageData.Data> sliderImageModelArrayList;
    ArrayList<FeatureModel.Data> featureModelArrayList;


    RecyclerView recycle_newhome;
    RecyclerView recycleview_feature;

    HomeAdapter homeAdapter;
    FeatureAdapter featureAdapter;
    SliderAdapterExample sliderAdapter;

    HomeArrivalAdapter homeArrivalAdapter;

    SliderView imageSlider;
    TextView cat_seeall, txt_favriteall,txt_newseeall;
    ImageView backbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cat_seeall = view.findViewById(R.id.cat_seeall);
        txt_newseeall = view.findViewById(R.id.txt_newseeall);
        txt_favriteall = view.findViewById(R.id.txt_favriteall);

        recycleview = view.findViewById(R.id.recycleview);

        recycleview_feature = view.findViewById(R.id.recycleview_feature);
        recycle_newhome = view.findViewById(R.id.recycle_newhome);

        imageSlider = view.findViewById(R.id.imageSlider);
        backbtn = view.findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.drawer.openDrawer(GravityCompat.START);
                //HomeActivity.drawer.openDrawer(GravityCompat.START);
            }
        });

        recycleview_feature.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recycle_newhome.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recycleview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        cat_seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CategoryActivity.class));
            }
        });

        txt_favriteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstats.sharedpreferences =getActivity().getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.FavPagestatus,"1");

                editor.apply();

                startActivity(new Intent(getActivity(), FeatureActivity.class));
            }
        });

        txt_newseeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstats.sharedpreferences =getActivity().getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.NawPagestatus,"1");

                editor.apply();

                startActivity(new Intent(getActivity(), NewArrivalActivity.class));
            }
        });

        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        imageSlider.setIndicatorSelectedColor(Color.BLUE);
        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        imageSlider.setScrollTimeInSec(3);
        imageSlider.startAutoCycle();

        //calling api
        showSlider();
        homeCategory();
        feature();
        arival();
        return view;

    }
    //homeCategory api
    public void arival(){
        AndroidNetworking.post(API_BaseUrl.show_new_arrival)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ArrivalHomeModel1 featureModel = gson.fromJson(response.toString(), ArrivalHomeModel1.class);
                        dataArrayList=new ArrayList<>();

                        try {
                            if (response.getString("status").equals("true")) {
                                dataArrayList.addAll(featureModel.getData());
                                homeArrivalAdapter = new HomeArrivalAdapter(dataArrayList, getActivity());
                                recycle_newhome.setAdapter(homeArrivalAdapter);
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

    //homeCategory api
    public void homeCategory() {
        AndroidNetworking.post(API_BaseUrl.show_catetory)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        CategoryModel category1Model = gson.fromJson(response.toString(), CategoryModel.class);
                       homeModelArrayList = new ArrayList<>();

                        try {
                            if (response.getString("status").equals("true")) {
                               homeModelArrayList.addAll(category1Model.getData());
                                homeAdapter = new HomeAdapter(homeModelArrayList, getContext());
                                recycleview.setAdapter(homeAdapter);
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




    //showSlider api
    public void showSlider() {
        AndroidNetworking.post(API_BaseUrl.show_banner)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        SliderImageData sliderImageData = gson.fromJson(response.toString(), SliderImageData.class);
                        sliderImageModelArrayList = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {

                            sliderImageModelArrayList.addAll(sliderImageData.getData());

                            sliderAdapter = new SliderAdapterExample(sliderImageModelArrayList, getActivity());
                            imageSlider.setSliderAdapter(sliderAdapter);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    //feature api
    public void feature() {
        AndroidNetworking.post(API_BaseUrl.show_featured)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FeatureModel featureModel = gson.fromJson(response.toString(), FeatureModel.class);
                        featureModelArrayList=new ArrayList<>();
                        try {
                            if (response.getString("status").equals("true")) {
                                featureModelArrayList.addAll(featureModel.getData());
                                featureAdapter = new FeatureAdapter(featureModelArrayList, getActivity());
                                recycleview_feature.setAdapter(featureAdapter);

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