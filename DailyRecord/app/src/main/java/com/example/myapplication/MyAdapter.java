package com.example.myapplication;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Post> mDataset;
    private String url;
    private MediaPlayer mediaPlayer = null;
    String RecordTime;
    String Sec;
    int Second;
    int Minute;
    Timer m_timer;
    TimerTask m_task;
    Bitmap a;
    String totalRecordTime ="";
    int value =0;
    boolean value2 =false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView hashs, records, title, userID, recordDate;
        public ImageButton start, heart, follow;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            images = view.findViewById(R.id.image);
            title = view.findViewById(R.id.maintext);
            hashs =  view.findViewById(R.id.hash);
            records = view.findViewById(R.id.record);
            start =  view.findViewById(R.id.imageButton2);
            heart = view.findViewById(R.id.heartBtn);
            follow = view.findViewById(R.id.follow);
            userID = view.findViewById(R.id.textView2);
            recordDate = view.findViewById(R.id.textView3);

        }
    }

    public MyAdapter(ArrayList<Post> myData){
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfeed, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        if(!mDataset.get(position).getImage().equals("null") ){
            Thread thread =new Thread(() -> {
                a = mDataset.get(position).getImageBitmap(); // network 동작, 인터넷에서 xml을 받아오는 코드
            });
            thread.start();
            try {
                thread.join();
                holder.images.setImageBitmap(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(MainActivity.user.getId().equals(mDataset.get(position).getUser_id())){
            holder.follow.setVisibility(View.INVISIBLE);
            System.out.println(mDataset.get(position).getUser_id());
        }else{
            if(!mDataset.get(position).isFollow()){
                holder.follow.setImageDrawable(holder.follow.getResources().getDrawable(R.drawable.unfollow, null));
            }else{
                holder.follow.setImageDrawable(holder.follow.getResources().getDrawable(R.drawable.follow, null));
            }
        }
        holder.hashs.setText(mDataset.get(position).getHtag());
        holder.title.setText(mDataset.get(position).getTitle());
        holder.userID.setText(mDataset.get(position).getUser_id());
        holder.recordDate.setText(mDataset.get(position).getCreated_at());
        if(mDataset.get(position).getLike()){
            holder.heart.setImageResource(R.drawable.heart);
        }
        //        holder.start.setImageResource(mDataset.get(position).getStart());
        m_timer = new Timer();
        url = mDataset.get(position).getRecord();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Sec = String.format("%02d", ((mediaPlayer.getDuration()) / 1000) % 60);
                    Second = 0;
                    Second = Integer.parseInt(Sec);
                    String b = String.format("%02d", Second);
                    Minute = Second / 60;
                    String a = String.format("%02d", Minute);
                    RecordTime = a + ":" + b;
                    totalRecordTime = RecordTime;
                    holder.records.setText(RecordTime);
                }
            });
            mediaPlayer.prepare();
        } catch (IOException e) { }
        //클릭이벤트

//        holder.hashs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "asd",Toast.LENGTH_SHORT).show();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//            }
//        });

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow f = new Follow(MainActivity.user.getId(), mDataset.get(position).getUser_id(), mDataset.get(position).isFollow());
                value2 = mDataset.get(position).isFollow();
                if (value2) {
                    holder.follow.setImageDrawable(holder.follow.getResources().getDrawable(R.drawable.unfollow, null));
                    mDataset.get(position).setFollow(false);
                } else {
                    holder.follow.setImageDrawable(holder.follow.getResources().getDrawable(R.drawable.follow, null));
                    mDataset.get(position).setFollow(true);
                }
                f.follow_db();
            }
        });

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (mDataset.get(position).getLike()) {
                        String result = new Like(String.valueOf(mDataset.get(position).getPostId()),"0").LikeClick();
                        if(result.equals("success")){
                            mDataset.get(position).setLike(false);
                            holder.heart.setSelected(true);
                            holder.heart.setImageResource(R.drawable.null_heart);
                        }else{
                            Toast.makeText(view.getContext(), result,Toast.LENGTH_LONG).show();
                        }
                    }else{

                        String result = new Like(String.valueOf(mDataset.get(position).getPostId()),"1").LikeClick();
                        if(result.equals("success")){
                            mDataset.get(position).setLike(true);
                            holder.heart.setSelected(false);
                            holder.heart.setImageResource(R.drawable.full_heart);

                        }else{
                            Toast.makeText(view.getContext(), result,Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });

        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (m_timer != null){
                    m_timer.cancel();
                }
                System.out.println("CLICK");
                m_timer = new Timer();
                timer(holder,position);
            }
        });


    }
    public void timer(@NonNull MyAdapter.MyViewHolder holder, int position){
        if (value == 0) {
            value = 1;
            holder.start.setImageDrawable(holder.start.getResources().getDrawable(R.drawable.play2, null)); // 녹음 상태 아이콘 변경
            url = mDataset.get(position).getRecord();
            mediaPlayer = new MediaPlayer();
            try {

                mediaPlayer.setDataSource(url);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        Sec = String.format("%02d", ((mediaPlayer.getDuration()) / 1000) % 60);
                        Second = 0;
                        Second = Integer.parseInt(Sec);


                        m_task = new TimerTask() {
                            @Override
                            public void run() {
                                if (Second >= 0) {
                                    String b = String.format("%02d", Second);
                                    Minute = Second / 60;
                                    String a = String.format("%02d", Minute);

                                    RecordTime = a + ":" + b;
                                    holder.records.setText(RecordTime);

                                    Second--;
                                    if(Second == -1){
                                        value = 0;
                                        holder.start.setImageDrawable(holder.start.getResources().getDrawable(R.drawable.paly, null)); // 녹음 상태 아이콘 변경
                                        holder.records.setText(totalRecordTime);
                                        m_timer.cancel();
                                        mediaPlayer.stop();
                                    }
                                }
                            }
                        };
                        m_timer.schedule(m_task, 1000, 1000);
                    }
                });
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            value = 0;
            holder.start.setImageDrawable(holder.start.getResources().getDrawable(R.drawable.paly, null)); // 녹음 상태 아이콘 변경
            holder.records.setText(totalRecordTime);
            m_timer.cancel();
            mediaPlayer.stop();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



}