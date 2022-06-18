package com.example.myapplication;

import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_db extends AsyncTask<Void, Void, String> {

    private String url1;

    public boolean isSuccess() {
        return success;
    }

    private ContentValues values1;
    private String result1; // 요청 결과를 저장할 변수
    private User user;
    private boolean success =false;

    public Login_db(String id, String pwd) {
        this.url1 = "http://" + MainActivity.IP + "/login.php";
        this.values1 = new ContentValues();
        values1.put("id",id);
        values1.put("pwd",pwd);
    }

    public User getUser() {
        return user;
    }

    @Override
    protected String doInBackground(Void... params) {
        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        result1 = requestHttpURLConnection.request(url1, values1); // 해당 URL로 부터 결과물을 얻어온다.
        return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
    }

    // 받아온 json 데이터를 파싱합니다..
    public void doJSONParser(String string) {
        if(string.equals("fail")){
            success = false;
        }else{
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = jsonObject.getJSONArray("user");

                for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject output = jsonArray.getJSONObject(i);
                    this.user = new User(output.getString("id"),output.getString("pwd"),output.getString("name")
                            ,output.getString("phone"),output.getString("email"),output.getString("gender")
                            ,output.getString("image"),output.getString("birth"));
                }
                success = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}