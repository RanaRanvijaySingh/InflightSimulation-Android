package webonise.mapboxdemo.strategy;

import android.content.Context;
import android.location.Location;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.precisionhawk.inflightmobile.model.CameraProperties;
import com.precisionhawk.inflightmobile.model.FlightPlanParams;

import java.util.List;

public abstract class TransectGenerationStrategy {

    // method to return transects as in-order list of LatLng points
    public abstract List<LatLng> generateTransects(List<LatLng> flightArea, FlightPlanParams params, CameraProperties cameraProps, Context c);

    // concrete method to make a location from a LatLng
    public Location makeLocation(LatLng ll) {
        Location loc = new Location("generated"); // name doesn't matter for this
        loc.setLatitude(ll.getLatitude());
        loc.setLongitude(ll.getLongitude());
        return loc;
    }

}
