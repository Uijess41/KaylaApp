package com.kayla.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import com.kayla.Model.CategoryModel;

import com.kayla.R;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    ArrayList<CategoryModel.Data> homeModelArrayList;

    Context context;


    public HomeAdapter(ArrayList<CategoryModel.Data> homeModelArrayList, Context context) {
        this.homeModelArrayList = homeModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel.Data categorymodel=homeModelArrayList.get(position);
        holder.cateimgage.setImageResource(R.drawable.womenimg);
        Glide.with(context).load(categorymodel.getPath()+categorymodel.getImage()).into(holder.cateimgage);
        holder.cat_name.setText(categorymodel.getName());

    }

    @Override
    public int getItemCount()
    {
        return homeModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cat_name;
        ImageView cateimgage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cateimgage=itemView.findViewById(R.id.cateimgage);
            cat_name = itemView.findViewById(R.id.cat_name);
        }
    }

}