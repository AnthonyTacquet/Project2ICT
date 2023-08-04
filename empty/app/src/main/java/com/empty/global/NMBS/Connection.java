package com.empty.global.NMBS;

import com.empty.global.Main.TimeOnly;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Connection {
    private int id;
    private Departure departure;
    private Arrival arrival;
    private TimeOnly duration;
    private ArrayList<Alert> alerts;

    public Connection(int id, Departure departure, Arrival arrival, TimeOnly duration, ArrayList<Alert> alerts) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.duration = duration;
        this.alerts = alerts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Arrival getArrival() {
        return arrival;
    }

    public void setArrival(Arrival arrival) {
        this.arrival = arrival;
    }

    public TimeOnly getDuration() {
        return duration;
    }

    public void setDuration(TimeOnly duration) {
        this.duration = duration;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    public String shortString(){
        return "FROM: " + departure.getStationName() +
                ", platform: " + departure.getPlatformNumber() +
                "\n" + departure.getDateTime().getHours() +
                ":" + departure.getDateTime().getMinutes() +
                "\nTO: " + arrival.getStationName() +
                ", platform: " + arrival.getPlatformNumber() +
                "\n" + arrival.getDateTime().getHours() +
                ":" + arrival.getDateTime().getMinutes();
    }

    @Override
    public String toString() {
        return (departure.isCanceled() ? "CANCELED " : "") +
                "FROM: " + departure.getStationName() +
                ", platform: " + departure.getPlatformNumber() +
                "\n" + departure.getDateTime().getHours() +
                ":" + departure.getDateTime().getMinutes() +
                (departure.getDelay()!=0? "+" + departure.getDelay():"") +
                "\nTO: " + arrival.getStationName() +
                ", platform: " + arrival.getPlatformNumber() +
                "\n" + arrival.getDateTime().getHours() +
                ":" + arrival.getDateTime().getMinutes() +
                (arrival.getDelay()!=0? "+" + arrival.getDelay():"") +
                "     duration: " + duration.getHour() + ":" + duration.getMinute();
    }
}
