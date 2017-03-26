package com.company;
/**
 * Created by Richard on 2/17/2017.
 */
import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;
import com.company.RandomStuff.IntH;
import net.java.games.input.*;
import net.java.games.input.Component;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.DefaultControllerEnvironment;

import javax.swing.*;

import static com.company.RandomStuff.BooleanH.newBooleanH;
import static com.company.RandomStuff.DoubleH.newDoubleH;
import static com.company.RandomStuff.IntH.newIntH;

public class ControllerInput {
    // instance variables
    // private data
    private DoubleH XAxis, YAxis, XRotation, YRotation, DPad, ZAxis; // Value of each axis
    private BooleanH[] buttons = new BooleanH[10];                   // array of the buttons we have and whether or not they are in use -BOOL-
    private BooleanH[] updated = new BooleanH[16];                    // whether or not the pads or buttons have been updated
    private IntH DPadIntVal;
    private BooleanH LeftAnalogUpdated, RightAnalogUpdated;
    private Controller[] Controllers; // this temporarily holds an array of controllers that can be accessed.
    private Controller Controller1;   // Driver controller
    private Component[] Components;   // different controller components. Used for checking if active
    private DefaultControllerEnvironment ce = new DefaultControllerEnvironment();

    // constructors
    public ControllerInput(){
        // default constructor
        DPad = newDoubleH(0);
        XAxis = newDoubleH(0);
        YAxis = newDoubleH(0);
        XRotation = newDoubleH(0);
        YRotation = newDoubleH(0);
        ZAxis = newDoubleH(0);
        LeftAnalogUpdated = newBooleanH(false);
        RightAnalogUpdated =newBooleanH(false);
        DPadIntVal = newIntH(0);
        //
        // sets all of the updates
        //
        for(int i = 0; i < 10; i++){
            buttons[i] = newBooleanH(false);
        }
        for(int i = 0; i < 16; i++){
            updated[i] = newBooleanH(false);
        }
    }

    // update
    public void UpdateController1Components(){
        if(Controller1 != null) {
            try {
                Controller1.poll();
            }catch(Exception ex){
                System.out.println("Trouble Polling Controller: Possible Disconnection");
            }
            EventQueue queue = Controller1.getEventQueue();
            Event event = new Event();
            while(queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(Controller1.getName());
                buffer.append(" at ");
                buffer.append(event.getNanos()).append(", ");
                Component comp = event.getComponent();
                buffer.append(comp.getName()).append(" changed to ");
                double value = event.getValue();
                if(comp.isAnalog()) {
                    buffer.append(value);
                    //
                    // checks all the analog sticks
                    //
                    if(comp.getIdentifier() == Component.Identifier.Axis.Y) {
                        YAxis.setDouble(value);
                        updated[0].setBoolean(true);
                        LeftAnalogUpdated.setBoolean(true);
                    } else if(comp.getIdentifier() == Component.Identifier.Axis.X) {
                        XAxis.setDouble(value);
                        updated[1].setBoolean(true);
                        LeftAnalogUpdated.setBoolean(true);
                    } else if(comp.getIdentifier() == Component.Identifier.Axis.RY) {
                        YRotation.setDouble(value);
                        updated[2].setBoolean(true);
                        RightAnalogUpdated.setBoolean(true);
                    } else if(comp.getIdentifier() == Component.Identifier.Axis.RX) {
                        XRotation.setDouble(value);
                        updated[3].setBoolean(true);
                        RightAnalogUpdated.setBoolean(true);
                    } else if(comp.getIdentifier() == Component.Identifier.Axis.Z) {
                        ZAxis.setDouble(value);
                        updated[4].setBoolean(true);
                    }
                } else {
                    if(comp.getIdentifier() == Component.Identifier.Axis.POV) {
                        DPad.setDouble(value);
                        DPadIntVal.setInt((int)((value*8)+0.25));
                        updated[15].setBoolean(true);
                        buffer.append(value);
                    } else {
                        int buttonnum = 0;
                        for(int i = 0; i < 10; i++) {
                            if(comp.getIdentifier().getName().contains(Integer.toString(i))) {
                                buttonnum = i;
                                updated[i+5].setBoolean(true);
                            }
                        }
                        if(value >0.5) {
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

    // This outputs all of the controller functions
    public void ControllerComboBoxSelection(){
        //JLabel SomeLabel = (JLabel) MainInterfaceFrame.getComponentByName("lblControllerDetails");
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        //SomeLabel.setText("");
        int counter = SomeComboBox.getSelectedIndex();
        //System.out.println(counter);
        for(int i = 0; i < Controllers.length; i++) {
            if (Controllers[i].getType() == Controller.Type.GAMEPAD) {
                if (counter == 0) {
                    String output = "<html>";
                    output += Controllers[i].getName();
                    output += "<BR>";
                    output += "Type: " + Controllers[i].getType().toString();
                    output += "<BR>";
                    Component[] Components = Controllers[i].getComponents();
                    output += "<BR>";
                    output += "Component Count: " + Components.length;
                    output += "<BR>";
                    for(int j = 0; j < Components.length; j++) {
                        output += "Component " + j + ": " + Components[j].getName();
                        output += "<BR>";
                        output += "    Identifier: "+ Components[j].getIdentifier().getName();
                        output += "    ComponentType: ";
                        //
                        // Checks to see if the value is absolute or relative
                        //
                        if (Components[j].isRelative()) {
                            output += "Relative";
                        } else {
                            output += "Absolute";
                        }
                        //
                        // Checks to see if the connection is analog or digital
                        //
                        if (Components[j].isAnalog()) {
                            output += " Analog";
                        } else {
                            output += " Digital";
                        }
                        output += "<BR>";

                    }
                    System.out.println(output);
                    break;
                }
                counter--;
            }
        }
    }

    // refresh
    public void btnControllerRefreshClicked(){
        // refreshes all controllers, not just a specific one

        Controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        SomeComboBox.removeAllItems();
        for(int i = 0; i < Controllers.length; i++){
            if(Controllers[i].getType() == Controller.Type.GAMEPAD) {
                SomeComboBox.addItem(Controllers[i]);
            }
        }
    }

    // connections
    public void btnController1ConnectClicked() {
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        int counter = SomeComboBox.getSelectedIndex();
        //System.out.println(counter);
        for(int i = 0; i < Controllers.length; i++) {
            if (Controllers[i].getType() == Controller.Type.GAMEPAD) {
                if (counter == 0) {
                    Controller1 = Controllers[i];
                    break;
                }
                counter--;
            }
        }
        MainInterfaceFrame.getComponentByName("btnControllerConnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnControllerDisconnect").setVisible(true);
        Components = Controller1.getComponents();
    }
    public void btnController1DisconnectClicked() {
        MainInterfaceFrame.getComponentByName("btnControllerConnect").setVisible(true);
        MainInterfaceFrame.getComponentByName("btnControllerDisconnect").setVisible(false);
        Controller1 = null;
        Components = null;
    }

    // gets
    public boolean getController1Connected(){
        return (Controller1 != null);
    }

    public double getYValue(){return YAxis.getDouble();}

    public DoubleH getYValueH(){return YAxis;}

    public double getXValue(){return XAxis.getDouble();}

    public DoubleH getXValueH(){return XAxis;}

    public double getYRotation(){return YRotation.getDouble();}

    public DoubleH getYRotationH(){return YRotation;}

    public double getXRotation(){
        return XRotation.getDouble();
    }

    public DoubleH getXRotationH(){return XRotation;}

    public int getDPad(){
        return (int)(DPad.getDouble()*8+.25);
    } // EXPLAIN

    public IntH getDPadH(){ return DPadIntVal;}

    // gets the specific pads
    public boolean getDPadLeft(){
        int DPadVal = getDPad();
        if(DPadVal == 8) return true;
        else return false;
    }

    public boolean getDPadRight(){
        int DPadVal = getDPad();
        if(DPadVal == 4) return true;
        else return false;
    }

    public boolean getDPadUp(){
        int DPadVal = getDPad();
        if(DPadVal == 2) return true;
        else return false;
    }

    public boolean getDPadDown(){
        int DPadVal = getDPad();
        if(DPadVal == 6) return true;
        else return false;
    }

    // gets which button or buttons is pressed
    public boolean getButton(int index){
        return buttons[index].getBoolean();
    }

    public BooleanH getButtonH(int index){return buttons[index];}

    public boolean getUpdated(int index) { return updated[index].getBoolean();}

    public BooleanH getUpdatedH(int index){return updated[index];}

    public double getZAxis(){
        return ZAxis.getDouble();
    }

    public DoubleH  getZAxisH(){ return ZAxis;}

    public boolean getLeftAnalogUpdated(){
        return LeftAnalogUpdated.getBoolean();
    }

    public BooleanH getLeftAnalogUpdatedH(){return LeftAnalogUpdated;}

    public boolean getRightAnalogUpdated(){
        return RightAnalogUpdated.getBoolean();
    }

    public BooleanH getRightAnalogUpdatedH(){return RightAnalogUpdated;}

    public void resetUpdated(){
        for(int i = 0; i < 16; i++){
            updated[i].setBoolean(false);
        }
        LeftAnalogUpdated.setBoolean(false);
        RightAnalogUpdated.setBoolean(false);
    }
    // COMPONENTS LIST //
    // 0: Y AXIS ABSOLUTE ANALOG: Left Stick X
    // 1: X AXIS ABSOLUTE ANALOG: Left Stick Y
    // 2: X ROTATION ABSOLUTE ANALOG: Right Stick X
    // 3: Y ROTATION ABSOLUTE ANALOG: Right Stick Y
    // 4: Z AXIS ABSOLUTE ANALOG: Sum of Left Trigger(+) and Right Trigger(-)
    // 5: BUTTON 0: A Button
    // 6: BUTTON 1: B Button
    // 7: BUTTON 2: X Button
    // 8: BUTTON 3: Y Button
    // 9: BUTTON 4: L Button (not working well ATM)
    // 10: BUTTON 5: R Button
    // 11: BUTTON 6: Back Button
    // 12: BUTTON 7: Start Button
    // 13: BUTTON 8: L Stick Push Down
    // 14: BUTTON 9: R Stick Push Down
    // 15: HAT SWITCH: D Pad 0 Nothing .125 FL .25 F, .375 FR, etc..... 1.0 L
    // --------------- //
}
