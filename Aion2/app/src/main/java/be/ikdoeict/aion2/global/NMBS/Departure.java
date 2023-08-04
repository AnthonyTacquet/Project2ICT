package be.ikdoeict.aion2.global.NMBS;

import java.util.ArrayList;
import java.util.Date;

public class Departure {
    private int delay;
    private String stationName;
    private Station station;
    private Date dateTime;
    private String vehicleName;
    private Vehicle vehicle;
    private int platformNumber;
    private Platform platform;
    private boolean left;
    private boolean canceled;
    private String direction;
    private int stopsNumber;
    private ArrayList<Stop> stops;
    private ArrayList<Alert> alerts;
    private int walking;
    private String departureConnection;

    public Departure(int delay, String stationName, Station station, Date dateTime, String vehicleName, Vehicle vehicle, int platformNumber, Platform platform, boolean left, boolean canceled, String direction, int stopsNumber, ArrayList<Stop> stops, ArrayList<Alert> alerts, int walking, String departureConnection) {
        this.delay = delay;
        this.stationName = stationName;
        this.station = station;
        this.dateTime = dateTime;
        this.vehicleName = vehicleName;
        this.vehicle = vehicle;
        this.platformNumber = platformNumber;
        this.platform = platform;
        this.left = left;
        this.canceled = canceled;
        this.direction = direction;
        this.stopsNumber = stopsNumber;
        this.stops = stops;
        this.alerts = alerts;
        this.walking = walking;
        this.departureConnection = departureConnection;
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

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getStopsNumber() {
        return stopsNumber;
    }

    public void setStopsNumber(int stopsNumber) {
        this.stopsNumber = stopsNumber;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    public int getWalking() {
        return walking;
    }

    public void setWalking(int walking) {
        this.walking = walking;
    }

    public String getDepartureConnection() {
        return departureConnection;
    }

    public void setDepartureConnection(String departureConnection) {
        this.departureConnection = departureConnection;
    }

    @Override
    public String toString() {
        return "Departure{" +
                "delay=" + delay +
                ", stationName='" + stationName + '\'' +
                ", station=" + station +
                ", dateTime=" + dateTime +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicle=" + vehicle +
                ", platformNumber=" + platformNumber +
                ", platform=" + platform +
                ", left=" + left +
                ", canceled=" + canceled +
                ", direction='" + direction + '\'' +
                ", stopsNumber=" + stopsNumber +
                ", stops=" + stops +
                ", alerts=" + alerts +
                ", walking=" + walking +
                ", departureConnection='" + departureConnection + '\'' +
                '}';
    }
}
