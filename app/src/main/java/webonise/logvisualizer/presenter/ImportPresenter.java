package webonise.logvisualizer.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import webonise.logvisualizer.ImportActivity;
import webonise.logvisualizer.R;
import webonise.logvisualizer.model.FlightLogModel;
import webonise.logvisualizer.utilities.Constants;
import webonise.logvisualizer.utilities.FileUtil;
import webonise.logvisualizer.utilities.FlightLogParserTask;
import webonise.logvisualizer.view.interfaces.ImportView;

public class ImportPresenter {
    private final ImportView mImportView;
    private final Activity mActivity;

    public ImportPresenter(ImportView importView, Activity activity) {
        this.mActivity = activity;
        this.mImportView = importView;
    }

    public void getFileData(int requestCode, Intent data, Activity activity) {
        if (requestCode == ImportActivity.PICK_FILE_RESULT_CODE) {
            if (data == null) {
                mImportView.showToast(R.string.toast_unable_to_pick_up_file);
                return;
            }
            Uri uri = data.getData();
            FileUtil fileUtil = new FileUtil(activity);
            if (fileUtil.isCorrectFile(uri)) {
                String fileContent = fileUtil.readFile(uri);
                if (TextUtils.isEmpty(fileContent)) {
                    mImportView.showToast(R.string.toast_invalid_file_content);
                    return;
                }
                FlightLogModel flightLogModel = buildFlightPlanModel(fileContent);
            } else {
                mImportView.showToast(R.string.toast_error_incorrect_file_format);
            }
        }
    }

    /**
     * Function to create flight plan model from given file content;
     *
     * @param fileContent String File content
     */
    private FlightLogModel buildFlightPlanModel(String fileContent) {
        if (isFileContentValid(fileContent)) {
            FlightLogParserTask flightLogParserTask = new FlightLogParserTask(mActivity,
                    onFlightLogParserListener);
            flightLogParserTask.execute(fileContent);
        }
        return null;
    }

    /**
     * Function to check if the given file content is valid or not.
     *
     * @param fileContent String file content
     * @return boolean
     */
    public boolean isFileContentValid(String fileContent) {
        return fileContent != null
                && !TextUtils.isEmpty(fileContent)
                && fileContent.contains(Constants.FILE_CONTENT_VALIDATION_CHECK_STRING);
    }

    private FlightLogParserTask.OnFlightLogParserListener onFlightLogParserListener =
            new FlightLogParserTask.OnFlightLogParserListener() {
        @Override
        public void onSuccess(FlightLogModel flightLogModel) {
            mImportView.showToast(R.string.toast_success);
            mImportView.gotoHomePage(flightLogModel);
        }

        @Override
        public void onError(String message) {
            mImportView.showToast(message);
        }
    };
}
