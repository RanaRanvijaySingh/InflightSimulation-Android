package webonise.logvisualizer.view.interfaces;

import webonise.logvisualizer.model.FlightLogModel;

public interface ImportView {
    void showToast(int toastMessageId);

    void showToast(String message);

    void gotoHomePage(FlightLogModel flightLogModel);
}
