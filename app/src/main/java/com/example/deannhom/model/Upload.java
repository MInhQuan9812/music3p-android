package com.example.deannhom.model;

public class Upload {

    String name ="Thiên hạ nghe gì" ;
//    String url;
    String url ="https://th.bing.com/th/id/OIP.vUXMO2WQCtGrRqUlbqhHvQHaHa?pid=ImgDet&rs=1";
    String songsCategory;

    public Upload(String name, String url, String songsCategory) {
        this.name = name;
        this.url = url;
        this.songsCategory = songsCategory;
    }

    public Upload() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSongsCategory() {
        return songsCategory;
    }

    public void setSongsCategory(String songsCategory) {
        this.songsCategory = songsCategory;
    }
}
