package com.empty.global.NMBS;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Stop {
    private int id;
    private Station station;
    private Date time;
    private int delay;
    private Platform platform;
    private boolean canceled;
    private int departureDelay;
    private boolean departureCanceled;
    private Date scheduledDepartureTime;
    private int arrivalDelay;
    private int left;
    private boolean arrivalCanceled;
    private boolean isExtraStop;
    private boolean arrived;
    private Date scheduleArrivalTime;
    private String departureConnection;
    private String occupancy;

    public Stop(int id, Station station, Date time, int delay, Platform platform, boolean canceled, int departureDelay, boolean departureCanceled, Date scheduledDepartureTime, int arrivalDelay, int left, boolean arrivalCanceled, boolean isExtraStop, boolean arrived, Date scheduleArrivalTime, String departureConnection, String occupancy) {
        this.id = id;
        this.station = station;
        this.time = time;
        this.delay = delay;
        this.platform = platform;
        this.canceled = canceled;
        this.departureDelay = departureDelay;
        this.departureCanceled = departureCanceled;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.arrivalDelay = arrivalDelay;
        this.left = left;
        this.arrivalCanceled = arrivalCanceled;
        this.isExtraStop = isExtraStop;
        this.arrived = arrived;
        this.scheduleArrivalTime = scheduleArrivalTime;
        this.departureConnection = departureConnection;
        this.occupancy = occupancy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(int departureDelay) {
        this.departureDelay = departureDelay;
    }

    public boolean isDepartureCanceled() {
        return departureCanceled;
    }

    public void setDepartureCanceled(boolean departureCanceled) {
        this.departureCanceled = departureCanceled;
    }

    public Date getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public void setScheduledDepartureTime(Date scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public int getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(int arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public boolean isArrivalCanceled() {
        return arrivalCanceled;
    }

    public void setArrivalCanceled(boolean arrivalCanceled) {
        this.arrivalCanceled = arrivalCanceled;
    }

    public boolean isExtraStop() {
        return isExtraStop;
    }

    public void setExtraStop(boolean extraStop) {
        isExtraStop = extraStop;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public Date getScheduleArrivalTime() {
        return scheduleArrivalTime;
    }

    public void setScheduleArrivalTime(Date scheduleArrivalTime) {
        this.scheduleArrivalTime = scheduleArrivalTime;
    }

    public String getDepartureConnection() {
        return departureConnection;
    }

    public void setDepartureConnection(String departureConnection) {
        this.departureConnection = departureConnection;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    @Override
    public String toString() {
        return (canceled?"CANCELED  ": "") +
                "station: " + station +
                ", arrival time: " + time.getHours() +
                ":" +time.getMinutes() +
                " " + (delay!=0?"+" + delay:"");
    }
}
