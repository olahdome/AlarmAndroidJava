package com.example.alarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.Settings;

public class MediaPlayerSingleton {

    // static variable single_instance of type Singleton
    private static MediaPlayerSingleton single_instance = null;

    // variable of type String
    public MediaPlayer mediaPlayer;
    //Context context;
    // private constructor restricted to this class itself
    private MediaPlayerSingleton()
    {
        //mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
    }

    // static method to create instance of Singleton class
    public static MediaPlayerSingleton getInstance()
    {
        if (single_instance == null)
            single_instance = new MediaPlayerSingleton();
        return single_instance;
    }
}
