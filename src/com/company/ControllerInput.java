package com.company;

import net.java.games.input.*;
import net.java.games.input.Component;
import javax.swing.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.Timer;

import static net.java.games.input.Controller.Type.GAMEPAD;
@SuppressWarnings("unchecked")
class ControllerInput {
    //Organizes the controllers and method of connection
    private GamepadController controllers[] = new GamepadController[2];
    private Controller[] Controllers; // this temporarily holds an array of controllers that can be accessed
    private TimerTask refreshController = new TimerTask() {
        @Override
        public void run() {
            controllers[0].UpdateController();
            controllers[1].UpdateController();
        }
    };
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
    ControllerInput(){
        // default constructor
        controllers[0] = new GamepadController();
        controllers[1] = new GamepadController();
        java.util.Timer ControllerRefreshTimer = new Timer();

        ControllerRefreshTimer.schedule(refreshController,40,40);
    }

    // This outputs all of the controller functions
    void ControllerComboBoxSelection(){
        //JLabel SomeLabel = (JLabel) MainInterfaceFrame.getComponentByName("lblControllerDetails");
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        //SomeLabel.setText("");
        int counter = 0;
        if (SomeComboBox != null) {
            counter = SomeComboBox.getSelectedIndex();
        }
        //System.out.println(counter);
        for (net.java.games.input.Controller Controller : Controllers) {
            if (Controller.getType() == GAMEPAD) {
                if (counter == 0) {
                    StringBuilder output = new StringBuilder();
                    Component[] Components = Controller.getComponents();
                    output.append(Controller.getName());
                    output.append( '\n').append( "Type: ").append(Controller.getType().toString()).append( '\n').append( "Component Count: ").append(Components.length).append( '\n');
                    for (int j = 0; j < Components.length; j++) {
                        output.append("Component ").append(j).append(": ").append(Components[j].getName()).append( '\n').append( "    Identifier: ").append(Components[j].getIdentifier().getName()).append( "    ComponentType: ");
                        if (Components[j].isRelative()) {
                            output.append( "Relative");
                        } else {
                            output.append( "Absolute");
                        }
                        //
                        // Checks to see if the connection is analog or digital
                        //
                        if (Components[j].isAnalog()) {
                            output.append( " Analog");
                        } else {
                            output.append( " Digital");
                        }
                        output.append( '\n');

                    }
                    System.out.println(output);
                    break;
                }
                counter--;
            }
        }
    }

    // refresh
    void btnControllerRefreshClicked(){
        // refreshes all controllers, not just a specific one
        try {
            Controllers = createDefaultEnvironment().getControllers();
        }catch(ReflectiveOperationException ex){
            System.out.print("Error creating environment:");
            ex.printStackTrace();
        }
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        if (SomeComboBox != null) {
            SomeComboBox.removeAllItems();
            for (net.java.games.input.Controller Controller : Controllers) {
                if (Controller.getType() == GAMEPAD) {
                    SomeComboBox.addItem(Controller);
                }
            }
        }
    }

    // connections
    void btnControllerConnectClicked(int index) {
        JComboBox SomeComboBox = (JComboBox) MainInterfaceFrame.getComponentByName("ControllerComboBox");
        if(SomeComboBox!= null) {
            int counter = SomeComboBox.getSelectedIndex();
            //System.out.println(counter);
            for (net.java.games.input.Controller Controller : Controllers) {
                if (Controller.getType() == GAMEPAD) {
                    if (counter == 0) {
                        controllers[index].ConnectController(Controller);
                        break;
                    }
                    counter--;
                }
            }
        }
    }
    void btnControllerDisconnectClicked(int index) {
        controllers[index].DisconnectController();
    }
    GamepadController getController(int index){
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
