package Model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Database extends DataParser{

    private Sensor[] sensor = new Sensor[Constants.NUM_SENSORS];
    public abstract void setUpDB();
    public abstract void parseData();

    public Database() {}
    public Sensor[] getSensor() {return sensor;}


    public void initializeSensors(){

        for(int i=0; i<Constants.NUM_SENSORS; i++){
            Sensor S = new Sensor(Constants.PI_ID[i], Constants.AQUARIUM_TYPE[i], Constants.RANGE[i]);
            sensor[i] = S;
        }
    }

    public void loadSensorData() {
    //todo: load each sensors with the list of sensor data associated with it - Done!
        int i = 0;
        for (String entry : super.getEntries()) {
            for(Sensor s : sensor){
                if(entry.contains(s.getSensorID())){
                    loadSensor(s, entry);
                    i++;
                    break;
                }
            }
        }
    }

    public void loadSensor(Sensor s, String entry){
    //todo : take specific sensor and load sensorData for this sensor - Done!
        StringTokenizer str = new StringTokenizer(entry.replaceAll("[(';)]",""),",");
        int entryId = Integer.parseInt(str.nextToken());
        String dateTime = str.nextToken();
        String sensorPi = str.nextToken();
        float temperature = Float.parseFloat(str.nextToken());

        SensorData sData = new SensorData(entryId, dateTime, sensorPi,temperature);
        s.getSdList().add(sData);
    }


    /*Loads temperature data into each appropriate sensor*/
    public void loadTemperatureReading(String dataString){
        SensorData sData;

        //matches 0 or more digits immediately following a > sign
       // String pattern = "(?<=>)[0-9]*"; <-- old pattern for output

        String pattern ="(?<=')[0-9.]+";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dataString);

        int i=0;
        while(m.find()){
            float a = Float.parseFloat(m.group());
           // System.out.println(a);
            sData = new SensorData((float)a);
            sensor[i].getSdList().add(sData);
            i++;
        }
    }



}


