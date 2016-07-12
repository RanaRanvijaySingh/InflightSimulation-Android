package webonise.mapboxdemo.controller;

import com.mapbox.mapboxsdk.maps.MapboxMap;

import webonise.mapboxdemo.model.FlightPlanModel;
import webonise.mapboxdemo.utilities.FileUtil;
import webonise.mapboxdemo.view.MainActivity;

public class SimulationController {

    private final MainActivity mActivity;
    private final MapboxMap mMapboxMap;
    private final FlightPlanModel mFlightPlanModel;

    public SimulationController(MainActivity activity, MapboxMap mapboxMap) {
        this.mActivity = activity;
        this.mMapboxMap = mapboxMap;
        FileUtil fileUtil = new FileUtil(mActivity);
        mFlightPlanModel = fileUtil.getFlightPlanModel();
    }

    /**
     * Function to draw mission plans
     * This function should called just once
     */
    public void initializeMissionPlan() {
        PolygonController polygonController = new PolygonController(mActivity, mMapboxMap);
        polygonController.initializeMissionBase(mFlightPlanModel);

    }
}
