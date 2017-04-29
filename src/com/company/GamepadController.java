package com.company;

import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;
import com.company.RandomStuff.IntH;

import static com.company.RandomStuff.BooleanH.newBooleanH;
import static com.company.RandomStuff.DoubleH.newDoubleH;
import static com.company.RandomStuff.IntH.newIntH;

import net.java.games.input.*;

/**
 * Created by Richard on 3/26/2017.
 */
public class GamepadController {
    private DoubleH XAxis, YAxis, XRotation, YRotation, DPad, ZAxis; // Value of each axis
    private BooleanH[] buttons = new BooleanH[10];                   // array of the buttons we have and whether or not they are in use -BOOL-
    private BooleanH[] updated = new BooleanH[16];                    // whether or not the pads or buttons have been updated
    private IntH DPadIntVal;
    private BooleanH LeftAnalogUpdated, RightAnalogUpdated;
    private Controller controller;
    private boolean isLinux = false;
    private double ZR, ZA;
    private Component.Identifier[] Identifiers = {
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
    public GamepadController(){
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
    public boolean isConnected(){return controller != null;}
    public void ConnectController(Controller controller){
        this.controller = controller;
        if(controller.getComponents().length==18){
            System.out.print("Linux Controller Detected");
            isLinux = true;
        }
    }
    public void DisconnectController(){this.controller = null;}
    public void UpdateController(){
        if(controller != null) {
            try {
                controller.poll();
            } catch (Exception ex) {
                System.out.println("Trouble Polling Controller: Possible Disconnection");
            }
            EventQueue queue = controller.getEventQueue();
            Event event = new Event();
            while (queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(controller.getName());
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
                        int buttonnum = 0;
                        if(isLinux){
                            for(int i = 0; i < 10; i++){
                                if(comp.getIdentifier()==Identifiers[i]){
                                    buttonnum = i;
                                    updated[i+5].setBoolean(true);
                                }
                            }
                        }else {
                            for (int i = 0; i < 10; i++) {
                                if (comp.getIdentifier().getName().contains(Integer.toString(i))) {
                                    buttonnum = i;
                                    updated[i + 5].setBoolean(true);
                                }
                            }
                        }
                        if (value > 0.5) {
                            buffer.append("On" + Integer.toString(buttonnum));
                            buttons[buttonnum].setBoolean(true);
                        } else {
                            buffer.append("Off" + Integer.toString(buttonnum));
                            buttons[buttonnum].setBoolean(false);
                        }
                    }
                }
                System.out.println(buffer.toString());
            }

        }
    }
    public double getYValue(){return YAxis.getDouble();}
    public DoubleH getYValueH(){return YAxis;}
    public double getXValue(){return XAxis.getDouble();}
    public DoubleH getXValueH(){return XAxis;}
    public double getYRotation(){return YRotation.getDouble();}
    public DoubleH getYRotationH(){return YRotation;}
    public double getXRotation(){return XRotation.getDouble();}
    public DoubleH getXRotationH(){return XRotation;}
    public IntH getDPadH(){ return DPadIntVal;}
    public boolean getDPadLeft(){return (DPadIntVal.getInt() == 8);}
    public boolean getDPadRight(){return (DPadIntVal.getInt() == 4);}
    public boolean getDPadUp(){return (DPadIntVal.getInt() == 2);}
    public boolean getDPadDown(){return (DPadIntVal.getInt() == 6);}
    public boolean getButton(int index){return buttons[index].getBoolean();}
    public BooleanH getButtonH(int index){return buttons[index];}
    public boolean getUpdated(int index) { return updated[index].getBoolean();}
    public BooleanH getUpdatedH(int index){return updated[index];}
    public DoubleH  getZAxisH(){ return ZAxis;}
    public boolean getLeftAnalogUpdated(){return LeftAnalogUpdated.getBoolean();}
    public BooleanH getLeftAnalogUpdatedH(){return LeftAnalogUpdated;}
    public boolean getRightAnalogUpdated(){return RightAnalogUpdated.getBoolean();}
    public BooleanH getRightAnalogUpdatedH(){return RightAnalogUpdated;}
    public void resetUpdated(){
        for(int i = 0; i < 16; i++){
            updated[i].setBoolean(false);
        }
        LeftAnalogUpdated.setBoolean(false);
        RightAnalogUpdated.setBoolean(false);
    }
}
