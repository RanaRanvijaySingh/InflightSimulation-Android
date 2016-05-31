package webonise.mapboxdemo.controller;

import android.app.Activity;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

import webonise.mapboxdemo.MainActivity;
import webonise.mapboxdemo.models.CameraProperties;
import webonise.mapboxdemo.models.FlightPlanParams;
import webonise.mapboxdemo.strategy.BandPassTransectGenerationStrategy;

//TODO : Temporary class - Can need to be replaced when integrated in main app.
public class TransectsController {
    private final Activity mActivity;
    private BandPassTransectGenerationStrategy mTransectStrategy;
    private PolygonBufferController mPolygonBufferedController;

    public TransectsController(MainActivity activity) {
        this.mActivity = activity;
        mPolygonBufferedController = new PolygonBufferController(mActivity);
    }

    //TODO : Will be replaced with original function from core app.
    public List<LatLng> getTransectsPoints(List<LatLng> poly) {
        mTransectStrategy = new BandPassTransectGenerationStrategy();
        List<LatLng> transects = generateTransects(poly, FlightPlanParams.makeDefaultParams());
        return transects;
    }

    //TODO : Will be replaced with original function from core app.
    public List<LatLng> generateTransects(List<LatLng> areaOfInterest, FlightPlanParams params) {
        mTransectStrategy = new BandPassTransectGenerationStrategy();
        List<LatLng> flightArea = mPolygonBufferedController
                .getBufferedPoints(areaOfInterest, params.getAlt());
        return mTransectStrategy.generateTransects(
                flightArea,
                params, getCameraProperties(), mActivity);
    }

    //TODO : Will be replaced with original function from core app.
    private CameraProperties getCameraProperties() {
        return CameraProperties.makeCameraPropsFromUserSettings(mActivity, null, null);
    }
}
