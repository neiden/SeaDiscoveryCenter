package Model;

public final class Constants {

    public static final int NUM_SENSORS = 7;
    public static final String[] AQUARIUM_TYPE = {"Tropical","Freshwater","Anemone","Octopus", "Look Down", "Jelly", "Restoration"};
    public static final String[] PI_ID = {"pi_1","pi_2","pi_3","pi_4","pi_5","pi_6","pi_7"};
    public static final String[] COL_HEADER = {" ","pi_1","pi_2","pi_3","pi_4","pi_5","pi_6","pi_7"};

    public static final Object[][] TEST = {{"Tropical","Freshwater","Anemone","Octopus", "Look Down", "Jelly", "Restoration"},
            {1,2,3,4,5,6,7},{1,2,3,4,5,6,7},{"73-80", "45-65", "45-60", "45-60","45-60","45-60","45-60"} };

    public static final int NUM_ROWS = 4;
    public static final int NUM_COLS =8;

    public static final String[] RANGE = {"Range:","73-80", "48-54", "48-54", "48-54","48-54","48-54","48-54"};
    public static final String[] NAME_PI = {"Name", "Tropical","Freshwater","Anemone","Octopus", "Look Down", "Jelly", "Restoration"};

    public static final String BAD = " status: Out of range!";
    public static final String GOOD = " status: Good!";

    public static final long PERIOD = 1000 * 1800L;
    public static final long DELAY = 0L;


    public static final String MESSAGE_OUT_oF_RANGE = ": Out of Range. Please check tank!";
    public static final String NO_DATA = " No temperature data recorded";
    public static final String ALL_SENSOR_GOOD = "Status Report: Found no temperature out of range";

    public static final int ACCEPTABLE_MIN_COLD_TEMP = 48; //<-- official
    public static final int ACCEPTABLE_MAX_COLD_TEMP = 54;  //<--official
    public static final int ACCEPTABLE_MIN_TROP_TEMP = 73; //<--official
    public static final int ACCEPTABLE_MAX_TROP_TEMP = 80; //<--official

    public static final int INTERVAL = 1800 * 1000;

    //returns true if the temp is within range of tropical tank temp
    public static boolean HotTempWithinRange(float temp){

        if (temp >= Constants.ACCEPTABLE_MIN_TROP_TEMP &&
                temp <= Constants.ACCEPTABLE_MAX_TROP_TEMP)
            return true;
        else{
            return false;
        }

    }

    //returns true if the temp is within range of cold tank temp
    public static boolean ColdTempWithinRange(float temp){

        if (temp >= Constants.ACCEPTABLE_MIN_COLD_TEMP &&
                temp <= Constants.ACCEPTABLE_MAX_COLD_TEMP)
            return true;
        else{
            return false;
        }

    }
}
