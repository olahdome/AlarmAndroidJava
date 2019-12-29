package com.example.alarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;

public class MediaPlayerSingleton {

    // static variable single_instance of type Singleton
    private static MediaPlayerSingleton single_instance = null;

    public MediaPlayer mediaPlayer;
    // private constructor restricted to this class itself
    private MediaPlayerSingleton() {
        //mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
    }

    // static method to create instance of Singleton class
    public static MediaPlayerSingleton getInstance() {
        if (single_instance == null)
            single_instance = new MediaPlayerSingleton();
        return single_instance;
    }

    public void create(Context context, Uri uri){
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
    }

    public void start(){
        mediaPlayer.start();
    }

    public void stop(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
