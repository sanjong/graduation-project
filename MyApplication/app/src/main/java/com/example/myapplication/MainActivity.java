package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today = findViewById(R.id.today);
        calendarView = findViewById(R.id.calendarView);

        DateFormat formatter = new SimpleDateFormat("yyyy년mm월dd일");
            Date date = new Date(calendarView.getDate());
            today.setText(formatter.format(date));

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofMonth) {
                    String day;
                    day = year + "년" + (month+1) + "월" + dayofMonth + "일";
                    today.setText(day);
                }
            });
    }
}