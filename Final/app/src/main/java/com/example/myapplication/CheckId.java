package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;


class CheckId extends AsyncTask<Void, Void, String>  {
    ContentValues content;
    private String url1;
    private String result1; // 요청 결과를 저장할 변수

    public CheckId(String userId) {
        content = new ContentValues();
        content.put("id",userId);
        this.url1 = "http://" + MainActivity.IP + "/checkId.php";
    }


    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1,content); // 해당 URL로 부터 결과물을 얻어온다.
        return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
    }

}
