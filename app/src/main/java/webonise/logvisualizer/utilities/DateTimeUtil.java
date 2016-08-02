package webonise.logvisualizer.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    private static final String FLIGHT_LOG_TIME_FORMAT = "MM-dd hh:mm:ss.SSS aaa";

    /**
     * Function to convert time like '07-19 04:34:06.452 PM' into milliseconds
     * @param time String
     * @return long
     */
    public static long getTimeInMillisecond(String time) {
        if (time == null || time.equals(Constants.StringValues.BLANK)) {
            return 0;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FLIGHT_LOG_TIME_FORMAT, Locale.ENGLISH);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
