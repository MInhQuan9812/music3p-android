package com.example.deannhom.model;

import android.net.Uri;

import java.io.Serializable;

public class AudioModel implements Serializable {
    String path;
    String title;
    long duration;
    String avatar;
    String actors;

    public AudioModel(String path, String title, long duration,String avatar,String actors) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.avatar=avatar;
        this.actors=actors;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
