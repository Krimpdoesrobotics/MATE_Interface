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

    public void sendRobotInfo(){
        for(int i = 0; i < 6; i++)
            PortSend((byte)(ControllerRobot.getMotorSpeed(i).getDouble()*90));
        PortSend((byte)(ControllerRobot.getGripperRotation().getDouble()*90));
        PortSend((byte)(ControllerRobot.getGripperClamp().getDouble()*90));
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
        MainInterfaceFrame.getComponentByName("btnSerialSendRefresh").setVisible(true);
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

    public boolean PortSend(byte val){
        if(isOpen()){
            try{
                serialPort.writeByte(val);
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
            if (event.isRXCHAR() && event.getEventValue() > 0)
            {
                try
                {
                    byte temparray[] = serialPort.readBytes(event.getEventValue());
                    for(byte a: temparray){
                        if(BufferState == 8){
                            for(int i = 0; i < 6; i++)
                                Robot.setMotorSpeed(i,(double)(Buffer[i]/128));
                            Robot.setGripperRotation((double)(Buffer[6]/128));
                            Robot.setGripperClamp((double)(Buffer[7]/128));
                            Buffer[0] = a;
                            BufferState = 1;
                        }else{
                            Buffer[BufferState] = a;
                            BufferState++;
                        }
                    }
                        
                    /*String receivedData = serialPort.readString(event.getEventValue());
                    System.out.print(receivedData);
                    if(receivedData.length() > 0) {
                        fullmessage += receivedData;
                        System.out.println(fullmessage);
                        if (fullmessage.length() > 0)
                        {
                            int messageend, messagecontent;
                            messageend = -1;
                            while (fullmessage.length() > lastconsidered) {
                                for(int i = lastconsidered+1; i < fullmessage.length(); i++){
                                    if(fullmessage.charAt(i) == ','){
                                        messageend = i-1;
                                        //
                                        // breaks up the message into the motor
                                        // then length of incoming string
                                        // then motor speed
                                        // 8-2-20
                                        //
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
                    }*/
                } catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                }
            }
        }
    }
}
