// Sea Discovery TMS: Alarm Warning 
// Authors: Chris Miller, Maria Adams, Archan Rupella
// 
// Last Modified: 3/19/2020
//
// This class is used to primarily play an alarm sound effect over local speakers, 
// accompanied by a pop-up window that allows any user to stop the alarm. 
// 
// This class used in conjunction with the other pieces of the TMS is an 
// imperitive part of alerting the staff of potential problems in the tanks.
package Model;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

import java.lang.*;
import java.util.List;
import javax.swing.*;

public class SoundAlarm implements Feature {

    StringBuilder strmsg = new StringBuilder();

    @Override
    public void SendAlert(List<Sensor> s) {

        for(Sensor x: s){
            strmsg.append(x.getSensorID()+" "+x.getSdList().get(0).getTemperature()+"\n");
        }
       play();

    }

    // Method that will call the sound alarm constructor and create the sound clip object that will be played.
    // After the sound clip has been created and started, when the user clicks the close button on the dialog
    // the sound will cease to play.
    public void play(){

        Clip alarm = this.playAlarm();
        if(message(strmsg)==0){
          alarm.stop();
        }

    }
  
    /*
    Method that creates and starts the audio clip object, in this case an alarm sound effect will start
    when this method is done, also returns said clip object so that we can successfully stop the sound
    with a button press when we no longer need it to play.
    */
    public Clip playAlarm(){
        try{

            File noise = new File("alarmSound.wav");
            Clip alarm = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(noise);
            alarm.open(stream);
            alarm.start();

           return alarm;
        // Catch all of the potential errors that come from the clip class
        } catch(UnsupportedAudioFileException e){
            System.out.println(e);
            return null;
        } catch(IOException e){
            System.out.println(e);
            return null;
        } catch( LineUnavailableException e){
            System.out.println(e);
            return null;
        }

    }

    // A small method that creates a pop-up dialog that will stop the audio from playing
    // when the user clicks the prompt on the dialog.
    //
    // This method will take in the sensor object that is currently under durress and use
    // said object to print a helpful message (displaying the tank's name and temperature).
    public int message(StringBuilder s){
      Object[] option = {"Close"};// Only allow them to close the dialog, we don't need other options
      JFrame mainFrame = new JFrame ("Warning");// The name of the warning dialog

      int n = JOptionPane.showOptionDialog(mainFrame,("WARNING: Check tank(s) \n" + s),
                "Tank Warning",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, option, option[0]);

      System.out.println("printing n: "+ n);
      return n;// return an int when they have pressed the cancel button, used to shut off the sound alarm.
    }

}
