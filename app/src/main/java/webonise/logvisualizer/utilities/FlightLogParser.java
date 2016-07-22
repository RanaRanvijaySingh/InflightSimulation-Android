package webonise.logvisualizer.utilities;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import webonise.logvisualizer.model.FlightLogModel;

public class FlightLogParser {
    private static final String NEW_LINE = "\n";
    private List<LatLng> mTransectsList;

    public FlightLogModel getFlightLogModel(String fileContent) {
        try {
            mTransectsList = new ArrayList<>();
            String[] lines = fileContent.split(NEW_LINE);
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.contains(LogKeys.WAYPOINT)) {
                    addWayPoint(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to add new waypoint from the given string.
     * "07-19 04:32:45.407 PM I/MissionController: Waypoint 31: (18.510935585429227, 73.77736357978945) alt: 50.0"
     *
     * @param line String
     */
    private void addWayPoint(String line) {
        try {
            /**
             * Extract : (18.510935585429227, 73.77736357978945)
             * From : 07-19 04:32:45.407 PM I/MissionController: Waypoint 31: (18.510935585429227, 73.77736357978945) alt: 50.0
             */
            String waypoint = line.substring(line.indexOf(Strings.OPEN_BRACES),
                    line.indexOf(Strings.CLOSE_BRACES));
            /**
             * Extract:  18.510935585429227, 73.77736357978945
             * From :  (18.510935585429227, 73.77736357978945)
             */
            waypoint = waypoint.substring(1, waypoint.length() - 1);
            /**
             * Split 18.510935585429227, 73.77736357978945 by ','.
             */
            String points[] = waypoint.split(Strings.COMMA);
            double latitude = Double.parseDouble(points[0].trim());
            double longitude = Double.parseDouble(points[1].trim());
            LatLng latLngWaypoint = new LatLng(latitude, longitude);
            mTransectsList.add(latLngWaypoint);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class LogKeys {
        public static final String WAYPOINT = "Waypoint";
        public static final String BATTERY = "battery";
        public static final String LOCATION = "location";
    }

    public class Strings {
        public static final String OPEN_BRACES = "(";
        public static final String CLOSE_BRACES = ")";
        public static final String COMMA = ",";
    }
}
