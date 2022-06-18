package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Post_follower extends AsyncTask<Void, Void, String> {
    private String success;
    private String url1;
    private String result1; // 요청 결과를 저장할 변수
    private ArrayList<Post> post;
    private ContentValues content;
    public ArrayList<Post> getPost() {
        return post;
    }

    public Post_follower(String userId) {
        this.content = new ContentValues();
        content.put("userId",userId);
        this.url1 = "http://" + MainActivity.IP + "/post_follower.php";
    }
    public ArrayList<Post> search_follower(){
        try {
            this.result1 = this.execute().get();
            doJSONParser(result1);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return this.post;
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1,content);
        return result1;
    }

    public void doJSONParser(String string) {
        try {
            if(string == null) {
                this.post= new ArrayList<>();
            }else{
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = jsonObject.getJSONArray("follower_feed");
                this.post = new ArrayList<Post>();
                for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject output = jsonArray.getJSONObject(i);
                    this.post.add(new Post(output.getInt("postid"),output.getString("title"),
                            output.getString("created_at"),
                            output.getString("like"), output.getString("record"),
                            output.getString("htag"),Integer.parseInt(output.getString("post_range")),
                            output.getString("image"),output.getString("user_id"), output.getString("follow")));
                    System.out.println(post.get(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}