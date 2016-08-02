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

    /**
     * GetLatLngPointFromLine function test ========================================
     */

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

    /**
     * GetAltitudeFromLine function test ========================================
     */

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
    public void testGetAltitudeFromLineForValidData2() throws Exception {
        String line = "07-19 04:33:05.052 PM I/MainActivity: location:(18.510895,73.7774) " +
                "alt:0.0 heading:0.0 satCount:10.0 velocity:(0.0,0.0,0.0)";
        double actualALtitude = flightLogParser.getAltitudeFromLine(line);
        Assert.assertEquals(0, actualALtitude, 0);
    }

    @Test
    public void testGetAltitudeFromLineForNullInput() throws Exception {
        String line = null;
        double actualALtitude = flightLogParser.getAltitudeFromLine(line);
        Assert.assertEquals(0, actualALtitude, 0);
    }

    /**
     * GetSatelliteCountFromLine function test ========================================
     */

    @Test
    public void testGetSatelliteCountFromLineForValidData() throws Exception {
        String line = "07-19 04:33:05.052 PM I/MainActivity: location:(18.510895,73.7774) " +
                "alt:0.0 heading:0.0 satCount:10.0 velocity:(0.0,0.0,0.0)";
        double actualSatCount = flightLogParser.getSatelliteCountFromLine(line);
        Assert.assertTrue(10 == actualSatCount);
    }

    @Test
    public void testGetSatelliteCountFromLineForValidData2() throws Exception {
        String line = "07-19 04:33:05.052 PM I/MainActivity: location:(18.510895,73.7774) " +
                "alt:0.0 heading:0.0 satCount:6.0 velocity:(0.0,0.0,0.0)";
        double actualSatCount = flightLogParser.getSatelliteCountFromLine(line);
        Assert.assertTrue(6 == actualSatCount);
    }

    @Test
    public void testGetSatelliteCountFromLineForInValidData() throws Exception {
        String line = "07-19 04:33:05.052 PM I/MainActivity: location:(18.510895,73.7774) " +
                "alt:0.0 heading:0.0 satCount:6.0 elocity:(0.0,0.0,0.0)";
        double actualSatCount = flightLogParser.getSatelliteCountFromLine(line);
        Assert.assertTrue(0 == actualSatCount);
    }

    @Test
    public void testGetSatelliteCountFromLineForNull() throws Exception {
        String line = "07-19 04:33:05.052 PM I/MainActivity: location:(18.510895,73.7774) " +
                "alt:0.0 heading:0.0 satount:6.0 velocity:(0.0,0.0,0.0)";
        double actualSatCount = flightLogParser.getSatelliteCountFromLine(line);
        Assert.assertTrue(0 == actualSatCount);
    }

    /**
     * getHeadingFromLine function test ========================================
     */

    @Test
    public void testGetHeadingFromLineForValidData() throws Exception {
        String line = "07-19 04:33:05.052 PM I/MainActivity: location:(18.510895,73.7774) " +
                "alt:0.0 heading:0.0 satCount:10.0 velocity:(0.0,0.0,0.0)";
        double actualSatCount = flightLogParser.getHeadingFromLine(line);
        Assert.assertEquals(0.0, actualSatCount, 0);
    }

    @Test
    public void testGetHeadingFromLineForValidData2() throws Exception {
        String line = "07-19 04:33:41.247 PM I/MainActivity: location:(18.51092022889394," +
                "73.77738328831583) alt:50.2 heading:-31.8 satCount:10.0 velocity:(1.9,-1.2,0.0)";
        double actualSatCount = flightLogParser.getHeadingFromLine(line);
        Assert.assertEquals(-31.8, actualSatCount, 0);
    }

    @Test
    public void testGetHeadingFromLineForInvalidData() throws Exception {
        String line = "07-19 04:33:41.247 PM I/MainActivity: location:(18.51092022889394," +
                "73.77738328831583) alt:50.2 :-31.8 satCount:10.0 velocity:(1.9,-1.2,0.0)";
        double actualSatCount = flightLogParser.getHeadingFromLine(line);
        Assert.assertEquals(0, actualSatCount, 0);
    }

    /**
     * getTimeFromLine function test ========================================
     */
    @Test
    public void testGetTimeFromLineForValidData() throws Exception {
        String line = "07-29 09:58:34.126 PM I/MainActivity: location:(18.510916725279202,73" +
                ".7773856089833) alt:50.2 heading:-31.8 satCount:10.0 velocity:(1.9,-1.2,0.0)";
        long actualTimeInMilliseconds = flightLogParser.getTimeFromLine(line);
        Assert.assertEquals(18116914126L, actualTimeInMilliseconds);
    }

    /**
     * getSpeedFromLine function test ========================================
     */
    @Test
    public void testGetSpeedFromLineForValidData() throws Exception {
        String line = "07-29 09:58:34.126 PM I/MainActivity: location:(18.510916725279202,73" +
                ".7773856089833) alt:50.2 heading:-31.8 satCount:10.0 velocity:(1.9,-1.2,0.0)";
        double actualTimeInMilliseconds = flightLogParser.getSpeedFromLine(line);
        Assert.assertEquals(2.247220505424423, actualTimeInMilliseconds,0);
    }
}
