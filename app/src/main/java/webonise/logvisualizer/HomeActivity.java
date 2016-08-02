package webonise.logvisualizer;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import webonise.logvisualizer.controller.SimulationController;
import webonise.logvisualizer.interfaces.HomeView;
import webonise.logvisualizer.model.DroneStatusModel;
import webonise.logvisualizer.model.FlightLogModel;
import webonise.logvisualizer.utilities.Constants;
import webonise.logvisualizer.utilities.PermissionUtil;
import webonise.logvisualizer.view.DroneMarkerView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, HomeView {

    private static final float ZOOM_DEFAULT = 18.0f;
    private MapboxMap mapboxMap;
    private MapView mapview;
    private boolean hasInternetPermission;

    private boolean hasFileWritePermission;
    private SimulationController mSimulationController;
    private DroneMarkerView mDroneView;
    private FlightLogModel mFlightLogModel;
    private TextView tvDroneAltitude;
    private TextView tvMissionTime;
    private TextView tvDroneSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlightLogModel = getFlightLogModel();
        requestForPermissions();
        initializeMapView(savedInstanceState);
        initializeComponents();
    }

    /**
     * Function to get flight log model from Intent data.
     *
     * @return FlightLogModel
     */
    private FlightLogModel getFlightLogModel() {
        if (getIntent() != null
                && getIntent().getParcelableExtra(Constants.IntentKeys.FLIGHT_PLAN_MODEL) != null)
            return getIntent().getParcelableExtra(Constants.IntentKeys.FLIGHT_PLAN_MODEL);
        else
            return null;
    }

    private void requestForPermissions() {
        final PermissionUtil permissionUtil = new PermissionUtil(this);
        permissionUtil.checkPermission(Manifest.permission.INTERNET, new PermissionUtil.OnPermissionGranted() {
            @Override
            public void permissionGranted() {
                hasInternetPermission = true;
            }
        }, "Need INTERNET permission.");
        permissionUtil.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionUtil
                .OnPermissionGranted() {
            @Override
            public void permissionGranted() {
                hasFileWritePermission = true;
            }
        }, "Need Storage permission.");
    }

    /**
     * Function to initialize mapbox view
     *
     * @param savedInstanceState
     */
    private void initializeMapView(Bundle savedInstanceState) {
        if (hasInternetPermission) {
            mapview = (MapView) findViewById(R.id.mapview);
            mapview.onCreate(savedInstanceState);
            mapview.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(MapboxMap mapboxMap) {
                    initMapBox(mapboxMap);
                }
            });
        } else {
            requestForPermissions();
        }
    }

    /**
     * Function to initialize mapbox
     *
     * @param mapboxMap MapboxMap
     */
    public void initMapBox(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        Toast.makeText(HomeActivity.this, "Map box object initialized", Toast.LENGTH_SHORT).show();
        CameraPosition.Builder b = new CameraPosition.Builder();
        if (mFlightLogModel != null)
            b.target(mFlightLogModel.getHomeLocation());
        b.zoom(ZOOM_DEFAULT);
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(b.build()), 1000);
    }

    /**
     * Function to initialize components
     */
    private void initializeComponents() {
        Button bReplay = (Button) findViewById(R.id.bShowMission);
        Button bStartMission = (Button) findViewById(R.id.bStartMission);
        mDroneView = (DroneMarkerView) findViewById(R.id.main_drone_view);
        tvDroneAltitude = (TextView) findViewById(R.id.tvDroneAltitude);
        tvDroneSpeed = (TextView) findViewById(R.id.tvDroneSpeed);
        tvMissionTime = (TextView) findViewById(R.id.tvMissionTime);
        bReplay.setOnClickListener(this);
        bStartMission.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bShowMission:
                showMission();
                break;
            case R.id.bStartMission:
                startMission();
                break;
        }
    }

    /**
     * Function to show mission
     */
    private void showMission() {
        if (mFlightLogModel != null) {
            mSimulationController = new SimulationController(this, mapboxMap, mDroneView,
                    mFlightLogModel, this);
            mSimulationController.initializeMissionPlan();
        }
    }

    /**
     * Function to start mission
     */
    private void startMission() {
        mSimulationController.runFlightPlan();
    }

    @Override
    public void updateDroneInfo(DroneStatusModel droneStatusModel) {
        tvDroneAltitude.setText(String.format(getString(R.string.drone_alt),
                droneStatusModel.getAltitude()));
        tvDroneSpeed.setText(String.format(getString(R.string.drone_speed),
                droneStatusModel.getSpeed()));
        tvMissionTime.setText(String.format(getString(R.string.mission_time),
                droneStatusModel.getTime()));
    }
}
