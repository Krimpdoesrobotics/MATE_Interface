package com.company;

/**
 * Created by Richard on 2/17/2017.
 */
import net.java.games.input.*;
import net.java.games.input.Component;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import sun.applet.Main;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class ControllerInput {
    private float XAxis,YAxis,XRotation,YRotation;
    private Controller[] Controllers;
    private Controller Controller1;
    private Component[] Components1;
    // constructors
    public ControllerInput(){
        // default constructor
        XAxis = 0;
        YAxis = 0;
        XRotation = 0;
        YRotation = 0;
    }

    public void UpdateControllerComponents(){
        if(Controller1!=null){
            Controller1.poll();
            EventQueue queue = Controller1.getEventQueue();
            Event event = new Event();
            while(queue.getNextEvent(event)) {
                StringBuffer buffer = new StringBuffer(Controller1.getName());
                buffer.append(" at ");
                buffer.append(event.getNanos()).append(", ");
                Component comp = event.getComponent();
                buffer.append(comp.getName()).append(" changed to ");
                float value = event.getValue();
                if(comp.isAnalog()) {
                    buffer.append(value);
                    if(comp.getIdentifier() == Component.Identifier.Axis.X){
                        XAxis = value;
                    }else if(comp.getIdentifier() == Component.Identifier.Axis.Y){
                        YAxis = value;
                    }else if(comp.getIdentifier() == Component.Identifier.Axis.RX) {
                        XRotation = value;
                    }else if(comp.getIdentifier() == Component.Identifier.Axis.RY){
                        YRotation = value;
                    }
                } else {
                    if(value==1.0f) {
                        buffer.append("On");
                    } else {
                        buffer.append("Off");
                    }
                }
                System.out.println(buffer.toString());
            }
        }
    }

    public void ControllerComboBoxSelection(){
        JLabel SomeLabel = (JLabel) MainInterfaceFrame.getComponentByName("lblControllerDetails");
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        SomeLabel.setText("");
        int counter = SomeComboBox.getSelectedIndex();
        System.out.println(counter);
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
                    for(int j=0;j<Components.length;j++) {
                        output += "Component " + j + ": " + Components[j].getName();
                        output += "<BR>";
                        output += "    Identifier: "+ Components[j].getIdentifier().getName();
                        output += "    ComponentType: ";
                        if (Components[j].isRelative()) {
                            output += "Relative";
                        } else {
                            output += "Absolute";
                        }
                        if (Components[j].isAnalog()) {
                            output += " Analog";
                        } else {
                            output += " Digital";
                        }
                        output += "<BR>";
                    }
                    SomeLabel.setText(output);
                    break;
                }
                counter--;
            }
        }
    }

    public void btnControllerRefreshClicked(){
        Controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        SomeComboBox.removeAllItems();
        for(int i = 0; i < Controllers.length; i++){
            if(Controllers[i].getType() == Controller.Type.GAMEPAD) {
                SomeComboBox.addItem(Controllers[i]);
            }
        }
    }

    public void btnControllerConnectClicked() {
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        int counter = SomeComboBox.getSelectedIndex();
        System.out.println(counter);
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
        Components1 = Controller1.getComponents();
    }
    public void btnControllerDisconnectClicked() {
        MainInterfaceFrame.getComponentByName("btnControllerConnect").setVisible(true);
        MainInterfaceFrame.getComponentByName("btnControllerDisconnect").setVisible(false);
        Controller1 = null;
        Components1 = null;
    }

    public boolean getController1Connected(){
        return (Controller1 != null);
    }

    public float getYValue(){
        return YAxis;
    }

    public float getXValue(){
        return XAxis;
    }

    public float getYRotation(){
        return YRotation;
    }

    public float getXRotation(){
        return XRotation;
    }

    // sets the portions of the controller into 8 regions
    private void setRegions() {
        double forward_arc = 0.0;
        double backward_arc = 0.0;
        double right_arc = 0.0;
        double left_arc = 0.0;
        double topRight_arc = 0.0;
        double topLeft_arc = 0.0;
        double backRight_arc = 0.0;
        double backLeft_arc = 0.0;

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
        // 9: BUTTON 4: L Button (not working ATM)
        // 10: BUTTON 5: R Button
        // 11: BUTTON 6: Back Button
        // 12: BUTTON 7: Start Button
        // 13: BUTTON 8: L Stick Push Down
        // 14: BUTTON 9: R Stick Push Down
        // 15: HAT SWITCH: D Pad
        // --------------- //

    }
}
