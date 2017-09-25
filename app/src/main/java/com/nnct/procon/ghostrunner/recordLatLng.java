package com.nnct.procon.ghostrunner;

/**
 * Created by kaito on 2017/09/11.
 */

public class recordLatLng {
    public void setLat(double lat,int index) {
        this.lat[index] = lat;
    }

    public double getLat(int index) {
        return lat[index];
    }

    public double getLng(int index) {
        return lng[index];
    }

    public void setLng(double lng, int index) {
        this.lng[index] = lng;

    }

    public recordLatLng() {

    }

    private double lat[] = new double[1800];
    private double lng[] = new double[1800];



}
