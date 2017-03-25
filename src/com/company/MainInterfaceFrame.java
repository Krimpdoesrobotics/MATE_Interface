package com.company;

/**
 * Created by Richard on 2/17/2017.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import static com.company.RandomStuff.IntH.newIntH;
import static com.company.RandomStuff.DoubleH.newDoubleH;
import static com.company.RandomStuff.BooleanH.newBooleanH;

public class MainInterfaceFrame extends JFrame
{
    private final int NumGraphics = 22;
    private final double pi = 3.14159;
    private double gripperRotation = 0.0;
    private double gripperClamp = 0.0;
    private final double reverseEfficencyHandicap = 1;
    private static HashMap componentMap;
    private SerialCommunications SerialCommunication = new SerialCommunications();
    private CustomPanel contentPane;
    private ControllerInput LogitechController = new ControllerInput();
    private Timer ControllerRefreshTimer;
    private static DefaultListModel<String> modelSerialSent = new DefaultListModel<>();
    private static DefaultListModel<String> modelSerialReceived = new DefaultListModel<>();
    private int timerCounter = 0;
    private int powerScaling = 1;   // this is from .1 to 1, and acts as a multiplier for power
    private boolean isControllerOne = false;
    private boolean isControllerTwo = false;
    private boolean newTelemetryReceived =false;
    private static double motorSpeeds[] = new double[6];
    private static double CameraServoTilt = 0;
    private static double CameraServoPan = 0;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            //
            // Invoke your function here
            //
            LogitechController.UpdateController1Components();
            contentPane.Refresh();
            UpdateArduino();
            if(LogitechController.getController1Connected()){LogitechController.resetUpdated();}
        }
    };
    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        for(int i = 0; i < 6; i++){
            motorSpeeds[i] = 0;
        }

        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    MainInterfaceFrame frame = new MainInterfaceFrame();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Create the frame.
     */
    public MainInterfaceFrame()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 25, 1500, 900);
        contentPane = new CustomPanel(NumGraphics);

        contentPane.InterfaceElements[0] = new Paintings(175,550,200,200,0,Color.BLACK,newBooleanH(LogitechController.getLeftAnalogUpdated()));
        contentPane.InterfaceElements[0].setReferenceType0(newDoubleH(LogitechController.getXValue()),newDoubleH(LogitechController.getYValue()),Color.BLACK,Color.CYAN);
        contentPane.InterfaceElements[1] = new Paintings(425,550,200,200,0,Color.BLACK,newBooleanH(LogitechController.getRightAnalogUpdated()));
        contentPane.InterfaceElements[1].setReferenceType0(newDoubleH(LogitechController.getXRotation()),newDoubleH(LogitechController.getYRotation()),Color.BLACK,Color.CYAN);
        contentPane.InterfaceElements[2] = new Paintings(300,250,200,50,3,Color.BLACK,newBooleanH(LogitechController.getUpdated(4)));
        contentPane.InterfaceElements[2].setReferenceType34(newDoubleH(LogitechController.getZAxis()),Color.WHITE,Color.BLUE);
        contentPane.InterfaceElements[3] = new Paintings(620,440,60,60,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(5)));
        contentPane.InterfaceElements[3].setReferenceType2(newBooleanH(LogitechController.getButton(0)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[4] = new Paintings(690,370,60,60,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(6)));
        contentPane.InterfaceElements[4].setReferenceType2(newBooleanH(LogitechController.getButton(1)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[5] = new Paintings(550,370,60,60,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(7)));
        contentPane.InterfaceElements[5].setReferenceType2(newBooleanH(LogitechController.getButton(2)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[6] = new Paintings(620,300,60,60,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(8)));
        contentPane.InterfaceElements[6].setReferenceType2(newBooleanH(LogitechController.getButton(3)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[7] = new Paintings(50,250,200,30,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(9)));
        contentPane.InterfaceElements[7].setReferenceType2(newBooleanH(LogitechController.getButton(4)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[8] = new Paintings(550,250,200,30,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(10)));
        contentPane.InterfaceElements[8].setReferenceType2(newBooleanH(LogitechController.getButton(5)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[9] = new Paintings(300,350,80,100,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(11)));
        contentPane.InterfaceElements[9].setReferenceType2(newBooleanH(LogitechController.getButton(6)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[10] = new Paintings(420,350,80,100,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(12)));
        contentPane.InterfaceElements[10].setReferenceType2(newBooleanH(LogitechController.getButton(7)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[11] = new Paintings(50,550,75,100,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(13)));
        contentPane.InterfaceElements[11].setReferenceType2(newBooleanH(LogitechController.getButton(8)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[12] = new Paintings(675,550,75,100,2,Color.BLACK,newBooleanH(LogitechController.getUpdated(14)));
        contentPane.InterfaceElements[12].setReferenceType2(newBooleanH(LogitechController.getButton(9)),Color.RED, Color.GREEN);
        contentPane.InterfaceElements[13] = new Paintings(50,300,200,200,1,Color.BLACK,newBooleanH(LogitechController.getUpdated(15)));
        contentPane.InterfaceElements[13].setReferenceType1(newIntH(LogitechController.getDPad()),Color.BLACK, Color.CYAN);
        contentPane.InterfaceElements[14] = new Paintings(800,300,80,200,4,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[14].setReferenceType34(newDoubleH(motorSpeeds[0]),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[15] = new Paintings(900,300,80,200,4,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[15].setReferenceType34(newDoubleH(motorSpeeds[1]),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[16] = new Paintings(1000,300,80,200,4,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[16].setReferenceType34(newDoubleH(motorSpeeds[2]),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[17] = new Paintings(1100,300,80,200,4,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[17].setReferenceType34(newDoubleH(motorSpeeds[3]),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[18] = new Paintings(1200,300,80,200,4,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[18].setReferenceType34(newDoubleH(motorSpeeds[4]),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[19] = new Paintings(1300,300,80,200,4,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[19].setReferenceType34(newDoubleH(motorSpeeds[5]),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[20] = new Paintings(800,550,200,30,3,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[20].setReferenceType34(newDoubleH(CameraServoTilt),Color.CYAN,Color.BLACK);
        contentPane.InterfaceElements[21] = new Paintings(1050,550,200,30,3,Color.BLACK,newBooleanH(newTelemetryReceived));
        contentPane.InterfaceElements[21].setReferenceType34(newDoubleH(CameraServoPan),Color.CYAN,Color.BLACK);

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

        JButton btnSerialSendRefresh= new JButton("Send Refresh");
        btnSerialSendRefresh.setBounds(new Rectangle(75, 150, 100, 40));
        btnSerialSendRefresh.setName("btnSerialSendRefresh");
        btnSerialSendRefresh.setVisible(false);
        btnSerialSendRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialDisconnectClicked();}});
        contentPane.add(btnSerialSendRefresh, BorderLayout.CENTER);

        JLabel lblSerialSent = new JLabel("Sent Serial Messages");
        lblSerialSent.setName("lblSerialSent");
        lblSerialSent.setBounds(new Rectangle(625, 50, 130, 20));
        contentPane.add(lblSerialSent, BorderLayout.CENTER);

        //
        JLabel lblGripperPositionName = new JLabel("Gripper's Current Position");
        lblGripperPositionName.setName("lblGripperPosName");
        lblGripperPositionName.setBounds(new Rectangle(625, 50, 130, 20));
        contentPane.add(lblGripperPositionName, BorderLayout.EAST);

        JLabel lblGripperPosition = new JLabel("");
        lblGripperPosition.setName("lblGripperPos");
        lblGripperPosition.setBounds(new Rectangle(625, 50, 130, 20));
        contentPane.add(lblGripperPosition, BorderLayout.EAST);
        //

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
        ControllerRefreshTimer.scheduleAtFixedRate(timerTask,6000,60);

        createComponentMap();
    }

    public static void scrollDown()
    {
        Component SomeComponent = getComponentByName("scrollPaneSerialReceived");
        Component SomeComponent2 = getComponentByName("scrollPaneSerialSent");
        if(SomeComponent instanceof JScrollPane && SomeComponent2 instanceof  JScrollPane){
            JScrollPane ScrollPaneReceived = (JScrollPane) SomeComponent;
            JScrollPane ScrollPaneSent = (JScrollPane) SomeComponent2;
            ScrollPaneReceived.getVerticalScrollBar().setValue(ScrollPaneReceived.getVerticalScrollBar().getMaximum());
            ScrollPaneSent.getVerticalScrollBar().setValue(ScrollPaneSent.getVerticalScrollBar().getMaximum());
        }
    }

    public void createComponentMap()
    {
        componentMap = new HashMap<String,Component>();
        Component[] components = getContentPane().getComponents();
        for (int i=0; i < components.length; i++)
        {
            componentMap.put(components[i].getName(), components[i]);
            System.out.println(components[i].getName());
        }
    }

    public static Component getComponentByName(String name)
    {
        if (componentMap.containsKey(name))
        {
            return (Component) componentMap.get(name);
        }
        else return null;
    }

    public static String getSelectedPort()
    {
        Component SomeComponent = getComponentByName("serialComboBox");
        if(SomeComponent instanceof JComboBox)
        {
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            return (String) SomeComboBox.getSelectedItem();
        }
        else return null;
    }

    public static void addSerialSent(String obj)
    {
        modelSerialSent.addElement(obj);
        if(modelSerialSent.getSize() > 25)
        {
            modelSerialSent.removeRange(0,13);
        }
    }

    public static void addSerialReceived(String obj)
    {
        modelSerialReceived.addElement(obj);
        if(modelSerialReceived.getSize() > 25)
        {
            modelSerialReceived.removeRange(0,13);
        }
        switch(obj.charAt(0))
        {
            case '0':
                //error occurred in arduino
            case '1':
                //int motornum = Character.getNumericValue(obj.charAt(1));
                //update motors
            case '2':
                //update gripper
        }
    }

    public void UpdateArduino()
    {
        if(SerialCommunication.isOpen() && LogitechController.getController1Connected())
        {
            timerCounter++;
            if (timerCounter >=100)
            {
                timerCounter = 0;
                SerialCommunication.PortSender("0");
            }
            //
            // Left Stick to move the robot forward, backward, left, or right.
            //
            if(LogitechController.getLeftAnalogUpdated())
            {
                //
                // joystick that will control lateral movement
                // This is the left controller and is controlled by 'values'
                //
                double xVal = LogitechController.getXValue();//from -1 to 1
                double yVal = LogitechController.getYValue();//from -1 to 1
                AdjFL((int)((xVal*90)+90));
                AdjFR((int)((yVal*90)+90));
                /*double rotation = Math.atan2(yVal,xVal); //radians, from -pi to pi
                double magnitude = Math.sqrt(xVal * xVal + yVal * yVal);
                int power = (int)((double)65 * magnitude * powerScaling);
                //
                // determine region (1 = forward, 2 = forward and right, 3 = right, 4 = back and right, 5 = back, 6 = back and left, 7 = left, 8 = forward and left)
                //
                int region = 0;
                if(rotation <= (5.0*pi)/8.0 && rotation >= (3.0*pi)/8.0)
                {
                    region = 1;
                }
                if(rotation <= (3.0*pi)/8.0 && rotation >= pi/8.0)
                {
                    region = 2;
                }
                if(rotation <= pi/8.0 && rotation >= -pi/8.0)
                {
                    region = 3;
                }
                if(rotation <= -pi/8.0 && rotation >= (-3.0*pi)/8.0)
                {
                    region = 4;
                }
                if(rotation <= (-3.0*pi)/8.0 && rotation >= (-5.0*pi)/8.0)
                {
                    region = 5;
                }
                if(rotation <= (-5.0*pi)/8.0 && rotation >= (-7.0*pi)/8.0)
                {
                    region = 6;
                }
                if(rotation <= (-7.0*pi)/8.0 || rotation >= (7.0*pi)/8.0)
                {
                    region = 7;
                }
                if(rotation <= (7.0*pi)/8.0 && rotation >= (5.0*pi)/8.0)
                {
                    region = 8;
                }
                switch(region)
                {
                    case 1:
                    {
                        //forward
                        AdjFL(power);
                        AdjFR(power);
                        AdjBL((-1 * power) + 90);
                        AdjBR((-1 * power) + 90);
                        break;
                    }
                    case 2:
                    {
                        //forward right
                        AdjFL(((int)((double)power * reverseEfficencyHandicap)) + 90);
                        AdjBR((-1 * power) + 90);
                        break;
                    }
                    case 3:
                    {
                        //right
                        AdjFL(power + 90);
                        AdjFR((-1 *power) + 90);
                        AdjBL(power + 90);
                        AdjBR((-1 *power) + 90);
                        break;
                    }
                    case 4:
                    {
                        //backward right
                        AdjFR((-1 * power) + 90);
                        AdjBL(((int)((double)power * reverseEfficencyHandicap)) + 90);
                        break;
                    }
                    case 5:
                    {
                        //backward
                        AdjFL((-1 * power) + 90);
                        AdjFR((-1 * power) + 90);
                        AdjBL(power + 90);
                        AdjBR(power + 90);
                        break;
                    }
                    case 6:
                    {
                        //backward left
                        AdjFL((-1 * power) + 90);
                        AdjBR(((int)((double)power * reverseEfficencyHandicap)) + 90);
                        break;
                    }
                    case 7:
                    {
                        //left
                        AdjFL((-1 * power) + 90);
                        AdjFR(power + 90);
                        AdjBL((-1 * power) + 90);
                        AdjBR(power + 90);
                        break;
                    }
                    case 8:
                    {
                        //forward left
                        AdjFR(((int)((double)power * reverseEfficencyHandicap)) + 90);
                        AdjBL((-1 * power) + 90);
                        break;
                    }
                }*/


            }
            //
            // Right Stick controls turning left n' right and up n' down
            //
            if (LogitechController.getRightAnalogUpdated())
            {
                //joystick that will contol vertical movement and turning
                double xVal = LogitechController.getXRotation();//from -1 to 1
                double yVal = LogitechController.getYRotation();//from -1 to 1
                double rotation = Math.atan2(yVal,xVal);//radians, from -pi to pi
                double magnitude = Math.sqrt(xVal*xVal + yVal * yVal);
                int power = (int)((double)65 * magnitude * powerScaling);
                //determine region (1 = up, 2 = turn right, 3 = down, 4 = turn left)
                int region = 0;
                if(rotation <= (3.0*pi)/4.0 && rotation >= pi/4.0)
                {
                    region = 1;
                }
                if(rotation <= pi/4.0 && rotation >= (-1.0*pi)/4.0)
                {
                    region = 2;
                }
                if(rotation <= (-1.0*pi)/4.0 && rotation >= (-3.0*pi)/4.0)
                {
                    region = 3;
                }
                if(rotation <= (-3.0*pi)/4.0 || rotation >= (3.0*pi)/4.0)
                {
                    region = 4;
                }
                switch(region)
                {
                    case 1:
                    {
                        //up
                        AdjVL(power + 90);
                        AdjVL(power + 90);
                        break;
                    }
                    case 2:
                    {
                        //turn right
                        AdjFL(power + 90);
                        AdjFR((-1 * power) + 90);
                        AdjBL((-1 * power) + 90);
                        AdjBR(power + 90);
                        break;
                    }
                    case 3:
                    {
                        //down
                        AdjVL((-1 * power) + 90);
                        AdjVR((-1 * power) + 90);
                        break;
                    }
                    case 4:
                    {
                        //turn left
                        AdjFL((-1 * power) + 90);
                        AdjFR(power + 90);
                        AdjBL(power + 90);
                        AdjBR((-1 * power) + 90);
                        break;
                    }
                }
            }
            //
            // D-Pad controls the Gripper control
            //
            if (LogitechController.getUpdated(15))
            {
                // updated D-Pad
                if(LogitechController.getDPadLeft())
                {
                    AdjGripperRotation(60);
                }
                else if (LogitechController.getDPadRight())
                {
                    AdjGripperRotation(120);
                }
                else
                {
                    AdjGripperRotation(90);
                }
                if (LogitechController.getDPadUp())
                {
                    AdjGripperClamp(17);
                }
                else if (LogitechController.getDPadDown())
                {
                    AdjGripperClamp(13);
                }
                else
                {
                    AdjGripperClamp(15);
                }
            }
        }
    }

    // Adjusters that go to the serial code
    public boolean AdjFlashLight(int power) { return SendCommand("10", power); }
    public boolean AdjPumpSpeed(int pos)
    {
        return SendCommand("4",pos);
    }
    public boolean AdjGripperRotation(int pos)
    {
        return SendCommand("3",pos);
    }
    public boolean AdjGripperClamp(int pos)
    {
        return SendCommand("2",pos);
    }
    public boolean AdjFL(int power)
    { //adjust the power of the front left motor
        return SendCommand("11",power);
    }
    public boolean AdjFR(int power)
    { //adjust the power of the front right motor
        return SendCommand("12",power);
    }
    public boolean AdjBL(int power)
    { //adjust the power of the back left motor
        return SendCommand("13",power);
    }
    public boolean AdjBR(int power)
    { //adjust the power of the back right motor
        return SendCommand("14",power);
    }
    public boolean AdjVL(int power)
    { //adjust the power of the vertical left motor
        return SendCommand("15",power);
    }
    public boolean AdjVR(int power)
    { //adjust the power of the vertical right motor
        return SendCommand("16",power);
    }
    public boolean SendCommand(String identifier, int command)
    {
        return SerialCommunication.PortSender(identifier+Integer.toString(Integer.toString(command).length())+Integer.toString(command));
    }

    // COMPONENTS LIST //
    // 0: Y AXIS ABSOLUTE ANALOG: Left Stick X
    // 1: X AXIS ABSOLUTE ANALOG: Left Stick Y
    // 2: X ROTATION ABSOLUTE ANALOG: Right Stick X
    // 3: Y ROTATION ABSOLUTE ANALOG: Right Stick Y
    // 4: Z AXIS ABSOLUTE ANALOG: Sum of Left Trigger(+) and Right Trigger(-)
    // 5: BUTTON 0: A Button (PUMP IN)
    // 6: BUTTON 1: B Button (PUMP OUT)
    // 7: BUTTON 2: X Button (YOU ARE DRIVER 1)
    // 8: BUTTON 3: Y Button (YOU ARE DRIVER 2)
    // 9: BUTTON 4: L Button (not working well ATM)
    // 10: BUTTON 5: R Button
    // 11: BUTTON 6: Back Button (Flash Light On/Off)
    // 12: BUTTON 7: Start Button (MAKES YOU DRIVER)
    // 13: BUTTON 8: L Stick Push Down
    // 14: BUTTON 9: R Stick Push Down
    // 15: HAT SWITCH: D Pad 0 Nothing .125 FL .25 F, .375 FR, etc..... 1.0 L
    // --------------- //

}
