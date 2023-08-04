package com.empty.global.NMBS;

import java.util.ArrayList;

public class Vehicle {
    private String name;
    private String shortName;
    private double locationX;
    private double locationY;
    private String id;

    private ArrayList<Stop> stops = new ArrayList<>();

    public Vehicle(String name, String shortName, double locationX, double locationY, String id) {
        this.name = name;
        this.shortName = shortName;
        this.locationX = locationX;
        this.locationY = locationY;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Stop> getStops() {
        return stops;
    }

    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", id='" + id + '\'' +
                '}';
    }
}
