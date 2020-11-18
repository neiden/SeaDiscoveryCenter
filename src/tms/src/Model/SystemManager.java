package Model;

import View.LoginPageView;

import java.util.*;

public class SystemManager extends TMSDatabase{

    private Feature broadcast, soundAlarm, sensorHealth;
    private String email;

    /*Executes Monitoring process */
    public void runTMS()  {

        super.setUpDB();
        assignStatus();
        print(); // <-- go inside this method to get command on how and what to print (it's below)
    }

    /* Goes through each sensors temperature status,
     * finds a critical temperature and calls TriggerAlert()
     * returns list or sensors found with out range temp, otherwise
     * returns "found no temperature out of range" */
     public List<String> monitor()  {
        List<String> statusList = new ArrayList<>();
        List<Sensor> listofOutofRangeTemps = new ArrayList<>();
        boolean allSensorGood = true;

        for(Sensor thisSensor : getSensor()) {

            if ( thisSensor.getStatus() != null ) {
                if ( thisSensor.getStatus().equals("BAD") ) {
                    statusList.add(thisSensor.getSensorID() + "  " +
                            thisSensor.getSensorName() + Constants.MESSAGE_OUT_oF_RANGE+ "\n");
                    listofOutofRangeTemps.add(thisSensor);
                    allSensorGood = false;
                }
            }
        }
        if(allSensorGood) {
            statusList.add(Constants.ALL_SENSOR_GOOD);
        }else{
            TriggerAlert(listofOutofRangeTemps);
            statusList.add("A notification email has been sent");
        }
        return statusList;

    }

    /* Assigns status for each recorded temperature for each sensor */
    private void assignStatus(){
    //todo: Assign all status as soon as data is parsed in-Done!
        for (Sensor s : getSensor()){
            for(SensorData t : s.getSdList()) {

                if (s.getSensorName().equals("Tropical") &&
                        Constants.HotTempWithinRange(t.getTemperature())){
                    s.setStatus("Okay");
                }
                /*else if ( Constants.ColdTempWithinRange(t.getTemperature())){
                    s.setStatus("Okay");
                }*/

                else if ( Constants.ColdTempWithinRange(t.getTemperature()) || (s.getSensorID().equals("pi_2")
                        || s.getSensorID().equals("pi_7"))){
                    s.setStatus("Okay");
                }
                else
                    s.setStatus("BAD");

            }
        }
    }

    /*Triggers alert when called once TMS finds critical temperature*/
    private void TriggerAlert(List<Sensor> s) {


        broadcast = new BroadcastWarning();
        if(broadcast instanceof BroadcastWarning){
            ((BroadcastWarning) broadcast).setEmail(email);
        }
        System.out.println("In trigger email = "+ email);
        soundAlarm = new SoundAlarm();
        sensorHealth = new HealthDetection();
        broadcast.SendAlert(s);
        soundAlarm.SendAlert(s);
        //sensorHealth.SendAlert(s);

    }

    /*Gets email entered by the user*/
    public void setUpEmail(String email){
        this.email = email;
    }


    /*returns the table data values*/
    public Object[][] showSummary(){
        Object[][] tableValues = new Object[Constants.NUM_ROWS][Constants.NUM_COLS];
        Object[] temp = new Object[Constants.NUM_COLS];
        Object[] stats = new Object[Constants.NUM_COLS];

        int i =1;
        temp[0] = "Temp:";
        stats[0] = "Status:";
        for(Sensor s: getSensor()){
            if ( s.getStatus() != null ) {
                temp[i] = s.getSdList().get(0).getTemperature();
                stats[i] =s.getStatus();
                i++;
            }
        }
        tableValues[0] = temp;
        tableValues[1] = stats;
        tableValues[2] = Constants.RANGE;
        tableValues[3] = Constants.NAME_PI;
        return tableValues;
    }

    /* Different ways to print data -- pick your choice: this prints in the console */
    public void print() {
      Printer p = new Printer(getSensor());
       p.printAllSensorStatus();
        /*check Printer class to pick a method on what you want to do*/
    }


}
