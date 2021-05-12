package com.kayla.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kayla.Activity.PaymentActivity;
import com.kayla.Model.DemoModel;
import com.kayla.Model.PaymentModel1;
import com.kayla.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    ArrayList<PaymentModel1> paymentAdapterArrayList;
    Context context;
    int  selectionposition=-1
;
    public PaymentAdapter(ArrayList<PaymentModel1> paymentAdapterArrayList, Context context) {
        this.paymentAdapterArrayList = paymentAdapterArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment, parent, false);
        return new PaymentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentModel1 paymentModel1=paymentAdapterArrayList.get(position);
        holder.cardnum.setText(paymentModel1.getCard_no());
        holder.expiry.setText(paymentModel1.getExpiry_date());
        holder.cvv.setText(paymentModel1.getCvv_no());
        holder.rd_cardid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectionposition=position;
                notifyDataSetChanged();
            }
        });
        if (selectionposition==position){
            holder.rd_cardid.setChecked(true);
            if (context instanceof PaymentActivity){
                ((PaymentActivity)context).GetCardDetail(paymentModel1.getCard_no(),paymentModel1.getExpiry_date(),paymentModel1.getCvv_no());
            }
        }else {
            holder.rd_cardid.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return paymentAdapterArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView cardnum,expiry,cvv;
        RadioButton rd_cardid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            cardnum=itemView.findViewById(R.id.cardnum);
            expiry=itemView.findViewById(R.id.expiry);
            cvv=itemView.findViewById(R.id.cvv);
            rd_cardid=itemView.findViewById(R.id.rd_cardid);
        }
    }

}
