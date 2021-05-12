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

import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class HomeArrivalAdapter extends RecyclerView.Adapter<HomeArrivalAdapter.ViewHolder> {
    ArrayList<ArrivalHomeModel1.Data> arrivalHomeModelArrayList;
    Context context;

    public HomeArrivalAdapter(ArrayList<ArrivalHomeModel1.Data> arrivalHomeModelArrayList, Context context) {
        this.arrivalHomeModelArrayList = arrivalHomeModelArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.arrival1, parent, false);
        return new HomeArrivalAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrivalHomeModel1.Data arrivaModel=arrivalHomeModelArrayList.get(position);

        Glide.with(context).load(arrivaModel.getPath()+arrivaModel.getImage()).into(holder.img);
       // Glide.with(context).load(arrivaModel.getPath()+arrivaModel.getImage()).into(holder.img2);
        holder.txt_arrivalprice.setText(arrivaModel.getMRP());
        holder.txt_womantShirt.setText(arrivaModel.getProduct_title());

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstats.sharedpreferences =context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.Product_id,arrivaModel.getId());

                Log.e("CategoryAdapter", "onClick: " +arrivaModel.getId());
                editor.apply();
                context.startActivity(new Intent(context, ItemDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrivalHomeModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img,img2;
        TextView txt_arrivalprice,txt_womantShirt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
           // img=itemView.findViewById(R.id.img2);
            txt_arrivalprice=itemView.findViewById(R.id.txt_arrivalprice);
            txt_womantShirt=itemView.findViewById(R.id.txt_womantShirt);
        }
    }
}
