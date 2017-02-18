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
    Controller Controller1; // the driver's controller
    ControllerSetup controller;

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
        double forward_arc = 0.0;
        double backward_arc = 0.0;
        double right_arc = 0.0;
        double left_arc = 0.0;
        double topRight_arc = 0.0;
        double topLeft_arc = 0.0;
        double backRight_arc = 0.0;
        double backLeft_arc = 0.0;

        // COMPONENTS LIST //
        // 0: Y AXIS ABSOLUTE ANALOG:
        // 1: X AXIS ABSOLUTE ANALOG:
        // 2: Y ROTATION ABSOLUTE ANALOG:
        // 3: Y ROTATION ABSOLUTE ANALOG:
        // 4: Z AXIS ABSOLUTE ANALOG:
        // 5: BUTTON 0:
        // 6: BUTTON 1:
        // 7: BUTTON 2:
        // 8: BUTTON 3:
        // 9: BUTTON 4:
        // 10: BUTTON 5:
        // 11: BUTTON 6:
        // 12: BUTTON 7:
        // 13: BUTTON 8:
        // 14: BUTTON 9:
        // 15: HAT SWITCH:
        // --------------- //

    }
}
