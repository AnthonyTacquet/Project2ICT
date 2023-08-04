package com.empty.global.NMBS;

public class Station implements Comparable<Station> {
    private double locationY;
    private double locationX;
    private String standardName;
    private String name;
    private String id;
    private String apiId;

    public Station(){}

    public Station(double locationY, double locationX, String standardName, String name, String id, String apiId){
        this.locationX = locationX;
        this.locationY = locationY;
        this.standardName = standardName;
        this.name = name;
        this.id = id;
        this.apiId = apiId;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Override
    public String toString() {
        return standardName;
    }

    @Override
    public int compareTo(Station o) {
        return this.name.compareTo(o.name);
    }


}
