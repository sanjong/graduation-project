package com.example.myapplication;

public class FriendData {
    private Integer images;
    private String hashs;
    private String records;


    public FriendData(Integer images, String hashs, String records){
        this.images = images;
        this.hashs = hashs;
        this.records = records;
    }

    public Integer getImages()
    {
        return this.images;
    }

    public String getHashs()
    {
        return this.hashs;
    }

    public String getRecords()
    {
        return this.records;
    }
}
