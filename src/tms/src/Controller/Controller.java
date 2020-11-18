package Controller;

import Model.BroadcastWarning;
import Model.Constants;
import Model.Feature;
import Model.SystemManager;
import View.DashView;
import View.DashField;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Controller {

    private SystemManager sm = new SystemManager();
    private DashField display;
    private Timer ModelTimer;
    private Boolean isMonitoring = false;

    public DashField getDisplay () {
        return display;
    }

    public Controller(String email, DashField f){
        this.display = f;
        sm.setUpEmail(email);

    }

   /*sends the go signal to system manager to run program
   Program is scheduled to run at a given period
    Executes when user clicks Monitor button*/
   public void Monitor ()   {
       isMonitoring = true;
       ModelTimer = new Timer();
       ModelTimer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run () {
               sm.runTMS();
               List<String> status = sm.monitor();
               Object[][] data = sm.showSummary();
               displayOnScreen(status, data);
           }
       },Constants.DELAY, Constants.PERIOD);

   }

   /*User clicks the stop button */
   public void StopMonitoringTime(){

       ModelTimer.cancel();
       isMonitoring = false;

       display.getOutputArea().setText("Stopped monitoring on: " + new Date()+"\n");
       display.getMonitorBtn().setText("Monitor");
       display.getOutputArea().append("Monitoring is currently stopped\n\nPress Monitor to continue or logout");
       System.out.println("Stopped Monitoring here");
   }

   /*Display on screen with the updated sensor and temperature dataTable*/
   public void displayOnScreen(List<String> status, Object[][]data){

       display.getOutputArea().setText("");
       display.getOutputArea().append(new Date() + "\n");
       display.getOutputArea().append("Refreshes every:  "+ (Constants.INTERVAL)/1000+ " seconds\n");

       for(String s: status){
           display.getOutputArea().append(s);
       }
       display.setData (data);

       try {
           Thread.sleep(Constants.INTERVAL);
       } catch (InterruptedException e) {
           System.out.println("Interrupted Exception check thread in getSummary()");
       }
       if (isMonitoring)
           display.getOutputArea().setText("");
   }

    /*shuts off the program completely when logout is pressed*/
    public void logOff(){
        System.exit(0);
    }

}
