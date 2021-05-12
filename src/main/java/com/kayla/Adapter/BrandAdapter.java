package com.kayla.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kayla.Model.BrandModel;
import com.kayla.Model.CategoryModel;
import com.kayla.R;

import java.util.ArrayList;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder>{
    ArrayList<BrandModel.Data> brandDataArrayList;

    Context context;

    public BrandAdapter(ArrayList<BrandModel.Data> brandDataArrayList, Context context) {
        this.brandDataArrayList = brandDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brand, parent, false);
        return new BrandAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BrandModel.Data brandModel=brandDataArrayList.get(position);
        Log.e("kljdlkfjkl", "onBindViewHolder: "+brandModel.getPath()+brandModel.getImage());
        Glide.with(context).load(brandModel.getPath()+brandModel.getImage()).into(holder.brand_img);
    }

    @Override
    public int getItemCount() {
        return brandDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView brand_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brand_img = itemView.findViewById(R.id.brand_img);
        }
    }
}


