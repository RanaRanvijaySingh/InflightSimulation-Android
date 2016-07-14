package webonise.logvisualizer.model;

/**
 * This class is meant to contain the settings that a new flight plan can be cloned
 * from (like a template). Specific attributes, like area of interest or home location,
 * should be kept in the FlightPlan data structure.
 */
//TODO : This file is different from original
public class FlightPlanParams {

    private static String DEFAULT_NAME = "New Flight Plan";
    private static int DEFAULT_ALTITUDE = 0;
    public static int DEFAULT_V_OVERLAP = 70;
    public static int DEFAULT_H_OVERLAP = 60;

    private String name; // plan name
    private int alt; // altitude
    private int vOverlap; // vertical overlap
    private int hOverlap; // horizontal overlap

    public static FlightPlanParams makeDefaultParams() {
        return new FlightPlanParams(DEFAULT_NAME, DEFAULT_ALTITUDE, DEFAULT_V_OVERLAP, DEFAULT_H_OVERLAP);
    }

    public FlightPlanParams(String name, int alt, int vOverlap, int hOverlap) {
        this.name = name;
        this.alt = alt;
        this.vOverlap = vOverlap;
        this.hOverlap = hOverlap;
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
}
