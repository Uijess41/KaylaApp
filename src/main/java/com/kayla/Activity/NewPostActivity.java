package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;
import com.kayla.Adapter.BrandAdapter;
import com.kayla.Adapter.CategoryAdapter;
import com.kayla.Model.BrandModel;
import com.kayla.Model.BrandModel1;
import com.kayla.Model.CategoryModel;
import com.kayla.Model.PostModel;
import com.kayla.Model.SubcattitleModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    ArrayList<String> catNameList, catIdList;
    ArrayList<String> subCatNameList, subCatIdList;
    ArrayList<String> brandNameList, brandIdList;
    ArrayList<String> conditionNameList, conditionIdList;
    ArrayList<String> LGANameList, LGAIdList;
    ArrayList<String> stateNameList, stateIdList;
    AutoCompleteTextView categorySpinner, subCategorySpinner, brandSpinner,canditionSpinner,stateSpinner,LGASpinner;

    String state_id="",seller_id="",category_id="",sub_category_id="",product_title="",product_description="",mrp="",discount="",selling_price="",
            stock="",color="",size="",dress_name="",year_purches="",fabric="",location_state="",location_LGA="",image="",type="",draft_post="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        categorySpinner = findViewById(R.id.categorySpinner);
        subCategorySpinner = findViewById(R.id.subCategorySpinner);
        brandSpinner = findViewById(R.id.brandSpinner);
        canditionSpinner = findViewById(R.id.canditionSpinner);
        stateSpinner = findViewById(R.id.stateSpinner);
        LGASpinner = findViewById(R.id.LGASpinner);


        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        Log.e("hjghf", "onCreateViewHolder: "+state_id );
        state_id = AppConstats.sharedpreferences.getString(AppConstats.STATE_ID, "");
        showcategory();

        categorySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String catId = catIdList.get(position);

                Toast.makeText(NewPostActivity.this, catId, Toast.LENGTH_SHORT).show();

                show_SubCatItems(catId);

            }
        });


        stateSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String stateId = stateIdList.get(position);
                AppConstats.sharedpreferences =getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.STATE_ID,state_id);
                Log.e("sdsds", "state_id: "+state_id );
                editor.apply();

                show_city(stateId);

            }
        });

        sellitem();

        brand();

        show_condition();
        show_state();

    }

    public void showcategory() {

        AndroidNetworking.post(API_BaseUrl.show_catetory)

                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ghfghgs", "showcategory: "+response );
                        Gson gson = new Gson();
                        CategoryModel category1Model = gson.fromJson(response.toString(), CategoryModel.class);

                        try {
                            if (response.getString("status").equals("true")) {

                                catIdList = new ArrayList<>();
                                catNameList = new ArrayList<>();

                                List<CategoryModel.Data> dataList = category1Model.getData();

                                for (CategoryModel.Data data : dataList) {

                                    Log.e("kcmksdcms", data.getName() + "");
                                    catNameList.add(data.getName());
                                    catIdList.add(data.getId());
                                }


                                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_list_item_1, catNameList);
                                categorySpinner.setAdapter(adapter);
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


    private void show_SubCatItems(String catID) {
        Log.e("jhghjfsahc", "show_SubCatItems: "+ catID);
        AndroidNetworking.post(API_BaseUrl.show_subcategory_by_catid)

                .addBodyParameter("category_id", catID)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("dsfsfvdsv", "onResponse: " + response);
                        try {
                            if (response.getString("status").equals("true")) {
                                subCatIdList = new ArrayList<>();
                                subCatNameList = new ArrayList<>();
                                JSONArray jsonArray = new JSONArray(response.getString("data"));
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    subCatNameList.add(jsonObject.getString("name"));
                                    subCatIdList.add(jsonObject.getString("id"));

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_list_item_1, subCatNameList);
                                subCategorySpinner.setAdapter(adapter);

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



    public void brand() {
        AndroidNetworking.post(API_BaseUrl.show_brand)
                .setPriority(Priority.HIGH)
                .setTag("text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hjdsghs", "onResponse: "+response );

                        try {
                            JSONArray jsonArray = new JSONArray(response.getString("data"));
                            brandIdList = new ArrayList<>();
                            brandNameList = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("jskdgjsj", "onResponse: "+(jsonObject.getString("name")));
                                brandNameList.add(jsonObject.getString("name"));
                                brandIdList.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_list_item_1, brandNameList);
                            brandSpinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e("jhsfgh", "onResponse: "+e.getMessage() );
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("hjdghdg", "onError: "+anError.getMessage());

                    }
                });
    }

    public void show_condition(){
        AndroidNetworking.post(API_BaseUrl.show_condition)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.getString("data"));
                            conditionNameList=new ArrayList<>();
                            conditionIdList=new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("fdhdfhd", "onResponse: "+(jsonObject.getString("name")));
                                conditionNameList.add(jsonObject.getString("name"));
                                conditionIdList.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_list_item_1, conditionNameList);
                            canditionSpinner.setAdapter(adapter);


                        } catch (JSONException e) {
                            Log.e("dsvgshvh", "onResponse: "+e.getMessage() );
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("dsgfghsgh", "onError: "+anError.getMessage() );
                    }
                });
    }
    public void show_state(){
        AndroidNetworking.post(API_BaseUrl.show_state)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.getString("data"));
                            stateNameList=new ArrayList<>();
                            stateIdList=new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("jskdgjsj", "onResponse: "+(jsonObject.getString("name")));
                                stateNameList.add(jsonObject.getString("name"));
                                stateIdList.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_list_item_1, stateNameList);
                            stateSpinner.setAdapter(adapter);


                        } catch (JSONException e) {
                            Log.e("fcgcfg", "onResponse: "+e.getMessage() );
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("gfgd", "onResponse: "+anError.getMessage());

                    }
                });
    }

    public void show_city(String stateId){
        Log.e("sgdhde", "state_id: "+ stateId);
        AndroidNetworking.post(API_BaseUrl.show_city)
                .addBodyParameter("state_id",stateId)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response.getString("data"));
                            LGANameList=new ArrayList<>();
                            LGAIdList=new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Log.e("jskdgjsj", "onResponse: "+(jsonObject.getString("name")));
                                LGANameList.add(jsonObject.getString("name"));
                                LGAIdList.add(jsonObject.getString("id"));

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(NewPostActivity.this, android.R.layout.simple_list_item_1, stateNameList);
                            LGASpinner.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e("fcgcfg", "onResponse: "+e.getMessage() );
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("gfgd", "onResponse: "+anError.getMessage());

                    }
                });



    }



    public void sellitem(){
        AndroidNetworking.post(API_BaseUrl.sell_an_item)
                .addBodyParameter("seller_id",seller_id)
                .addBodyParameter("category_id",category_id)
                .addBodyParameter("sub_category_id",sub_category_id)
                .addBodyParameter("product_title",product_title)
                .addBodyParameter("product_description",product_description)
                .addBodyParameter("mrp",mrp)
                .addBodyParameter("discount",discount)
                .addBodyParameter("selling_price",selling_price)
                .addBodyParameter("stock",stock)
                .addBodyParameter("color",color)
                .addBodyParameter("size",size)
                .addBodyParameter("dress_name",dress_name)
                .addBodyParameter("year_purches",year_purches)
                .addBodyParameter("fabric",fabric)
                .addBodyParameter("location_state",location_state)
                .addBodyParameter("location_LGA",location_LGA)
                .addBodyParameter("image",image)
                .addBodyParameter("type",type)
                .addBodyParameter("draft_post",draft_post)
                .setPriority(Priority.HIGH)
                .setTag("Test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("result").equals("successfully")){

                                JSONObject jsonObject=new JSONObject(response.getString("data"));
                                PostModel postModel = new PostModel();

                                String id = jsonObject.getString("id");

                                Log.e("sgfgdfshj", "onResponse: "+ (jsonObject.getString("seller_id")));
                                postModel.setSeller_id(jsonObject.getString("seller_id"));
                                postModel.setCategory_id(jsonObject.getString("category_id"));
                                postModel.setSub_category_id(jsonObject.getString("sub_category_id"));
                                postModel.setProduct_title(jsonObject.getString("product_title"));
                                postModel.setProduct_description(jsonObject.getString("product_description"));
                                postModel.setMrp(jsonObject.getString("MRP"));
                                postModel.setDiscount(jsonObject.getString("discount"));
                                postModel.setSelling_price(jsonObject.getString("selling_price"));
                                postModel.setStock(jsonObject.getString("stock"));
                                postModel.setImage(jsonObject.getString("image"));
                                postModel.setColor(jsonObject.getString("color"));
                                postModel.setSize(jsonObject.getString("size"));
                                postModel.setBrand(jsonObject.getString("brand"));
                                postModel.setVerify_status(jsonObject.getString("verify_status"));
                                postModel.setPath(jsonObject.getString("path"));
                            }
                        } catch (JSONException e) {
                            Log.e(" ", "onResponse: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("fghfhdgdg", "onError: "+anError.getMessage());
                    }
                });
    }
}