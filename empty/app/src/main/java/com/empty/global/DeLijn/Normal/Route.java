package com.empty.global.DeLijn.Normal;

import com.empty.global.DeLijn.Interface.ITrip;

import java.util.ArrayList;

public class Route implements ITrip {
    private int id;
    private int agencyId;
    private ArrayList<Trip> trips = new ArrayList<>();
    private Agency agency;
    private String shortName;
    private String longName;
    private String desc;
    private int type;
    private String url;
    private String color;
    private String textColor;

    public Route(){}
    public Route(int id, int agencyId, String shortName, String longName, String desc, int type, String url, String color, String textColor) {
        this.id = id;
        this.agencyId = agencyId;
        this.shortName = shortName;
        this.longName = longName;
        this.desc = desc;
        this.type = type;
        this.url = url;
        this.color = color;
        this.textColor = textColor;
    }

    @Override
    public void AddTrips(ArrayList<Trip> trips) {
        this.trips.addAll(trips);
    }

    @Override
    public void AddTrip(Trip trip) {
        this.trips.add(trip);
    }

    public Agency getAgency(){
        return this.agency;
    }

    public void setAgency(Agency agency){
        this.agency = agency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
