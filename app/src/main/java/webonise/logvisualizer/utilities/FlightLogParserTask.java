package webonise.logvisualizer.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import webonise.logvisualizer.model.FlightLogModel;

public class FlightLogParserTask extends AsyncTask<String, String, FlightLogModel> {

    private ProgressDialog progressDialog;
    private OnFlightLogParserListener parserListener;

    public FlightLogParserTask(Activity mActivity, OnFlightLogParserListener parserListener) {
        this.parserListener = parserListener;
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("Loading ...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected FlightLogModel doInBackground(String... params) {
        FlightLogParser flightLogParser = new FlightLogParser();
        return flightLogParser.getFlightLogModel(params[0]);
    }

    @Override
    protected void onPostExecute(FlightLogModel flightLogModel) {
        progressDialog.dismiss();
        if (flightLogModel != null) {
            parserListener.onSuccess(flightLogModel);
        }else {
            parserListener.onError("Unable to parse flight logger file.");
        }
    }

    public interface OnFlightLogParserListener {
        void onSuccess(FlightLogModel flightLogModel);

        void onError(String message);
    }
}
