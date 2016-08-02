package webonise.logvisualizer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.List;

public class FlightLogModel implements Parcelable {
//    private List<LatLng> pointList;
    private LatLng homeLocation;
    private boolean isBufferEnabled;
    private List<LatLng> transectsList;
    private List<DroneStatusModel> droneStatusModelList;
/*

    public List<LatLng> getPointList() {
        return pointList;
    }

    public void setPointList(List<LatLng> pointList) {
        this.pointList = pointList;
    }
*/

    public LatLng getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(LatLng homeLocation) {
        this.homeLocation = homeLocation;
    }

    public boolean isBufferEnabled() {
        return isBufferEnabled;
    }

    public void setBufferEnabled(boolean bufferEnabled) {
        isBufferEnabled = bufferEnabled;
    }

    public List<LatLng> getTransectsList() {
        return transectsList;
    }

    public void setTransectsList(List<LatLng> transectsList) {
        this.transectsList = transectsList;
    }

    public List<DroneStatusModel> getDroneStatusModelList() {
        return droneStatusModelList;
    }

    public void setDroneStatusModelList(List<DroneStatusModel> droneStatusModelList) {
        this.droneStatusModelList = droneStatusModelList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeTypedList(this.pointList);
        dest.writeParcelable(this.homeLocation, flags);
        dest.writeByte(this.isBufferEnabled ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.transectsList);
        dest.writeList(this.droneStatusModelList);
    }

    public FlightLogModel() {
    }

    protected FlightLogModel(Parcel in) {
//        this.pointList = in.createTypedArrayList(LatLng.CREATOR);
        this.homeLocation = in.readParcelable(LatLng.class.getClassLoader());
        this.isBufferEnabled = in.readByte() != 0;
        this.transectsList = in.createTypedArrayList(LatLng.CREATOR);
        this.droneStatusModelList = new ArrayList<DroneStatusModel>();
        in.readList(this.droneStatusModelList, DroneStatusModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<FlightLogModel> CREATOR = new Parcelable.Creator<FlightLogModel>() {
        @Override
        public FlightLogModel createFromParcel(Parcel source) {
            return new FlightLogModel(source);
        }

        @Override
        public FlightLogModel[] newArray(int size) {
            return new FlightLogModel[size];
        }
    };
}
