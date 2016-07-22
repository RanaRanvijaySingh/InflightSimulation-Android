package webonise.logvisualizer.controller;

import android.util.Log;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.Timer;
import java.util.TimerTask;

import webonise.logvisualizer.HomeActivity;
import webonise.logvisualizer.R;
import webonise.logvisualizer.model.FlightLogModel;
import webonise.logvisualizer.utilities.FileUtil;
import webonise.logvisualizer.view.DroneMarkerView;

public class SimulationController {

    private final HomeActivity mActivity;
    private final MapboxMap mMapboxMap;
    private final FlightLogModel mFlightLogModel;
    private MarkerOptions mDroneMarkerOptions;
    private int position = 0;
    Timer timer = new Timer();
    private Marker mDroneMarker;
    private DroneMarkerView mDroneView;

    public SimulationController(HomeActivity activity, MapboxMap mapboxMap, DroneMarkerView
            droneView) {
        this.mActivity = activity;
        this.mMapboxMap = mapboxMap;
        this.mDroneView = droneView;
        FileUtil fileUtil = new FileUtil(mActivity);
        mFlightLogModel = fileUtil.getFlightPlanModel();
    }

    /**
     * Function to draw mission plans
     * This function should called just once
     */
    public void initializeMissionPlan() {
        PolygonController polygonController = new PolygonController(mActivity, mMapboxMap);
        polygonController.initializeMissionBase(mFlightLogModel);
        drawDroneIcon(mFlightLogModel.getHomeLocation());
        mDroneView.initMarker(mFlightLogModel.getHomeLocation(), 100);
        mDroneView.setMap(mMapboxMap);
    }

    /**
     * Function to draw home icon on the map
     *
     * @param latLng
     */
    public void drawDroneIcon(LatLng latLng) {
        final Icon droneIcon = IconFactory.getInstance(mActivity).fromDrawable(
                mActivity.getResources().getDrawable(R.drawable.quad_marker));
        if (mMapboxMap != null) {
            mDroneMarkerOptions = new MarkerOptions();
            mDroneMarkerOptions.position(latLng);
            mDroneMarkerOptions.icon(droneIcon);
            mDroneMarker = mMapboxMap.addMarker(mDroneMarkerOptions);
        }
    }

    /**
     * Function to start mission plan
     */
    public void runFlightPlan() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i("", "Position : " + position);
//                updateDronePosition(mFlightLogModel.getDroneTail().get(position));
                position++;
            }
        }, 500, 1000);
    }

    private void updateDronePosition(final LatLng latLng) {
        if (mMapboxMap != null) {
            mDroneMarkerOptions.position(latLng);
            mDroneMarker.setPosition(latLng);
            mMapboxMap.invalidate();
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDroneView.moveBall(latLng, 100);
                }
            });
        }
    }
}
