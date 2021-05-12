package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.kayla.Adapter.DemoAdapter;
import com.kayla.Adapter.PaymentAdapter;
import com.kayla.Model.DemoModel;
import com.kayla.Model.PaymentModel1;
import com.kayla.R;
import com.kayla.other.API_BaseUrl;
import com.kayla.other.AppConstats.AppConstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    PaymentAdapter paymentAdapter;
    ArrayList<PaymentModel1> paymentAdapterArrayList=new ArrayList<>();
    RecyclerView recycleview_payment;
    TextView addcard;
    Button btnpayment;
    String id="",user_id="",address_id="",address="",cod="",delivery_fee,total_amount="",CardNum="",Expiry="",Cvv="",User_Email="";
    RelativeLayout relative_add;
    private Card card;
    private Charge charge;
    TextView txt_error;
    int FinalMonth;
    int FinalYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PaystackSdk.initialize(getApplicationContext());
        setContentView(R.layout.activity_payment);
        addcard=findViewById(R.id.addcard);
        relative_add=findViewById(R.id.relative_add);
        btnpayment=findViewById(R.id.btnpayment);

        AppConstats.sharedpreferences = getSharedPreferences(AppConstats.MY_PREF, MODE_PRIVATE);
        user_id = AppConstats.sharedpreferences.getString(AppConstats.User_id, "");
        address_id = AppConstats.sharedpreferences.getString(AppConstats.Address_Id, "");
        address = AppConstats.sharedpreferences.getString(AppConstats.Address, "");
        cod = AppConstats.sharedpreferences.getString(AppConstats.Paymenttype, "");
        delivery_fee = AppConstats.sharedpreferences.getString(AppConstats.DELIVERY_FEE, "");
        total_amount = AppConstats.sharedpreferences.getString(AppConstats.TOTAL_AMOUNT, "");
        User_Email = AppConstats.sharedpreferences.getString(AppConstats.User_Email, "");


        Log.e("sdasajghag",user_id );
        txt_error=findViewById(R.id.txt_error);
        recycleview_payment=findViewById(R.id.recycleview_payment);

        recycleview_payment.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   checkout();
                if (!validateForm()) {
                    return;
                }
                try {


                    card = new Card(CardNum, FinalMonth, FinalYear, Cvv);

                    if (card.isValid()) {
                        Toast.makeText(PaymentActivity.this, "Card is Valid", Toast.LENGTH_LONG).show();
                        performCharge();
                    } else {
                        Toast.makeText(PaymentActivity.this, "Card not Valid", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

        relative_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),CreditCardActivity.class));
                finish();
            }
        });
        show_card();
    }

    //implement show_card API
    public void show_card(){
        AndroidNetworking.post(API_BaseUrl.show_card)
                .addBodyParameter("user_id",user_id)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        paymentAdapterArrayList=new ArrayList<>();
                        for (int i = 0; i <response.length() ; i++) {
                            Log.e("jgdshj", "onResponse: "+response.length() );
                            try {
                                JSONObject jsonObject=response.getJSONObject(i);
                                PaymentModel1 paymentModel1=new PaymentModel1();
                                paymentModel1.setCard_no(jsonObject.getString("card_no"));
                                paymentModel1.setExpiry_date(jsonObject.getString("expiry_date"));
                                paymentModel1.setCvv_no(jsonObject.getString("cvv_no"));

                                paymentAdapterArrayList.add(paymentModel1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            paymentAdapter=new PaymentAdapter(paymentAdapterArrayList,PaymentActivity.this);
                            recycleview_payment.setAdapter(paymentAdapter);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
    public  void GetCardDetail(String cardnum,String expiry, String cvv){
        CardNum=cardnum;
        Expiry=expiry;
        Cvv=cvv;
        String [] str=Expiry.split("/");
        FinalMonth= Integer.parseInt(str[0]);
        FinalYear= Integer.parseInt(str[1]);
        Log.e("sbfkabfkadfb", FinalMonth+""+FinalYear );
    }


    private void performCharge() {
        //create a Charge object
        charge = new Charge();

        //set the card to charge
        charge.setCard(card);

        //call this method if you set a plan
        //charge.setPlan("PLN_yourplan");

        charge.setEmail(User_Email); //yha user ki email ko add krdena jaha address id store krake lekr

        charge.setAmount(Integer.parseInt("500")); //test amount

        PaystackSdk.chargeCard(PaymentActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.
                String paymentReference = transaction.getReference();
                Toast.makeText(PaymentActivity.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();

              //  SuccessTransaction(stUserId,Subject_id,paymentReference,SubjectAmount);
                checkout();



                Log.e("dsfgdfbgd", "Transaction Successful! payment reference:"+ paymentReference);
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
                Toast.makeText(PaymentActivity.this, "Validated"+transaction.toString(), Toast.LENGTH_SHORT).show();
                Log.e("dsfgdfbgd", "Validated"+transaction.toString());
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //handle error here
                Toast.makeText(PaymentActivity.this, transaction.toString()+"Error"+error, Toast.LENGTH_SHORT).show();
                Log.e("dsfgdfbgd", transaction.toString()+"Error"+error );
            }
        });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = "Raghu@gmail.com";

        if (TextUtils.isEmpty(CardNum)) {
            txt_error.setError("Please select Card.");
            valid = false;
        } else {
            txt_error.setError(null);
        }

        return valid;
    }



    public void checkout(){
        Log.e("hghhsds", "buy: "+ user_id+","+address_id+","+CardNum+","+Cvv+","+Expiry);
        AndroidNetworking.post(API_BaseUrl.booking)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("address_id",address_id)
                .addBodyParameter("card_number",CardNum)
                .addBodyParameter("cvv_no",Cvv)
                .addBodyParameter("card_expiry",Expiry)
                .setPriority(Priority.HIGH)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("hghgfh",response.toString());
                            /*JSONObject data=response.getJSONObject("data");
                            data.getString(response.getString("card_number"));
*/
                            if (response.getString("result").equals("successfully")){

                                startActivity(new Intent(getApplicationContext(),SuccesfullActivity.class));
                                finish();
                            }else {

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}