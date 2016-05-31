package webonise.mapboxdemo.strategy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.models.CameraProperties;
import webonise.mapboxdemo.models.FlightPlanParams;
import webonise.mapboxdemo.utilities.GeoUtils;

/*
    Band Pass Transect Generation Strategy

    Built for flight only, camera will operate independently.
    Create east-west "bands" spaced for horizontal overlap, and
    calculate intersection with edges of polygon at either
    extreme to cover entire area.
 */
public class BandPassTransectGenerationStrategy extends TransectGenerationStrategy {

    public static String TAG = "BandPassTransectGenerationStrategy";

    private static double MIN_LAT_INIT = 90.0; // initialize min lat at max value
    private static double MAX_LAT_INIT = -90.0; // initialize max lat at min value
    private static double MIN_LON_INIT = 180.0; // initialize min lon at max value
    private static double MAX_LON_INIT = -180.0; // initialize max lon at min value

    public BandPassTransectGenerationStrategy() {
    }

    @Override
    public List<LatLng> generateTransects(List<LatLng> flightArea, FlightPlanParams params,
                                          CameraProperties cameraProps, Context c) {
        // 0: Define the waypoint list
        List<LatLng> result = new ArrayList<LatLng>();
        boolean transectSwitch = true; // determines order to add waypoints (back and forth)

        // 0b: validation
        if (flightArea == null) {
            return result;
        }

        // 1: calculate margins for images (basically, distance separating images)
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        int hOverlap = Integer.valueOf(sp.getString("h_overlap", Integer.toString(FlightPlanParams.DEFAULT_H_OVERLAP)));
        double marginAcross = (1 - (((double) hOverlap) / 100)) * cameraProps.getFieldOfView(params.getAlt())[CameraProperties.INDEX_FOV_HORIZONTAL];
        int vOverlap = Integer.valueOf(sp.getString("v_overlap", Integer.toString(FlightPlanParams.DEFAULT_V_OVERLAP)));
        double marginAlong = (1 - (((double) vOverlap) / 100)) * cameraProps.getFieldOfView(params.getAlt())[CameraProperties.INDEX_FOV_VERTICAL];

        // 1b: buffer polygon to get area outside
        //flightArea = bufferPolygon(flightArea, marginAlong);

        // 2: find min/max values
        double minLat = MIN_LAT_INIT;
        double maxLat = MAX_LAT_INIT;
        double minLon = MIN_LON_INIT;
        double maxLon = MAX_LON_INIT;
        for (LatLng ll : flightArea) {
            if (ll.getLatitude() < minLat) {
                minLat = ll.getLatitude();
            }
            if (ll.getLatitude() > maxLat) {
                maxLat = ll.getLatitude();
            }
            if (ll.getLongitude() < minLon) {
                minLon = ll.getLongitude();
            }
            if (ll.getLongitude() > maxLon) {
                maxLon = ll.getLongitude();
            }
        }

        // 3: set up LatLng point to track working latitude, and edges for algo
        LatLng trackPt = new LatLng(maxLat, maxLon);
        List<Edge> edgeList = new ArrayList<Edge>();
        for (int i = 0; i < flightArea.size(); i++) {
            Edge e = new Edge(flightArea.get(i), flightArea.get((i + 1) % flightArea.size()));
            edgeList.add(e);
        }

        // 4: figure out if we're going lengthwise or widthwise
        if (GeoUtils.distanceLatLng(new LatLng(minLat, minLon), new LatLng(minLat, maxLon)) >
                GeoUtils.distanceLatLng(new LatLng(minLat, minLon), new LatLng(maxLat, minLon))) {
            // polygon is wide, use lat bands
            // step trackPt down so it isn't directly on edge - move by 1/2 the overlap
            trackPt = GeoUtils.destPoint(trackPt, GeoUtils.BEARING_SOUTH, marginAcross / 2);

            // start doing bandpasses
            while (trackPt.getLatitude() > minLat) {
                // find waypoints
                BandData bandWpts = findBandDataAtLatitude(edgeList, trackPt.getLatitude());

                // add waypoints in proper order
                if (transectSwitch) {
                    result.add(new LatLng(trackPt.getLatitude(), bandWpts.getMin()));
                    result.add(new LatLng(trackPt.getLatitude(), bandWpts.getMax()));
                } else {
                    result.add(new LatLng(trackPt.getLatitude(), bandWpts.getMax()));
                    result.add(new LatLng(trackPt.getLatitude(), bandWpts.getMin()));
                }
                transectSwitch = !transectSwitch;

                // move trackPt down
                trackPt = GeoUtils.destPoint(trackPt, GeoUtils.BEARING_SOUTH, marginAcross);
            }
        } else {
            // polygon is long, use lon bands
            // step trackPt down so it isn't directly on edge - move by 1/2 the overlap
            trackPt = GeoUtils.destPoint(trackPt, GeoUtils.BEARING_WEST, marginAcross / 2);

            // start doing bandpasses
            while (trackPt.getLongitude() > minLon) {
                // find waypoints
                BandData bandWpts = findBandDataAtLongitude(edgeList, trackPt.getLongitude());

                // add waypoints in proper order
                if (transectSwitch) {
                    result.add(new LatLng(bandWpts.getMin(), trackPt.getLongitude()));
                    result.add(new LatLng(bandWpts.getMax(), trackPt.getLongitude()));
                } else {
                    result.add(new LatLng(bandWpts.getMax(), trackPt.getLongitude()));
                    result.add(new LatLng(bandWpts.getMin(), trackPt.getLongitude()));
                }
                transectSwitch = !transectSwitch;

                // move trackPt down
                trackPt = GeoUtils.destPoint(trackPt, GeoUtils.BEARING_WEST, marginAcross);
            }
        }


        result = validateTransects(result);

        return result;
    }

    public BandData findBandDataAtLatitude(List<Edge> flightEdges, double bandLat) {
        BandData data = new BandData(MIN_LON_INIT, MAX_LON_INIT, BandType.LON);

        List<Edge> workingEdges = new ArrayList<Edge>(); // edges we are comparing against

        for (Edge e : flightEdges) {
            if ((e.wpt1.getLatitude() >= bandLat && e.wpt2.getLatitude() <= bandLat) ||
                    (e.wpt1.getLatitude() <= bandLat && e.wpt2.getLatitude() >= bandLat)) {
                workingEdges.add(e);
            }
        }

        // now, get intersections with each edge and compare against working min/max
        for (Edge e : workingEdges) {
            // area is small enough to use raw numbers for calculations
            double latRange = e.wpt1.getLatitude() - e.wpt2.getLatitude();
            double latDiff = e.wpt1.getLatitude() - bandLat;
            double lonRange = e.wpt1.getLongitude() - e.wpt2.getLongitude();
            double lonDiff = (latDiff / latRange) * lonRange;
            double resultLon = e.wpt1.getLongitude() - lonDiff;
            if (resultLon < data.getMin()) {
                data.setMin(resultLon);
            }
            if (resultLon > data.getMax()) {
                data.setMax(resultLon);
            }
        }

        // return result
        return data;
    }

    public BandData findBandDataAtLongitude(List<Edge> flightEdges, double bandLon) {
        BandData data = new BandData(MIN_LAT_INIT, MAX_LAT_INIT, BandType.LAT);

        List<Edge> workingEdges = new ArrayList<Edge>(); // edges we are comparing against

        for (Edge e : flightEdges) {
            if ((e.wpt1.getLongitude() >= bandLon && e.wpt2.getLongitude() <= bandLon) ||
                    (e.wpt1.getLongitude() <= bandLon && e.wpt2.getLongitude() >= bandLon)) {
                workingEdges.add(e);
            }
        }

        // now, get intersections with each edge and compare against working min/max
        for (Edge e : workingEdges) {
            // area is small enough to use raw numbers for calculations
            double lonRange = e.wpt1.getLongitude() - e.wpt2.getLongitude();
            double lonDiff = e.wpt1.getLongitude() - bandLon;
            double latRange = e.wpt1.getLatitude() - e.wpt2.getLatitude();
            double latDiff = (lonDiff / lonRange) * latRange;
            double resultLat = e.wpt1.getLatitude() - latDiff;
            if (resultLat < data.getMin()) {
                data.setMin(resultLat);
            }
            if (resultLat > data.getMax()) {
                data.setMax(resultLat);
            }
        }

        // return result
        return data;
    }

    private List<LatLng> validateTransects(List<LatLng> transects) {
        List<LatLng> newList = new ArrayList<LatLng>();

        for (LatLng ll : transects) {
            if (newList.size() > 0) {
                if (GeoUtils.distanceLatLng(newList.get(newList.size() - 1), ll) > 5.0) {
                    newList.add(ll);
                }
            } else {
                newList.add(ll);
            }
        }

        return newList;
    }

    private enum BandType {
        LAT,
        LON
    }

    private class BandData {
        private double min;
        private double max;
        private BandType bandType;

        private BandData() {
        }

        private BandData(double min, double max, BandType bt) {
            this.min = min;
            this.max = max;
            this.bandType = bt;
        }

        private void setMin(double m) {
            min = m;
        }

        private double getMin() {
            return min;
        }

        private void setMax(double m) {
            max = m;
        }

        private double getMax() {
            return max;
        }

        private BandType getBandType() {
            return bandType;
        }
    }

    private class Edge {
        private LatLng wpt1;
        private LatLng wpt2;

        private Edge(LatLng w1, LatLng w2) {
            wpt1 = w1;
            wpt2 = w2;
        }
    }

}
