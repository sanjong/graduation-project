package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FragmentPage1 extends Fragment {

    TextView monthYearText; //년월 텍스트뷰
    LocalDate selectedDate; //년월 변수

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_page_1,container,false);

        //초기화
        monthYearText = (TextView)view.findViewById(R.id.monthYearText);
        ImageButton preBtn = view.findViewById(R.id.pre_btn);
        ImageButton nextBtn = view.findViewById(R.id.next_btn);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        //현재 날짜
        selectedDate = LocalDate.now();

        //화면 설정
        setMonthView();

        //이전 달 버튼 이벤트
        preBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        //다음 달 버튼 이벤트
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //+1한 월을 넣어준다.(2월 -> 3월)
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
            }
        });



        return view;
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 yyyy");

        return date.format(formatter);
    }

    //날짜 타입 설정(2020년 4월)
    private String yearMonthFromDate(LocalDate date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월");

        return date.format(formatter);
    }

    //화면 설정
    private void setMonthView(){

        //년월 텍스트뷰 셋팅
        monthYearText.setText(monthYearFromDate(selectedDate));

        //해당 월 날짜 가져오기
        ArrayList<LocalDate> dayList = daysInMonthArray(selectedDate);

        //어뎁터 데이터 적용
        CalendarAdapter adapter = new CalendarAdapter(getContext(),dayList);

        //레이아웃 설정(열 7개)
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity().getApplicationContext(), 7);

        //레이아웃 적용
        recyclerView.setLayoutManager(manager);

        //어뎁터 적용
        recyclerView.setAdapter(adapter);
    }

    //날짜 생성
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date){

        ArrayList<LocalDate> dayList = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(date);

        //해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        int lastDay = yearMonth.lengthOfMonth();

        //해당 월의 첫 번째 날 가져오기(예 4월1일)
        LocalDate firstDay = selectedDate.withDayOfMonth(1);

        //첫 번째 날 요일 가져오기(월:1 , 일:7)
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        //날짜 생성
        for(int i = 1; i < 42; i++){

            if( i <= dayOfWeek || i > lastDay + dayOfWeek){
                dayList.add(null);
            }else{
                dayList.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
            }
        }

        return dayList;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}