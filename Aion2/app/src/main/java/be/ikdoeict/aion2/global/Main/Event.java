package be.ikdoeict.aion2.global.Main;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Event implements Parcelable { // Serializable
    private LocalDateTime dateTime = null;
    private Location location = null;
    private String name = "";

    public Event(LocalDateTime dateTime, Location location, String name){
        this.dateTime = dateTime;
        this.location = location;
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeSerializable(location);
        dest.writeSerializable(dateTime);
    }

    protected Event(Parcel in) {
        name = in.readString();
        location = (Location) in.readSerializable();
        dateTime = (LocalDateTime) in.readSerializable();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return  dateTime.toString() + " " + name + "\n"  + location.toString();
    }
}
