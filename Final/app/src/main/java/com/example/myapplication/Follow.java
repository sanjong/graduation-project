package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;


class Follow extends AsyncTask<Void, Void, String>  {
    ContentValues content;
    private String url1;
    private String result1; // 요청 결과를 저장할 변수

    public Follow(String userId, String followId, boolean check) {
        content = new ContentValues();
        content.put("userId",userId);
        content.put("followId",followId);
        content.put("check",check?"0":"1");
        this.url1 = "http://" + MainActivity.IP + "/follow.php";
    }
    public void follow_db(){
        try {
            this.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1,content); // 해당 URL로 부터 결과물을 얻어온다.
        return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
