package be.ikdoeict.aion2.global.DeLijn.Api;

public class StopTimeApi {
    private int stopCode;
    private int stopId;
    private int departureDelay;

    public StopTimeApi(int stopCode, int stopId, int departureDelay) {
        this.stopCode = stopCode;
        this.stopId = stopId;
        this.departureDelay = departureDelay;
    }

    public int getStopCode() {
        return stopCode;
    }

    public void setStopCode(int stopCode) {
        this.stopCode = stopCode;
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
}
