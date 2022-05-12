package com.example.myapplication;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Search extends AppCompatActivity {
    private List<String> items = Arrays.asList("a","b","c","d","e");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        SearchView searchView = findViewById(R.id.searchView);
        TextView resultTextView = findViewById(R.id.textView);
        resultTextView.setText(getResult());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultTextView.setText(search(newText));
                return true;
            }
        });
    }

    private String search(String query) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            if(item.toLowerCase().contains(query.toLowerCase())) {
                sb.append(item);
                if (i != items.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private String getResult() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            sb.append(item);
            if (i != items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
