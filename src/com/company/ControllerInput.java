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

import java.lang.reflect.Constructor;

import static com.company.RandomStuff.BooleanH.newBooleanH;
import static com.company.RandomStuff.DoubleH.newDoubleH;
import static com.company.RandomStuff.IntH.newIntH;

public class ControllerInput {
    // instance variables
    // private data
    private GamepadController controllers[] = new GamepadController[2];
    private Controller[] Controllers; // this temporarily holds an array of controllers that can be accessed.

    private static ControllerEnvironment createDefaultEnvironment() throws ReflectiveOperationException {

        // Find constructor (class is package private, so we can't access it directly)
        Constructor<ControllerEnvironment> constructor = (Constructor<ControllerEnvironment>)
                Class.forName("net.java.games.input.DefaultControllerEnvironment").getDeclaredConstructors()[0];

        // Constructor is package private, so we have to deactivate access control checks
        constructor.setAccessible(true);

        // Create object with default constructor
        return constructor.newInstance();
    }

    // constructors
    public ControllerInput(){
        // default constructor
        controllers[0] = new GamepadController();
        controllers[1] = new GamepadController();
    }

    // update
    public void UpdateController1Components(){
        controllers[0].UpdateController();
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
        try
        {
            Controllers = createDefaultEnvironment().getControllers();
        }catch(ReflectiveOperationException ex){
            System.out.print("Error creating environment:");
            System.out.print(ex);
        }
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
                    controllers[0].ConnectController(Controllers[i]);
                    break;
                }
                counter--;
            }
        }
        MainInterfaceFrame.getComponentByName("btnControllerConnect").setVisible(false);
        MainInterfaceFrame.getComponentByName("btnControllerDisconnect").setVisible(true);
    }
    public void btnController1DisconnectClicked() {
        MainInterfaceFrame.getComponentByName("btnControllerConnect").setVisible(true);
        MainInterfaceFrame.getComponentByName("btnControllerDisconnect").setVisible(false);
        controllers[0].DisconnectController();
    }
    public GamepadController getController(int index){
        return controllers[index];
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
