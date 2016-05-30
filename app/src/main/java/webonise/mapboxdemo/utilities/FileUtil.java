package webonise.mapboxdemo.utilities;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import webonise.mapboxdemo.MainActivity;

public class FileUtil {

    private static final String DIRECTORY_NAME = "/fileDemo/";
    private final MainActivity mActivity;
    private String fileName = "inflight-simulator-test";

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
            String message = "hello this is my first message";
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();

                Toast.makeText(mActivity, "Message saved",
                        Toast.LENGTH_SHORT).show();
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
     * @return List<LatLng>
     */
    public List<LatLng> getPolygonPoints() {
        String fileContent = readFromFile();
        final List<LatLng> latLngPolygon = new ArrayList<>();
        {
            latLngPolygon.add(new LatLng(18.515600, 73.781900));//Bhavdan
            latLngPolygon.add(new LatLng(18.517600, 73.781900));//Bhavdan
            latLngPolygon.add(new LatLng(18.517600, 73.782900));//Bhavdan
            latLngPolygon.add(new LatLng(18.515600, 73.782900));//Bhavdan
            latLngPolygon.add(new LatLng(18.515600, 73.781900));//Bhavdan

        }
        return latLngPolygon;
    }
}
