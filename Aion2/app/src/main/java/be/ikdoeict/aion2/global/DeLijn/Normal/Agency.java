package be.ikdoeict.aion2.global.DeLijn.Normal;


import java.util.ArrayList;

import be.ikdoeict.aion2.global.DeLijn.Interface.IRoute;

public class Agency implements IRoute {
    private int id;
    private ArrayList<Route> routes = new ArrayList<>();
    private String name;
    private String url;
    private String timeZone;
    private String language;
    private String phone;

    public Agency(){}
    public Agency(int id, String name, String url, String timeZone, String language, String phone) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.timeZone = timeZone;
        this.language = language;
        this.phone = phone;
    }

    @Override
    public void AddRoutes(ArrayList<Route> routes) {
        routes.addAll(routes);
    }

    @Override
    public void AddRoute(Route route) {
        routes.add(route);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
