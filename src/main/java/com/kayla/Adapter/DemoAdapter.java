package com.kayla.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kayla.Model.DemoModel;
import com.kayla.R;

import java.util.ArrayList;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {

    ArrayList<DemoModel> demoModelArrayList;
    Context context;


    public DemoAdapter(ArrayList<DemoModel> demoModelArrayList, Context context) {
        this.demoModelArrayList = demoModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.demo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DemoModel demoModel = demoModelArrayList.get(position);
        holder.img.setImageResource(R.drawable.womenimg);
        holder.name.setText(demoModel.getName());
    }

    @Override
    public int getItemCount() {
        return demoModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            name = itemView.findViewById(R.id.name);
        }
    }

}