package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

public class Join_db extends AsyncTask<Void, Void, String> {

    private String url1;
    private ContentValues values1;
    private String result1; // 요청 결과를 저장할 변수
    private User user;
    private boolean success=false;

    public Join_db(User user) {
        this.url1 = "http://" + MainActivity.IP + "/register.php";
        this.values1 = new ContentValues();
        values1.put("id",user.getId());
        values1.put("pwd",user.getPwd());
        values1.put("name",user.getName());
        values1.put("phone",user.getPhone());
        values1.put("email",user.getEmail());
        values1.put("birth",user.getBirth());
        values1.put("gender",user.getGender());
        values1.put("image",user.getImage());
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1, values1);
        return result1;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}