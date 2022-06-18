//package com.example.myapplication;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
//
//    private ArrayList<FriendData> fDataset;
//
//    public class FriendViewHolder extends RecyclerView.ViewHolder {
//        public ImageView images;
//        public TextView hashs, records;
//
//        //ViewHolder
//        public FriendViewHolder(View view) {
//            super(view);
//            images = (ImageView) view.findViewById(R.id.image);
//            hashs = (TextView) view.findViewById(R.id.hash);
//            records = (TextView) view.findViewById(R.id.record);
//        }
//    }
//
//    public FriendAdapter(ArrayList<FriendData> friendData){
//        this.fDataset = friendData;
//    }
//
//    @NonNull
//    @Override
//    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend, parent, false);
//        return new FriendViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FriendAdapter.FriendViewHolder holder, int position) {
//
//        holder.images.setImageResource(fDataset.get(position).getImages());
//        holder.hashs.setText(fDataset.get(position).getHashs());
//        holder.records.setText(fDataset.get(position).getRecords());
//
//        //클릭이벤트
//        holder.hashs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //클릭시 name과 좌표정보를 지도 프래그먼트로 보내자.
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return fDataset.size();
//    }
//
//}
