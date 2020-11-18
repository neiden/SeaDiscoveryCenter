package Model;

import View.LoginPageView;

import java.util.ArrayList;
import java.util.List;

public class Printer{
    private Sensor[] S;


    public Printer (Sensor[] s) {
       this.S = s;
    }

    //prints raw data parsed from the source file
    public void printDataParsed(List<String> entries){
        for (String s : entries){
            System.out.println(s);
        }
        // System.out.println("size is : "+ super.getEntries().size());
    }

    //takes a sensor and prints data
    public void printSensor(Sensor s){
        for(SensorData sd: s.getSdList()){
            System.out.print(s.getSensorID() + ": "+ sd.getEntryId()+ " "+ sd.getDateTime()+" "+
                    sd.getTemperature()+" "+ s.getStatus());
            System.out.println();
        }
    }

    //prints all the sensor its their status
    public void printAllSensorStatus(){
        for(Sensor s: S){
            System.out.println(s.getSensorID()+ " "+ s.getStatus()+ "    "+ s.getSdList().get(0).getTemperature());
        }
    }

    //takes either name or id of the sensor and prints its data
    //returns all entries with status for specified sensor
    public List<String> printSensor(String nameOrId){
        List<String> sensorEntries = new ArrayList<>();

        for(Sensor s : S){

            if ( s.getSensorName().equals(nameOrId) || s.getSensorID().equals(nameOrId) ) {

                if(s.getStatus()== null){
                    sensorEntries.add(Constants.NO_DATA);
                }
                for (SensorData sd : s.getSdList()) {

                    //this prints in the UI
                    sensorEntries.add(s.getSensorName() +  "   " + sd.getTemperature() + "\t" + s.getStatus());

                    //this prints in the console
                    System.out.print(s.getSensorName() + " " + sd.getEntryId() + " " +
                            sd.getDateTime() + " " + sd.getTemperature() + " \t" + s.getStatus());
                    System.out.println();
                }
            }
        }

        return sensorEntries;
    }

    //searches by sensor name/id and Temperature
    //Example param: printSearchSensor("Tropical", "Temperature")
    //Prints the name, entry id and temperature
    public void printSearchSensor(String str1, String str2){

        for(Sensor s : S){
            if((str1.equals(s.getSensorName()) || str1.equals(s.getSensorID())) && str2.equals("Temperature")){
                for(SensorData sd : s.getSdList()){
                    System.out.print(s.getSensorName() + " " +sd.getEntryId()+ " " + sd.getTemperature());
                    System.out.println();
                }
            }
        }

    }
}
