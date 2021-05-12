package com.kayla.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.Model.SubCatData;
import com.kayla.Model.SubcattitleModel;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CatWomanAdapter extends RecyclerView.Adapter<CatWomanAdapter.ViewHolder> {

    ArrayList<SubcattitleModel.Data> subcatModelArrayList;
    ArrayList<SubCatData> subCatDataArrayList;
    Context context;

    public CatWomanAdapter(ArrayList<SubcattitleModel.Data> subcatModelArrayList, Context context) {
        this.subcatModelArrayList = subcatModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subcat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SubcattitleModel.Data showproduct = subcatModelArrayList.get(position);
        Log.e("CatWomanAdapter", "vgnvn: " + showproduct.getName());
        holder.title.setText(showproduct.getName());
        Log.e("CatWomanAdapter", "hrthjrt: " + showproduct.getName());

        holder.rvImage.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.rvImage.setHasFixedSize(true);
        holder.rvImage.setItemViewCacheSize(20);
        showSubCategory(showproduct.getId(), holder.rvImage);

    }

    @Override
    public int getItemCount() {
        return subcatModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        RecyclerView rvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rvImage = itemView.findViewById(R.id.rvImage);
        }
    }

    private void showSubCategory(String id, RecyclerView recyclerView) {

        AndroidNetworking.post(API_BaseUrl.show_products_bysubcat)
                .addBodyParameter("subcategory_id", id)
                .setTag("subCat")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        if (response != null) {

                            try {

                                if (response.getString("status").equals("true")) {
                                    subCatDataArrayList = new ArrayList<>();
                                    Log.e("lskxcl", response.getString("data"));

                                    JSONArray jsonArray = new JSONArray(response.getString("data"));
                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        SubCatData catData = new SubCatData();
                                        catData.setImage(jsonObject.getString("image"));
                                        catData.setPath(jsonObject.getString("path"));


                                        Log.e("xclkslcx", jsonObject.getString("image"));
                                        subCatDataArrayList.add(catData);

                                    }

                                    recyclerView.setAdapter(new CatWomanAdapter2(subCatDataArrayList, context));


                                }

                            } catch (Exception e) {
                                Log.e("posda", e.getMessage());
                            }


                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("posda", anError.getMessage());
                    }
                });
    }

}