package com.company;

/**
 * Created by Richard on 2/17/2017.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainInterfaceFrame extends JFrame {
    private static HashMap componentMap;
    private SerialCommunications SerialCommunication = new SerialCommunications();
    private CustomPanel contentPane;
    private ControllerInput LogitechController = new ControllerInput();
    private Timer ControllerRefreshTimer;
    private static DefaultListModel<String> modelSerialSent = new DefaultListModel<>();
    private static DefaultListModel<String> modelSerialReceived = new DefaultListModel<>();
    private int timerCounter = 0;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            //
            // Invoke your function here
            //
            LogitechController.UpdateController1Components();
            contentPane.Refresh();
            UpdateArduino();
        }
    };
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainInterfaceFrame frame = new MainInterfaceFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public class CustomPanel extends JPanel{
        private JPanel contentPanel;

        public CustomPanel(){
            contentPanel = new JPanel();
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPanel.setLayout(new BorderLayout(0, 0));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw Text
            if(LogitechController.getController1Connected()){
                if(true) {
                    g.setColor(Color.BLUE);
                    int change = (int) (LogitechController.getZAxis() * 100);
                    change *= -1;
                    if (change > 0) {
                        g.fillRect(400, 250, change, 50);
                    } else {
                        g.fillRect(400 + change, 250, change * -1, 50);
                        //System.out.println(change);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(300,250, 200, 50);
                }
                int X, Y, SIZE1, SIZE2;
                if(true) {
                    int DPadValue = LogitechController.getDPad();
                    switch (DPadValue) {
                        case 0:
                            X = 145 + 0;
                            Y = 395 + 0;
                            break;
                        case 1:
                            X = 145 - 100;
                            Y = 395 - 100;
                            break;
                        case 2:
                            X = 145 + 0;
                            Y = 395 - 100;
                            break;
                        case 3:
                            X = 145 + 100;
                            Y = 395 - 100;
                            break;
                        case 4:
                            X = 145 + 100;
                            Y = 395 + 0;
                            break;
                        case 5:
                            X = 145 + 100;
                            Y = 395 + 100;
                            break;
                        case 6:
                            X = 145 + 0;
                            Y = 395 + 100;
                            break;
                        case 7:
                            X = 145 - 100;
                            Y = 395 + 100;
                            break;
                        case 8:
                            X = 145 - 100;
                            Y = 395 + 0;
                            break;
                        default:
                            X = 0;
                            Y = 0;
                    }
                    g.fillRect(50, 300, 200, 200);
                    g.setColor(Color.YELLOW);
                    g.fillOval(X, Y, 10, 10);
                    g.drawLine(150, 400, X + 5, Y + 5);
                    g.setColor(Color.BLACK);
                }
                if(true) {
                    g.fillRect(175, 550, 200, 200);
                    g.setColor(Color.YELLOW);
                    g.fillOval(270 + (int) (LogitechController.getXValue() * 100), 645 + (int) (LogitechController.getYValue() * 100), 10, 10);
                    g.drawLine(275, 650, 275 + (int) (LogitechController.getXValue() * 100), 650 + (int) (LogitechController.getYValue() * 100));
                    g.setColor(Color.BLACK);

                }
                if(true) {
                    g.fillRect(425, 550, 200, 200);
                    g.setColor(Color.YELLOW);
                    g.fillOval(520 + (int) (LogitechController.getXRotation() * 100), 645 + (int) (LogitechController.getYRotation() * 100), 10, 10);
                    g.drawLine(525, 650, 525 + (int) (LogitechController.getXRotation() * 100), 650 + (int) (LogitechController.getYRotation() * 100));
                    g.setColor(Color.BLACK);
                }
                for(int i = 0; i < 10; i++){
                    if(true) {
                        if (LogitechController.getButton(i)) {
                            g.setColor(Color.GREEN);
                        } else {
                            g.setColor(Color.BLUE);
                        }
                        switch (i) {
                            case 0:
                                X = 620;
                                Y = 440;
                                SIZE1 = 60;
                                SIZE2 = 60;
                                break;
                            case 1:
                                X = 690;
                                Y = 370;
                                SIZE1 = 60;
                                SIZE2 = 60;
                                break;
                            case 2:
                                X = 550;
                                Y = 370;
                                SIZE1 = 60;
                                SIZE2 = 60;
                                break;
                            case 3:
                                X = 620;
                                Y = 300;
                                SIZE1 = 60;
                                SIZE2 = 60;
                                break;
                            case 4:
                                X = 50;
                                Y = 250;
                                SIZE1 = 200;
                                SIZE2 = 30;
                                break;
                            case 5:
                                X = 550;
                                Y = 250;
                                SIZE1 = 200;
                                SIZE2 = 30;
                                break;
                            case 6:
                                X = 300;
                                Y = 350;
                                SIZE1 = 80;
                                SIZE2 = 100;
                                break;
                            case 7:
                                X = 420;
                                Y = 350;
                                SIZE1 = 80;
                                SIZE2 = 100;
                                break;
                            case 8:
                                X = 50;
                                Y = 550;
                                SIZE1 = 75;
                                SIZE2 = 100;
                                break;
                            case 9:
                                X = 675;
                                Y = 550;
                                SIZE1 = 75;
                                SIZE2 = 100;
                                break;
                            default:
                                X = 0;
                                Y = 0;
                                SIZE1 = 0;
                                SIZE2 = 0;
                        }
                        g.fillRect(X, Y, SIZE1, SIZE2);
                        g.setColor(Color.BLACK);
                        g.drawRect(X, Y, SIZE1, SIZE2);
                    }
                }
            }
        }

        public void Refresh(){
            int X, Y, SIZE1, SIZE2;

            for(int i = 0; i < 16; i++){
                if(LogitechController.updated[i]){
                    switch(i){
                        case 0:
                            X = 175;
                            Y= 550;
                            SIZE1= 200;
                            SIZE2= 200;
                            break;
                        case 1:
                            X = 175;
                            Y= 550;
                            SIZE1= 200;
                            SIZE2= 200;
                            break;
                        case 2:
                            X = 425;
                            Y= 550;
                            SIZE1= 200;
                            SIZE2= 200;
                            break;
                        case 3:
                            X = 425;
                            Y= 550;
                            SIZE1= 200;
                            SIZE2= 200;
                            break;
                        case 4:
                            X = 300;
                            Y= 250;
                            SIZE1= 200;
                            SIZE2= 50;
                            break;
                        case 5:
                            X = 620;
                            Y = 440;
                            SIZE1 = 60;
                            SIZE2 = 60;
                            break;
                        case 6:
                            X = 690;
                            Y = 370;
                            SIZE1 = 60;
                            SIZE2 = 60;
                            break;
                        case 7:
                            X = 550;
                            Y = 370;
                            SIZE1 = 60;
                            SIZE2 = 60;
                            break;
                        case 8:
                            X = 620;
                            Y = 300;
                            SIZE1 = 60;
                            SIZE2 = 60;
                            break;
                        case 9:
                            X = 50;
                            Y = 250;
                            SIZE1 = 200;
                            SIZE2 = 30;
                            break;
                        case 10:
                            X = 550;
                            Y = 250;
                            SIZE1 = 200;
                            SIZE2 = 30;
                            break;
                        case 11:
                            X = 300;
                            Y = 350;
                            SIZE1 = 80;
                            SIZE2 = 100;
                            break;
                        case 12:
                            X = 420;
                            Y = 350;
                            SIZE1 = 80;
                            SIZE2 = 100;
                            break;
                        case 13:
                            X = 50;
                            Y = 550;
                            SIZE1 = 75;
                            SIZE2 = 100;
                            break;
                        case 14:
                            X = 675;
                            Y = 550;
                            SIZE1 = 75;
                            SIZE2 = 100;
                            break;
                        case 15:
                            X = 50;
                            Y= 300;
                            SIZE1= 200;
                            SIZE2= 200;
                            break;
                        default: X = 0; Y = 0; SIZE1 = 0; SIZE2 = 0;
                    }
                    repaint(X-5,Y-5,SIZE1+10,SIZE2+10);
                }
            }
        }
    }
    /**
     * Create the frame.
     */
    public MainInterfaceFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 25, 1500, 900);
        contentPane = new CustomPanel();
        setContentPane(contentPane);

        getContentPane().setLayout(null);

        JLabel lblChooseSerialPort = new JLabel("Choose Serial Port");
        lblChooseSerialPort.setName("lblChooseSerialPort");
        lblChooseSerialPort.setBounds(new Rectangle(50, 50, 130, 20));
        contentPane.add(lblChooseSerialPort, BorderLayout.CENTER);

        JComboBox serialComboBox = new JComboBox();
        serialComboBox.setBounds(new Rectangle(50, 80, 130, 30));
        serialComboBox.setName("serialComboBox");
        contentPane.add(serialComboBox, BorderLayout.CENTER);

        JButton btnSerialRefresh = new JButton("Refresh");
        btnSerialRefresh.setBounds(new Rectangle(200, 50, 100, 40));
        btnSerialRefresh.setName("btnSerialRefresh");
        btnSerialRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialRefreshClicked();}});
        contentPane.add(btnSerialRefresh, BorderLayout.CENTER);

        JButton btnSerialConnect = new JButton("Connect");
        btnSerialConnect.setBounds(new Rectangle(200, 100, 100, 40));
        btnSerialConnect.setName("btnSerialConnect");
        btnSerialConnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialConnectClicked();}});
        contentPane.add(btnSerialConnect, BorderLayout.CENTER);

        JButton btnSerialDisconnect = new JButton("Disconnect");
        btnSerialDisconnect.setBounds(new Rectangle(200, 150, 100, 40));
        btnSerialDisconnect.setName("btnSerialDisconnect");
        btnSerialDisconnect.setVisible(false);
        btnSerialDisconnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialDisconnectClicked();}});
        contentPane.add(btnSerialDisconnect, BorderLayout.CENTER);

        JLabel lblSerialSent = new JLabel("Sent Serial Messages");
        lblSerialSent.setName("lblSerialSent");
        lblSerialSent.setBounds(new Rectangle(625, 50, 130, 20));
        contentPane.add(lblSerialSent, BorderLayout.CENTER);

        JList<String> listSerialSent = new JList<>(modelSerialSent);
        JScrollPane scrollPaneSerialSent = new JScrollPane();
        scrollPaneSerialSent.setViewportView(listSerialSent);
        scrollPaneSerialSent.setName("scrollPaneSerialSent");
        scrollPaneSerialSent.setBounds(new Rectangle(625, 80, 175, 150));
        contentPane.add(scrollPaneSerialSent, BorderLayout.CENTER);

        JLabel lblSerialReceived = new JLabel("Received Serial Messages");
        lblSerialReceived.setName("lblSerialReceived");
        lblSerialReceived.setBounds(new Rectangle(825, 50, 170, 20));
        contentPane.add(lblSerialReceived, BorderLayout.CENTER);

        JList<String> listSerialReceived = new JList<>(modelSerialReceived);
        JScrollPane scrollPaneSerialReceived = new JScrollPane();
        scrollPaneSerialReceived.setViewportView(listSerialReceived);
        scrollPaneSerialReceived.setName("scrollPaneSerialReceived");
        scrollPaneSerialReceived.setBounds(new Rectangle(825, 80, 175, 150));
        contentPane.add(scrollPaneSerialReceived, BorderLayout.CENTER);

		JLabel lblChooseController = new JLabel("Choose Controller");
		lblChooseController.setName("lblChooseController");
		lblChooseController.setBounds(new Rectangle(350, 50, 130, 20));
		contentPane.add(lblChooseController, BorderLayout.CENTER);
        /*
        JLabel lblControllerDetails = new JLabel("No Controller Selected");
        lblControllerDetails.setName("lblControllerDetails");
        lblControllerDetails.setBounds(new Rectangle(750, 50, 1300, 800));
        contentPane.add(lblControllerDetails, BorderLayout.CENTER);*/

		JComboBox ControllerComboBox = new JComboBox();
		ControllerComboBox.setBounds(new Rectangle(350, 80, 130, 30));
		ControllerComboBox.setName("ControllerComboBox");
		ControllerComboBox.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.ControllerComboBoxSelection();}});
		contentPane.add(ControllerComboBox, BorderLayout.CENTER);

		JButton btnControllerRefresh = new JButton("Refresh");
		btnControllerRefresh.setBounds(new Rectangle(500, 50, 100, 40));
		btnControllerRefresh.setName("btnControllerRefresh");
		btnControllerRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnControllerRefreshClicked();}});
		contentPane.add(btnControllerRefresh, BorderLayout.CENTER);

		JButton btnControllerConnect = new JButton("Connect");
		btnControllerConnect.setBounds(new Rectangle(500, 100, 100, 40));
		btnControllerConnect.setName("btnControllerConnect");
		btnControllerConnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnController1ConnectClicked();}});
		contentPane.add(btnControllerConnect, BorderLayout.CENTER);

		JButton btnControllerDisconnect = new JButton("Disconnect");
		btnControllerDisconnect.setBounds(new Rectangle(500, 150, 100, 40));
		btnControllerDisconnect.setName("btnControllerDisconnect");
		btnControllerDisconnect.setVisible(false);
		btnControllerDisconnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnController1DisconnectClicked();}});
		contentPane.add(btnControllerDisconnect, BorderLayout.CENTER);

		final JTextField txtManualSerialSend = new JTextField("");
		txtManualSerialSend.setBounds(new Rectangle(50,200,200,30));
		txtManualSerialSend.setName("txtManualSerialSend");
		contentPane.add(txtManualSerialSend,BorderLayout.CENTER);

        JButton btnManualSerialSend = new JButton("Send");
        btnManualSerialSend.setBounds(new Rectangle(270, 200, 100, 30));
        btnManualSerialSend.setName("btnManualSerialSend");
        btnManualSerialSend.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.PortSender(txtManualSerialSend.getText().trim());}});
        contentPane.add(btnManualSerialSend, BorderLayout.CENTER);

        ControllerRefreshTimer = new Timer();
        ControllerRefreshTimer.scheduleAtFixedRate(timerTask,100,50);

        createComponentMap();
    }

    public static void scrollDown(){
        Component SomeComponent = getComponentByName("scrollPaneSerialReceived");
        Component SomeComponent2 = getComponentByName("scrollPaneSerialSent");
        /*if(SomeComponent instanceof JScrollPane && SomeComponent2 instanceof  JScrollPane){
            JScrollPane ScrollPaneReceived = (JScrollPane) SomeComponent;
            JScrollPane ScrollPaneSent = (JScrollPane) SomeComponent2;
            ScrollPaneReceived.getVerticalScrollBar().setValue(ScrollPaneReceived.getVerticalScrollBar().getMaximum());
            ScrollPaneSent.getVerticalScrollBar().setValue(ScrollPaneSent.getVerticalScrollBar().getMaximum());
        }*/
    }

    public void createComponentMap() {
        componentMap = new HashMap<String,Component>();
        Component[] components = getContentPane().getComponents();
        for (int i=0; i < components.length; i++) {
            componentMap.put(components[i].getName(), components[i]);
            System.out.println(components[i].getName());
        }
    }

    public static Component getComponentByName(String name) {
        if (componentMap.containsKey(name)) {
            return (Component) componentMap.get(name);
        }
        else return null;
    }

    public static String getSelectedPort(){
        Component SomeComponent = getComponentByName("serialComboBox");
        if(SomeComponent instanceof JComboBox){
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            return (String) SomeComboBox.getSelectedItem();
        }
        else return null;
    }

    public static void addSerialSent(String obj){
        modelSerialSent.addElement(obj);
        if(modelSerialSent.getSize() > 25){
            modelSerialSent.removeRange(0,13);
        }
    }

    public static void addSerialReceived(String obj) {
        modelSerialReceived.addElement(obj);
        if(modelSerialReceived.getSize() > 25) {
            modelSerialReceived.removeRange(0,13);
        }
    }

    public void UpdateArduino(){
        if(SerialCommunication.isOpen() && LogitechController.getController1Connected()) {
            timerCounter++;
            if (timerCounter >=100) {
                timerCounter = 0;
                SerialCommunication.PortSender("0");
            }
            if(LogitechController.updated[0]||LogitechController.updated[1])  {
                //updated joystick left
                //assume fr and fl motors face to front, br and bl motors to the back
                float x = LogitechController.getXValue();
                float y = LogitechController.getYValue();
                AdjFL(255);
                /*
                double angle = Math.atan(y/x);
                if(x < 0){
                    angle += Math.PI;
                }else if(y < 0){
                    angle += Math.PI * 2;
                }
                double power;
                if(angle > Math.PI /8 && angle < 3*Math.PI / 8){
                    //diag fr
                    power = Math.sqrt(x*x+y*y)/Math.sqrt(2);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL((int)((power+1)*127)+1);
                    AdjFR(128);
                    AdjBL(128);
                    AdjBR((int)((power-1)*-127)+1);
                }else if(angle > 3*Math.PI /8 && angle < 5*Math.PI / 8){
                    //front
                    power = Math.abs(y);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL((int)((power+1)*127)+1);
                    AdjFR((int)((power+1)*127)+1);
                    AdjBL((int)((power-1)*-127)+1);
                    AdjBR((int)((power-1)*-127)+1);
                }else if(angle > 5*Math.PI /8 && angle < 7*Math.PI / 8){
                    //diag fl
                    power = Math.sqrt(x*x+y*y)/Math.sqrt(2);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL(128);
                    AdjFR((int)((power+1)*127)+1);
                    AdjBL((int)((power-1)*-127)+1);
                    AdjBR(128);
                }else if(angle > 7*Math.PI /8 && angle < 9*Math.PI / 8){
                    //left
                    power = Math.abs(x);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL((int)((power-1)*-127)+1);
                    AdjFR((int)((power+1)*127)+1);
                    AdjBL((int)((power-1)*-127)+1);
                    AdjBR((int)((power+1)*127)+1);
                }else if(angle > 9*Math.PI /8 && angle < 11*Math.PI / 8){
                    //diag bl
                    power = Math.sqrt(x*x+y*y)/Math.sqrt(2);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL((int)((power-1)*-127)+1);
                    AdjFR(128);
                    AdjBL(128);
                    AdjBR((int)((power+1)*127)+1);
                }else if(angle > 11*Math.PI /8 && angle < 13*Math.PI / 8){
                    //back
                    power = Math.abs(y);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL((int)((power-1)*-127)+1);
                    AdjFR((int)((power-1)*-127)+1);
                    AdjBL((int)((power+1)*127)+1);
                    AdjBR((int)((power+1)*127)+1);
                }else if(angle > 13*Math.PI /8 && angle < 15*Math.PI / 8){
                    //diag br
                    power = Math.sqrt(x*x+y*y)/Math.sqrt(2);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL(128);
                    AdjFR((int)((power-1)*-127)+1);
                    AdjBL((int)((power+1)*127)+1);
                    AdjBR(128);
                }else{
                    //right
                    power = Math.abs(x);
                    if(power > 1){
                        power = 1;
                    }
                    AdjFL((int)((power+1)*127)+1);
                    AdjFR((int)((power-1)*-127)+1);
                    AdjBL((int)((power+1)*127)+1);
                    AdjBR((int)((power-1)*-127)+1);
                }*/
            }
            if (LogitechController.updated[2]) {
                //updated joystick right y axis
                AdjVL((int)((LogitechController.getYRotation()-1)*-127)+1);
                AdjVR((int)((LogitechController.getYRotation()-1)*-127)+1);
            }
            if (LogitechController.updated[5]) {
                if(LogitechController.getButton(0)) {
                    AdjPumpSpeed(180);
                } else {
                    AdjPumpSpeed(0);
                }
            }
            if (LogitechController.updated[15]) {
                //updated D-Pad
                if(LogitechController.getDPadLeft()){
                    AdjGripperRotation(60);
                } else if (LogitechController.getDPadRight()){
                    AdjGripperRotation(120);
                } else {
                    AdjGripperRotation(90);
                }
                if (LogitechController.getDPadUp()) {
                    AdjGripperClamp(17);
                } else if (LogitechController.getDPadDown()) {
                    AdjGripperClamp(13);
                } else {
                    AdjGripperClamp(15);
                }
            }
        }
        if(LogitechController.getController1Connected()) {
            for (int i = 0; i < 16; i++) {
                LogitechController.updated[i] = false;
            }
        }
    }

    // Adjusters that go to the serial code
    public boolean AdjPumpSpeed(int pos){
        return SendCommand("4",pos);
    }
    public boolean AdjGripperRotation(int pos){
        return SendCommand("3",pos);
    }
    public boolean AdjGripperClamp(int pos){
        return SendCommand("2",pos);
    }
    public boolean AdjFL(int power){ //adjust the power of the front left motor
        return SendCommand("11",power);
    }
    public boolean AdjFR(int power){ //adjust the power of the front right motor
        return SendCommand("12",power);
    }
    public boolean AdjBL(int power){ //adjust the power of the back left motor
        return SendCommand("13",power);
    }
    public boolean AdjBR(int power){ //adjust the power of the back right motor
        return SendCommand("14",power);
    }
    public boolean AdjVL(int power){ //adjust the power of the vertical left motor
        return SendCommand("15",power);
    }
    public boolean AdjVR(int power){ //adjust the power of the vertical right motor
        return SendCommand("16",power);
    }
    public boolean SendCommand(String identifier, int command){
        return SerialCommunication.PortSender(identifier+Integer.toString(Integer.toString(command).length())+Integer.toString(command));
    }

}