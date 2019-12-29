package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity {

    TimePicker timePicker;
    Button setAlarmButton;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        timePicker = findViewById(R.id.time_picker);
        setAlarmButton = findViewById(R.id.set_alarm_button);
        textView = findViewById(R.id.textView);
        timePicker.setIs24HourView(true);
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

        setAlarm(calendar.getTimeInMillis(), calendar);
    }

    private void setAlarm(long timeInMillis, Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(TimePickerActivity.this, AlarmAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                TimePickerActivity.this, 0, intent, 0);

        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, 60000, pendingIntent);
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Toast.makeText(TimePickerActivity.this, "Alarm set to: " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
        String timeText = "Alarm set to: " + hour + ":" + minute;
        textView.setText(timeText);
        //Intent mainActivityintent
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
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(TimePickerActivity.this, AlarmAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(TimePickerActivity.this, 0, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(TimePickerActivity.this, "Alarm has been cleared", Toast.LENGTH_SHORT).show();
        textView.setText(getString(R.string.no_alarm_set));
        //tv_display.setText("Alarm not set");
    }
    /*
    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    */
}
