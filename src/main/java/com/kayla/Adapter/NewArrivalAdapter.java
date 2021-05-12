package com.kayla.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kayla.Activity.ItemDetailsActivity;
import com.kayla.Model.ArrivalHomeModel1;
import com.kayla.Model.NewArrivalModel;
import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.ViewHolder> {

    ArrayList<ArrivalHomeModel1.Data> newArrivalModelArrayList;
    Context context;


    public NewArrivalAdapter(ArrayList<ArrivalHomeModel1.Data> newArrivalModelArrayList, Context context) {
        this.newArrivalModelArrayList = newArrivalModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.arrival, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrivalHomeModel1.Data newArrivalModel=newArrivalModelArrayList.get(position);
        Glide.with(context).load(newArrivalModel.getPath()+newArrivalModel.getImage()).into(holder.img);

        holder.txt_puff.setText(newArrivalModel.getProduct_title());
        holder.pricepuff.setText(newArrivalModel.getMRP());
//product details
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstats.sharedpreferences =context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.Product_id,newArrivalModel.getId());

                Log.e("CategoryAdapter", "onClick: " +newArrivalModel.getId());
                editor.apply();
                context.startActivity(new Intent(context, ItemDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newArrivalModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_puff,pricepuff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img);
            txt_puff = itemView.findViewById(R.id.txt_puff);
            pricepuff = itemView.findViewById(R.id.pricepuff);
        }
    }

}