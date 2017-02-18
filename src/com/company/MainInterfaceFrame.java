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
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            //Invoke your function here
            LogitechController.UpdateControllerComponents();
            contentPane.Refresh();
            UpdateArduino();
            for(int i = 0; i < 16; i++){
                LogitechController.setUpdated(i,false);
            }
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
                if(LogitechController.getUpdated(4)) {
                    g.setColor(Color.BLUE);
                    int change = (int) (LogitechController.getZAxis() * 100);
                    change *= -1;
                    if (change > 0) {
                        g.fillRect(400, 200, change, 50);
                    } else {
                        g.fillRect(400 + change, 200, change * -1, 50);
                        //System.out.println(change);
                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(300,200, 200, 50);
                }
                int X, Y, SIZE1, SIZE2;
                if(LogitechController.getUpdated(15)) {
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
                if(LogitechController.getUpdated(0) || LogitechController.getUpdated(1)) {
                    g.fillRect(175, 550, 200, 200);
                    g.setColor(Color.YELLOW);
                    g.fillOval(270 + (int) (LogitechController.getXValue() * 100), 645 + (int) (LogitechController.getYValue() * 100), 10, 10);
                    g.drawLine(275, 650, 275 + (int) (LogitechController.getXValue() * 100), 650 + (int) (LogitechController.getYValue() * 100));
                    g.setColor(Color.BLACK);
                }
                if(LogitechController.getUpdated(2)|| LogitechController.getUpdated(3)) {
                    g.fillRect(425, 550, 200, 200);
                    g.setColor(Color.YELLOW);
                    g.fillOval(520 + (int) (LogitechController.getXRotation() * 100), 645 + (int) (LogitechController.getYRotation() * 100), 10, 10);
                    g.drawLine(525, 650, 525 + (int) (LogitechController.getXRotation() * 100), 650 + (int) (LogitechController.getYRotation() * 100));
                    g.setColor(Color.BLACK);
                }
                for(int i = 0; i < 10; i++){
                    if(LogitechController.getUpdated(i+5)) {
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
            repaint();
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
        listSerialSent.setBounds(new Rectangle(625, 80, 175, 150));
        listSerialSent.setName("listSerialSent");
        contentPane.add(listSerialSent, BorderLayout.CENTER);

        JLabel lblSerialReceived = new JLabel("Received Serial Messages");
        lblSerialReceived.setName("lblSerialReceived");
        lblSerialReceived.setBounds(new Rectangle(825, 50, 170, 20));
        contentPane.add(lblSerialReceived, BorderLayout.CENTER);

        JList<String> listSerialReceived = new JList<>(modelSerialReceived);
        listSerialReceived.setBounds(new Rectangle(825, 80, 175, 150));
        listSerialReceived.setName("listSerialReceived");
        contentPane.add(listSerialReceived, BorderLayout.CENTER);

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
		btnControllerConnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnControllerConnectClicked();}});
		contentPane.add(btnControllerConnect, BorderLayout.CENTER);

		JButton btnControllerDisconnect = new JButton("Disconnect");
		btnControllerDisconnect.setBounds(new Rectangle(500, 150, 100, 40));
		btnControllerDisconnect.setName("btnControllerDisconnect");
		btnControllerDisconnect.setVisible(false);
		btnControllerDisconnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnControllerDisconnectClicked();}});
		contentPane.add(btnControllerDisconnect, BorderLayout.CENTER);

        ControllerRefreshTimer = new Timer();
        ControllerRefreshTimer.scheduleAtFixedRate(timerTask,25,25);

        createComponentMap();
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
    }

    public static void addSerialReceived(String obj){
        modelSerialReceived.addElement(obj);
    }

    public void UpdateArduino(){
        if(SerialCommunication.isOpen()){

        }
    }

    public boolean AdjFL(int power) //adjust the power of the front left motor
    {
        String command;
        String powerstr;
        powerstr = Integer.toString(power);
        command = "1" + powerstr.length();//selects motor and details length of command
        command+= powerstr;
        return SerialCommunication.PortSender(command);
    }
    public boolean AdjFR(int power) //adjust the power of the front right motor
    {
        String command;
        String powerstr;
        powerstr = Integer.toString(power);
        command = "2" + powerstr.length();//selects motor and details length of command
        command+= powerstr;
        return SerialCommunication.PortSender(command);
    }
    public boolean AdjBL(int power) //adjust the power of the back left motor
    {
        String command;
        String powerstr;
        powerstr = Integer.toString(power);
        command = "3" + powerstr.length();//selects motor and details length of command
        command+= powerstr;
        return SerialCommunication.PortSender(command);
    }
    public boolean AdjBR(int power) //adjust the power of the back right motor
    {
        String command;
        String powerstr;
        powerstr = Integer.toString(power);
        command = "4" + powerstr.length();//selects motor and details length of command
        command+= powerstr;
        return SerialCommunication.PortSender(command);
    }
    public boolean AdjVL(int power) //adjust the power of the vertical left motor
    {
        String command;
        String powerstr;
        powerstr = Integer.toString(power);
        command = "5" + powerstr.length();//selects motor and details length of command
        command+= powerstr;
        return SerialCommunication.PortSender(command);
    }
    public boolean AdjVR(int power) //adjust the power of the vertical right motor
    {
        String command;
        String powerstr;
        powerstr = Integer.toString(power);
        command = "6" + powerstr.length();//selects motor and details length of command
        command+= powerstr;
        return SerialCommunication.PortSender(command);
    }

}