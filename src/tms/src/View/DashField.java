package View;
import Model.Constants;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class DashField extends Frame implements Initializable{

    private JFrame frame;
    private JLabel heading;
    private JButton  monitorBtn, logoffbtn, stopMonitorbtn;
    private JTable outputTable;
    private JTextArea outputArea;
    private JScrollPane scrollDown;
    private JScrollPane scrollDown1;
    private JPanel panelTop, panelMid, panelDown;
    private JComboBox<String> senCombo;
    private Object[][] data = new Object[Constants.NUM_ROWS][Constants.NUM_COLS];;

    public Object[][] getData () {
        return data;
    }

    /*refreshes the values in the table*/
    public void setData ( Object[][] data ) {
        this.data = data;

        for(int i=0; i<data.length; i++){
            for(int j=0; j<Constants.NUM_COLS; j++){
                outputTable.setValueAt("", i,j);
            }
        }
        outputTable.setVisible(false);
        outputTable.revalidate();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception check thread in getSummary()");
        }

        for(int i=0; i<data.length; i++){
           for(int j=0; j<Constants.NUM_COLS; j++){
               outputTable.setValueAt(data[i][j], i, j);
           }
       }
       outputTable.setVisible(true);
       outputTable.revalidate();

    }

    public JButton getMonitorBtn () {
        return monitorBtn;
    }

    public JButton getLogoffbtn () {
        return logoffbtn;
    }

    public JButton getStopMonitor () {
        return stopMonitorbtn;
    }

    public JTextArea getOutputArea () {
        return outputArea;
    }

    public JComboBox<String> getSenCombo () {
        return senCombo;
    }

    @Override
    public void initialize () {
        init_frame();
        init_heading();
        init_buttons();
        init_outputArea();
        init_outpuTable();
        init_panels();
        init_listSensors();
        addPanel_to_Frame();
    }

    private void init_frame (){

        frame = new JFrame("Welcome to TMS");
        init_frame_Dimensions();
    }

    private void init_heading (){
        heading = new JLabel(); heading.setText("DASHBOARD");
        heading.setForeground(Color.white);

    }

    private void init_frame_Dimensions (){
        Color c = new Color(90, 107, 130);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5 , 5, 5, c));
        frame.setSize(400,400);
       // frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    private void init_buttons (){
       monitorBtn = new JButton("Monitor");
       logoffbtn = new JButton("Log out");
       stopMonitorbtn = new JButton("STOP");
    }

    private void init_outputArea (){
        outputArea = new JTextArea("Please press monitor ");
       // scrollDown = new JScrollPane(outputArea);
        init_outputAreaDimensions();
    }

    private void init_outpuTable(){
        outputTable = new JTable(data, Constants.COL_HEADER);
        scrollDown1 = new JScrollPane(outputTable);
        init_outputTableDimensions();
    }

    private void init_outputTableDimensions(){
       outputTable.setEnabled(false);
       outputTable.setFillsViewportHeight(true);

    }

    private void init_outputAreaDimensions (){
        outputArea.setBackground(Color.white);
        outputArea.setEditable(false);
        outputArea.setLineWrap(false);
      //  outputArea.setLineWrap(true);
    }


    private void init_panels (){
        panelTop = new JPanel(new BorderLayout(10,10));
        panelMid = new JPanel(new GridLayout(2,1, 10,25));
        panelDown = new JPanel(new BorderLayout(10,10));
        init_panelDimensions();
    }

    private void init_panelDimensions (){
        Color c = new Color(81, 99, 146);
        panelTop.setBounds(3, 10, 370, 60);
        panelTop.add(BorderLayout.CENTER, monitorBtn);
        panelTop.add(BorderLayout.NORTH,heading);
        panelTop.setBackground(c);

        panelMid.setBounds(3,95 , 370, 195);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints. HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panelMid.add(outputArea, gbc);
        panelMid.setBackground(c);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelMid.add(scrollDown1,gbc);

        panelDown.setBounds(3, 305, 370, 40);
        panelDown.add(BorderLayout.WEST, stopMonitorbtn);
        panelDown.add(BorderLayout.EAST, logoffbtn);
        panelDown.setBackground(c);
    }

    private void addPanel_to_Frame (){
        Color c = new Color(81, 99, 146);
        frame.getContentPane().add(BorderLayout.NORTH,panelTop);
        frame.getContentPane().add(panelMid);
        frame.getContentPane().add(BorderLayout.SOUTH,panelDown);
        frame.getContentPane().setBackground(c);
    }

    private void init_listSensors (){
        String[] sensors = Constants.PI_ID ;
        senCombo = new JComboBox<>(sensors);
       // panelDown.add(BorderLayout.CENTER,senCombo);
    }
}
