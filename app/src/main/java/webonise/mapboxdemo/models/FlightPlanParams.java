package webonise.mapboxdemo.models;

import dji.sdk.base.DJIBaseProduct;

/**
 * This class is meant to contain the settings that a new flight plan can be cloned
 * from (like a template). Specific attributes, like area of interest or home location,
 * should be kept in the FlightPlan data structure.
 */
public class FlightPlanParams {

    private static String DEFAULT_NAME = "New Flight Plan";
    private static int DEFAULT_ALTITUDE = 0;
    public static int DEFAULT_V_OVERLAP = 70;
    public static int DEFAULT_H_OVERLAP = 60;
    private static DJIBaseProduct.Model DEFAULT_DRONE_TYPE = DJIBaseProduct.Model.Inspire_1;

    private String name; // plan name
    private int alt; // altitude
    private int vOverlap; // vertical overlap
    private int hOverlap; // horizontal overlap
    private DJIBaseProduct.Model droneType;

    public static FlightPlanParams makeDefaultParams() {
        return new FlightPlanParams(DEFAULT_NAME, DEFAULT_ALTITUDE, DEFAULT_V_OVERLAP, DEFAULT_H_OVERLAP, DEFAULT_DRONE_TYPE);
    }

    public FlightPlanParams(String name, int alt, int vOverlap, int hOverlap, DJIBaseProduct.Model droneType) {
        this.name = name;
        this.alt = alt;
        this.vOverlap = vOverlap;
        this.hOverlap = hOverlap;
        this.droneType = droneType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public int getvOverlap() {
        return vOverlap;
    }

    public void setvOverlap(int vOverlap) {
        this.vOverlap = vOverlap;
    }

    public int gethOverlap() {
        return hOverlap;
    }

    public void sethOverlap(int hOverlap) {
        this.hOverlap = hOverlap;
    }

    public DJIBaseProduct.Model getDroneType() {
        return droneType;
    }

    public void setDroneType(DJIBaseProduct.Model droneType) {
        this.droneType = droneType;
    }
}
