package be.ikdoeict.aion2.global.Main;

public class TimeOnly {
    private int hour;
    private int minute;
    private int second;

    public TimeOnly(int hour, int minute, int second){
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static TimeOnly FromSecondsToTime(int seconds){
        int second = seconds % 60;
        int min = (seconds / 60)%60;
        int hours = (seconds/60)/60;

        return new TimeOnly(hours, min, second);
    }




    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return hour + ":" + minute + ":" + second;
    }
}
