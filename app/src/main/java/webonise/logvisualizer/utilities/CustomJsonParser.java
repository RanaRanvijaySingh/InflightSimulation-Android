package webonise.logvisualizer.utilities;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webonise.logvisualizer.model.FlightPlanModel;

public class CustomJsonParser {

    private static final String TAG = "CustomJsonParser";

    /**
     * Function to get FlightPlanModel class from given json content
     * @param content String
     * @return FlighPlanModel
     */
    public static FlightPlanModel fromJson(String content) {
        List<FlightPlanModel> planModelList = new ArrayList<>();
        if (!TextUtils.isEmpty(content)) {
            try {
                JSONArray jsonArrayContent = new JSONArray(content);
                Log.i(TAG,"JSONArray created");
                for (int i = 0; i < jsonArrayContent.length(); i++) {
                    JSONObject jsonObjectItem = jsonArrayContent.getJSONObject(i);
                    Log.i(TAG,"JSONObject found from json array");
                    int classType = jsonObjectItem.getInt(Constants.JsonKeys.CLASS_TYPE);
                    Log.i(TAG,"Object class type "+ classType);
                    if (classType == Constants.ClassType.FLIGHT_PLAN) {
                        FlightPlanModel flightPlanModel = new Gson()
                                .fromJson(jsonObjectItem.toString(), FlightPlanModel.class);
                        planModelList.add(flightPlanModel);
                        Log.i(TAG,"Flight plan model created and added to list"+ classType);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return planModelList.size() > 0 ? planModelList.get(0) : null;
    }
}
