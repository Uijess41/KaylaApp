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

import com.kayla.Activity.CatWomenActivity;
import com.kayla.Model.CategoryModel;

import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryModel.Data> categoryModelArrayList;

    Context context;
    String id="";

    public CategoryAdapter(ArrayList<CategoryModel.Data> categoryModelArrayList, Context context) {
        this.categoryModelArrayList = categoryModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CategoryModel.Data categorymodel=categoryModelArrayList.get(position);

        Log.e("jhfhgfh", "onBindViewHolder: "+categorymodel.getPath()+categorymodel.getImage());

        Glide.with(context).load(categorymodel.getPath()+categorymodel.getImage()).into(holder.cateimgage);

        holder.cat_name.setText(categorymodel.getName());

        holder.cateimgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstats.sharedpreferences =context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.CATEGORYId, categorymodel.getId());
                editor.putString(AppConstats.IMAGE,categorymodel.getPath()+categorymodel.getImage());
                editor.putString(AppConstats.NAME,categorymodel.getName());
                editor.apply();

                Log.e("CategoryAdapter", "onClick: " +categorymodel.getId());
                Log.e("CategoryAdapter", "onClick: " +categorymodel.getImage());
                context.startActivity(new Intent(context, CatWomenActivity.class));
            }

        });
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

       ImageView cateimgage;
       TextView cat_name,cat_seeall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cat_name = itemView.findViewById(R.id.cat_name);
            cat_seeall = itemView.findViewById(R.id.cat_seeall);
            cateimgage = itemView.findViewById(R.id.cateimgage);

        }
    }

}