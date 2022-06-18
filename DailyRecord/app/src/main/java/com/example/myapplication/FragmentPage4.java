package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentPage4 extends Fragment {

    private ArrayList<Post> myList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter fAdapter;

    private ImageView imageView,nullView,updateUser;
    private TextView textView5 , textView6;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_page_4,container,false);
        imageView = (ImageView)view.findViewById(R.id.image);
        textView5 =  (TextView)view.findViewById(R.id.textView5);
        textView6 = (TextView)view.findViewById(R.id.textView6);
        nullView = (ImageView)view.findViewById(R.id.nullView);
        updateUser = view.findViewById(R.id.updateUser);

        Thread thread = new Thread(){
            @Override
            public void run(){
                Bitmap bit = MainActivity.user.getImageBitmap();
                if(!(bit==null)){
                    imageView.setImageBitmap(bit);
                }
                textView5.setText(MainActivity.user.getName());
                textView6.setText(MainActivity.user.getBirth());
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateUser.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {
               AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

               dialog.setTitle("회원정보수정")
                       .setMessage("회원 정보를 수정하시겠습니까?")
                       .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                           }
                       })
                       .setPositiveButton("예", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(),UpdateUser.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                           }
                       })
                       .show();
           }
        });


        myList.clear();
        Post_user pu = new Post_user(MainActivity.user.getId());
        myList= pu.search_user();
        //recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        fAdapter = new MyAdapter(myList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fAdapter);
        if(!myList.isEmpty()){
            nullView.setVisibility(View.INVISIBLE);
        }else{
            nullView.setVisibility(View.VISIBLE);
        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}