package com.empty.global.NMBS;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Arrival {
    private int delay;
    private String stationName;
    private Station station;
    private Date dateTime;
    private String vehicleName;
    private Vehicle vehicle;
    private int platformNumber;
    private Platform platform;
    private boolean arrived;
    private boolean canceled;
    private int walking;
    private String direction;

    public Arrival(int delay, String stationName, Station station, Date dateTime, String vehicleName, Vehicle vehicle, int platformNumber, Platform platform, boolean arrived, boolean canceled, int walking, String direction) {
        this.delay = delay;
        this.stationName = stationName;
        this.station = station;
        this.dateTime = dateTime;
        this.vehicleName = vehicleName;
        this.vehicle = vehicle;
        this.platformNumber = platformNumber;
        this.platform = platform;
        this.arrived = arrived;
        this.canceled = canceled;
        this.walking = walking;
        this.direction = direction;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(int platformNumber) {
        this.platformNumber = platformNumber;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getWalking() {
        return walking;
    }

    public void setWalking(int walking) {
        this.walking = walking;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "delay=" + delay +
                ", stationName='" + stationName + '\'' +
                ", station=" + station +
                ", dateTime=" + dateTime +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicle=" + vehicle +
                ", platformNumber=" + platformNumber +
                ", platform=" + platform +
                ", arrived=" + arrived +
                ", canceled=" + canceled +
                ", walking=" + walking +
                ", direction='" + direction + '\'' +
                '}';
    }
}
