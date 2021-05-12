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

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;
import com.kayla.Activity.OrderStatusActivity;
import com.kayla.Activity.ReturnRequestActivity;
import com.kayla.Model.DemoModel;
import com.kayla.Model.MyOrderModel;
import com.kayla.R;
import com.kayla.other.AppConstats.AppConstats;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;


public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    ArrayList<MyOrderModel>myOrderModelArrayList;
    Context context;

    String user_id="",order_id;

    public MyOrderAdapter(ArrayList<MyOrderModel> myOrderModelArrayList, Context context) {
        this.myOrderModelArrayList = myOrderModelArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myorder, parent, false);
        AppConstats.sharedpreferences = context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        Log.e("hjghf", "onCreateViewHolder: "+user_id );
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        return new MyOrderAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyOrderModel myOrderModel=myOrderModelArrayList.get(position);

        holder.txt_title.setText(myOrderModel.getProduct_description());
      holder.txt_tshirt.setText(myOrderModel.getProduct_title());
        holder.txtdoller.setText(myOrderModel.getMRP());
        Glide.with(context).load(myOrderModel.getPath()+ myOrderModel.getImage()).into(holder.img_sea);
        holder.ordernu.setText(String.valueOf(myOrderModel.getOrder_id()));
        if (myOrderModel.getStatus().equals("1")){
            holder.img_return.setText("pending");
            holder.img_return.setBackgroundColor(context.getResources().getColor(R.color.design_default_color_error));
        }else if (myOrderModel.getStatus().equals("2")){

            holder.img_return.setText("Delivered");
            holder.img_return.setBackgroundColor(context.getResources().getColor(R.color.teal_200));
        }else if (myOrderModel.getStatus().equals("3")){
            holder.img_return.setText("Returned");
        }else if (myOrderModel.getStatus().equals("4")){
            holder.img_return.setText("Cancelled");
        }else {

        }

        holder.btnstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstats.sharedpreferences =context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = AppConstats.sharedpreferences.edit();
                editor.putString(AppConstats.User_id,user_id);
                editor.putString(AppConstats.ORDER_ID,myOrderModel.getOrder_id());
                Log.e("jgjghj", "onClick: "+user_id );
                Log.e("jgjghj", "onClick: "+myOrderModel.getOrder_id());
                editor.apply();
                context.startActivity(new Intent(context.getApplicationContext(), OrderStatusActivity.class));

            }
        });

        holder.img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // context.startActivity(new Intent(context.getApplicationContext(), ReturnRequestActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_sea;
        MaterialTextView btnstatus;
        TextView txt_tshirt,txt_title,txtdoller,order,ordernu,img_return;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_sea=itemView.findViewById(R.id.img_sea);
            txt_tshirt=itemView.findViewById(R.id.txt_tshirt);
            btnstatus=itemView.findViewById(R.id.btnstatus);
            txt_title=itemView.findViewById(R.id.txt_title);
            txtdoller=itemView.findViewById(R.id.txtdoller);
            order=itemView.findViewById(R.id.order);
            ordernu=itemView.findViewById(R.id.ordernu);
            img_return=itemView.findViewById(R.id.img_return);

        }
    }
}
