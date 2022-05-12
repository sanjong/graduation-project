package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDay extends AppCompatActivity {

    ListView list;

    Integer[] images = {
            R.drawable.iu,
            R.drawable.jmy,
            R.drawable.kth,
            R.drawable.psj
    };

    String[] hashs = {
            "#아이유 #앨범 #가나다라마바사",
            "조미연 #(여자)아이들 #톰보이",
            "김태희 #김태희는김태희",
            "박서준 #쌈마이웨이"
    };

    String[] records = {
            "ddddd",
            "ddddd",
            "ddddd",
            "dddd"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectday);

        setTitle("캘린더 날짜 클릭 화면 Demo");

        CustomList adapter = new
                CustomList(SelectDay.this);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getBaseContext(), hashs[+position], Toast.LENGTH_SHORT).show();
            }
        });

//        TextView textView = (TextView)findViewById(R.id.textView2);
//        textView.setText(select);

    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout.selectday2, hashs);
            this.context = context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.selectday2, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
            TextView title = (TextView) rowView.findViewById(R.id.hash);
            TextView rating = (TextView) rowView.findViewById(R.id.record);

            imageView.setImageResource(images[position]);
            title.setText(hashs[position]);
            rating.setText(records[position]);
            return rowView;
        }
    }
}
