package webonise.mapboxdemo.utilities;

import android.app.Activity;

import webonise.mapboxdemo.R;


public class ConversionUtils {

    private static final double FEET_TO_METER = 0.3048;
    private static final double ACRES_TO_HECTARE = 0.404686;
    private static double METERS_TO_FEET = 3.281;
    private static double MPS_TO_MPH = 2.2369;
    private static double SQUARE_METERS_TO_ACRES = 0.000247105;

    /**
     * Function to get distance in string
     *
     * @param strId    string resource id
     * @param distance int its always in meters, coming directly from drone location
     * @param activity  Context
     * @return String
     */
    public static String getDistanceString(int strId, int distance, Activity activity) {
        if (new SharedPreferencesUtil(activity).isMeasurementUnitMeter()) {
            // meters string
            return String.format(activity.getResources().getString(strId), distance,
                    activity.getResources().getString(R.string.unit_meters));
        } else {
            int feet = ((int) (distance * METERS_TO_FEET));
            return String.format(activity.getResources().getString(strId), feet,
                    activity.getResources().getString(R.string.unit_feet));
        }
    }

    /**
     * Function to get distance in string
     *
     * @param strId    string resource id
     * @param distance double its always in meters, coming directly from drone location
     * @param activity  Context
     * @return String
     */
    public static String getDistanceString(int strId, double distance, Activity activity) {
        if (new SharedPreferencesUtil(activity).isMeasurementUnitMeter()) {
            // meters string
            return String.format(activity.getResources().getString(strId), distance,
                    activity.getResources().getString(R.string.unit_meters));
        } else {
            double feet = distance * METERS_TO_FEET;
            return String.format(activity.getResources().getString(strId), feet,
                    activity.getResources().getString(R.string.unit_feet));
        }
    }

    /**
     * Function to get quad speed
     * @param strId int
     * @param speed double
     * @param activity Context
     * @return string
     */
    public static String getSpeedString(int strId, double speed, Activity activity) {
        if (new SharedPreferencesUtil(activity).isMeasurementUnitMeter()) {
            // meters per second string
            return String.format(activity.getResources().getString(strId), speed,
                    activity.getResources().getString(R.string.unit_meters_per_second));
        } else {
            double mph = speed * MPS_TO_MPH;
            return String.format(activity.getResources().getString(strId), mph,
                    activity.getResources().getString(R.string.unit_miles_per_hour));
        }
    }

    public static double squareMetersToAcres(double sm) {
        return sm * SQUARE_METERS_TO_ACRES;
    }

    /**
     * Function to get flight altitude in respective measurement unit
     *
     * @param activity       Context
     * @param inputAltitude String
     * @return int
     */
    public static int getFlightAltitude(Activity activity, String inputAltitude) {
        try {
            //If measurement unit is in metric system
            if (new SharedPreferencesUtil(activity).isMeasurementUnitMeter()) {
                //TRUE - return as it it
                return Integer.parseInt(inputAltitude);
            } else {
                //FALSE - Convert feet into meters and then return
                return (int) (Integer.parseInt(inputAltitude) * FEET_TO_METER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Function to convert acres to heactares
     * @param acres double
     * @return double
     */
    public static double convertToHectares(double acres) {
        return ACRES_TO_HECTARE * acres;
    }

    /**
     * Function to convert given altitude in feet
     * @param alt int
     * @return int
     */
    public static int convertToFeet(int alt) {
        return (int) (METERS_TO_FEET * alt);
    }
}
