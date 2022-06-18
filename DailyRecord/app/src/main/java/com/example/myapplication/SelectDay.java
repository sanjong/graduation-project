package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SelectDay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Post> mList = new ArrayList<>();
    private MyAdapter sAdapter;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<Post> nList = new ArrayList<>();
    Post_day pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectday);
        System.out.println(getIntent().getStringExtra("day"));
        pd = new Post_day(MainActivity.user.getId(),getIntent().getStringExtra("day"));
        mList = pd.search_day();
        //recyclerview
        if( !mList.isEmpty()){
            recyclerView = (RecyclerView)findViewById(R.id.list);
            recyclerView.removeAllViews();
            recyclerView.setHasFixedSize(true);
            sAdapter = new MyAdapter(mList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(sAdapter);

            refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshLayout.setRefreshing(false);
                    sAdapter.notifyDataSetChanged();
                }
            });

        }else{
            setContentView(R.layout.null_feed);
        }
    }
}