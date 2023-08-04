package com.empty.global.NMBS;

import java.util.ArrayList;

public class Composition {

    private ArrayList<Segment> segments = new ArrayList<>();

    public Composition(ArrayList<Segment> segments) {
        this.segments = segments;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<Segment> segments) {
        this.segments = segments;
    }
}
