package com.company;

/**
 * Created by Richard on 2/17/2017.
 */

import jssc.*;
import sun.applet.Main;

import java.awt.*;
import javax.swing.JComboBox;

public class SerialCommunications {
    // instance variables
    // private data
    private String[] portNames;
    private String portSelected;
    private SerialPort serialPort;
    private boolean Opened;
    // constructor
    public SerialCommunications() {
        // default constructor

    }

    // refresh
    public void btnSerialRefreshClicked() {
        //
        // refreshes the port so if the port changes, it will update.
        //
        refreshPortList();

    }
    private void refreshPortList() {
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
        try {
            portSelected = MainInterfaceFrame.getSelectedPort();
            serialPort = new SerialPort(portSelected);
            Opened = true;

            serialPort.openPort();

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
    public void btnSerialDisconnectClicked() {
        MainInterfaceFrame.getComponentByName("btnSerialDisconnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnSerialConnect").setVisible(true);
        try {
            serialPort.closePort();
            Opened=false;

        } catch (SerialPortException e) {
            e.printStackTrace();

        }

    }

    public boolean isOpen(){
        return Opened;
    }

    // outputs to the port
    public boolean PortSender(String command) {
        try{
            serialPort.writeString(command);
            MainInterfaceFrame.addSerialSent(command);
        } catch(SerialPortException ex) {
            System.out.println("Error sending string: " + ex);
            return false;

        }
        return true;

    }

    // class that is a port listener
    public class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    MainInterfaceFrame.addSerialReceived(receivedData);

                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);

                }
            }
        }
    }

}
