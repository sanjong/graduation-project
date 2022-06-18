package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ImageButton loginButton = (ImageButton) findViewById(R.id.login_button);
        ImageButton joinButton = (ImageButton) findViewById(R.id.join_button);
        EditText loginId = findViewById(R.id.login_email);
        EditText loginPwd = findViewById(R.id.login_password);


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String result="";
                Login_db ld = new Login_db(loginId.getText().toString(),loginPwd.getText().toString());
                try {
                    result = ld.execute().get();
                    ld.doJSONParser(result);
                    if(ld.isSuccess()){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", (Serializable) ld.getUser());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"Fail to login",Toast.LENGTH_LONG).show();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        joinButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
