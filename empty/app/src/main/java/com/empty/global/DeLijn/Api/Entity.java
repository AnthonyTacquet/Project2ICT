package com.empty.global.DeLijn.Api;

public class Entity{
    private TripApi trip;
    private long id;

    public Entity(){}
    public Entity(TripApi trip, long id) {
        this.trip = trip;
        this.id = id;
    }

    public TripApi getTrip() {
        return trip;
    }

    public void setTrip(TripApi trip) {
        this.trip = trip;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
