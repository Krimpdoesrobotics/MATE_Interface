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

    public ControllerInput(){

    }
    public void ControllerComboBoxSelection(){

    }

    public void btnControllerRefreshClicked(){
        Controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        Component SomeComponent = MainInterfaceFrame.getComponentByName("ControllerComboBox");
        if(SomeComponent instanceof JComboBox){
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            SomeComboBox.removeAllItems();
            for(int i = 0; i < Controllers.length; i++){
                SomeComboBox.addItem(Controllers[i]);
            }
        }
    }

    public void btnControllerConnectClicked(){

    }

    public void btnControllerDisconnectClicked(){

    }
}
