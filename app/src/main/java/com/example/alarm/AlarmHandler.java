package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {
    public void stopAlarm(){
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        if (mediaPlayerSingleton.mediaPlayer != null) {
            mediaPlayerSingleton.stop();
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
