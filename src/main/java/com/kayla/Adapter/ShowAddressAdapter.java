package com.kayla.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.Activity.CatWomenActivity;
import com.kayla.Activity.CheckoutActivity;
import com.kayla.Activity.ShowAddressActivity;
import com.kayla.Activity.UpdateAddressActivity;
import com.kayla.Model.CategoryModel;
import com.kayla.Model.ShowAddress;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.kayla.other.AppConstats.AppConstats.Address;
import static com.kayla.other.AppConstats.AppConstats.Address_Id;

public class ShowAddressAdapter extends RecyclerView.Adapter<ShowAddressAdapter.ViewHolder> {

    ArrayList<ShowAddress.Data.AllAddres> showAllAddresArrayList;
    Context context;
     String user_id="",address_id="",address;

    public ShowAddressAdapter(ArrayList<ShowAddress.Data.AllAddres> showAllAddresArrayList, Context context) {
        this.showAllAddresArrayList = showAllAddresArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.showaddress, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ShowAddress.Data.AllAddres showAddress=showAllAddresArrayList.get(position);
        holder.txt_address.setText(showAddress.getAddress());
        holder.txt_name.setText(showAddress.getName());
        holder.txt_postalcode.setText(showAddress.getPincode());

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              delete_address(showAddress.getId());

            }
        });

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstats.sharedpreferences=context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.Address_Id,showAddress.getId());
                editor.apply();
                Log.e("ShowAddressAdapter", "onClick: " +showAddress.getId());
                context.startActivity(new Intent(context, UpdateAddressActivity.class));
            }
        });

        holder.txt_rdobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstats.sharedpreferences =context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();

                editor.putString(Address_Id,showAddress.getId());
                editor.putString(Address,showAddress.getAddress());
                Log.e("fdghsf", "onClick: "+ showAddress.getId()+","+showAddress.getAddress());
                editor.apply();
                context.startActivity(new Intent(context.getApplicationContext(), CheckoutActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return showAllAddresArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_address,txt_name,txt_postalcode;
        ImageView update,img_delete;
        RadioButton txt_rdobtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_address = itemView.findViewById(R.id.txt_address);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_postalcode = itemView.findViewById(R.id.txt_postalcode);
            update = itemView.findViewById(R.id.update);
            img_delete = itemView.findViewById(R.id.img_delete);
            txt_rdobtn = itemView.findViewById(R.id.txt_rdobtn);
        }
    }
    public void delete_address(String id){

        AppConstats.sharedpreferences = context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        AndroidNetworking.post(API_BaseUrl.delete_address)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("id",id)
                .setPriority(Priority.HIGH)
                .setTag("Text")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ShowAddressAdapter", "onResponse: " +response);

                        try {
                            if (response.getString("result").equals("successfully")){
                                Toast.makeText(context, "Address Delete successfully", Toast.LENGTH_SHORT).show();
                                 context.startActivity(new Intent(context,ShowAddressActivity.class));

                            }else {
                                Toast.makeText(context, "Address Not Delete successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("ShowAddressAdapter", "e: " +e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ShowAddressAdapter", "anError: " +anError);
                    }
                });
    }
}