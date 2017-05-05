package com.company;

import com.company.RandomStuff.BooleanH;
import jssc.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComboBox;

import static com.company.MainInterfaceFrame.getComponentByName;
import static com.company.MainInterfaceFrame.setVisibility;

class SerialCommunications{
    // instance variables
    // private data
    private static SerialPort serialPort;
    private boolean Opened =false;
    private static RobotInfo Robot= new RobotInfo();
    private ControllerRobotInfo ControllerRobot;
    private static int[] multipliers = {1,-1,1,-1,1,-1,1,1,1,1};
    private static BooleanH SerialReceived = new BooleanH(false);
    private static BooleanH SerialReceivedU = new BooleanH(false);
    private static int TimeSinceLastReceived = 0;
    // constructor
    SerialCommunications(GamepadController controller1, GamepadController controller2)
    {
        ControllerRobot = new ControllerRobotInfo(controller1,controller2);
        // default constructor
        Timer SerialRefreshTimer = new Timer();
        TimerTask refreshSerial = new TimerTask() {
            @Override
            public void run() {
                if(isOpen()){
                    if(ControllerRobot.getController1Connected()){
                        ControllerRobot.updateVariables();
                    }
                    sendRobotInfo();
                    if(TimeSinceLastReceived >= 3){
                        btnSerialDisconnectClicked();
                        btnSerialConnectClicked();
                    }
                    TimeSinceLastReceived++;
                }
            }
        };
        SerialRefreshTimer.schedule(refreshSerial,250);
    }

    RobotInfo getRobot(){return Robot;}

    BooleanH getSerialReceived(){return SerialReceived;}
    BooleanH getSerialReceivedU(){return SerialReceivedU;}

    void resetSerialReceived(){SerialReceived.setBoolean(false);}
    void resetSerialReceivedU(){SerialReceivedU.setBoolean(true);}

    private byte fromDouble(double a, int index){
        int b = (int)(a*63*multipliers[index]+90); // WARNING : Do not set a*65 to a*90 or more or it will break serial
        if(b>=128){
            b = -1*(256-b);
            return ((byte) b);
        }else{
            return ((byte)b);
        }
    }

    private void sendRobotInfo(){
        byte[] stuff = new byte[11];
        stuff[0] = 0;
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
            stuff[i+1] = fromDouble(ControllerRobot.getMotorSpeed(i).getDouble(),i);
        stuff[7]=fromDouble(ControllerRobot.getGripperRotation().getDouble(),6);
        stuff[8]=fromDouble(ControllerRobot.getGripperClamp().getDouble(),7);
        stuff[9]=fromDouble(ControllerRobot.getCameraPan().getDouble(),8);
        stuff[10]=fromDouble(ControllerRobot.getCameraTilt().getDouble(),9);
        PortSend(stuff);
    }
    void refreshPortList(){
        String[] portNames = SerialPortList.getPortNames();
        Component SomeComponent = getComponentByName("serialComboBox");
        if (SomeComponent instanceof JComboBox) {
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            SomeComboBox.removeAllItems();
            for (String portName : portNames) {
                SomeComboBox.addItem(portName);
            }
        }
    }
    // connection
    void btnSerialConnectClicked() {
        if(!Opened) {
            setVisibility(getComponentByName("btnSerialConnect"),false);
            setVisibility(getComponentByName("btnSerialDisconnect"),true);
            try {
                String portSelected = MainInterfaceFrame.getSelectedPort();
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
                TimeSinceLastReceived = 0;
            } catch (SerialPortException ex) {
                System.out.println("There are an error on writing string to port: " + ex);
            }
        }
    }

    void btnSerialDisconnectClicked(){
        if(Opened) {
            TimeSinceLastReceived = 0;
            Opened = false;
            setVisibility(getComponentByName("btnSerialConnect"),true);
            setVisibility(getComponentByName("btnSerialDisconnect"),false);
            try {
                serialPort.closePort();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isOpen(){return Opened;}

    private void PortSend(byte[] val){
        if(isOpen()){
            try{
                serialPort.writeBytes(val);
                String stuff ="I:";
                for(int i=0; i< 11;i++){
                    stuff = stuff.concat(" ");
                    int temp = (int)(val[i]);
                    stuff = stuff.concat(String.valueOf(temp));
                }
                MainInterfaceFrame.addSerialSent(stuff);
            } catch (SerialPortException ex){
                System.out.println("Error sending byte: "+ex);
            }
        }
    }

    private static class PortReader implements SerialPortEventListener {
        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() >= 11) {
                try {
                    TimeSinceLastReceived =0;
                    SerialReceived.setBoolean(true);
                    SerialReceivedU.setBoolean(true);
                    String toDisplay = "O:";
                    byte a[] = serialPort.readBytes(1);
                    if(a[0] != 0){
                        return;
                    }
                    for(int j = 0; j < 10; j++){
                        a = serialPort.readBytes(1);
                        int temp = (int)(a[0]);
                        if(temp < 0){
                            temp += 256;
                        }else if(temp == 0){
                            break;
                        }
                        toDisplay = toDisplay.concat(" ");
                        toDisplay = toDisplay.concat(String.valueOf(temp));
                        switch(j){
                            case 6:Robot.setGripperRotation((double)(multipliers[6]*(temp-90))/90);break;
                            case 7:Robot.setGripperClamp((double)(multipliers[7]*(temp-90))/90);break;
                            case 8:Robot.setCameraPan((double)(multipliers[8]*(temp-90))/90);break;
                            case 9:Robot.setCameraTilt((double)(multipliers[9]*(temp-90))/90);break;
                            default:Robot.setMotorSpeed(j,(double)(multipliers[j]*(temp-90))/70);break;
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
