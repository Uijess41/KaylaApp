package com.kayla.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kayla.Model.ShowProductDetailsModel;
import com.kayla.Model.SliderImageData;
import com.kayla.R;

import java.util.ArrayList;

public class ItemBainerAdapter extends RecyclerView.Adapter<ItemBainerAdapter.ViewHolder> {

    ArrayList<ShowProductDetailsModel.Data> showproDataArrayList;
    Context context;


    public ItemBainerAdapter(ArrayList<ShowProductDetailsModel.Data> showproDataArrayList, Context context) {
        this.showproDataArrayList = showproDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sliderimage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ShowProductDetailsModel.Data model=showproDataArrayList.get(position);

        Glide.with(context).load(model.getPath()+model.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return showproDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }

}