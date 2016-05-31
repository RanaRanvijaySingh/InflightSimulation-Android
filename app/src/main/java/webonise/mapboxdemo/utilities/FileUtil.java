package webonise.mapboxdemo.utilities;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import webonise.mapboxdemo.MainActivity;
import webonise.mapboxdemo.models.FlightPlanModel;

public class FileUtil {

    private static final String DIRECTORY_NAME = "/inflightSimulator/";
    private static final String TAG = "FileUtil";
    private static final String OPEN_ARRAY_BRACES = "[";
    private static final char COMMA = ',';
    private static final String CLOSE_ARRAY_BRACES = "]";
    private final MainActivity mActivity;
    private String fileName = "simulatorTest.txt";

    public FileUtil(MainActivity activity) {
        this.mActivity = activity;
    }

    /**
     * Function to write content to a file
     */
    public void writeToFile() {
        if (isExternalStorageAvailable()) {
            File file = getFile();
            Toast.makeText(mActivity, file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            String message = getFileContent();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();
                Toast.makeText(mActivity, "Message saved", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(mActivity, "External storage is not available",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileContent() {
        return "{\"type\":0,\"homeLocation\":{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7819},\"isBufferEnabled\":true,\"pointList\":[{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7819},{\"altitude\":0.0,\"latitude\":18.5176,\"longitude\":73.7819},{\"altitude\":0.0,\"latitude\":18.5176,\"longitude\":73.7829},{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7829},{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7819}],\"transectsList\":[{\"altitude\":0.0,\"latitude\":18.515363689673244,\"longitude\":73.7834543143793},{\"altitude\":0.0,\"latitude\":18.517956957888817,\"longitude\":73.7834543143793},{\"altitude\":0.0,\"latitude\":18.518088098273576,\"longitude\":73.78325947148679},{\"altitude\":0.0,\"latitude\":18.51524957785906,\"longitude\":73.78325947148679},{\"altitude\":0.0,\"latitude\":18.51523282201783,\"longitude\":73.78306462859427},{\"altitude\":0.0,\"latitude\":18.51810149061056,\"longitude\":73.78306462859427},{\"altitude\":0.0,\"latitude\":18.51810021642835,\"longitude\":73.78286978570176},{\"altitude\":0.0,\"latitude\":18.515223033531615,\"longitude\":73.78286978570176},{\"altitude\":0.0,\"latitude\":18.5152132450454,\"longitude\":73.78267494280925},{\"altitude\":0.0,\"latitude\":18.518098942246144,\"longitude\":73.78267494280925},{\"altitude\":0.0,\"latitude\":18.518097668063934,\"longitude\":73.78248009991674},{\"altitude\":0.0,\"latitude\":18.515203456559185,\"longitude\":73.78248009991674},{\"altitude\":0.0,\"latitude\":18.51519366807297,\"longitude\":73.78228525702423},{\"altitude\":0.0,\"latitude\":18.518096393881727,\"longitude\":73.78228525702423},{\"altitude\":0.0,\"latitude\":18.518095119699517,\"longitude\":73.78209041413172},{\"altitude\":0.0,\"latitude\":18.515183879586758,\"longitude\":73.78209041413172},{\"altitude\":0.0,\"latitude\":18.515174091100544,\"longitude\":73.7818955712392},{\"altitude\":0.0,\"latitude\":18.51809384551731,\"longitude\":73.7818955712392},{\"altitude\":0.0,\"latitude\":18.51805534363307,\"longitude\":73.78170072834669},{\"altitude\":0.0,\"latitude\":18.51519520436081,\"longitude\":73.78170072834669},{\"altitude\":0.0,\"latitude\":18.515356105610167,\"longitude\":73.78150588545417},{\"altitude\":0.0,\"latitude\":18.51782951616352,\"longitude\":73.78150588545417}]}\n";
    }

    @NonNull
    private File getFile() {
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File directory = new File(root.getAbsolutePath() + DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdir();
            Toast.makeText(mActivity, "Created new directory",
                    Toast.LENGTH_SHORT).show();
        }
        return new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DCIM).getAbsolutePath() + DIRECTORY_NAME + fileName);
    }

    /**
     * Function is called when user click on read button
     */
    public String readFromFile() {
        File file = getFile();
        String message = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message = bufferedReader.readLine()) != null) {
                stringBuffer.append(message + "\n");
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * Checks if external storage is available for read and write
     *
     * @return boolean
     */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Function to get list of polygon points
     *
     * @return FlightPlanModel
     */
    public FlightPlanModel getFlightPlanModel() {
        String fileContent = readFromFile();
        Log.i(TAG, fileContent);
        String jsonContent = getJsonContent(fileContent);
        Log.i(TAG, "Converted to json content > " + jsonContent);
        return CustomJsonParser.fromJson(jsonContent);
    }

    /**
     * Function to convert file content into a proper json content
     *
     * @param content String
     * @return String
     */
    private String getJsonContent(String content) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OPEN_ARRAY_BRACES);
        content = removeLastComma(content);
        stringBuilder.append(content);
        stringBuilder.append(CLOSE_ARRAY_BRACES);
        return stringBuilder.toString();
    }

    /**
     * Function to remove last comma from the given content
     *
     * @param content String
     * @return String
     */
    private String removeLastComma(String content) {
        try {
            char lastCharacter = content.charAt(content.length() - 1);
            if (lastCharacter == COMMA) {
                return content.substring(0, content.length() - 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
