package com.kayla.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.kayla.Activity.CartActivity;
import com.kayla.Model.CartModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    ArrayList<CartModel> showDataArrayList;
    Context context;

    String Qty = "",user_id="",address_id="";
    int quantity;

    public CartAdapter(ArrayList<CartModel> showDataArrayList, Context context) {
        this.showDataArrayList = showDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart, parent, false);
        AppConstats.sharedpreferences = context.getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        Log.e("ghfhhf", user_id);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");

        address_id = AppConstats.sharedpreferences.getString(AppConstats.Address_Id, "");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartModel cartDemoModel = showDataArrayList.get(position);
        // Glide.with(context).load(cartDemoModel.getPath()+cartDemoModel.getImage()).into(holder.img_sea);

        holder.txt_producttitle.setText(cartDemoModel.getProduct_title());
        Log.e("CatWomanAdapter", "vgnvn: " + cartDemoModel.getProduct_description());
        holder.productname.setText(cartDemoModel.getProduct_description());
        holder.price.setText(cartDemoModel.getMRP());
        holder.txt_quntity.setText(cartDemoModel.getQntity());



        Log.e("cjshjgj", "onCreateViewHolder: " + cartDemoModel.getPath() + cartDemoModel.getImage());

        Glide.with(context).load(cartDemoModel.getPath()+ cartDemoModel.getImage()).into(holder.img_sea);
        Log.e("hjfsaghf", "onBindViewHolder: " + cartDemoModel.getPath() + cartDemoModel.getImage());
        Log.e("CatWomanAdapter", "vgnvn: " + cartDemoModel.getProduct_description());


        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_cart(user_id,cartDemoModel.getId(),cartDemoModel);
                Log.e("jdgshsgh", "onClick: "+cartDemoModel.getId());
                Log.e("jdgshsgh", "onClick: "+user_id);

            }
        });

        holder.txt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qty = holder.txt_quntity.getText().toString().trim();
                int qty = Integer.parseInt(Qty) + 1;
                holder.txt_quntity.setText(qty + "");
                update_quantity(cartDemoModel.getId(), qty + "");
                Log.e("kjhjk", "onClick: " + qty + "");
              // Toast.makeText(context, "Quantity updated add" + cartDemoModel.getId(), Toast.LENGTH_LONG).show();
            }
        });

        holder.txtsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int val = Integer.parseInt(holder.txt_quntity.getText().toString().trim());

                if (val != 1) {
                    val--;
                    holder.txt_quntity.setText(String.valueOf(val));
                   // Toast.makeText(context, "Quantity updated", Toast.LENGTH_SHORT).show();
                } else {

                    holder.txt_quntity.setText(String.valueOf(val));
                    update_quantity(cartDemoModel.getId(), val + "");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return showDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_sea,img_delete;
        TextView productname, txt_producttitle, price, txt_add, txtsub, txt_quntity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_sea = itemView.findViewById(R.id.img_sea);
            productname = itemView.findViewById(R.id.productname);
            txt_producttitle = itemView.findViewById(R.id.txt_producttitle);
            txt_add = itemView.findViewById(R.id.txt_add);
            txtsub = itemView.findViewById(R.id.txtsub);
            price = itemView.findViewById(R.id.price);
            txt_quntity = itemView.findViewById(R.id.txt_quntity);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }

    public void delete_cart(String user_id,String id,CartModel model){
        Log.e("TAadasG", "user: "+user_id +" ,"+id);
        AndroidNetworking.post(API_BaseUrl.delete_cart)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("id",id)
                .setPriority(Priority.HIGH)
                .setTag("Test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("result").equals("successfully")){
                                Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                                showDataArrayList.remove(model);
                                if (context instanceof CartActivity) {
                                    ((CartActivity) context).show_Cart();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    //implement update_quantity api
    public void update_quantity(String id, String qty) {
        Log.e("rhffsfsffsfsf", "update_quantity: "+id+","+qty);
        AndroidNetworking.post(API_BaseUrl.update_cart)
                .addBodyParameter("id", id)
                .addBodyParameter("quantity", qty)
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("ddsgsgsfsgssff", response.toString() );
                        try {
                            if (response.getString("result").equals("sucessfull")) {
                                Toast.makeText(context, "Quantity_Update successfully", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("agesrrgfgdfhdh", "onResponse: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("agesrrgfgdfhdh", "onError: " + anError.getMessage());
                    }
                });

    }

}