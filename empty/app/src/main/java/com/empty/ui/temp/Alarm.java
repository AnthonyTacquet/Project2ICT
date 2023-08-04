package com.empty.ui.temp;


import android.content.Context;

public class Alarm {
    private int hour;
    private int minute;

    public Alarm(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Alarm(Context context){


    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
