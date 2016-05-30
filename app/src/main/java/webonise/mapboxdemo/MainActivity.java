package webonise.mapboxdemo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.controller.SimulationController;
import webonise.mapboxdemo.utilities.PermissionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MapboxMap mapboxMap;
    private MapView mapview;
    private boolean hasInternetPermission;

    final List<LatLng> latLngPolygon = new ArrayList<>();

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
    }

    /**
     * Function to initialize components
     */
    private void initializeComponents() {
        Button bReplay = (Button) findViewById(R.id.bReplay);
        bReplay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bReplay:
                startSimulation();
                break;
        }
    }

    /**
     * Function to start simulation process
     */
    private void startSimulation() {
        SimulationController simulationController = new SimulationController(this, mapboxMap);
        simulationController.drawMissionPlan();
    }
}
