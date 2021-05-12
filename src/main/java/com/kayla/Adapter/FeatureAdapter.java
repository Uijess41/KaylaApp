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
import com.kayla.Activity.MessageActivity;
import com.kayla.Model.FeatureModel;
import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    ArrayList<FeatureModel.Data> featureModelArrayList;
    Context context;
    String FavPagestatus = "";

    public FeatureAdapter(ArrayList<FeatureModel.Data> featureModelArrayList, Context context) {
        this.featureModelArrayList = featureModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feature, parent, false);

        AppConstats.sharedpreferences = context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        FavPagestatus = AppConstats.sharedpreferences.getString(AppConstats.FavPagestatus, "");
        Log.e("FeatureAdapter", "onCreateViewHolder: " + FavPagestatus);
        return new FeatureAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeatureModel.Data featureModel = featureModelArrayList.get(position);
        Glide.with(context).load(featureModel.getPath() + featureModel.getImage()).into(holder.img_feature);
        holder.womantshirt.setText(featureModel.getProduct_title());
        holder.txt_price.setText(featureModel.getMRP());
        if (FavPagestatus.equals("1")) {
            holder.img.setVisibility(View.GONE);
        } else {
            holder.img.setVisibility(View.VISIBLE);
        }

        holder.img_feature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstats.sharedpreferences =context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.Product_id,featureModel.getId());

               Log.e("CategoryAdapter", "onClick: " +featureModel.getId());
                editor.apply();
                context.startActivity(new Intent(context, ItemDetailsActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return featureModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_feature, img,notification;
        TextView womantshirt, txt_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_feature = itemView.findViewById(R.id.img_feature);
            img = itemView.findViewById(R.id.img);
            notification = itemView.findViewById(R.id.notification);
            womantshirt = itemView.findViewById(R.id.womantshirt);
            txt_price = itemView.findViewById(R.id.txt_price);
        }
    }
}
