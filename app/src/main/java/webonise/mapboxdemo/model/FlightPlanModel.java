package webonise.mapboxdemo.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class FlightPlanModel {
    private int type;
    private List<LatLng> pointList;
    private LatLng homeLocation;
    private boolean isBufferEnabled;
    private List<LatLng> transectsList;
    private List<LatLng> droneTail;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<LatLng> getPointList() {
        return pointList;
    }

    public void setPointList(List<LatLng> pointList) {
        this.pointList = pointList;
    }

    public LatLng getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(LatLng homeLocation) {
        this.homeLocation = homeLocation;
    }

    public boolean isBufferEnabled() {
        return isBufferEnabled;
    }

    public void setBufferEnabled(boolean bufferEnabled) {
        isBufferEnabled = bufferEnabled;
    }

    public List<LatLng> getTransectsList() {
        return transectsList;
    }

    public void setTransectsList(List<LatLng> transectsList) {
        this.transectsList = transectsList;
    }

    public List<LatLng> getDroneTail() {
        return droneTail;
    }

    public void setDroneTail(List<LatLng> droneTail) {
        this.droneTail = droneTail;
    }
}
