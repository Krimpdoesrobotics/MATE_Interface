package com.company;

/**
 * Created by Richard on 2/17/2017.
 */

import jssc.*;
import java.awt.*;

import javax.swing.JComboBox;

public class SerialCommunications {
    public String[] portNames;
    public String portSelected;
    public SerialPort serialPort;

    //constructor
    public SerialCommunications(){
    }

    public void btnSerialRefreshClicked(){
        refreshPortList();
    }

    public void btnSerialConnectClicked(){
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(true);
        try {
            portSelected = MainInterfaceFrame.getSelectedPort();
            serialPort = new SerialPort(portSelected);

            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
        }
        catch (SerialPortException ex) {
            System.out.println("There are an error on writing string to port: " + ex);
        }
    }

    public void btnSerialDisconnectClicked(){
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(true);
        try {
            serialPort.closePort();
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public void refreshPortList(){
        portNames = SerialPortList.getPortNames();
        Component SomeComponent = MainInterfaceFrame.getComponentByName("serialComboBox");
        if(SomeComponent instanceof JComboBox){
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            SomeComboBox.removeAllItems();
            for(int i = 0; i < portNames.length; i++){
                SomeComboBox.addItem(portNames[i]);
            }
        }
    }

    public void PortSender(String command){
        //message identifiers 1-6 motor set throttle         length byte    0-full reverse 128-no thrust 255-full forward
        //                    7   gripper close open servo   length byte    0-0 degrees        180- 180 degrees
        //                    8   gripper rotation servo     length byte    0-full reverse 128 no-rotation 255-full forward
        try{
            serialPort.writeString(command);
        }
        catch(SerialPortException ex){
            System.out.println("Error sending string: "+ex);
        }
    }

    public class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    System.out.println("Received Response: " + receivedData);
                }
                catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }
    }
}
