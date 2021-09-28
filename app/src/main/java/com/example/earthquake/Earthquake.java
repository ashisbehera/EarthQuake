package com.example.earthquake;

public class Earthquake {
    private double mMagnitude;
    private String mlocation;
    private long mtimeinmilisec;
    private String murl;

    public Earthquake(double magnitude , String location , long timeinmilis , String url){
        mMagnitude = magnitude;
        mlocation = location;
        mtimeinmilisec = timeinmilis;
        murl = url;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getMlocation() {
        return mlocation;
    }

    public long getMtimeinmilisec() {
        return mtimeinmilisec;
    }

    public String getMurl() {
        return murl;
    }
}
