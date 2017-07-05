package com.example.a0104.mhtalk.MapGPS;

/**
 * Created by a0104 on 2017-07-04.
 */

public class GPSPoint {
    private String id;
    private double lat;
    private double lon;

    public GPSPoint(String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
