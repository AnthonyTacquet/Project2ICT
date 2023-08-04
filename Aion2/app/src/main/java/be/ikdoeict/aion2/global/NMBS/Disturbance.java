package be.ikdoeict.aion2.global.NMBS;

import java.time.LocalDateTime;

public class Disturbance {
    private int id;
    private String title;
    private String description;
    private String link;
    private String type;
    private LocalDateTime timeStamp;
    private String attatchment;

    public Disturbance(int id, String title, String description, String link, String type, LocalDateTime timeStamp, String attatchment) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.type = type;
        this.timeStamp = timeStamp;
        this.attatchment = attatchment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAttatchment() {
        return attatchment;
    }

    public void setAttatchment(String attatchment) {
        this.attatchment = attatchment;
    }
}
