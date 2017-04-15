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
    private static SerialPort serialPort;
    private boolean Opened =false;
    private static byte[] Buffer = new byte[8];
    private static int BufferState = 0;
    private String fullmessage;
    private int lastconsidered = 0;
    private static RobotInfo Robot= new RobotInfo();
    private ControllerRobotInfo ControllerRobot;
    private int currentUpdate = 0;
    private int TimeSinceLastUpdate = -1;

    // constructor
    public SerialCommunications(GamepadController controller1, GamepadController controller2)
    {
        ControllerRobot = new ControllerRobotInfo(controller1,controller2);
        // default constructor
        for(int i = 0; i < 8; i++)
            Buffer[i] = 0;
    }

    public RobotInfo getRobot(){return Robot;}

    public ControllerRobotInfo getControllerRobot(){return ControllerRobot;}

    public int getTimeSinceLastUpdate(){return TimeSinceLastUpdate;}

    public void incrementTime(){TimeSinceLastUpdate++;}

    public void sendRobotInfo(){
        byte[] stuff = new byte[8];
        for(int i = 0; i < 6; i++)
            stuff[i] = (byte)(ControllerRobot.getMotorSpeed(i).getDouble()*90+90);
        stuff[6]=(byte)(ControllerRobot.getGripperRotation().getDouble()*90+90);
        stuff[7]=(byte)(ControllerRobot.getGripperClamp().getDouble()*90+90);
        PortSend(stuff);
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
    public void btnSerialConnectClicked() {
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(true);
        //MainInterfaceFrame.getComponentByName("btnSerialSendRefresh").setVisible(true);
        try   {
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
            TimeSinceLastUpdate = 0;
        } catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port: " + ex);
        }
    }
    public void btnSerialDisconnectClicked()
    {
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(true);
        //MainInterfaceFrame.getComponentByName("btnSerialSendRefresh").setVisible(false);
        try {
            serialPort.closePort();
            Opened=false;
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    /*public void btnSerialSendRefreshClicked()
    {
        if(isOpen())
        {
            PortSender("A");
        }
    }*/

    public boolean isOpen(){return Opened;}

    // outputs to the port
    /*public boolean PortSender(String command) {
        try {
            serialPort.writeString(command);
            MainInterfaceFrame.addSerialSent(command);
        } catch(SerialPortException ex) {
            System.out.println("Error sending string: " + ex);
            return false;
        }
        return true;
    }*/

    public boolean PortSend(byte[] val){
        if(isOpen()){
            try{
                serialPort.writeBytes(val);
                String stuff ="I:";
                for(int i=0; i< 8;i++){
                    stuff += " ";
                    int temp = (int)(val[i]);
                    stuff += String.valueOf(temp);
                }
                MainInterfaceFrame.addSerialSent(stuff);
                return true;
            } catch (SerialPortException ex){
                System.out.println("Error sending byte: "+ex);
            }
        }
        return false;
    }

    private static class PortReader implements SerialPortEventListener
    {
        public void serialEvent(SerialPortEvent event)
        {
            if (event.isRXCHAR() && event.getEventValue() > 8)
            {
                try
                {
                    String toDisplay = "O:";
                    System.out.print("O:");
                    for(int j = 0; j < 8; j++){
                        byte a[] = serialPort.readBytes(1);
                        int temp = (int)(a[0]);
                        toDisplay+= " ";
                        toDisplay += String.valueOf(temp);
                        System.out.print(a[0]);
                        if(j == 6){
                            Robot.setGripperRotation((double)(a[0]/90));
                        }else if(j == 7){
                            Robot.setGripperClamp((double)(a[0]/90));
                        }else{
                            Robot.setMotorSpeed(j,(double)(a[0]/90));
                        }
                    }
                    MainInterfaceFrame.addSerialReceived(toDisplay);
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }
    }
}
