package Model;

import java.io.*;
import java.util.List;


public class BroadcastWarning implements Feature {

    StringBuilder strmsg = new StringBuilder();
    private String email;

    public BroadcastWarning () {
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    @Override
    public void SendAlert( List<Sensor> s) {

        for(Sensor x: s){
            strmsg.append(x.getSensorID()+"   Temperature: "+x.getSdList().get(0).getTemperature() + " ");
        }

        //append the email entered in the system
        strmsg.append(this.email);

        SendMessage(strmsg);

        //prints on the console string passed to python script
        System.out.println(strmsg);
        return;
    }

    private String getOSName() {
        return System.getProperty("os.name");
    }

    // this method finds the "send_broadcast.py" file that is stored along with the source code
    //  and invokes the script to send a broadcast notification
    private void SendMessage(StringBuilder s) {
        System.out.println("Sending alert notifications from Broadcast message...");

        File f = new File("send_broadcast.py");
        String absolute = f.getAbsolutePath();
        String command = "";

        // If running file on Microsoft Windows then invoke using 'py ...'
            // else it is a Unix system and invoke using 'python ...'
        if(getOSName().startsWith("Windows")) {
            command = "py " + absolute + " "+ s;
           // command = "py " + absolute + " " + s.getSensorID() + " " + s.getSensorName() + " " + s.getSdList().get(0).getTemperature();
        } else {
          //  command = "python " + absolute + " " + s.getSensorID() + " " + s.getSensorName() + " " + s.getSdList().get(0).getTemperature();
            command = "python " + absolute + " " + s;
        }

        try {
            Process p = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            System.out.println("Problem executing python script!");
        }

        return;
    }

}
