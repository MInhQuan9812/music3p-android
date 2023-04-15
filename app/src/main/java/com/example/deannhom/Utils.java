package com.example.deannhom;

import android.content.Context;

import java.io.InputStream;

public class Utils {
    public static final String DATABASE_NAME= "music-db";

    public static final String TABLE_USER="users";
    public static final String TABLE_ALBUMS="songs";
    public static final String TABLE_PLAYLISTS="playlists";
    public static final String TABLE_ARTISTS="artists";

    public static final String COLUMN_USER_ID="id";
    public static final String COLUMN_USER_FULLNAME="fullname";
    public static final String COLUMN_USER_EMAIL="email";
    public static final String COLUMN_USER_PASSWORD="password";

    public static final String COLUMN_SONG_ID="id";
    public static final String COLUMN_SONG_TITLE="title";
    public static final String COLUMN_SONG_AVATAR="avatar";
    public static final String COLUMN_SONG_DURATION="duration";
    public static final String COLUMN_SONG_PATH="path";
    public static final String COLUMN_SONG_ARTIST="artistName";

    public static final String COLUMN_PLAYLIST_ID="id";
    public static final String COLUMN_PLAYLIST_TITLE="title";
    public static final String COLUMN_PLAYLIST_SONGID="songId";
    public static final String COLUMN_PLAYLIST_USERID="userId";

    public static final String COLUMN_ARTIST_ID="id";
    public static final String COLUMN_ARTIST_NAME="name";

    public static String getAssetsJsonData(Context context){
        String json;
        try{
             InputStream is = context.getAssets().open("json/songDto.json");
             int size=is.available();
             byte[] buffer=new byte[size];
             is.read(buffer);
             is.close();
             json=new String(buffer,"UTF-8");
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}