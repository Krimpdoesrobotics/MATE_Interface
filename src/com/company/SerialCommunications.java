package com.company;
/**
 * Created by Richard on 2/17/2017.
 */
import com.company.RandomStuff.BooleanH;
import jssc.*;

import java.awt.*;
import javax.swing.JComboBox;

public class SerialCommunications
{
    // instance variables
    // private data
    private String[] portNames;
    private String portSelected;
    private SerialPort serialPort;
    private boolean Opened =false;
    private String fullmessage;
    private int lastconsidered = 0;
    private RobotInfo Robot;
    private ControllerRobotInfo ControllerRobot;
    private int currentUpdate = 0;

    // constructor
    public SerialCommunications(GamepadController controller1, GamepadController controller2)
    {
        Robot = new RobotInfo();
        ControllerRobot = new ControllerRobotInfo(controller1,controller2);
        // default constructor
    }

    public RobotInfo getRobot(){return Robot;}

    public ControllerRobotInfo getControllerRobot(){return ControllerRobot;}

    public void sendRobotInfo(){
        String stuff = "";
        for(int i = 0; i < 6; i++){
            stuff+=String.valueOf((int)((ControllerRobot.getMotorSpeed(i).getDouble()*90)+90));
            stuff += ",";
        }
        stuff+=String.valueOf((int)((ControllerRobot.getGripperRotation().getDouble()*90)+90));
        stuff += ",";
        stuff+=String.valueOf((int)((ControllerRobot.getGripperClamp().getDouble()*90)+90));
        stuff += ",";
        System.out.print(stuff);
        PortSender(stuff);
    }
    // refresh
    public void btnSerialRefreshClicked()
    {
        //
        // refreshes the port so if the port changes, it will update.
        //
        refreshPortList();
    }
    private void refreshPortList()
    {
        portNames = SerialPortList.getPortNames();
        Component SomeComponent = MainInterfaceFrame.getComponentByName("serialComboBox");
        if (SomeComponent instanceof JComboBox) {
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            SomeComboBox.removeAllItems();
            for (int i = 0; i < portNames.length; i++) {
                SomeComboBox.addItem(portNames[i]);
            }
        }
    }

    // connection
    public void btnSerialConnectClicked()
    {
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(true);
        MainInterfaceFrame.getComponentByName("btnSerialSendRefresh").setVisible(true);
        try
        {
            portSelected = MainInterfaceFrame.getSelectedPort();
            serialPort = new SerialPort(portSelected);
            serialPort.openPort();
            Opened = true;
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
        } catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port: " + ex);
        }
    }
    public void btnSerialDisconnectClicked()
    {
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(true);
        MainInterfaceFrame.getComponentByName("btnSerialSendRefresh").setVisible(false);
        try {
            serialPort.closePort();
            Opened=false;
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void btnSerialSendRefreshClicked()
    {
        if(isOpen())
        {
            PortSender("A");
        }
    }
    public boolean isOpen(){
        return Opened;
    }

    // outputs to the port
    public boolean PortSender(String command)
    {
        try
        {
            serialPort.writeString(command);
            MainInterfaceFrame.addSerialSent(command);
        } catch(SerialPortException ex) {
            System.out.println("Error sending string: " + ex);
            return false;
        }
        return true;
    }

    // class that is a port listener
    public class PortReader implements SerialPortEventListener
    {
        public void serialEvent(SerialPortEvent event)
        {
            if (event.isRXCHAR() && event.getEventValue() > 0)
            {
                try
                {
                    String receivedData = serialPort.readString(event.getEventValue());
                    //System.out.println(receivedData);
                    if(receivedData.length() > 0)
                    {
                        fullmessage += receivedData;
                        System.out.println(fullmessage);
                        if (fullmessage.length() > 0)
                        {
                            int messageend, messagecontent;
                            messageend = -1;
                            //
                            // breaks up the message into the motor
                            // then length of incoming string
                            // then motor speed
                            // 8-2-20
                            //
                            while (fullmessage.length() > lastconsidered) {
                                for(int i = lastconsidered+1; i < fullmessage.length(); i++){
                                    if(fullmessage.charAt(i) == ','){
                                        messageend = i-1;
                                        break;
                                    }
                                }
                                if(messageend > -1){
                                    messagecontent = Integer.getInteger(fullmessage.substring(lastconsidered,messageend));
                                    MainInterfaceFrame.addSerialReceived(String.valueOf(messagecontent));
                                    switch(currentUpdate){
                                        case 6:
                                            Robot.setGripperRotation((((double) messagecontent)-90)/90);
                                            break;
                                        case 7:
                                            Robot.setGripperClamp((((double) messagecontent)-90)/90);
                                            currentUpdate = -1;
                                            String someString = fullmessage.substring(messageend+1);
                                            fullmessage = someString;
                                            messageend = -1;
                                            break;
                                        default:
                                            Robot.setMotorSpeed(currentUpdate,(((double) messagecontent)-90)/90);
                                            break;
                                    }
                                    lastconsidered = messageend+1;
                                    currentUpdate++;
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }
    }
}
