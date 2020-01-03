package com.example.alarm;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements
        AlarmRecyclerViewAdapter.ItemClickListener,TimePickerFragment.OnTimeDialogListener{

    AlarmRecyclerViewAdapter adapter;
    ArrayList<String> setTime;
    String time;
    AlarmHandler alarmHandler;
    int position;
    //static int insertIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.alarm_recycler);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTime = AlarmDataSingleton.getInstance().setTimeList;
        alarmHandler = new AlarmHandler();
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(MainActivity.this, TimePickerActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String hour = intent.getStringExtra(TimePickerActivity.EXTRA_HOUR);
        String minute = intent.getStringExtra(TimePickerActivity.EXTRA_MINUTE);
        boolean isSet = intent.getBooleanExtra(TimePickerActivity.EXTRA_IS_SET, false);
        time = hour + ":" + minute;
        //setTimeList.add(time);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlarmRecyclerViewAdapter(this, setTime);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        if (isSet){
            Toast.makeText(this, "Alarm set for: " + hour + ":" + minute,Toast.LENGTH_LONG).show();
            insertSingleItem();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        this.position = position;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void insertSingleItem() {
        setTime.add(time);
        int insertIndex = setTime.size()-1;
        adapter.notifyItemInserted(insertIndex);
    }

    public void sleepAlarm(View view){
        alarmHandler.stopAlarm();
    }

    public void offAlarm(View view){
        alarmHandler.stopAlarm();
        alarmHandler.cancelAlarm(MainActivity.this);
        alarmHandler.isSet = false;
    }
    /*public void insert(View view) {
        insertSingleItem();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_navigation) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String newTime = hourOfDay + ":" + minute;
        setTime.set(position, newTime);
        adapter.notifyItemChanged(position);
        setAlarmTime(hourOfDay, minute);
    }

    private void setAlarmTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    hour,
                    minute,
                    0
        );
        alarmHandler.setAlarm(MainActivity.this,calendar.getTimeInMillis(),60000);
        alarmHandler.isSet = true;
    }
}
