package View;

import Controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginPageView extends JFrame implements ActionListener {

    private JLabel greetings;
    private JLabel userNotifLbl;
    private JLabel emailLbl;
    private JLabel pWord;
    private JTextField userNameTextfield;
    private JPasswordField passWordField;
    private JButton loginBtn;
    private JFrame frame;

    private JLabel labelImg;
    private ImageIcon img;

    private String email;

    public String getEmail () {
        return email;
    }

    public LoginPageView(){

        SetUpWindow();
        SetUpFields();
        addAllComponents();
        setUpUserNotif();
        addListeners();

    }

    public void SetUpWindow(){
        frame = new JFrame("TMS");
        greetings = new JLabel("TMS Monitoring System!");
        greetings.setBounds(110, 30, 200, 30);
        greetings.setSize(250, 30);
        greetings.setFont(new Font("Verdana", Font.BOLD,12 ));

        img = new ImageIcon(this.getClass().getResource("/Images/logo.jpg"));
        Image im = img.getImage();
        Image mod = im.getScaledInstance(170, 60, Image.SCALE_SMOOTH);
        img = new ImageIcon(mod);

        labelImg = new JLabel(img);
        labelImg.setBounds(150+20,150+30, img.getIconWidth(),  img.getIconHeight());

    }


    public void SetUpFields(){

        emailLbl = new JLabel("Email:" );
        pWord = new JLabel("Password:");
        userNameTextfield = new JTextField();
        passWordField = new JPasswordField();
        loginBtn = new JButton("Login");


        emailLbl.setBounds(30+20, 70+30, 150, 30);
        pWord.setBounds(30+20, 110+30, 150, 30);
        userNameTextfield.setBounds(150+20, 70+30, 170, 30);
        passWordField.setBounds(150+20, 110+30, 170, 30);
        loginBtn.setBounds(30+20, 160+30, 70, 30);

        Color c = new Color(77, 75, 130);

        frame.getContentPane().setBackground(Color.white);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(5, 5 , 5, 5, c));
        frame.setUndecorated(true);
        frame.setSize(400,300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void addAllComponents(){
        frame.add(greetings);
        frame.add(emailLbl);
        frame.add(pWord);
        frame.add(loginBtn);
        frame.add(userNameTextfield);
        frame.add(passWordField);
        frame.add(labelImg);


    }

    public void addListeners(){
        loginBtn.addActionListener(this);
    }


    //label for notifying successful or failed login
    public void setUpUserNotif(){
        userNotifLbl = new JLabel("Granted!");
        userNotifLbl.setForeground(Color.blue);
        userNotifLbl.setBounds(150, 250, 200, 30);
        userNotifLbl.setVisible(false);
        frame.add(userNotifLbl);

    }


    @Override
    public void actionPerformed ( ActionEvent e ) {

        if(e.getSource()==loginBtn){
             authenticate();
            //new Controller();
        }
    }

    //authenticates user login
    private void authenticate(){

        String userEmail = userNameTextfield.getText();
        String password = passWordField.getText();

        if(password.equals("Fish123") && !userEmail.equals("")){
            System.out.println("Granted!");
            userNotifLbl.setVisible(true);
            this.email = userEmail;

           Controller c =  new Controller(email, new DashField());
           if(c instanceof Controller){
               new DashView(c);
           }
           frame.setVisible(false);
           //System.out.println("Authenticate email = " + userEmail);

        } else {
            System.out.println("Wrong credential");
            userNotifLbl.setText("Please input correct credentials.");
            userNotifLbl.setVisible(true);
            userNotifLbl.setForeground(Color.red);

        }

    }
}
