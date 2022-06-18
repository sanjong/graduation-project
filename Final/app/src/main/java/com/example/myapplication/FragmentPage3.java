package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class FragmentPage3 extends Fragment {

    private ArrayList<Post> MyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private Post_follower pf;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        MyList.clear();
        //recyclerview
        pf = new Post_follower(MainActivity.user.getId());
        MyList = pf.search_follower();
        View view;
        if(!MyList.isEmpty()){
            view = inflater.inflate(R.layout.fragment_page_3,container,false);
            recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setHasFixedSize(true);
            mAdapter = new MyAdapter(MyList);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);}
        else{
            view = inflater.inflate(R.layout.null_feed,container,false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}