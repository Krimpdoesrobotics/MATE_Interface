package com.company;

/**
 * Created by Richard on 2/17/2017.
 */
import net.java.games.input.*;
import net.java.games.input.Component;

import javax.swing.*;
import java.awt.*;

public class ControllerInput {
    Controller[] Controllers;
    Controller Controller1;

    // constructors
    public ControllerInput(){
        // default constructor

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

    }

    public void btnControllerDisconnectClicked() {

    }

    // sets the portions of the controller into 8 regions
    private void setRegions() {


    }
}
