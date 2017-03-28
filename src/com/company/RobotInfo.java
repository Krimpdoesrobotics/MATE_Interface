package com.company;

import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;

import static com.company.RandomStuff.DoubleH.newDoubleH;

/**
 * Created by julia on 3/27/2017.
 */
public class RobotInfo {
    private final double pi = 3.14159;
    private final double reverseEfficencyHandicap = 1;
    private int timerCounter = 0;
    private int powerScaling = 1;   // this is from .1 to 1, and acts as a multiplier for power
    private DoubleH motorSpeeds[] = new DoubleH[6];
    private DoubleH gripperRotation = newDoubleH(0);
    private DoubleH gripperClamp = newDoubleH(0);
    private BooleanH Updated[] = new BooleanH[8];
    private GamepadController controller;
    public RobotInfo(GamepadController controller){
        for(int i = 0; i < 6; i++){
            motorSpeeds[i]= newDoubleH(0);
        }
        for(int i = 0; i < 8; i++){
            Updated[i] = BooleanH.newBooleanH(false);
        }
        this.controller = controller;
    }
    private double constrain(double x, double min, double max){
        if(x < min)
            return min;
        else if(x > max)
            return max;
        else
            return x;
    }
    public DoubleH getMotorSpeed(int index){return motorSpeeds[index];}
    public DoubleH getGripperRotation(){return gripperRotation;}
    public DoubleH getGripperClamp(){return gripperClamp;}
    public BooleanH getUpdated(int index){return Updated[index];}
    public void setMotorSpeed(int index, double a){motorSpeeds[index].setDouble(constrain(a,-1,1)); Updated[index].setBoolean(true);}
    public void setGripperRotation(double a){gripperRotation.setDouble(constrain(a,-1,1)); Updated[6].setBoolean(true);}
    public void setGripperClamp(double a){gripperClamp.setDouble(constrain(a,-1,1)); Updated[7].setBoolean(true);}
    public void resetUpdated(){
        for(int i = 0; i < 8; i++)
            Updated[i].setBoolean(false);
    }
    public void updateVariables(){
        // Left Stick to move the robot forward, backward, left, or right.
        if(controller.getLeftAnalogUpdated()) {
            // joystick that will control lateral movement
            // This is the left controller and is controlled by 'values'
            double xVal = controller.getXValue();//from -1 to 1
            double yVal = controller.getYValue();//from -1 to 1
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
        // Right Stick controls turning left n' right and up n' down
        //
        if (controller.getRightAnalogUpdated()){
            //joystick that will contol vertical movement and turning
            double xVal = controller.getXRotation();//from -1 to 1
            double yVal = controller.getYRotation();//from -1 to 1
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
        if (controller.getUpdated(15)){
            // updated D-Pad
            if(controller.getDPadLeft()){gripperRotation.setDouble(-0.5);}
            else if (controller.getDPadRight()){gripperRotation.setDouble(0.5);}
            else{gripperRotation.setDouble(0);}
            if (controller.getDPadUp()){
                gripperClamp.setDouble(gripperClamp.getDouble()+0.05);
                if(gripperClamp.getDouble() > 1){
                    gripperClamp.setDouble(1);
                }
            }
            else if (controller.getDPadDown()){
                gripperClamp.setDouble(gripperClamp.getDouble()-0.05);
                if(gripperClamp.getDouble() < -1){
                    gripperClamp.setDouble(-1);
                }
            }
        }
    }
}
