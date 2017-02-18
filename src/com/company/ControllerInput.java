package com.company;

/**
 * Created by Richard on 2/17/2017.
 */
import net.java.games.input.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Component;

public class ControllerInput {
    Controller[] Controllers;
    Controller Controller1;

    // constructors
    public ControllerInput(){
        // default constructor

    }

    public void ControllerComboBoxSelection(){
        Component SomeComponent = MainInterfaceFrame.getComponentByName("lblControllerDetails");
        Component SomeComponent2 = MainInterfaceFrame.getComponentByName("ControllerComboBox");
        if(SomeComponent instanceof JLabel && SomeComponent2 instanceof JComboBox){
            JLabel SomeLabel = (JLabel) SomeComponent;
            JComboBox SomeComboBox = (JComboBox) SomeComponent2;
            SomeLabel.setText("");
            int counter = SomeComboBox.getSelectedIndex();
            for(int i = 0; i < Controllers.length; i++){
                if(Controllers[i].getType() == Controller.Type.GAMEPAD){
                    if(counter == 0){
                        Controller1 = Controllers[i];
                        break;
                    }
                    counter--;
                }
            }
        }
    }

    public void btnControllerRefreshClicked(){
        Controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Component SomeComponent = MainInterfaceFrame.getComponentByName("ControllerComboBox");
        if(SomeComponent instanceof JComboBox){
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            SomeComboBox.removeAllItems();
            for(int i = 0; i < Controllers.length; i++){
                if(Controllers[i].getType() == Controller.Type.GAMEPAD) {
                    SomeComboBox.addItem(Controllers[i]);
                }
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
