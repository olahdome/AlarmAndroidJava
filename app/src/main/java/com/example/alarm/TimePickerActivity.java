package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity {

    TimePicker timePicker;
    Button setAlarmButton;
    TextView textView;
    //MediaPlayerSingleton mediaPlayerSingleton;
    EditText editText;
    AlarmHandler alarmHandler;
    int hour;
    int minute;
    public final static String EXTRA_HOUR = "com.example.alarm.HOUR";
    public final static String EXTRA_MINUTE = "com.example.alarm.MINUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        timePicker = findViewById(R.id.time_picker);
        setAlarmButton = findViewById(R.id.set_alarm_button);
        textView = findViewById(R.id.alarmTextView);
        editText = findViewById(R.id.repeat_time_ET);
        timePicker.setIs24HourView(true);
        alarmHandler = new AlarmHandler();
    }

    public void setCalendar(View view) {
        Calendar calendar = Calendar.getInstance();
        if(Build.VERSION.SDK_INT >= 23) {
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.getHour(),
                    timePicker.getMinute(),
                    0
            );
        }else{
            calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute(),
                    0
            );
        }
        //int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //int minute = calendar.get(Calendar.MINUTE);
        //Toast.makeText(getApplicationContext(), hour + " " + minute + " " + calendar.getTimeInMillis(), Toast.LENGTH_LONG).show();

        setAlarm(calendar);
    }

    private void setAlarm(Calendar calendar) {
        long timeInMillis = calendar.getTimeInMillis();
        String repeatTimeString = editText.getText().toString();
        int repeatTime = 0;
        try {
            repeatTime = Integer.parseInt(repeatTimeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (repeatTime != 0) {
            alarmHandler.setAlarm(TimePickerActivity.this, timeInMillis, repeatTime*60000);
            alarmHandler.isSet = true;
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
            String timeText = "Alarm set to: " + hour + ":" + minute;
            Toast.makeText(TimePickerActivity.this, timeText, Toast.LENGTH_SHORT).show();
            textView.setText(timeText);
        }
        else {
            Toast.makeText(TimePickerActivity.this, "Write repeat interval in minutes", Toast.LENGTH_LONG).show();
        }

        /*
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(TimePickerActivity.this, AlarmAdapter.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(
                TimePickerActivity.this, 0, alarmIntent, 0);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, 60000, alarmPendingIntent);
        }*/

        /*
        int ampm = calendar.get(Calendar.AM_PM);
        String day = "";
        if(ampm == Calendar.AM){
            day = "AM";
        }else if(ampm == Calendar.PM){
            day = "PM";
        }
        String timeText = "Alarm set for: ";
        timeText += hour +": " + minute + " " + day;
        tv_display.setText(timeText);

         */
    }

    public void clearAlarm(View view) {
        if (alarmHandler.isSet) {
            alarmHandler.stopAlarm();
            alarmHandler.cancelAlarm(TimePickerActivity.this);
            alarmHandler.isSet = false;
            Toast.makeText(TimePickerActivity.this, "Alarm has been cleared", Toast.LENGTH_SHORT).show();
            textView.setText(getString(R.string.no_alarm_set));
        }
        else{
            Toast.makeText(TimePickerActivity.this, "No alarm set", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void stopAlarm(){
        MediaPlayerSingleton mediaPlayerSingleton = MediaPlayerSingleton.getInstance();
        if (mediaPlayerSingleton.mediaPlayer != null) {
            mediaPlayerSingleton.stop();
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

     */

    public void saveAlarm(View view) {
        if (alarmHandler.isSet) {
            Intent mainActivityintent = new Intent(TimePickerActivity.this, MainActivity.class);
            mainActivityintent.putExtra(EXTRA_HOUR, hour);
            mainActivityintent.putExtra(EXTRA_MINUTE, minute);
            startActivity(mainActivityintent);
        }
        else {
            Toast.makeText(TimePickerActivity.this, "Please set the alarm", Toast.LENGTH_LONG).show();
        }
    }

    /*
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    */
}
