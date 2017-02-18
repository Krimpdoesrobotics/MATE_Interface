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
    private float XAxis,YAxis,YRotation,ZRotation;
    private Controller[] Controllers;
    private Controller Controller1;
    private Component[] Components1;
    // constructors
    public ControllerInput(){
        // default constructor
        XAxis = 0;
        YAxis = 0;
        YRotation = 0;
        ZRotation = 0;
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
                    }else if(comp.getIdentifier() == Component.Identifier.Axis.RY) {
                        YRotation = value;
                    }else if(comp.getIdentifier() == Component.Identifier.Axis.RZ){
                        ZRotation = value;
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

    // sets the portions of the controller into 8 regions
    private void setRegions() {


    }
}
