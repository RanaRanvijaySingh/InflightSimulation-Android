package webonise.logvisualizer.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import webonise.logvisualizer.R;
import webonise.logvisualizer.HomeActivity;

/**
 * Adapted from StackOverflow view example.
 */

public class DroneMarkerView extends View {

    public static String TAG = "DroneMarkerView";

    private LatLng droneLoc = null;
    private PointF screenPos = null;
    private int heading = 0;
    private Bitmap srcBmp;
    private Bitmap bmp = null;

    Paint paint;
    Canvas canvas;
    MapboxMap map;

    public DroneMarkerView(Context context) {
        super(context);
        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.quad_marker);
    }

    public DroneMarkerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.quad_marker);
    }

    public DroneMarkerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        setFocusable(true); // necessary for getting the touch events
        canvas = new Canvas();
        srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.quad_marker);
    }

    // the method that draws the balls
    @Override
    protected void onDraw(Canvas canvas) {
        if (screenPos != null) {
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStrokeJoin(Paint.Join.ROUND);

            // draw the balls on the canvas
            paint.setColor(Color.BLUE);
            paint.setTextSize(18);
            paint.setStrokeWidth(0);
            if (bmp != null){
                canvas.drawBitmap(bmp, screenPos.x - (bmp.getWidth() / 2), screenPos.y - (bmp.getHeight() / 2), paint);
            }
        }
    }

    public void moveBall(LatLng loc, int head) {
        if (map != null) {
            try {
                droneLoc = loc;
                screenPos = map.getProjection().toScreenLocation(loc);
                heading = head;

                bmp = rotateBitmap(srcBmp, (float) head);
            } catch (Exception e) {
                // due to application lifecycle, native MapView may not be available
                e.printStackTrace();
            }
        }

        invalidate();
    }

    public void initMarker(LatLng loc, int head) {
        droneLoc = loc;
        bmp = rotateBitmap(srcBmp, (float) head);

        moveBall(loc, head);

        // trigger redraw
        invalidate();
    }

    public void removeMarker() {
        if (droneLoc != null) {
            droneLoc = null;
        }
        if (screenPos != null) {
            screenPos = null;
        }

        invalidate();
    }

    private Bitmap rotateBitmap(Bitmap source, float angle)
    {
        if (source != null) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }
        return null;
    }

    // call when map has been moved; pass in new visible region and move box points accordingly.
    public void onMapMoved(MapboxMap proj) {
        // set map region
        map = proj;

        screenPos = map.getProjection().toScreenLocation(droneLoc);

        // trigger redraw
        invalidate();
    }

    private HomeActivity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof HomeActivity) {
                return (HomeActivity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public boolean isMarkerInitialized() {
        return (droneLoc != null);
    }

    public LatLng getDroneLocation() {
        return droneLoc;
    }

    public void setMap(MapboxMap mapboxMap) {
        this.map = mapboxMap;
    }

}
