package be.ikdoeict.aion2.global.DeLijn.Normal;

import java.time.LocalDate;

public class FeedInfo {
    private String publisherName;
    private String publisherUrl;
    private String language;
    private LocalDate startDate;
    private LocalDate endDate;
    private String version;
    private String mail;

    public FeedInfo(){}
    public FeedInfo(String publisherName, String publisherUrl, String language, LocalDate startDate, LocalDate endDate, String version, String mail) {
        this.publisherName = publisherName;
        this.publisherUrl = publisherUrl;
        this.language = language;
        this.startDate = startDate;
        this.endDate = endDate;
        this.version = version;
        this.mail = mail;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
