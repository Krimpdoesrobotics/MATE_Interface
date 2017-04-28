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
    private static int[] multipliers = {1,1,1,1,1,1,1,1};
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

    public byte fromDouble(double a, int index){
        int b = (int)(a*65*multipliers[index]+90); // WARNING : Do not set a*65 to a*90 or more or it will break serial
        if(b>=128){
            b = -1*(256-b);
            return ((byte) b);
        }else{
            return ((byte)b);
        }
    }

    public void sendRobotInfo(){
        byte[] stuff = new byte[8];
        /*int remain = (getTimeSinceLastUpdate()/10) % 6;
        switch(remain){
            case 0: stuff[0] = fromDouble(0.5); break;
            case 1: stuff[0] = fromDouble(0.5);break;
            case 2: stuff[0] = fromDouble(0.5);break;
            case 3: stuff[0] = fromDouble(0.5);break;
            case 4: stuff[0] = fromDouble(0.5);break;
            case 5: stuff[0] = fromDouble(0.5);break;
        }*/
        for(int i = 0; i < 6; i++)
            stuff[i] = fromDouble(ControllerRobot.getMotorSpeed(i).getDouble(),i);
        stuff[6]=fromDouble(ControllerRobot.getGripperRotation().getDouble(),6);
        stuff[7]=fromDouble(ControllerRobot.getGripperClamp().getDouble(),7);
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
        try   {
            portSelected = MainInterfaceFrame.getSelectedPort();
            serialPort = new SerialPort(portSelected);
            serialPort.openPort();
            Opened = true;
            serialPort.setParams(SerialPort.BAUDRATE_19200,
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
        try {
            serialPort.closePort();
            Opened=false;
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen(){return Opened;}

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

    private static class PortReader implements SerialPortEventListener {
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 8) {
                try {
                    String toDisplay = "O:";
                    for(int j = 0; j < 8; j++){
                        byte a[] = serialPort.readBytes(1);
                        int temp = (int)(a[0]);
                        if(temp < 0){
                            temp = 256+temp;
                        }
                        toDisplay+= " ";
                        toDisplay += String.valueOf(temp);
                        if(j == 6){
                            Robot.setGripperRotation((double)(multipliers[6]*(temp-90))/90);
                        }else if(j == 7){
                            Robot.setGripperClamp((double)(multipliers[7]*(temp-90))/90);
                        }else{
                            Robot.setMotorSpeed(j,(double)(multipliers[j]*(temp-90))/90);
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
