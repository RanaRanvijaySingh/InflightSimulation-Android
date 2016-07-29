package webonise.logvisualizer.utilities;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

import webonise.logvisualizer.model.DroneStatusModel;
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
                    LatLng waypoint = getLatLngPointFromLine(line);
                    if (waypoint != null) {
                        mTransectsList.add(waypoint);
                    }
                } else if (line.contains(LogKeys.LOCATION)) {
                    parseLocationLine(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to parse location line.
     * "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792,73.77712822837942)
     * alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)"
     *
     * @param line String
     */
    private DroneStatusModel parseLocationLine(String line) {
        DroneStatusModel droneStatusModel = new DroneStatusModel();
        LatLng location = getLatLngPointFromLine(line);
        if (location != null) {
            droneStatusModel.setLocation(location);
        }
        double altitude = getAltitudeFromLine(line);
        return null;
    }

    /**
     * Function to return LatLng object from the given string.
     * <p/>
     * 1> "07-19 04:32:45.407 PM I/MissionController: Waypoint 31: (18.510935585429227,
     * 73.77736357978945) alt: 50.0"
     * <p/>
     * 2> "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792,73.77712822837942)
     * alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)"
     *
     * @param line String
     */
    public LatLng getLatLngPointFromLine(String line) {
        if (line == null || line.equals(Constants.StringValues.BLANK)) {
            return null;
        }
        try {
            /**
             * Extract : (18.510935585429227, 73.77736357978945)
             * From :
             * 1> 07-19 04:32:45.407 PM I/MissionController: Waypoint 31: (18.510935585429227,
             * 73.77736357978945) alt: 50.0
             *
             * 2> "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792,73.77712822837942)
             * alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)"
             */
            String point = line.substring(line.indexOf(Strings.OPEN_BRACES),
                    line.indexOf(Strings.CLOSE_BRACES));
            /**
             * Extract:  18.510935585429227, 73.77736357978945
             * From :  (18.510935585429227, 73.77736357978945)
             */
            point = point.substring(1, point.length());
            /**
             * Split 18.510935585429227, 73.77736357978945 by ','.
             */
            String points[] = point.split(Strings.COMMA);
            double latitude = Double.parseDouble(points[0].trim());
            double longitude = Double.parseDouble(points[1].trim());
            return new LatLng(latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to get altitude from given line
     * Extract : 50.1
     * From :
     * "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792,73.77712822837942)
     * alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)"
     *
     * @param line String
     * @return double
     */
    public double getAltitudeFromLine(String line) {
        if (line == null || line.equals(Constants.StringValues.BLANK)) {
            return 0;
        }
        try {
            /**
             * Extract : alt:50.1 h
             * From : "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792,73.77712822837942)
             * alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)"
             */
            String altitude = line.substring(line.indexOf(LogKeys.ALT),
                    line.indexOf(LogKeys.HEADING));
            /**
             * Extract:  50.1
             * From :  alt:50.1 h
             */
            altitude = altitude.substring(LogKeys.ALT.length(), altitude.length() - 1);
            return Double.parseDouble(altitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private class LogKeys {
        public static final String WAYPOINT = "Waypoint";
        public static final String BATTERY = "battery";
        public static final String LOCATION = "location";
        public static final String LOCATION_START = "location:(";
        public static final String ALT = "alt:";
        public static final String HEADING = "heading:";
    }

    public class Strings {
        public static final String OPEN_BRACES = "(";
        public static final String CLOSE_BRACES = ")";
        public static final String COMMA = ",";
    }
}
