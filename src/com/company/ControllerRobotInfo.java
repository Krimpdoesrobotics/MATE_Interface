package com.company;

class ControllerRobotInfo extends RobotInfo{
    private GamepadController controller1, controller2;
    private double tmpmotors1[] = new double[4];
    private double tmpmotors2[] = new double[4];
    private void setMotorSpeed1(int index, double power){tmpmotors1[index]=power;}
    private void setMotorSpeed2(int index, double power){tmpmotors2[index]=power;}
    boolean getController1Connected(){return controller1.isConnected();}
    boolean getController2Connected(){return controller2.isConnected();}
    ControllerRobotInfo(GamepadController controller1, GamepadController controller2){
        this.controller1 = controller1;
        this.controller2 = controller2;
    }
    void updateVariables() {
        double pi = 3.14159;
        int powerScaling = 1;   // this is from .1 to 1, and acts as a multiplier for power
        // Left Stick to move the robot forward, backward, left, or right.
        if(controller1.getLeftAnalogUpdated())
        {
            // joystick that will control lateral movement
            // This is the left controller and is controlled by 'values'
            double xVal = controller1.getXValue();//from -1 to 1
            double yVal = controller1.getYValue();//from -1 to 1
            if(xVal*xVal+yVal*yVal>0.01) {
                double rotation = Math.atan2(yVal, xVal); //radians, from -pi to pi
                double power;
                // determine region (1 = forward, 2 = forward and right, 3 = right, 4 = back and right, 5 = back, 6 = back and left, 7 = left, 8 = forward and left)
                if (rotation <= (5.0 * pi) / 8.0 && rotation >= (3.0 * pi) / 8.0) {
                    //forward
                    power = Math.abs(yVal * powerScaling);
                    setMotorSpeed1(0, power);
                    setMotorSpeed1(1, power);
                    setMotorSpeed1(2, -power);
                    setMotorSpeed1(3, -power);
                } else if (rotation <= (3.0 * pi) / 8.0 && rotation >= pi / 8.0) {
                    //forward right
                    power = Math.sqrt(xVal * xVal + yVal * yVal) * powerScaling;
                    setMotorSpeed1(0, 0);
                    setMotorSpeed1(1, power);
                    setMotorSpeed1(2, -power);
                    setMotorSpeed1(3, 0);
                } else if (rotation <= pi / 8.0 && rotation >= -pi / 8.0) {
                    //right
                    power = Math.abs(xVal);
                    setMotorSpeed1(0, -power);
                    setMotorSpeed1(1, power);
                    setMotorSpeed1(2, -power);
                    setMotorSpeed1(3, power);
                } else if (rotation <= -pi / 8.0 && rotation >= (-3.0 * pi) / 8.0) {
                    //backward right
                    power = Math.sqrt(xVal * xVal + yVal * yVal) * powerScaling;
                    setMotorSpeed1(0, -power);
                    setMotorSpeed1(1, 0);
                    setMotorSpeed1(2, 0);
                    setMotorSpeed1(3, power);
                } else if (rotation <= (-3.0 * pi) / 8.0 && rotation >= (-5.0 * pi) / 8.0) {
                    //backward
                    power = Math.abs(yVal);
                    setMotorSpeed1(0, -power);
                    setMotorSpeed1(1, -power);
                    setMotorSpeed1(2, power);
                    setMotorSpeed1(3, power);
                } else if (rotation <= (-5.0 * pi) / 8.0 && rotation >= (-7.0 * pi) / 8.0) {
                    //backward left
                    power = Math.sqrt(xVal * xVal + yVal * yVal) * powerScaling;
                    setMotorSpeed1(0, 0);
                    setMotorSpeed1(1, -power);
                    setMotorSpeed1(2, power);
                    setMotorSpeed1(3, 0);
                } else if (rotation <= (-7.0 * pi) / 8.0 || rotation >= (7.0 * pi) / 8.0) {
                    //left
                    power = Math.abs(xVal);
                    setMotorSpeed1(0, power);
                    setMotorSpeed1(1, -power);
                    setMotorSpeed1(2, power);
                    setMotorSpeed1(3, -power);
                } else if (rotation <= (7.0 * pi) / 8.0 && rotation >= (5.0 * pi) / 8.0) {
                    //forward left
                    power = Math.sqrt(xVal * xVal + yVal * yVal) * powerScaling;
                    setMotorSpeed1(0, power);
                    setMotorSpeed1(1, 0);
                    setMotorSpeed1(2, 0);
                    setMotorSpeed1(3, -power);
                }
            }else{
                for(int i = 0; i < 4; i++){
                    setMotorSpeed1(i,0);
                }
            }
        }
        // Right Stick controls turning left and right, up and down
        //
        if (controller1.getRightAnalogUpdated()){
            //joystick that will contol vertical movement and turning
            double xVal = controller1.getXRotation();//from -1 to 1
            double yVal = controller1.getYRotation();//from -1 to 1
            if(xVal*xVal+yVal*yVal>0.01) {
                double rotation = Math.atan2(yVal, xVal);//radians, from -pi to pi
                //double magnitude = Math.sqrt(xVal*xVal + yVal * yVal);
                //int power = (int)((double)65 * magnitude * powerScaling);
                double power;
                //determine region (1 = up, 2 = turn right, 3 = down, 4 = turn left)
                if (rotation <= (3.0 * pi) / 4.0 && rotation >= pi / 4.0) {
                    //up
                    power = Math.abs(yVal) * powerScaling;
                    setMotorSpeed(4, power);
                    setMotorSpeed(5, -power);
                } else if (rotation <= pi / 4.0 && rotation >= (-1.0 * pi) / 4.0) {
                    power = Math.abs(xVal) * powerScaling;
                    setMotorSpeed2(0, -power);
                    setMotorSpeed2(1, power);
                    setMotorSpeed2(2, power);
                    setMotorSpeed2(3, -power);
                    setMotorSpeed(4, 0);
                    setMotorSpeed(5, 0);
                } else if (rotation <= (-1.0 * pi) / 4.0 && rotation >= (-3.0 * pi) / 4.0) {
                    power = Math.abs(yVal) * powerScaling;
                    setMotorSpeed(4, -power);
                    setMotorSpeed(5, +power);
                } else if (rotation <= (-3.0 * pi) / 4.0 || rotation >= (3.0 * pi) / 4.0) {
                    power = Math.abs(xVal) * powerScaling;
                    setMotorSpeed2(0, power);
                    setMotorSpeed2(1, -power);
                    setMotorSpeed2(2, -power);
                    setMotorSpeed2(3, power);
                    setMotorSpeed(4, 0);
                    setMotorSpeed(5, 0);
                }
            }else{
                for(int i = 0; i < 4; i++){
                    setMotorSpeed2(i,0);
                    setMotorSpeed(4,0);
                    setMotorSpeed(5,0);
                }
            }
        }
        for(int i = 0; i< 4; i++) {
            setMotorSpeed(i, tmpmotors1[i] + tmpmotors2[i]);
        }
        // D-Pad controls the Gripper control
        if (controller1.getUpdatedH(15).getBoolean()){
            // updated D-Pad
            if(controller1.getDPadLeft()){setGripperRotation(-0.5);}
            else if (controller1.getDPadRight()){setGripperRotation(0.5);}
            else{setGripperRotation(0);}
        }
        if (controller1.getDPadUp()){
            setGripperClamp(getGripperClamp().getDouble()+0.02);
        }else if (controller1.getDPadDown()){
            setGripperClamp(getGripperClamp().getDouble()-0.02);
        }
        if(controller1.getButton(0) && !controller1.getButton(3)){
            setCameraTilt(getCameraTilt().getDouble()-0.02);
        }else if(controller1.getButton(3) && !controller1.getButton(0)){
            setCameraTilt(getCameraTilt().getDouble()+0.02);
        }
        if(controller1.getButton(1) && !controller1.getButton(2)){
            setCameraPan(getCameraPan().getDouble()-0.02);
        }else if(controller1.getButton(2) && !controller1.getButton(1)){
            setCameraPan(getCameraPan().getDouble()+0.02);
        }
        if(controller1.getButton(8) || controller1.getButton(9)){
            for(int k = 0; k < 6; k++){
                setMotorSpeed(k,getMotorSpeed(k).getDouble()/2);
            }
        }
        if(controller1.getButton(7)){
            for (int k = 0; k < 6; k++){
                setMotorSpeed(k,0);
            }
        }
    }
}
