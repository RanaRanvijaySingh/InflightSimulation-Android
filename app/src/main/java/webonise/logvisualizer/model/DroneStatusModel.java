package webonise.logvisualizer.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class DroneStatusModel {
    private LatLng location;
    private int batteryPercentage;
    private int satelliteCount;
    private double speed;
    private double altitude;
    private String alertMessage;
    private long time;

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public int getSatelliteCount() {
        return satelliteCount;
    }

    public void setSatelliteCount(int satelliteCount) {
        this.satelliteCount = satelliteCount;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
