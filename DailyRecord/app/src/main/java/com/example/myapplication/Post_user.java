package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Post_user extends AsyncTask<Void, Void, String> {

    private String url1;
    private String result1; // 요청 결과를 저장할 변수
    private ArrayList<Post> post;
    private ContentValues content;
    public Post_user(String userId) {
        this.content = new ContentValues();
        content.put("userId",userId);
        this.url1 = "http://" + MainActivity.IP + "/post_user.php";  //select_follower.php
    }
    public ArrayList<Post> search_user(){
        try {
            this.result1 = this.execute().get();
            doJSONParser(result1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.post;
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1,content); // 해당 URL로 부터 결과물을 얻어온다.
        return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
    }

    // 받아온 json 데이터를 파싱합니다..
    public void doJSONParser(String string) {
        try {
            if(string == null) {
                this.post = new ArrayList<>();
            }else{
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = jsonObject.getJSONArray("user_feed");
                this.post = new ArrayList<Post>();
                for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject output = jsonArray.getJSONObject(i);
                    this.post.add(new Post(output.getInt("postid"),output.getString("title"),
                            output.getString("created_at"),
                            output.getString("like"), output.getString("record"),
                            output.getString("htag"),Integer.parseInt(output.getString("post_range")),
                            output.getString("image"),output.getString("user_id"),output.getString("follow")));
                    System.out.println(post.get(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}