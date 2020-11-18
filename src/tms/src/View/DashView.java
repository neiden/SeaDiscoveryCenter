package View;

import Controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class DashView implements ActionListener{

    private Initializable mainView;
    private Controller controller;

    public DashView(Controller c){
        controller = c; //new Controller((DashField)mainView);
        mainView = c.getDisplay();
        mainView.initialize();
        addListeners();
    }

    public void addListeners(){
        if(mainView instanceof DashField){
            ((DashField) mainView).getMonitorBtn().addActionListener(this);
            ((DashField) mainView).getLogoffbtn().addActionListener(this);
            ((DashField) mainView).getStopMonitor().addActionListener(this);
        }
    }

    @Override
    public void actionPerformed ( ActionEvent e ) {

        if(mainView instanceof DashField){
            if(e.getSource()==((DashField) mainView).getMonitorBtn()){
                controller.Monitor();
                ((DashField) mainView).getMonitorBtn().setText("Monitoring in progress...");

            }
            if(e.getSource()==((DashField) mainView).getLogoffbtn()){
                controller.logOff();

            }
            if(e.getSource()==((DashField) mainView).getStopMonitor()){
                  controller.StopMonitoringTime();
             }
        }

    }

}