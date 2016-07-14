package webonise.logvisualizer.model;

import android.content.Context;
import android.util.Log;

public class CameraProperties {

    private static final String TAG = "CameraProperties";

    public static CameraProperties makeCameraPropsFromUserSettings(Context c, String name, String lensName) {
        // actually makes camera properties based on user setting

        return makeCameraPropsFromDisplayName(name, lensName);

    }

    public static CameraProperties makeCameraPropsFromDisplayName(String camName, String lensName) {
        if (camName == null) {
            camName = "";
        }

        CameraProperties props = null;

        switch (camName) {

            default:
                // default case: assume X3 for generic params
                props = new CameraProperties(
                        3.6, // true focal length, not equivalent
                        4000,
                        3000,
                        6.17,
                        4.55);
                break;
        }

        return props;
    }

    private static CameraProperties getPropsForX5(String lensName) {
        int number = 0;
        if (lensName != null) {
            lensName = lensName.toLowerCase();
            int mmIndex = lensName.indexOf("mm");
            int scalar = 1;
            // pull number out of string that comes right before "mm"
            if (mmIndex > 1) {
                while (Character.isDigit(lensName.charAt(mmIndex - 1))) {
                    number = number + (scalar * Integer.parseInt(Character.toString(lensName.charAt(mmIndex - 1))));
                    scalar = scalar * 10;
                    mmIndex--;
                }
            }
        } else {
        }
        if (number == 0) {
            number = 15; // default case
        }
        double focalLength = (double) number;
        Log.i(TAG, "camerafocallength " + focalLength);
        if (focalLength > 0.0) {
            return new CameraProperties(
                    focalLength,
                    4608,
                    3456,
                    17.3,
                    13.0);
        } else {
            // return default values
            return new CameraProperties(
                    15.0,
                    4608,
                    3456,
                    17.3,
                    13.0);
        }
    }

    // indices to reference elements of fieldOfView array
    public static int INDEX_FOV_HORIZONTAL = 0;
    public static int INDEX_FOV_VERTICAL = 1;

    private double pixelSize; // in micrometers
    private double focalLength; // in mm
    private double resolution; // in cm/pixel
    private int width; // image width, in pixels
    private int height; // image height, in pixels
    private double sensorWidth; // sensor width, in mm
    private double sensorHeight; // sensor height, in mm
    private boolean hasSensorDimensions = false;

    public CameraProperties(double pixelSize, double focalLength, double resolution, int width, int height) {
        this.pixelSize = pixelSize;
        this.focalLength = focalLength;
        this.resolution = resolution;
        this.width = width;
        this.height = height;
    }

    public CameraProperties(double focalLength, int width, int height, double sensorWidth, double sensorHeight) {
        this.focalLength = focalLength;
        this.sensorWidth = sensorWidth;
        this.sensorHeight = sensorHeight;
        this.width = width;
        this.height = height;
        hasSensorDimensions = true;
    }

    // calculated using trig functions
    public double[] getFieldOfView(double altitude) {
        double fov[] = new double[2];
        double horizontalValue;
        double verticalValue;
        if (hasSensorDimensions) {
            horizontalValue = (sensorWidth * altitude);
            verticalValue = (sensorHeight * altitude);
        } else {
            horizontalValue = (width * pixelSize * altitude * (1e-3));
            verticalValue = (height * pixelSize * altitude * (1e-3));
        }
        fov[0] = horizontalValue / focalLength; //Horizontal field of view
        fov[1] = verticalValue / focalLength; //Vertical field of view
        return fov;
    }

    public double getPixelSize() {
        return pixelSize;
    }

    public void setPixelSize(double pixelSize) {
        this.pixelSize = pixelSize;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        try {
            CameraProperties other = (CameraProperties) o;

            // test equality by field values
            return (this.width == other.getWidth()
                    && this.height == other.getHeight()
                    && this.resolution == other.getResolution()
                    && this.focalLength == other.getFocalLength()
                    && this.pixelSize == other.getPixelSize());
        } catch (ClassCastException e) {
            return false;
        }
    }
}
