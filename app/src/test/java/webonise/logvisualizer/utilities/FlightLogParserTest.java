package webonise.logvisualizer.utilities;

import com.mapbox.mapboxsdk.geometry.LatLng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FlightLogParserTest {

    private FlightLogParser flightLogParser;

    @Before
    public void setUp() {
        flightLogParser = new FlightLogParser();
    }

    @Test
    public void testGetLatLngPointFromLineForValidWaypointInput() throws Exception {
        LatLng expectedPoint = new LatLng(18.510935585429227, 73.77736357978945);
        String line = "07-19 04:32:45.407 PM I/MissionController: Waypoint 31: (18" +
                ".510935585429227, 73.77736357978945) alt: 50.0";
        LatLng actualPoint = flightLogParser.getLatLngPointFromLine(line);
        Assert.assertEquals(expectedPoint, actualPoint);
    }

    @Test
    public void testGetLatLngPointFromLineForInValidLocationInput() throws Exception {
        LatLng expectedPoint = new LatLng(18.510935585429227, 73.77736357978945);
        String line = "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792," +
                "73.77712822837942) alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)";
        LatLng actualPoint = flightLogParser.getLatLngPointFromLine(line);
        Assert.assertNotEquals(expectedPoint, actualPoint);
    }

    @Test
    public void testGetLatLngPointFromLineForValidLocationInput() throws Exception {
        LatLng expectedPoint = new LatLng(18.511305300975792, 73.77712822837942);
        String line = "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792," +
                "73.77712822837942) alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)";
        LatLng actualPoint = flightLogParser.getLatLngPointFromLine(line);
        Assert.assertEquals(expectedPoint, actualPoint);
    }

    @Test
    public void testGetLatLngPointFromLineForInValidInput() throws Exception {
        String line = "07-19 04:32:45.407 PM I/MissionController: Waypoint 31: 18" +
                ".510935585429227, 73.77736357978945) alt: 50.0";
        LatLng actualPoint = flightLogParser.getLatLngPointFromLine(line);
        Assert.assertEquals(null, actualPoint);
    }

    @Test
    public void testGetLatLngPointFromLineForNull() throws Exception {
        String line = null;
        LatLng actualWaypoint = flightLogParser.getLatLngPointFromLine(line);
        Assert.assertEquals(null, actualWaypoint);
    }

    @Test
    public void testGetAltitudeFromLineForValidData() throws Exception {
        String line = "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792," +
                "73.77712822837942) alt:50.1 heading:-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)";
        double expectedAltitude = 50.1;
        double actualALtitude = flightLogParser.getAltitudeFromLine(line);
        Assert.assertEquals(expectedAltitude, actualALtitude, 0);
    }

    @Test
    public void testGetAltitudeFromLineForInValidData() throws Exception {
        String line = "07-19 04:34:06.452 PM I/MainActivity: location:(18.511305300975792," +
                "73.77712822837942) alt:50.1 :-31.8 satCount:10.0 velocity:(1.6,-1.0,0.0)";
        double actualALtitude = flightLogParser.getAltitudeFromLine(line);
        Assert.assertEquals(0, actualALtitude, 0);
    }

    @Test
    public void testGetAltitudeFromLineForNullInput() throws Exception {
        String line = null;
        double actualALtitude = flightLogParser.getAltitudeFromLine(line);
        Assert.assertEquals(0, actualALtitude, 0);
    }
}
