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
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            //Invoke your function here
            LogitechController.UpdateControllerComponents();
            contentPane.Refresh();
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
            g.drawRect(150,550,200,200);
            g.drawRect(450,550,200,200);
            if(LogitechController.getController1Connected()){
                g.drawOval(245+(int)(LogitechController.getXValue()*100),645 +(int)(LogitechController.getYValue()*100),10,10);
                g.drawOval(545+(int)(LogitechController.getXRotation()*100),645 +(int)(LogitechController.getYRotation()*100),10,10);
            }
            else
            {
                g.drawOval(245,645,10,10);
                g.drawOval(545,645,10,10);
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

		JLabel lblChooseController = new JLabel("Choose Controller");
		lblChooseController.setName("lblChooseController");
		lblChooseController.setBounds(new Rectangle(450, 50, 130, 20));
		contentPane.add(lblChooseController, BorderLayout.CENTER);

        JLabel lblControllerDetails = new JLabel("No Controller Selected");
        lblControllerDetails.setName("lblControllerDetails");
        lblControllerDetails.setBounds(new Rectangle(750, 50, 1300, 800));
        contentPane.add(lblControllerDetails, BorderLayout.CENTER);

		JComboBox ControllerComboBox = new JComboBox();
		ControllerComboBox.setBounds(new Rectangle(450, 80, 130, 30));
		ControllerComboBox.setName("ControllerComboBox");
		ControllerComboBox.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.ControllerComboBoxSelection();}});
		contentPane.add(ControllerComboBox, BorderLayout.CENTER);

		JButton btnControllerRefresh = new JButton("Refresh");
		btnControllerRefresh.setBounds(new Rectangle(600, 50, 100, 40));
		btnControllerRefresh.setName("btnControllerRefresh");
		btnControllerRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnControllerRefreshClicked();}});
		contentPane.add(btnControllerRefresh, BorderLayout.CENTER);

		JButton btnControllerConnect = new JButton("Connect");
		btnControllerConnect.setBounds(new Rectangle(600, 100, 100, 40));
		btnControllerConnect.setName("btnControllerConnect");
		btnControllerConnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnControllerConnectClicked();}});
		contentPane.add(btnControllerConnect, BorderLayout.CENTER);

		JButton btnControllerDisconnect = new JButton("Disconnect");
		btnControllerDisconnect.setBounds(new Rectangle(600, 150, 100, 40));
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