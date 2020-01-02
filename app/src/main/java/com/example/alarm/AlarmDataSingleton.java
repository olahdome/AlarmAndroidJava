package com.example.alarm;

import java.util.ArrayList;
import java.util.List;

public class AlarmDataSingleton {
    private static AlarmDataSingleton instance = null;

    ArrayList<String> setTimeList = new ArrayList<>();

    private AlarmDataSingleton() {}

    static AlarmDataSingleton getInstance() {
        if( instance == null ) {
            instance = new AlarmDataSingleton();
        }
        return instance;
    }

}
