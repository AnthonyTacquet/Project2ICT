package com.empty.global.Main;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event {
    private LocalDateTime dateTime = null;
    private Location location = null;
    private String name = "";

    public Event(LocalDateTime dateTime, Location location, String name){
        this.dateTime = dateTime;
        this.location = location;
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return  dateTime.toString() + " " + name + "\n"  + location.toString();
    }
}
