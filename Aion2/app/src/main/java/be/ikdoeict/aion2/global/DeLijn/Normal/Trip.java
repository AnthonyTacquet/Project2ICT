package be.ikdoeict.aion2.global.DeLijn.Normal;


import java.time.LocalDateTime;
import java.util.ArrayList;

import be.ikdoeict.aion2.global.DeLijn.Interface.IStopTime;

public class Trip implements IStopTime {
    private int id;
    private int routeId;
    private int serviceId;
    private ArrayList<StopTime> stopTimes = new ArrayList<>();
    private Route route;
    private CalendarDate calendarDate;
    private Shape shape;
    private int vehicleId; // API
    private LocalDateTime dateTime; // API
    private String headSign;
    private String shortName;
    private int directionId;
    private int blockId = 0;
    private int shapeId = 0;

    public Trip(){}
    public Trip(int id, int routeId, int serviceId, String headSign, String shortName, int directionId, int blockId, int shapeId) {
        this.id = id;
        this.routeId = routeId;
        this.serviceId = serviceId;
        this.headSign = headSign;
        this.shortName = shortName;
        this.directionId = directionId;
        this.blockId = blockId;
        this.shapeId = shapeId;
    }

    @Override
    public void AddStopTime(StopTime stopTime) {
        this.stopTimes.add(stopTime);
    }

    @Override
    public void AddStopTimes(ArrayList<StopTime> stopTimes) {
        this.stopTimes.addAll(stopTimes);
    }

    public Route getRoute(){return this.route;}

    public void setRoute(Route route){
        this.route = route;
    }

    public Shape getShape(){return this.shape;}

    public void setShape(Shape shape){
        this.shape = shape;
    }

    public CalendarDate getCalendarDate(){return this.calendarDate;}

    public void setCalendarDate(CalendarDate calendarDate){this.calendarDate = calendarDate;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getHeadSign() {
        return headSign;
    }

    public void setHeadSign(String headSign) {
        this.headSign = headSign;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getDirectionId() {
        return directionId;
    }

    public void setDirectionId(int directionId) {
        this.directionId = directionId;
    }

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getShapeId() {
        return shapeId;
    }

    public void setShapeId(int shapeId) {
        this.shapeId = shapeId;
    }
}
