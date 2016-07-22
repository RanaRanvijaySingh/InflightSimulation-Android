package webonise.logvisualizer.model;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class FlightLogModel {
    private List<LatLng> pointList;
    private LatLng homeLocation;
    private boolean isBufferEnabled;
    private List<LatLng> transectsList;
    private List<DroneStatusModel> droneStatusModelList;

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

    public List<DroneStatusModel> getDroneStatusModelList() {
        return droneStatusModelList;
    }

    public void setDroneStatusModelList(List<DroneStatusModel> droneStatusModelList) {
        this.droneStatusModelList = droneStatusModelList;
    }
}
