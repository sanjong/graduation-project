//package com.example.myapplication;
//
//import android.content.ContentValues;
//import android.os.AsyncTask;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class Register extends AsyncTask<Void, Void, String> {
//
//    private String url1;
//    private ContentValues values1;
//    private String result1; // 요청 결과를 저장할 변수
//    private User user;
//
//    public Register(User user) {
//        this.url1 = "http://" + MainActivity.IP + "/register.php";
//        this.values1 = new ContentValues();
//        values1.put("id",user.getId());
//        values1.put("pwd",user.getPwd());
//        values1.put("name",user.getName());
//        values1.put("phone",user.getPhone());
//        values1.put("email",user.getEmail());
//        values1.put("birth",user.getBirth());
//        values1.put("gender",user.getGender());
//        values1.put("image",user.getImage());
//    }
//
//    @Override
//    protected String doInBackground(Void... params) {
//        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
//        result1 = requestHttpURLConnection.request(url1, values1); // 해당 URL로 부터 결과물을 얻어온다.
//        return result1; // 여기서 당장 실행 X, onPostExcute에서 실행
//    }
//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        System.out.println(s);
//    }
//}