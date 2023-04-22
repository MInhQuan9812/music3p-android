package com.example.deannhom.adapter;

import java.util.HashMap;
import java.util.Map;

public class Song {
    public String album_art;
    public String artist;
    public String songDuration;
    public String songLink;
    public String songTitle;
    public String songscategory;



    public String album;

    public Song(String album_art, String artist, String songDuration, String songLink, String songTitle, String songscategory) {
        this.album_art = album_art;
        this.artist = artist;
        this.songDuration = songDuration;
        this.songLink = songLink;
        this.songTitle = songTitle;
        this.songscategory = songscategory;
        this.album = album;
    }
    public Song(){}
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongscategory() {
        return songscategory;
    }

    public void setSongscategory(String songscategory) {
        this.songscategory = songscategory;
    }
    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("album_art", album_art);
        result.put("artist", artist);
        result.put("songDuration",songDuration);
        result.put("songLink", songLink);
        result.put("songTitle",songTitle);
        result.put("songscategory",songscategory);
        return result;
    }
}
