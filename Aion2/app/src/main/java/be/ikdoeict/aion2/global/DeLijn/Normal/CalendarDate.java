package be.ikdoeict.aion2.global.DeLijn.Normal;

import java.time.LocalDate;

public class CalendarDate {
    private int serviceId;
    private LocalDate date;
    private int exceptionType;

    public CalendarDate(){}
    public CalendarDate(int serviceId, LocalDate date, int exceptionType) {
        this.serviceId = serviceId;
        this.date = date;
        this.exceptionType = exceptionType;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(int exceptionType) {
        this.exceptionType = exceptionType;
    }
}
