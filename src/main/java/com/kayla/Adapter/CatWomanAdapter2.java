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
import com.kayla.Model.SubCatData;
import com.kayla.R;

import java.util.ArrayList;


public class CatWomanAdapter2 extends RecyclerView.Adapter<CatWomanAdapter2.ViewHolder> {

    ArrayList<SubCatData> subcatModelArrayList;
    Context context;

    public CatWomanAdapter2(ArrayList<SubCatData> subcatModelArrayList, Context context) {
        this.subcatModelArrayList = subcatModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subcat_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubCatData showproduct = subcatModelArrayList.get(position);

        Glide.with(context).load(showproduct.getPath() + showproduct.getImage()).into(holder.dress_img);


    }

    @Override
    public int getItemCount() {
        return subcatModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgage, dress_img;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgage = itemView.findViewById(R.id.imgage);
            dress_img = itemView.findViewById(R.id.dress_img);

        }
    }

}