package Model;

import java.text.DecimalFormat;

public class SensorData {

    private int entryId;
    private  String dateTime;
    private String sensorPi;
    private float temperature;


    public SensorData(int entryId, String dateTime, String sensorPi, float temperature) {

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);
        this.entryId = entryId;
        this.dateTime = dateTime;
        this.sensorPi = sensorPi;
        this.temperature = Float.parseFloat(df.format(temperature));
    }

    public SensorData(float temperature){
        this.temperature = temperature;
    }
    public SensorData(){
    }


    public int getEntryId() {
        return entryId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSensorPi() {
        return sensorPi;
    }

    public float getTemperature() {
        return temperature;
    }


}


