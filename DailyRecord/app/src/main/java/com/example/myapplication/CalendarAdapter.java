package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    ArrayList<LocalDate> dayList;
    private Context mContext;

    public CalendarAdapter(Context context, ArrayList<LocalDate> dayList) {
        this.dayList = dayList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.calendar_cell, parent, false);

        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {

        //날짜 변수에 담기
        LocalDate day = dayList.get(position);

        if(day == null){
            holder.dayText.setText("");

        }else{
            //해당 일자를 넣는다.
            holder.dayText.setText(String.valueOf(day.getDayOfMonth()));
        }

        //텍스트 색상 지정
        if( (position + 1) % 7 == 0){ //토요일 파랑

            holder.dayText.setTextColor(Color.BLUE);

        }else if( position == 0 || position % 7 == 0){ //일요일 빨강

            holder.dayText.setTextColor(Color.RED);
        }

        //날짜 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int iYear = day.getYear(); //년
                String iMonth = String.format("%02d",day.getMonthValue());//월
                String iDay = String.format("%02d", day.getDayOfMonth());//일
                String yearMonDay = iYear + "-" +iMonth + "-" + iDay;
                Toast.makeText(holder.itemView.getContext() , yearMonDay , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext.getApplicationContext(), SelectDay.class );
                intent.putExtra("day",yearMonDay);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView dayText;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            dayText = itemView.findViewById(R.id.dayText);
        }
    }
}
