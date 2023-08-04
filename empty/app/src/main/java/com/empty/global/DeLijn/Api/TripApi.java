package com.empty.global.DeLijn.Api;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TripApi {
    private ArrayList<StopTimeApi> stopTime = new ArrayList<>();
    private int tripId;
    private int vehicleId;
    private LocalDateTime dateTime;

    public TripApi(ArrayList<StopTimeApi> stopTime, int tripId, int vehicleId, LocalDateTime dateTime) {
        this.stopTime = stopTime;
        this.tripId = tripId;
        this.vehicleId = vehicleId;
        this.dateTime = dateTime;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    public ArrayList<StopTimeApi> getStopTime() {
        return stopTime;
    }

    public void setStopTime(ArrayList<StopTimeApi> stopTime) {
        this.stopTime = stopTime;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
