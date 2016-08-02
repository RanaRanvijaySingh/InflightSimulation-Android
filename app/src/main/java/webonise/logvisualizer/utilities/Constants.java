package webonise.logvisualizer.utilities;

public class Constants {
    //TODO : Changes to be made before deployment
    public static final boolean IS_DEVELOPER_MODE_ENABLED = true;

    //TODO : Confirm the low signal value and change
    public static final int QUAD_SAT_COUNT_THRESHOLD_LOW = 6;
    public static final double QUAD_SIGNAL_THRESHOLD_LOW_SIGNAL = 30;
    public static final int QUAD_BATTERY_THRESHOLD_SHOW_ALERT = 30;
    public static final int QUAD_BATTERY_THRESHOLD_GO_HOME = 20;
    public static final int QUAD_BATTERY_THRESHOLD_LAND_NOW = 15;
    public static final int DRONE_TRAIL_WIDTH = 2;

    // for drawer toggle
    public static final float TOOLBOX_DRAWER_CLOSE_THRESHOLD = 0.99f;

    // for map search autocomplete
    public static final int MAP_SEARCH_CHARACTER_THRESHOLD = 3;
    public static final int MAP_SEARCH_RESULTS = 5;

    public static final String FILE_EXTENSION_TYPE_TXT = ".txt";
    public static final String FILE_LOG = "log-";
    public static final String BUNDLE_CONNECTION_PARCELABLE_OBJECT = "connection_object";
    public static final String MEASUREMENT_UNIT_METERS = "1";
    public static final String FILE_CONTENT_VALIDATION_CHECK_STRING = "I/MainActivity:";

    public class StringValues {
        public static final String SPACE = " ";
        public static final String BLANK = "";
        public static final String DOT = ".";
        public static final String FORWARD_SLASH = "/";
        public static final String BACKWARD_SLASH_DOUBLE = "\\";
        public static final String COLON = ":";
        public static final String DASH = "-";
        public static final String NEW_LINE = "\n";
    }
    public static final double DEFAULT_BUFFER_VALUE = 0.000090;

    public class ClassType {
        public static final int FLIGHT_PLAN = 0;
    }

    public class JsonKeys {
        public static final String CLASS_TYPE = "type";
    }

    public class FileConstants {
        public static final String IMPORT_FILE_EXTENSION = "*/*";
    }

    public class SupportedFiles {
        public static final String TXT = ".txt";
    }

    public class IntentKeys {
        public static final String FLIGHT_PLAN_MODEL = "flight_plan_model";
    }
}
