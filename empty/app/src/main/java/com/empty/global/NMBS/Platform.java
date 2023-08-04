package com.empty.global.NMBS;

public class Platform {
    private String name;
    private String normal;

    public Platform(String name, String normal) {
        this.name = name;
        this.normal = normal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "name='" + name + '\'' +
                ", normal='" + normal + '\'' +
                '}';
    }
}
