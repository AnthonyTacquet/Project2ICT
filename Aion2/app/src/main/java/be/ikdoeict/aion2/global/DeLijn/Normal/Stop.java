package be.ikdoeict.aion2.global.DeLijn.Normal;

import java.util.ArrayList;

import be.ikdoeict.aion2.global.DeLijn.Interface.IStopTime;

public class Stop implements IStopTime {
    private int id;
    private ArrayList<StopTime> stopTimes = new ArrayList<>();
    private int code;
    private String name;
    private String desc = null;
    private double latitude;
    private double longitude;
    private int zoneId = 0;
    private String url;
    private String locationType = null;
    private String parentStation = null;
    private boolean wheelchairBoarding;

    public Stop(){}
    public Stop(int id, int code, String name, String desc, double latitude, double longitude, int zoneId, String url, String locationType, String parentStation, boolean wheelchairBoarding) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zoneId = zoneId;
        this.url = url;
        this.locationType = locationType;
        this.parentStation = parentStation;
        this.wheelchairBoarding = wheelchairBoarding;
    }
    @Override
    public void AddStopTime(StopTime stopTime) {
        this.stopTimes.add(stopTime);
    }

    @Override
    public void AddStopTimes(ArrayList<StopTime> stopTimes) {
        this.stopTimes.addAll(stopTimes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getParentStation() {
        return parentStation;
    }

    public void setParentStation(String parentStation) {
        this.parentStation = parentStation;
    }

    public boolean isWheelchairBoarding() {
        return wheelchairBoarding;
    }

    public void setWheelchairBoarding(boolean wheelchairBoarding) {
        this.wheelchairBoarding = wheelchairBoarding;
    }
}
