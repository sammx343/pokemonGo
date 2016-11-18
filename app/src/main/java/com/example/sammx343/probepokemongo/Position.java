package com.example.sammx343.probepokemongo;

/**
 * Created by Sammx343 on 16/11/2016.
 */


public class Position {
    private String lat;
    private String ln;

    public Position(String lat, String ln) {
        this.lat = lat;
        this.ln = ln;
    }

    public String getLat() {
        return lat;
    }

    public String getLn() {
        return ln;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }
}
