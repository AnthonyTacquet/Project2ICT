package be.ikdoeict.aion2.global.DeLijn.Normal;

public class Shape {
    private int id; // PRIMARY KEY
    private double latitude;
    private double longitude;
    private int sequence; // PRIMARY KEY
    private double travelled;

    public Shape(){}
    public Shape(int id, double latitude, double longitude, int sequence, double travelled) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sequence = sequence;
        this.travelled = travelled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public double getTravelled() {
        return travelled;
    }

    public void setTravelled(double travelled) {
        this.travelled = travelled;
    }
}
