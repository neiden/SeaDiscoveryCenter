package Model;

import java.util.ArrayList;
import java.util.List;

public class Sensor extends SensorData{

    private String sensorID;
    private String sensorName;
    private String status;
    private String range;
    private List<SensorData> sdList;

    public Sensor(String sensorID, String sensorName, String range) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.range = range;
        sdList = new ArrayList<>();
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public String getRange () {
        return range;
    }

    public void setSensorName( String sensorName) {
        this.sensorName = sensorName;
    }

    public List<SensorData> getSdList() {
        return sdList;
    }

    public void setSd(List<SensorData> sd) {
        this.sdList = sd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
