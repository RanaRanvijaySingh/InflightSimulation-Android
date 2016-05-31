package webonise.mapboxdemo.utilities;

import android.app.Activity;


public class SharedPreferencesUtil {
    private final Activity mActivity;

    public SharedPreferencesUtil(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Function to check is measurement unit selected is meter
     *
     * @return boolean
     */
    public boolean isMeasurementUnitMeter() {
        /*if (mActivity != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mActivity);
            String unit = sp.getString(mActivity.getString(R.string.pref_measurement_units),
                    Constants.MEASUREMENT_UNIT_METERS);
            if (unit.equals(Constants.MEASUREMENT_UNIT_METERS)) {
                return true;
            }
        }*/
        return true;
    }

    public String getMapStyleURL() {
        /*if (mActivity != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mActivity);
            String mapLayers = sp.getString(mActivity.getResources().getString(R.string.pref_key_map_layers),
                    mActivity.getResources().getString(R.string.pref_map_layers_value_default));
            String styleUrl = mActivity.getString(mapLayers.equals(mActivity.getResources().getString(R.string.pref_map_layers_value_satellite))
                    ? R.string.map_style_satellite
                    : R.string.map_style_street);
            return styleUrl;
        } else {
            return null;
        }*/
        return "";
    }

    /**
     * Function to check if the mission buffering is enabled for flight plan or not.
     *
     * @return boolean
     */
    public boolean isMissionBufferingEnabled() {
        /*if (mActivity != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mActivity);
            return sp.getBoolean(mActivity.getResources()
                    .getString(R.string.pref_key_mission_buffering), true);
        }*/
        return true;
    }

    public boolean isCameraAutoDetected() {
        /*if (mActivity != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mActivity);
            String cameraPref = sp.getString(mActivity.getResources().getString(R.string.pref_key_camera),
                    mActivity.getResources().getString(R.string.pref_camera_value_default));
            return cameraPref.equals(mActivity.getResources().getString(R.string.pref_camera_value_auto));
        } else {
            return false;
        }*/
        return true;
    }
}
