package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class UpdateUser extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    final private static String TAG = "GILBOMI";
    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;

    private final int GET_GALLERY_IMAGE = 200;
    private Uri selectedImageUri = null;
    String url = "https://test20220524.s3.ap-northeast-2.amazonaws.com/image/";
    private ImageView imageview;
    private Button updateButton , delete;
    private EditText update_name,update_email, update_email2, update_phonenumber,update_birthday, editText;
    private RadioGroup radioGroup;
    private RadioButton update_gender_m, update_gender_f;
    private NumberPicker numberPicker1,numberPicker2,numberPicker3;
    private String str_result = "0";
    private String email;

    private String check = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    String check_number = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";

    Dialog dialog01, birthday;
    boolean success = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateuser);

        dialog01 = new Dialog(UpdateUser.this); // dialog 초기화
        dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 지우기
        dialog01.setContentView(R.layout.dialog01);

        birthday = new Dialog(UpdateUser.this); // dialog 초기화
        birthday.requestWindowFeature(Window.FEATURE_NO_TITLE); //타이틀 지우기
        birthday.setContentView(R.layout.birthday_dialog);

        updateButton = (Button) findViewById(R.id.update_button);
        delete = (Button) findViewById(R.id.delete);
        imageview = (ImageView)findViewById(R.id.imagebtn);
        editText = (EditText)findViewById(R.id.update_email2);
        update_birthday = (EditText)findViewById(R.id.update_birthday);

        update_name = findViewById(R.id.update_name);
        update_email = findViewById(R.id.update_email);
        update_email2 = findViewById(R.id.update_email2);
        update_phonenumber = findViewById(R.id.update_phonenumber);

        update_name = findViewById(R.id.update_name);
        update_email = findViewById(R.id.update_email);
        update_email2 = findViewById(R.id.update_email2);
        update_phonenumber = findViewById(R.id.update_phonenumber);

        radioGroup = findViewById(R.id.radio_group);
        update_gender_m = findViewById(R.id.update_gender_m);
        update_gender_f = findViewById(R.id.update_gender_f);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                {
                    switch (checkedId) {
                        case R.id.update_gender_m:
                            str_result = "0";
                            break;
                        case R.id.update_gender_f:
                            str_result = "1";
                            break;
                    }
                }
            }
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter emailAdapter = ArrayAdapter.createFromResource(this,R.array.email_array, android.R.layout.simple_spinner_dropdown_item);
        emailAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(emailAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    editText.setEnabled(true);
                    editText.setText("");
                }else{
                    editText.setText(spinner.getSelectedItem().toString());
                    editText.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        update_birthday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                birthdayDialog();
            }
        });

        imageview.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                showDialog01();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            public String getPathFromUri(Uri uri) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToNext();
                @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex("_data"));
                cursor.close();
                return path;
            }
            @Override
            public void onClick(View view) {
                String number = update_phonenumber.getText().toString().substring(0, 3) + "-" + update_phonenumber.getText().toString().substring(3, 7) + "-" + update_phonenumber.getText().toString().substring(7);
                if(selectedImageUri != null){
                    File file = new File(getPathFromUri(selectedImageUri));
                    Upload_S3 s3 = new Upload_S3(file, update_name.getText().toString() + ".jpg", getApplicationContext(), false);
                    Thread datathread = new Thread() {
                        @Override
                        public void run() {
                            s3.upload();
                        }
                    };
                    datathread.start();
                    try {
                        datathread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    url += update_name.getText().toString() + ".jpg";
                }
                String result = "";
                //
                MainActivity.user.updateUser(update_name.getText().toString(), number,
                        update_email.getText().toString() + "@" + update_email2.getText().toString(),
                        str_result, (!url.contains("jpg"))?"null":url, update_birthday.getText().toString());
                User user = MainActivity.user;
                UpdateUser_db ud = new UpdateUser_db(user);
                try {
                    result = ud.execute().get();
                    if (result.equals("Success")) {
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                    }

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try { photoFile = createImageFile(); }
            catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.myapplication", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("value is",""+newVal);
    }

    public void birthdayDialog()
    {
        birthday.show(); // 다이얼로그 띄우기

        NumberPicker np1 = birthday.findViewById(R.id.birthdayPicker1);
        np1.setMaxValue(2022);
        np1.setMinValue(1900);
        np1.setValue(1980);
        np1.setWrapSelectorWheel(false);
        np1.setOnValueChangedListener(this);

        NumberPicker np2 = birthday.findViewById(R.id.birthdayPicker2);
        np2.setMaxValue(12);
        np2.setMinValue(01);
        np2.setValue(06);
        np2.setWrapSelectorWheel(false);
        np2.setOnValueChangedListener(this);

        NumberPicker np3 = birthday.findViewById(R.id.birthdayPicker3);
        np3.setMaxValue(31);
        np3.setMinValue(01);
        np3.setValue(15);
        np3.setWrapSelectorWheel(false);
        np3.setOnValueChangedListener(this);


        Button birthday_btn_ok = birthday.findViewById(R.id.birthday_btn_ok);
        birthday_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_birthday.setText(String.valueOf( np1.getValue() + "-" + String.format("%02d", np2.getValue()) + "-" + String.format("%02d", np3.getValue()) ) );
                birthday.dismiss();
            }
        });

        Button birthday_btn_cancel = birthday.findViewById(R.id.birthday_btn_cancel);
        birthday_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthday.dismiss();
            }
        });

    }


    public void showDialog01() {
        dialog01.show(); // 다이얼로그 띄우기

        Button cameraBtn = dialog01.findViewById(R.id.cameraBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cameraBtn:
                        dispatchTakePictureIntent();
                        break;
                }
                dialog01.dismiss();
            }
        });

        Button albumBtn = dialog01.findViewById(R.id.albumBtn);
        albumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,GET_GALLERY_IMAGE);
                dialog01.dismiss();
            }
        });

        Button cancelBtn = dialog01.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog01.dismiss();
            }
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO: {
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) { imageview.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) { imageview.setImageBitmap(bitmap); }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
            System.out.print(selectedImageUri);
        }
    }
}
