package webonise.mapboxdemo.controller;

import android.support.annotation.NonNull;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.MainActivity;
import webonise.mapboxdemo.utilities.Constants;

public class PolygonBufferController {
    private final MainActivity mActivity;
    private final GeometryFactory mGeometryFactory;

    public PolygonBufferController(MainActivity mainActivity) {
        this.mActivity = mainActivity;
        //Initialize geometry factory object to get Geometry object.
        mGeometryFactory = new GeometryFactory();
    }

    /**
     * Funtion to get buffered point from the given list of lat lng
     *
     * @param latLngList List<LatLng>
     * @return List<LatLng>
     */
    public List<LatLng> getBufferedPoints(List<LatLng> latLngList, double altitude) {
        //Need to add then first lat lng in the end so as to create a closed polygon
        latLngList.add(latLngList.get(0));
        //Create geometry object using your own lat lang points
        Geometry geometryOriginal = getGeometryForPolygon(latLngList);
        //Create geometry for buffered polygon
        Geometry geometryBuffered = geometryOriginal.buffer(getBufferingDistance(altitude,
                latLngList.get(0).getLatitude()));
        //return list of lat lng from after converting coordinates array to list lat lng
        return getPoints(geometryBuffered.getCoordinates());
    }

    /**
     * Function to get Geometry object (Class from vividsolutions)
     * from given list of latlng
     *
     * @param bounds List
     * @return Geometry (Class from vividsolutions)
     */
    public Geometry getGeometryForPolygon(List<LatLng> bounds) {
        List<Coordinate> coordinates = getCoordinatesList(bounds);
        if (!coordinates.isEmpty()) {
            return mGeometryFactory.createPolygon(getLinearRing(coordinates), null);
        }
        return null;
    }

    /**
     * Function to create a list of coordinates from a list of lat lng
     *
     * @param listLatLng list<LatLng>
     * @return List<Coordinate> (Class from vividsolutions)
     */
    private List<Coordinate> getCoordinatesList(List<LatLng> listLatLng) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < listLatLng.size(); i++) {
            coordinates.add(new Coordinate(
                    listLatLng.get(i).getLatitude(), listLatLng.get(i).getLongitude()));
        }
        return coordinates;
    }

    /**
     * Function to convert array of Coordinates (Class from vividsolutions)
     * to Android LatLng array
     *
     * @param coordinates Coordinates (Class from vividsolutions)
     * @return LatLng[]
     */
    @NonNull
    private List<LatLng> getPoints(Coordinate[] coordinates) {
        List<LatLng> listPoints = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            listPoints.add(new LatLng(coordinate.x, coordinate.y));
        }
        return listPoints;
    }

    /**
     * Function to create LinearRing (Class from vividsolutions) from a list of
     * Coordinate (Class from vividsolutions)
     *
     * @param coordinates List
     * @return LinearRing
     */
    @NonNull
    private LinearRing getLinearRing(List<Coordinate> coordinates) {
        return new LinearRing(getPoints(coordinates), mGeometryFactory);
    }

    /**
     * Function to get points of CoordinateArraySequence (Class from vividsolutions)
     *
     * @param coordinates List (Class from vividsolutions)
     * @return CoordinateArraySequence (Class from vividsolutions)
     */
    @NonNull
    private CoordinateArraySequence getPoints(List<Coordinate> coordinates) {
        return new CoordinateArraySequence(getCoordinates(coordinates));
    }

    /**
     * Function to get coordinates array from a list of coordinates
     *
     * @param coordinates List<Coordinate> (Class from vividsolutions)
     * @return Coordinate [] (Class from vividsolutions)
     */
    @NonNull
    private Coordinate[] getCoordinates(List<Coordinate> coordinates) {
        return coordinates.toArray(new Coordinate[coordinates.size()]);
    }

    /**
     * Function to get buffering distance based on given altitude
     *
     * @param altitude double
     * @param latitude
     * @return double
     */
    public double getBufferingDistance(double altitude, double latitude) {
        return Constants.DEFAULT_BUFFER_VALUE;
    }
}
