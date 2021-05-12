package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kayla.Adapter.DemoAdapter;
import com.kayla.Model.DemoModel;
import com.kayla.R;

import java.util.ArrayList;

public class Demo extends AppCompatActivity {

    DemoAdapter demoAdapter;
    ArrayList<DemoModel> arrayList=new ArrayList<>();
    RecyclerView recycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        recycleview=findViewById(R.id.recycleview);

        recycleview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        list();
    }
    public void list(){

        DemoModel myModel=new DemoModel("fgjhf",R.drawable.womenimg);
        for (int i = 0; i <4 ; i++) {

            arrayList.add(myModel);
            //set Recycleview Adapter
            demoAdapter=new DemoAdapter(arrayList,Demo.this);
            recycleview.setAdapter(demoAdapter);
        }
    }
}