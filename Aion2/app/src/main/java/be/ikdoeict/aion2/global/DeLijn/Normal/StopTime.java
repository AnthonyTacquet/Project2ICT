package be.ikdoeict.aion2.global.DeLijn.Normal;

import java.time.LocalTime;

public class StopTime {
    private int tripId; // PRIMARY KEY
    private int stopId; // PRIMARY KEY
    private Stop stop;
    private Trip trip;
    private int departureDelay;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int stopSequence;
    private int stopHeadsign = 0;
    private int pickUpType;
    private int dropOffType;
    private String shapeDistTravelled;

    public StopTime(){}
    public StopTime(int tripId, int stopId, int departureDelay, LocalTime arrivalTime, LocalTime departureTime, int stopSequence, int stopHeadsign, int pickUpType, int dropOffType, String shapeDistTravelled) {
        this.tripId = tripId;
        this.stopId = stopId;
        this.departureDelay = departureDelay;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopSequence = stopSequence;
        this.stopHeadsign = stopHeadsign;
        this.pickUpType = pickUpType;
        this.dropOffType = dropOffType;
        this.shapeDistTravelled = shapeDistTravelled;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(int departureDelay) {
        this.departureDelay = departureDelay;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public int getStopHeadsign() {
        return stopHeadsign;
    }

    public void setStopHeadsign(int stopHeadsign) {
        this.stopHeadsign = stopHeadsign;
    }

    public int getPickUpType() {
        return pickUpType;
    }

    public void setPickUpType(int pickUpType) {
        this.pickUpType = pickUpType;
    }

    public int getDropOffType() {
        return dropOffType;
    }

    public void setDropOffType(int dropOffType) {
        this.dropOffType = dropOffType;
    }

    public String getShapeDistTravelled() {
        return shapeDistTravelled;
    }

    public void setShapeDistTravelled(String shapeDistTravelled) {
        this.shapeDistTravelled = shapeDistTravelled;
    }
}
