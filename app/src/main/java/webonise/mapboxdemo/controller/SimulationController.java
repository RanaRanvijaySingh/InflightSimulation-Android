package webonise.mapboxdemo.controller;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import webonise.mapboxdemo.MainActivity;

public class SimulationController {

    private final MainActivity mActivity;
    private final MapboxMap mMapboxMap;

    public SimulationController(MainActivity activity, MapboxMap mapboxMap) {
        this.mActivity = activity;
        this.mMapboxMap = mapboxMap;
    }

    /**
     * Function to draw mission plans
     * This function should called just once
     */
    public void drawMissionPlan() {
        PolygonController polygonController = new PolygonController(mActivity, mMapboxMap);
        polygonController.drawPolygon();
    }
}
