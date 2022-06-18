package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amazonaws.mobileconnectors.cognitoauth.tokens.RefreshToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentPage2 extends Fragment {

    private List<String> items = Arrays.asList("a","b","c","d","e","aa","abc");
    /**
     * xml 변수
     */
    ImageButton audioRecordImageBtn;
    TextView audioRecordText;

    /**
     * 오디오 파일 관련 변수
     */
    // 오디오 권한
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;


    // 오디오 파일 재생 관련 변수
    private MediaPlayer mediaPlayer = null;
    private Boolean isPlaying = false;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Post> MyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private ImageView nullView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_page_2,container,false);
        AutoCompleteTextView edit = (AutoCompleteTextView) view.findViewById(R.id.edit);
        edit.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, items));
        ImageButton SearchButton = view.findViewById(R.id.SearchButton);
        TextView textView = view.findViewById(R.id.edit);
        Editable HashTag =(Editable)textView.getText();
        nullView = (ImageView) view.findViewById(R.id.nullView);

        MyList.clear();

        Post_every pe = new Post_every(MainActivity.user.getId());
        MyList = pe.search_every();
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        mAdapter = new MyAdapter(MyList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        if(!MyList.isEmpty()){
            nullView.setVisibility(View.INVISIBLE);
        }else{
            nullView.setVisibility(View.VISIBLE);
        }

        //해시태그 입력 후 검색버튼 눌렀을 시
        SearchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MyList.clear();
                Post_htag ph = new Post_htag(textView.getText().toString());
                MyList = ph.search_htag();
                if(!MyList.isEmpty()){
                    nullView.setVisibility(View.INVISIBLE);
                }else{
                    nullView.setVisibility(View.VISIBLE);
                }
                recyclerView.setHasFixedSize(true);
                mAdapter = new MyAdapter(MyList);

                mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}