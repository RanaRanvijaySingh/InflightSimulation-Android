package webonise.mapboxdemo.controller;

import android.app.Activity;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

import webonise.mapboxdemo.MainActivity;

public class TransectsController {
    private final Activity mActivity;

    public TransectsController(MainActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Function to get transects points from given lat lng list
     * @param latLngList List<LatLng>
     */
    public void getTransectsPoints(List<LatLng> latLngList) {
        mTransectStrategy = new BandPassTransectGenerationStrategy();
        List<LatLng> transects = null;
        if (mActivePlan != null) {
            transects = generateTransects(poly, mActivePlan.getParams());
        } else {
            transects = generateTransects(poly, FlightPlanParams.makeDefaultParams());
        }
        if (poly != null) {
            FlightLogger.i(TAG, "Polygon: " + poly.toString());
            FlightLogger.i(TAG, "Transects: " + transects.toString());
        }
        return transects;
    }
}
