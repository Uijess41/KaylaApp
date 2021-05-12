package com.kayla.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kayla.Adapter.DemoAdapter;
import com.kayla.Adapter.SearchAdapter;
import com.kayla.Model.DemoModel;
import com.kayla.Model.SearchModel;
import com.kayla.R;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    SearchAdapter searchAdapter;
    ArrayList<SearchModel> searchModelArrayList=new ArrayList<>();
    RecyclerView recycleview_search;
    ImageView imageback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        imageback=findViewById(R.id.imageback);
        recycleview_search=findViewById(R.id.recycleview_search);

        recycleview_search.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }
    public void list(){

        SearchModel searchModel=new SearchModel(R.drawable.womenimg,"hoodle",300);
        for (int i = 0; i <4 ; i++) {

            searchModelArrayList.add(searchModel);
            //set Recycleview Adapter
            searchAdapter=new SearchAdapter(searchModelArrayList,SearchResultActivity.this);
            recycleview_search.setAdapter(searchAdapter);
        }
    }
}