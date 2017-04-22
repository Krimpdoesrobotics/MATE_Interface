package com.company;

/**
 * Created by richard on 4/2/17.
 */
public class ControllerRobotInfo extends RobotInfo
{
    private final double pi = 3.14159;
    private final double reverseEfficencyHandicap = 1;
    private int timerCounter = 0;
    private int powerScaling = 1;   // this is from .1 to 1, and acts as a multiplier for power
    private GamepadController controller1, controller2;
    public ControllerRobotInfo(GamepadController controller1, GamepadController controller2)
    {
        this.controller1 = controller1;
        this.controller2 = controller2;
    }
    public void updateVariables()
    {
        // Left Stick to move the robot forward, backward, left, or right.
        if(controller1.getLeftAnalogUpdated())
        {
            // joystick that will control lateral movement
            // This is the left controller and is controlled by 'values'
            double xVal = controller1.getXValue();//from -1 to 1
            double yVal = controller1.getYValue();//from -1 to 1
            double rotation = Math.atan2(yVal, xVal); //radians, from -pi to pi
            double power;
            // determine region (1 = forward, 2 = forward and right, 3 = right, 4 = back and right, 5 = back, 6 = back and left, 7 = left, 8 = forward and left)
            if (rotation <= (5.0 * pi) / 8.0 && rotation >= (3.0 * pi) / 8.0) {
                //forward
                power = Math.abs(yVal * powerScaling);
                setMotorSpeed(0,power);
                setMotorSpeed(1,power);
                setMotorSpeed(2,-power);
                setMotorSpeed(3,-power);
            } else if (rotation <= (3.0 * pi) / 8.0 && rotation >= pi / 8.0) {
                //forward right
                power = Math.sqrt(xVal*xVal+yVal*yVal) * powerScaling;
                setMotorSpeed(0,power*reverseEfficencyHandicap);
                setMotorSpeed(1,0);
                setMotorSpeed(2,0);
                setMotorSpeed(3,-power);
            } else if (rotation <= pi / 8.0 && rotation >= -pi / 8.0) {
                //right
                power = Math.abs(xVal);
                setMotorSpeed(0,power);
                setMotorSpeed(1,-power);
                setMotorSpeed(2,power);
                setMotorSpeed(3,-power);
            } else if (rotation <= -pi / 8.0 && rotation >= (-3.0 * pi) / 8.0) {
                //backward right
                power = Math.sqrt(xVal*xVal+yVal*yVal) * powerScaling;
                setMotorSpeed(0,0);
                setMotorSpeed(1,-power);
                setMotorSpeed(2,power*reverseEfficencyHandicap);
                setMotorSpeed(3,0);
            } else if (rotation <= (-3.0 * pi) / 8.0 && rotation >= (-5.0 * pi) / 8.0) {
                //backward
                power = Math.abs(yVal);
                setMotorSpeed(0,-power);
                setMotorSpeed(1,-power);
                setMotorSpeed(2,power);
                setMotorSpeed(3,power);
            } else if (rotation <= (-5.0 * pi) / 8.0 && rotation >= (-7.0 * pi) / 8.0) {
                //backward left
                power = Math.sqrt(xVal*xVal+yVal*yVal) * powerScaling;
                setMotorSpeed(0,-power);
                setMotorSpeed(1,0);
                setMotorSpeed(2,0);
                setMotorSpeed(3,power*reverseEfficencyHandicap);
            } else if (rotation <= (-7.0 * pi) / 8.0 || rotation >= (7.0 * pi) / 8.0) {
                //left
                power = Math.abs(yVal);
                setMotorSpeed(0,-power);
                setMotorSpeed(1,power);
                setMotorSpeed(2,-power);
                setMotorSpeed(3,power);
            } else if (rotation <= (7.0 * pi) / 8.0 && rotation >= (5.0 * pi) / 8.0) {
                //forward left
                power = Math.sqrt(xVal*xVal+yVal*yVal) * powerScaling;
                setMotorSpeed(0,0);
                setMotorSpeed(1,power*reverseEfficencyHandicap);
                setMotorSpeed(2,-power);
                setMotorSpeed(3,0);
            }
        }
        // Right Stick controls turning left and right, up and down
        //
        if (controller1.getRightAnalogUpdated()){
            //joystick that will contol vertical movement and turning
            double xVal = controller1.getXRotation();//from -1 to 1
            double yVal = controller1.getYRotation();//from -1 to 1
            double rotation = Math.atan2(yVal,xVal);//radians, from -pi to pi
            //double magnitude = Math.sqrt(xVal*xVal + yVal * yVal);
            //int power = (int)((double)65 * magnitude * powerScaling);
            double power;
            //determine region (1 = up, 2 = turn right, 3 = down, 4 = turn left)
            if(rotation <= (3.0*pi)/4.0 && rotation >= pi/4.0){
                //up
                power = Math.abs(yVal) * powerScaling;
                setMotorSpeed(4,power);
                setMotorSpeed(5,power);
            }else if(rotation <= pi/4.0 && rotation >= (-1.0*pi)/4.0) {
                power = Math.abs(xVal) * powerScaling;
                setMotorSpeed(0,power+getMotorSpeed(0).getDouble());
                setMotorSpeed(1,-power+getMotorSpeed(1).getDouble());
                setMotorSpeed(2,-power+getMotorSpeed(2).getDouble());
                setMotorSpeed(3,power+getMotorSpeed(3).getDouble());
                setMotorSpeed(4,0);
                setMotorSpeed(5,0);
            }else if(rotation <= (-1.0*pi)/4.0 && rotation >= (-3.0*pi)/4.0) {
                power = Math.abs(yVal) * powerScaling;
                setMotorSpeed(4,-power);
                setMotorSpeed(5,-power);
            }else if(rotation <= (-3.0*pi)/4.0 || rotation >= (3.0*pi)/4.0)  {
                power = Math.abs(xVal) * powerScaling;
                setMotorSpeed(0,-power+getMotorSpeed(0).getDouble());
                setMotorSpeed(1,power+getMotorSpeed(1).getDouble());
                setMotorSpeed(2,power+getMotorSpeed(2).getDouble());
                setMotorSpeed(3,-power+getMotorSpeed(3).getDouble());
                setMotorSpeed(4,0);
                setMotorSpeed(5,0);
            }
        }
        // D-Pad controls the Gripper control
        if (controller1.getUpdated(15)){
            // updated D-Pad
            if(controller1.getDPadLeft()){setGripperRotation(-0.5);}
            else if (controller1.getDPadRight()){setGripperRotation(0.5);}
            else{setGripperRotation(0);}
            if (controller1.getDPadUp()){
                setGripperClamp(getGripperClamp().getDouble()+0.02);
                if(getGripperClamp().getDouble() > 1){setGripperClamp(1);}
            }
            else if (controller1.getDPadDown()){
                setGripperClamp(getGripperClamp().getDouble()-0.02);
                if(getGripperClamp().getDouble() < -1){getGripperClamp().setDouble(-1);}
            }
        }
        //temporary testing buttons
        boolean testing = false;
        for(int k = 0; k < 6; k++){
            if(controller1.getButton(k)){
                testing = true;
                setMotorSpeed(k,0.1);
            }
        }
        if(testing){
            for(int k = 0; k < 6; k++){
                if(getMotorSpeed(k).getDouble()!= 0.1){
                    setMotorSpeed(k,0);
                }
            }
        }else if(controller1.getButton(8) || controller1.getButton(9)){
            for(int k = 0; k < 6; k++){
                setMotorSpeed(k,getMotorSpeed(k).getDouble()/4);
            }
        }
        if(controller1.getButton(7)){
            for (int k = 0; k < 6; k++){
                setMotorSpeed(k,0);
            }
        }
    }
}
