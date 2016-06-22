package com.saurabhjn76.myapplication;

/**
 * Created by saurabh on 22/6/16.
 */
public class Salon {
    private String salonName;
    private String addressLine1;
    private String type;
    private String addressLine2;
    private double rating;
    private boolean tv;
    private boolean ac;
    private double distance;

    public Salon() {}

    public Salon(String salonName, String addressLine1,String addressLine2, String type ,double rating, boolean tv,boolean ac,double distance) {
        this.salonName = salonName;
        this.addressLine1 = addressLine1;
        this.addressLine2=addressLine2;
        this.type=type;
        this.rating=rating;
        this.tv=tv;
        this.ac=ac;
        this.distance=distance;
    }

    public String getSalonName() {
        return this.salonName;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }
    public String getType(){
        return type;
    }

    public double getRating() {
        return rating;
    }

    public double getDistance() {
        return this.distance;
    }
    public boolean getTv(){
        return tv;
    }
    public boolean getAc(){
        return ac;
    }

}

