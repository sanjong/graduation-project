package com.example.myapplication;

public class MyData {
    private Integer images;
    private String id;
    private String title;
    private String date;
    private String hashs;
    private String uri;


    public MyData(Integer images, String id, String title, String date, String hashs, String uri){
        this.images = images;
        this.id = id;
        this.title = title;
        this.date = date;
        this.hashs = hashs;
        this.uri = uri;
    }

    public Integer getImages()
    {
        return this.images;
    }
    public String getId(){return this.id;}
    public String getTitle(){return this.title;}
    public String getDate(){return this.date;}
    public String getHashs(){return this.hashs;}
    public String getUri(){return this.uri;}

}


