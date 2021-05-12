package com.kayla.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kayla.Model.DemoModel;
import com.kayla.Model.PostModel;
import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    ArrayList<PostModel> postModelArrayList;

    Context context;

    String user_id="";

    public PostAdapter(ArrayList<PostModel> postModelArrayList, Context context) {
        this.postModelArrayList = postModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AppConstats.sharedpreferences = context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        Log.e("fdgddaw", "onCreateViewHolder: "+user_id );
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        View view = LayoutInflater.from(context).inflate(R.layout.post, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel postModel=postModelArrayList.get(position);

        Glide.with(context).load(postModel.getPath()+ postModel.getImage()).into(holder.img_sea);
        Log.e("TAG", "onBindViewHolder: "+postModel.getProduct_title());
        holder.txtprice.setText(postModel.getProduct_title());
        holder.txtdoller.setText(postModel.getMrp());
        holder.textViewTitle1.setText(postModel.getProduct_description());

    }

    @Override
    public int getItemCount() {
        return postModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_sea;
        TextView textViewTitle1,txtprice,txtdoller;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sea=itemView.findViewById(R.id.img_sea);
            textViewTitle1=itemView.findViewById(R.id.textViewTitle1);
            txtprice=itemView.findViewById(R.id.txtprice);
            txtdoller=itemView.findViewById(R.id.txtdoller);
        }
    }
}
