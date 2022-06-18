package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

class User implements Serializable {
    private String id;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private Bitmap imageBitmap;
    private String birth;
    private String image;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", image='" + image + '\'' +
                '}';
    }


    public User(String id, String pwd, String name, String phone, String email, String gender, String image, String birth) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.image = image;
    }
    public Bitmap getImageBitmap() {
        if(!this.image.equals("null")){
            try {
                URL url = new URL(this.image);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // 서버로 부터 응답 수신
                conn.connect();

                InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                this.imageBitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
                return this.imageBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else{
            return null;
        }
    }
    public void updateUser(String name, String phone, String email, String gender,String image, String birth ){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.image = image;
        this.birth = birth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public String getGenderToString() {
        return gender.equals("0") ? "Male" : "Female";
    }


    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}