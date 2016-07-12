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

import webonise.mapboxdemo.view.MainActivity;
import webonise.mapboxdemo.model.FlightPlanModel;

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
        return "{\"type\":0,\"homeLocation\":{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7819},\"isBufferEnabled\":true,\"pointList\":[{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7819},{\"altitude\":0.0,\"latitude\":18.5176,\"longitude\":73.7819},{\"altitude\":0.0,\"latitude\":18.5176,\"longitude\":73.7829},{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7829},{\"altitude\":0.0,\"latitude\":18.5156,\"longitude\":73.7819}],\"transectsList\":[{\"altitude\":0.0,\"latitude\":18.515363689673244,\"longitude\":73.7834543143793},{\"altitude\":0.0,\"latitude\":18.517956957888817,\"longitude\":73.7834543143793},{\"altitude\":0.0,\"latitude\":18.518088098273576,\"longitude\":73.78325947148679},{\"altitude\":0.0,\"latitude\":18.51524957785906,\"longitude\":73.78325947148679},{\"altitude\":0.0,\"latitude\":18.51523282201783,\"longitude\":73.78306462859427},{\"altitude\":0.0,\"latitude\":18.51810149061056,\"longitude\":73.78306462859427},{\"altitude\":0.0,\"latitude\":18.51810021642835,\"longitude\":73.78286978570176},{\"altitude\":0.0,\"latitude\":18.515223033531615,\"longitude\":73.78286978570176},{\"altitude\":0.0,\"latitude\":18.5152132450454,\"longitude\":73.78267494280925},{\"altitude\":0.0,\"latitude\":18.518098942246144,\"longitude\":73.78267494280925},{\"altitude\":0.0,\"latitude\":18.518097668063934,\"longitude\":73.78248009991674},{\"altitude\":0.0,\"latitude\":18.515203456559185,\"longitude\":73.78248009991674},{\"altitude\":0.0,\"latitude\":18.51519366807297,\"longitude\":73.78228525702423},{\"altitude\":0.0,\"latitude\":18.518096393881727,\"longitude\":73.78228525702423},{\"altitude\":0.0,\"latitude\":18.518095119699517,\"longitude\":73.78209041413172},{\"altitude\":0.0,\"latitude\":18.515183879586758,\"longitude\":73.78209041413172},{\"altitude\":0.0,\"latitude\":18.515174091100544,\"longitude\":73.7818955712392},{\"altitude\":0.0,\"latitude\":18.51809384551731,\"longitude\":73.7818955712392},{\"altitude\":0.0,\"latitude\":18.51805534363307,\"longitude\":73.78170072834669},{\"altitude\":0.0,\"latitude\":18.51519520436081,\"longitude\":73.78170072834669},{\"altitude\":0.0,\"latitude\":18.515356105610167,\"longitude\":73.78150588545417},{\"altitude\":0.0,\"latitude\":18.51782951616352,\"longitude\":73.78150588545417}],\"droneTrail\":[{\"latitude\":73.78158330917358,\"longitude\":18.515204764101075},{\"latitude\":73.78158330917358,\"longitude\":18.51528615212377},{\"latitude\":73.78158330917358,\"longitude\":18.51535227986371},{\"latitude\":73.78158330917358,\"longitude\":18.51541840757809},{\"latitude\":73.78158330917358,\"longitude\":18.515484535266907},{\"latitude\":73.78157794475555,\"longitude\":18.51556083641454},{\"latitude\":73.78157258033751,\"longitude\":18.515647311007406},{\"latitude\":73.7815672159195,\"longitude\":18.515718525345175},{\"latitude\":73.7815672159195,\"longitude\":18.515759219239165},{\"latitude\":73.78155648708344,\"longitude\":18.515830433530358},{\"latitude\":73.78154575824738,\"longitude\":18.515896561059908},{\"latitude\":73.78154039382935,\"longitude\":18.51596777529393},{\"latitude\":73.78153502941132,\"longitude\":18.516033902770403},{\"latitude\":73.78153502941132,\"longitude\":18.516110203673033},{\"latitude\":73.78153502941132,\"longitude\":18.516176331094446},{\"latitude\":73.78151893615723,\"longitude\":18.516232285046456},{\"latitude\":73.78146529197693,\"longitude\":18.516298412420692},{\"latitude\":73.78144919872284,\"longitude\":18.516359453051134},{\"latitude\":73.78143846988678,\"longitude\":18.516415406943246},{\"latitude\":73.78142774105072,\"longitude\":18.516456100671476},{\"latitude\":73.78142237663269,\"longitude\":18.516512054531976},{\"latitude\":73.78142237663269,\"longitude\":18.51655783494968},{\"latitude\":73.78142237663269,\"longitude\":18.516598528644028},{\"latitude\":73.78142237663269,\"longitude\":18.516649395748352},{\"latitude\":73.78142237663269,\"longitude\":18.516695176129314},{\"latitude\":73.78142237663269,\"longitude\":18.51676639003089},{\"latitude\":73.78140091896057,\"longitude\":18.516827430494363},{\"latitude\":73.7813901901245,\"longitude\":18.516878297530614},{\"latitude\":73.78137946128845,\"longitude\":18.516944424655136},{\"latitude\":73.78137409687042,\"longitude\":18.517020725151664},{\"latitude\":73.7813901901245,\"longitude\":18.517112285702584},{\"latitude\":73.78140091896057,\"longitude\":18.517178412736655},{\"latitude\":73.7814062833786,\"longitude\":18.517234366360903},{\"latitude\":73.78141701221466,\"longitude\":18.517280146585318},{\"latitude\":73.78141164779663,\"longitude\":18.517336100176294},{\"latitude\":73.7813901901245,\"longitude\":18.51741240049813},{\"latitude\":73.7813901901245,\"longitude\":18.517468354045842},{\"latitude\":73.78142237663269,\"longitude\":18.517509047523596},{\"latitude\":73.7814599275589,\"longitude\":18.517539567625562},{\"latitude\":73.78151893615723,\"longitude\":18.517565001039706},{\"latitude\":73.78155112266539,\"longitude\":18.51754974099167},{\"latitude\":73.78157258033751,\"longitude\":18.51752430757526},{\"latitude\":73.78158062696457,\"longitude\":18.517486157443553},{\"latitude\":73.78158867359161,\"longitude\":18.517442920617334},{\"latitude\":73.78158867359161,\"longitude\":18.517414943841608},{\"latitude\":73.78159403800964,\"longitude\":18.517369163653274},{\"latitude\":73.78159940242767,\"longitude\":18.517333556831648},{\"latitude\":73.78159940242767,\"longitude\":18.517300493347786},{\"latitude\":73.78159940242767,\"longitude\":18.517282689930767},{\"latitude\":73.78159940242767,\"longitude\":18.517254713128832},{\"latitude\":73.78159403800964,\"longitude\":18.51722164962973},{\"latitude\":73.78159135580061,\"longitude\":18.517198759511224},{\"latitude\":73.78159940242767,\"longitude\":18.517135175832653},{\"latitude\":73.7816047668457,\"longitude\":18.51708939556945},{\"latitude\":73.78159940242767,\"longitude\":18.517020725151664},{\"latitude\":73.78159940242767,\"longitude\":18.51699783500629},{\"latitude\":73.78159940242767,\"longitude\":18.516969858157754},{\"latitude\":73.7816047668457,\"longitude\":18.516934251253026},{\"latitude\":73.78161281347273,\"longitude\":18.516901187691992},{\"latitude\":73.78161817789078,\"longitude\":18.51686558077296},{\"latitude\":73.78161549568176,\"longitude\":18.51685286401437},{\"latitude\":73.78161549568176,\"longitude\":18.516822343789894},{\"latitude\":73.78162086009979,\"longitude\":18.516763846677758},{\"latitude\":73.78161549568176,\"longitude\":18.516725696376476},{\"latitude\":73.78161817789078,\"longitude\":18.516705349545635},{\"latitude\":73.78162622451782,\"longitude\":18.51668245935805},{\"latitude\":73.78163158893584,\"longitude\":18.516639222328696},{\"latitude\":73.78162890672684,\"longitude\":18.51660615871064},{\"latitude\":73.78163158893584,\"longitude\":18.516588355221344},{\"latitude\":73.78163695335388,\"longitude\":18.516532401385806},{\"latitude\":73.7816396355629,\"longitude\":18.51649170767573},{\"latitude\":73.78164231777191,\"longitude\":18.516461187386827},{\"latitude\":73.78165036439896,\"longitude\":18.516417950301545},{\"latitude\":73.78165304660796,\"longitude\":18.516364539769356},{\"latitude\":73.78166109323502,\"longitude\":18.516339106176734},{\"latitude\":73.78166645765305,\"longitude\":18.516308585860614},{\"latitude\":73.78166913986206,\"longitude\":18.516270435457784},{\"latitude\":73.78167450428009,\"longitude\":18.516252631933554},{\"latitude\":73.7816771864891,\"longitude\":18.516229741685397},{\"latitude\":73.7816771864891,\"longitude\":18.516178874456305},{\"latitude\":73.78167450428009,\"longitude\":18.516161070922543},{\"latitude\":73.7816771864891,\"longitude\":18.516115290398663},{\"latitude\":73.78158330917358,\"longitude\":18.515204764101075}]}\n";
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
