package webonise.mapboxdemo.controller;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mapbox.mapboxsdk.annotations.Polygon;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.MainActivity;
import webonise.mapboxdemo.R;
import webonise.mapboxdemo.utilities.FileUtil;

public class PolygonController {
    public static final int TRANSECT_LINE_WIDTH = 2;
    private static final float POLYGON_FILL_ALPHA = 0.2f;
    private static final int POLYGON_LINE_WIDTH = 1;
    private static final String TAG = "PolygonController";
    private final MapboxMap mMapboxMap;
    private final MainActivity mActivity;
    private Polygon mPolygon;
    private Polyline mPolygonOutline;
    private PolygonBufferController mPolygonBufferController;
    private Polyline mPolylineBuffered;
    private List<LatLng> aoiBuffered = new ArrayList<LatLng>();
    private List<LatLng> transects;
    private Polyline mPolyline;

    public PolygonController(MainActivity activity, MapboxMap mapboxMap) {
        this.mActivity = activity;
        this.mMapboxMap = mapboxMap;
        mPolygonBufferController = new PolygonBufferController(activity);
    }

    /**
     * Function to draw polygon
     */
    public void drawPolygon() {
        FileUtil fileUtil = new FileUtil(mActivity);
        final List<LatLng> poly = fileUtil.getPolygonPoints();
        Log.i(TAG, "Polygon point obtained");
        if (poly != null) {
            drawPolygon(poly, 0);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    drawTransects(poly);
                }
            });
        }
    }

    /**
     * Function to draw
     * - polyline on the boundary of selected area
     * - polygon with faded white color covering boundary lines
     * - polyline on the boundary with buffered area.
     *
     * @param poly List<LatLng>
     */
    public void drawPolygon(List<LatLng> poly, double altitude) {
        clearPolygons();
        if (poly != null && poly.size() > 0) {
            //Create polygon with faded white color
            PolygonOptions opts = new PolygonOptions();
            opts.fillColor(Color.WHITE);
            opts.addAll(poly);
            opts.alpha(POLYGON_FILL_ALPHA);
            mPolygon = mMapboxMap.addPolygon(opts);
            //Create outer boundary
            PolylineOptions lOpts = new PolylineOptions();
            lOpts.color(Color.WHITE);
            lOpts.width(POLYGON_LINE_WIDTH);
            lOpts.addAll(poly);
            lOpts.add(poly.get(0));
            mPolygonOutline = mMapboxMap.addPolyline(lOpts);
            //Create buffered polygon using current polygon points
            drawBufferedPolygon(poly, altitude);
            Log.i(TAG, "Polygon, buffered polygon drawn Drawn");
        }
    }

    /**
     * Function to clear polygon every time a new one is drawn
     */
    private void clearPolygons() {
        if (mPolygon != null) {
            mPolygon.remove();
        }
        if (mPolygonOutline != null) {
            mPolygonOutline.remove();
        }
        if (mPolylineBuffered != null) {
            mPolylineBuffered.remove();
        }
    }

    /**
     * Function to create buffered polygon from given polygon points
     *
     * @param latLngList List<LatLng>
     */
    public Polyline drawBufferedPolygon(List<LatLng> latLngList, double altitude) {
        if (mMapboxMap != null && latLngList != null) {
            aoiBuffered = mPolygonBufferController.getBufferedPoints(latLngList, altitude);
            mPolylineBuffered = mMapboxMap.addPolyline(new PolylineOptions()
                    .add(getPoints(aoiBuffered))
                    .width(POLYGON_LINE_WIDTH)
                    .color(Color.CYAN));
            return mPolylineBuffered;
        }
        return null;
    }

    /**
     * Function to get points array from the list
     *
     * @param listBufferedPoints List<LatLng>
     * @return LatLng[]
     */
    @NonNull
    private LatLng[] getPoints(List<LatLng> listBufferedPoints) {
        if (listBufferedPoints != null) {
            return listBufferedPoints.toArray(new LatLng[listBufferedPoints.size()]);
        }
        return new LatLng[0];
    }

    /**
     * Function to draw transects
     *
     * @param latLngList List<LatLng>
     */
    private void drawTransects(List<LatLng> latLngList) {
        TransectsController transectsController = new TransectsController(mActivity);
        drawTransectsOnMap(transectsController.getTransectsPoints(latLngList));
    }

    /**
     * Function to draw transects using given lat lng list
     *
     * @param transects List<LatLng>
     */
    public void drawTransectsOnMap(List<LatLng> transects) {
        this.transects = transects;
        if (mPolyline != null) {
            mPolyline.remove();
        }
        PolylineOptions opts = new PolylineOptions();
        opts.color(mActivity.getResources().getColor(R.color.transect_stroke));
        opts.width(TRANSECT_LINE_WIDTH);
        for (int i = 0; i < transects.size(); i++) {
            opts.add(transects.get(i));
        }
        mPolyline = mMapboxMap.addPolyline(opts);
    }
}
