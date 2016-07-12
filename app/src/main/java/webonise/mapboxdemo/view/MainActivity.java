package webonise.mapboxdemo.view;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.R;
import webonise.mapboxdemo.controller.SimulationController;
import webonise.mapboxdemo.utilities.FileUtil;
import webonise.mapboxdemo.utilities.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final float ZOOM_DEFAULT = 15.0f;
    private MapboxMap mapboxMap;
    private MapView mapview;
    private boolean hasInternetPermission;

    final List<LatLng> latLngPolygon = new ArrayList<>();
    private LatLng myLoc = new LatLng();
    private boolean hasFileWritePermission;
    {
        myLoc.setLatitude(18.515600);
        myLoc.setLongitude(73.781900);
    }

    {
        latLngPolygon.add(new LatLng(28.6139, 77.2090));//delhi
        latLngPolygon.add(new LatLng(22.2587, 71.1924));//gujarat
        latLngPolygon.add(new LatLng(18.5204, 73.8567));//pune
        latLngPolygon.add(new LatLng(12.9716, 77.5946));//banglore
        latLngPolygon.add(new LatLng(25.5941, 85.1376));//patna
        latLngPolygon.add(new LatLng(28.6139, 77.2090));//delhi
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestForPermissions();
        initializeMapView(savedInstanceState);
        initializeComponents();
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
        Toast.makeText(MainActivity.this, "Map box object initialized", Toast.LENGTH_SHORT).show();
        CameraPosition.Builder b = new CameraPosition.Builder();
        b.target(new LatLng(myLoc.getLatitude(), myLoc.getLongitude()));
        b.zoom(ZOOM_DEFAULT);
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(b.build()), 1000);
    }

    /**
     * Function to initialize components
     */
    private void initializeComponents() {
        Button bReplay = (Button) findViewById(R.id.bReplay);
        Button bCreateFile = (Button) findViewById(R.id.bCreateFile);
        bReplay.setOnClickListener(this);
        bCreateFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bReplay:
                startSimulation();
                break;
            case R.id.bCreateFile:
                FileUtil fileUtil = new FileUtil(this);
                fileUtil.writeToFile();
                break;
        }
    }

    /**
     * Function to start simulation process
     */
    private void startSimulation() {
        SimulationController simulationController = new SimulationController(this, mapboxMap);
        simulationController.initializeMissionPlan();
    }
}
