package com.company;

import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;
import com.company.RandomStuff.IntH;

import static com.company.RandomStuff.BooleanH.newBooleanH;
import static com.company.RandomStuff.DoubleH.newDoubleH;
import static com.company.RandomStuff.IntH.newIntH;

import net.java.games.input.*;

class GamepadController {
    // JInput doesn't give you values for each axis and only enables you
    // to retrieve events from the log, so these are all the private variables
    // that are needed on Windows (only Windows 10, Logitech F310 tested)
    private DoubleH XAxis, YAxis, XRotation, YRotation, DPad, ZAxis; // values from -1 to 1 (Dpad is from 0 to 1)
    private BooleanH[] buttons = new BooleanH[10]; // boolean states of buttons - pressed or not
    private BooleanH[] updated = new BooleanH[16]; // whether components of the controller have been updated - 16 components in windows 10
    private IntH DPadIntVal;                       // D PAD value in terms of 0-8
    private BooleanH LeftAnalogUpdated, RightAnalogUpdated; // whether the controller stick has been updated
    private Controller controller;                 // reference to the controller to "poll" it for events
    private boolean isLinux = false;               // JInput's Linux Library recognizes 18 different components from windows, so we need to test for it
    private double ZR, ZA;                         // Triggers are separated in Linux
    private Component.Identifier[] Identifiers = { // Different Button Identifiers
            Component.Identifier.Button.A,
            Component.Identifier.Button.B,
            Component.Identifier.Button.X,
            Component.Identifier.Button.Y,
            Component.Identifier.Button.LEFT_THUMB,
            Component.Identifier.Button.RIGHT_THUMB,
            Component.Identifier.Button.BACK,
            Component.Identifier.Button.START,
            Component.Identifier.Button.LEFT_THUMB3,
            Component.Identifier.Button.RIGHT_THUMB3,
    };
    GamepadController(){                    // Initializing all private variables
        DPad = newDoubleH(0);
        XAxis = newDoubleH(0);
        YAxis = newDoubleH(0);
        XRotation = newDoubleH(0);
        YRotation = newDoubleH(0);
        ZAxis = newDoubleH(0);
        LeftAnalogUpdated = newBooleanH(false);
        RightAnalogUpdated =newBooleanH(false);
        DPadIntVal = newIntH(0);
        for(int i = 0; i < 10; i++){
            buttons[i] = newBooleanH(false);
        }
        for(int i = 0; i < 16; i++){
            updated[i] = newBooleanH(false);
        }
        ZR = 0;
        ZA = 0;
        controller = null;
    }
    boolean isConnected(){return controller != null;} // if the controller is connected
    void ConnectController(Controller controller){    // connect controller subroutine
        this.controller = controller;
        if(controller.getComponents().length==18){
            System.out.print("Linux Controller Detected");
            isLinux = true;
        }
    }
    void DisconnectController(){this.controller = null;} //disconnect controller subroutine
    void UpdateController(){                             // "polls" controller for changes and changes private variables accordingly
        if(isConnected()) {
            try {
                controller.poll();
            } catch (Exception ex) {
                System.out.println("Trouble Polling Controller: Possible Disconnection");
            }
            EventQueue queue = controller.getEventQueue();
            Event event = new Event();
            while (queue.getNextEvent(event)) {
                StringBuilder buffer = new StringBuilder(controller.getName());
                buffer.append(" at ");
                buffer.append(event.getNanos()).append(", ");
                Component comp = event.getComponent();
                buffer.append(comp.getName()).append(" changed to ");
                double value = event.getValue();
                if (comp.isAnalog()) {
                    buffer.append(value);
                    if (comp.getIdentifier() == Component.Identifier.Axis.Y) {
                        YAxis.setDouble(value);
                        updated[0].setBoolean(true);
                        LeftAnalogUpdated.setBoolean(true);
                    } else if (comp.getIdentifier() == Component.Identifier.Axis.X) {
                        XAxis.setDouble(value);
                        updated[1].setBoolean(true);
                        LeftAnalogUpdated.setBoolean(true);
                    } else if (comp.getIdentifier() == Component.Identifier.Axis.RY) {
                        YRotation.setDouble(value);
                        updated[2].setBoolean(true);
                        RightAnalogUpdated.setBoolean(true);
                    } else if (comp.getIdentifier() == Component.Identifier.Axis.RX) {
                        XRotation.setDouble(value);
                        updated[3].setBoolean(true);
                        RightAnalogUpdated.setBoolean(true);
                    } else if(isLinux) {
                        if(comp.getIdentifier() == Component.Identifier.Axis.Z){
                            ZA = value;
                            updated[4].setBoolean(true);
                            ZAxis.setDouble(ZR-ZA);
                        }else if(comp.getIdentifier() == Component.Identifier.Axis.RZ){
                            ZR = value;
                            updated[4].setBoolean(true);
                            ZAxis.setDouble(ZR-ZA);
                        }
                    } else if (comp.getIdentifier() == Component.Identifier.Axis.Z) {
                        ZAxis.setDouble(-value);
                        updated[4].setBoolean(true);
                    }
                } else {
                    if (comp.getIdentifier() == Component.Identifier.Axis.POV) {
                        DPad.setDouble(value);
                        DPadIntVal.setInt((int) ((value * 8) + 0.25));
                        updated[15].setBoolean(true);
                        buffer.append(value);
                    } else {
                        int buttonNum = 0;
                        if(isLinux){
                            for(int i = 0; i < 10; i++){
                                if(comp.getIdentifier()==Identifiers[i]){
                                    buttonNum = i;
                                    updated[i+5].setBoolean(true);
                                }
                            }
                        }else {
                            for (int i = 0; i < 10; i++) {
                                if (comp.getIdentifier().getName().contains(Integer.toString(i))) {
                                    buttonNum = i;
                                    updated[i + 5].setBoolean(true);
                                }
                            }
                        }
                        if (value > 0.5) {
                            buffer.append("On").append(Integer.toString(buttonNum));
                            buttons[buttonNum].setBoolean(true);
                        } else {
                            buffer.append("Off").append(Integer.toString(buttonNum));
                            buttons[buttonNum].setBoolean(false);
                        }
                    }
                }
                System.out.println(buffer.toString());
            }

        }
    }
    double getYValue(){return YAxis.getDouble();}                             // List of subroutines to return private variables
    DoubleH getYValueH(){return YAxis;}
    double getXValue(){return XAxis.getDouble();}
    DoubleH getXValueH(){return XAxis;}
    double getYRotation(){return YRotation.getDouble();}
    DoubleH getYRotationH(){return YRotation;}
    double getXRotation(){return XRotation.getDouble();}
    DoubleH getXRotationH(){return XRotation;}
    IntH getDPadH(){ return DPadIntVal;}
    boolean getDPadLeft(){return (DPadIntVal.getInt() == 8);}
    boolean getDPadRight(){return (DPadIntVal.getInt() == 4);}
    boolean getDPadUp(){return (DPadIntVal.getInt() == 2);}
    boolean getDPadDown(){return (DPadIntVal.getInt() == 6);}
    boolean getButton(int index){return buttons[index].getBoolean();}
    BooleanH getButtonH(int index){return buttons[index];}
    BooleanH getUpdatedH(int index){return updated[index];}
    DoubleH  getZAxisH(){ return ZAxis;}
    boolean getLeftAnalogUpdated(){return LeftAnalogUpdated.getBoolean();}
    BooleanH getLeftAnalogUpdatedH(){return LeftAnalogUpdated;}
    boolean getRightAnalogUpdated(){return RightAnalogUpdated.getBoolean();}
    BooleanH getRightAnalogUpdatedH(){return RightAnalogUpdated;}
    void resetUpdated(){                                                     // reset updated array to indicate none of the
        for(int i = 0; i < 16; i++){                                                // variables have been changed
            updated[i].setBoolean(false);
        }
        LeftAnalogUpdated.setBoolean(false);
        RightAnalogUpdated.setBoolean(false);
    }
}
