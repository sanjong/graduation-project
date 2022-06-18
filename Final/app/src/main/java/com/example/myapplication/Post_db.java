package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Post_db extends AsyncTask<Void, Void, String> {
    private String success;
    private String url1;
    private String result1; // 요청 결과를 저장할 변수
    private ArrayList<Post> htag;
    private ContentValues content;

    public Post_db(String user_id, String title, int post_range, String htag, String url, String createdAt) {
        success="Failed to connect db!";
        this.content = new ContentValues();
        content.put("id",user_id);
        content.put("title",title);
        content.put("postRange",post_range);
        String a = htag.replace("#","").replace(" ",",");
        content.put("htag",a);
        content.put("url",url);
        content.put("createdAt",createdAt);
        this.url1 = "http://" + MainActivity.IP + "/post.php";
    }
    public String insert_db(){
        try {
            String result = this.execute().get();
            if(result.contains("Success")){
                return result;
            }else{
                return "Error : " + result;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
    @Override
    protected String doInBackground(Void... params) {
        // url 넣는 곳

        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1,content);
        return result1;
    }
}