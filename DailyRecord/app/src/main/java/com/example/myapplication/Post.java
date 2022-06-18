package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

class Post implements Serializable {
    int postId;
    String title;
    String created_at;
    boolean like;
    String record;
    int post_range;
    String htag;
    String image;
    Bitmap imageBitmap;
    String user_id;
    String a;
    boolean follow;

    public String getA() {
        return a;
    }

    public boolean isLike() {
        return like;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public boolean isFollow() {
        return follow;
    }
    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", created_at='" + created_at + '\'' +
                ", like=" + like +
                ", record='" + record + '\'' +
                ", post_range=" + post_range +
                ", htag='" + htag + '\'' +
                ", image='" + image + '\'' +
                ", imageBitmap=" + imageBitmap +
                ", user_id='" + user_id + '\'' +
                ", a='" + a + '\'' +
                ", follow=" + follow +
                '}';
    }

    public Post(int postId, String title, String created_at, String like, String record, String htag, int post_range, String image, String user_id, String  follow) {
        this.postId = postId;
        this.title = title;
        this.created_at = created_at;
        this.like = !like.equals("0");
        this.post_range = post_range;
        this.record = record;
        this.htag = "#" + htag.replace(",", " #");
        this.image = image;
        this.user_id = user_id;
        this.a = htag;
        this.follow = follow.equals("true");
    }
    public Bitmap getImageBitmap(){
        try {
            URL url = new URL(this.image);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // 서버로 부터 응답 수신
            conn.connect();

            InputStream is = conn.getInputStream(); // InputStream 값 가져오기
            this.imageBitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

            return this.imageBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getHtag() {
        return htag;
    }

    public void setHtag(String htag) {
        this.htag = htag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void post_feed(String url) {
        new Post_db(this.user_id, this.title, this.post_range, this.htag,url,"20201010101010").execute();
    }
}