package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Record extends AppCompatActivity {

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
    String url = "https://test20220524.s3.ap-northeast-2.amazonaws.com/record/";
    String timeStamp;
    // 오디오 파일 녹음 관련 변수
    private MediaRecorder mediaRecorder;
    private String audioFileName; // 오디오 녹음 생성 파일 이름
    private boolean isRecording = false;    // 현재 녹음 상태를 확인하기 위함.
    private Uri audioUri = null; // 오디오 파일 uri

    // 오디오 파일 재생 관련 변수
    private MediaPlayer mediaPlayer = null;
    private Boolean isPlaying = false;
    ImageView playIcon;

    /**
     * 리사이클러뷰
     */
    private AudioAdapter audioAdapter;
    private AudioAdapter audioAdapter2;
    private ArrayList<Uri> audioList;
    private ArrayList TimeTable;

    String Name;
    String HashTag;
    EditText Tt;
    EditText Ht;
    long beforeTime, afterTime, secDiffTime;
    Timer timer;
    int time = 0;
    String[] HashString;
    ArrayList<String> HashList;
    TextView TimeText, userId, createdAt;
    ImageButton upload, cancel;
    RadioGroup radioGroup;
    int Show =2;
    ImageView audioUserImage;
    String S3Url = "https://test20220524.s3.ap-northeast-2.amazonaws.com/record/";  // S3 버킷 default 경로
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        init();

    }

    // xml 변수 초기화
    // 리사이클러뷰 생성 및 클릭 이벤트
    private void init() {
        createdAt = findViewById(R.id.createdAt);
        userId = findViewById(R.id.userId);
        upload = findViewById(R.id.upload);
        cancel = findViewById(R.id.cancel);
        audioUserImage = findViewById(R.id.audioUserImage);
        audioRecordImageBtn = findViewById(R.id.audioRecordImageBtn);
        TimeText = (TextView)findViewById(R.id.TimeText);
        radioGroup = findViewById(R.id.radio_group);
        Tt = findViewById(R.id.Title);
        Ht = findViewById(R.id.HashTag);
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월 dd일");

        Date time = new Date();
        userId.setText(MainActivity.user.getName());
        HashTag = Ht.getText().toString();
        createdAt.setText(format.format(time));
        Thread thread = new Thread(){
            @Override
            public void run(){
                Bitmap bit = MainActivity.user.getImageBitmap();
                if(!(bit==null)){
                    audioUserImage.setImageBitmap(MainActivity.user.getImageBitmap());
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Tt.getText().toString().equals(""))
                {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = new Date();
                    String nowTime1 = sdf1.format(now);
                    File file = new File(audioFileName);
                    new Upload_S3(file, Tt.getText().toString()+".mp4", getApplicationContext(), true).upload();
                    Post_db pd = new Post_db(MainActivity.user.getId(),Tt.getText().toString(),
                            Show,Ht.getText().toString(),
                            url+Tt.getText().toString()+".mp4",nowTime1);
                    String result = pd.insert_db();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        audioRecordImageBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRecording) {
                    // 현재 녹음 중 O
                    // 녹음 상태에 따른 변수 아이콘 & 텍스트 변경
                    isRecording = false; // 녹음 상태 값
                    audioRecordImageBtn.setImageDrawable(getResources().getDrawable(R.drawable.paly, null)); // 녹음 상태 아이콘 변경

                    timer.cancel();
                    stopRecording();
                    // 녹화 이미지 버튼 변경 및 리코딩 상태 변수값 변경
                } else {
                    // 현재 녹음 중 X
                    /*절차
                     *       1. Audio 권한 체크
                     *       2. 처음으로 녹음 실행한건지 여부 확인
                     * */

                    if (checkAudioPermission()) {
                        // 녹음 상태에 따른 변수 아이콘 & 텍스트 변경
                        isRecording = true; // 녹음 상태 값
                        audioRecordImageBtn.setImageDrawable(getResources().getDrawable(R.drawable.play2, null)); // 녹음 상태 아이콘 변경

                        timer = new Timer();
                        startRecording();
                    }
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.ALL:
                        Show = 2;
                        break;
                    case R.id.FRIEND:
                        Show = 1;
                        break;
                    case R.id.ME:
                        Show = 0;
                        break;
                }
                System.out.println(Show);
            }
        });
    }


    // 오디오 파일 권한 체크
    private boolean checkAudioPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }

    // 녹음 시작
    private void startRecording() {
        //타이머 생성
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                //반복 실행할 구문
                System.out.println("녹음시간 : " + time);
                int Min = time/60;
                int Sec = time%60;

                String b = String.format("%02d",Sec);
                String a = String.format("%02d",Min);
                String RecordTime = a +":" + b;
                System.out.println("getDuration: "+ RecordTime);


                TimeText.setText(RecordTime);
                time ++;
            }
        };
        //타이머 실행
        timer.schedule(timerTask, 1000 , 1000);
        //녹화 버튼 누른 후 시간 받아오기
        beforeTime = System.currentTimeMillis();

        //제목 값



        //파일의 외부 경로 확인
        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        // 파일 이름 변수를 현재 날짜가 들어가도록 초기화. 그 이유는 중복된 이름으로 기존에 있던 파일이 덮어 쓰여지는 것을 방지하고자 함.
        audioFileName = recordPath + "/" + Name + "audio.mp4";
        //Media Recorder 생성 및 설정
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(audioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //녹음 시작
        mediaRecorder.start();
    }

    // 녹음 종료
    private void stopRecording() {
        // 녹음 종료 종료
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

        //녹음 종료 후 녹음 시간 계산
        afterTime = System.currentTimeMillis();
        secDiffTime = (afterTime - beforeTime) / 1000;
        //서버에 올릴 기록시간 값
        System.out.println("기록시간 : " + secDiffTime);

        // 파일 경로(String) 값을 Uri로 변환해서 저장
        //      - Why? : 리사이클러뷰에 들어가는 ArrayList가 Uri를 가지기 때문
        //      - File Path를 알면 File을  인스턴스를 만들어 사용할 수 있기 때문
        audioUri = Uri.parse(audioFileName);

    }

}