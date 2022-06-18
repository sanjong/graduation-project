package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;


class Like extends AsyncTask<Void, Void, String> {
    ContentValues content;
    private String url1;
    private String result1; // 요청 결과를 저장할 변수

    public Like(String postId, String chkLike) {
        content = new ContentValues();
        content.put("id", MainActivity.user.getId());
        content.put("postId", postId);
        content.put("check", chkLike);
        this.url1 = "http://" + MainActivity.IP + "/like.php";
    }

    public String LikeClick() {
        String str ="fail to Like";
        try {
            this.result1 = this.execute().get();
            if(result1.equals("true")){
                str = "success";
            }else{
                str = result1;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1, content); // 해당 URL로 부터 결과물을 얻어온다.
        System.out.println(result1);
        return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
    }
}