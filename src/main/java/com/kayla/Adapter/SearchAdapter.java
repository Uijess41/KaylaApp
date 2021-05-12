package com.kayla.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kayla.Model.DemoModel;
import com.kayla.Model.MyOrderModel;
import com.kayla.Model.SearchModel;
import com.kayla.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    ArrayList<SearchModel> searchModelArrayList;
    Context context;


    public SearchAdapter(ArrayList<SearchModel> searchModelArrayList, Context context) {
        this.searchModelArrayList = searchModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search, parent, false);
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SearchModel searchModel=searchModelArrayList.get(position);
        holder.img.setImageResource(R.drawable.blueimg);
    }

    @Override
    public int getItemCount() {
        return searchModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }
}
