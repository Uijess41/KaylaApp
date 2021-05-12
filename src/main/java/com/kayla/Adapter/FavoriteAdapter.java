package com.kayla.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kayla.Activity.PostActivity;
import com.kayla.Model.DemoModel;
import com.kayla.Model.FavoriteModel;
import com.kayla.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    ArrayList<FavoriteModel> favoriteModelArrayList;
    Context context;

    public FavoriteAdapter(ArrayList<FavoriteModel> favoriteModelArrayList, Context context) {
        this.favoriteModelArrayList = favoriteModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorit, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FavoriteModel favoriteModel=favoriteModelArrayList.get(position);

        Glide.with(context).load(favoriteModel.getPath()+ favoriteModel.getImage()).into(holder.favoriteimg);
        Log.e("casca", "onBindViewHolder: "+favoriteModel.getName() );

        holder.txt_name.setText(favoriteModel.getName());
        Log.e("casca", "onBindViewHolder: "+favoriteModel.getPrice() );
        holder.dollerprice4.setText(favoriteModel.getPrice());

        holder.favoriteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context.getApplicationContext(), PostActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteimg;
        TextView txt_name,dollerprice4;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            favoriteimg=itemView.findViewById(R.id.favoriteimg);
            dollerprice4=itemView.findViewById(R.id.dollerprice4);

        }
    }
}
