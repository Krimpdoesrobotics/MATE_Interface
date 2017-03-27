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
    public DoubleH getMotorSpeed(int index){return motorSpeeds[index];}
    public DoubleH getGripperRotation(){return gripperRotation;}
    public DoubleH getGripperClamp(){return gripperClamp;}
    public BooleanH getUpdated(int index){return Updated[index];}
    public void setMotorSpeed(int index, double a){motorSpeeds[index].setDouble(a); Updated[index].setBoolean(true);}
    public void setGripperRotation(double a){gripperRotation.setDouble(a); Updated[6].setBoolean(true);}
    public void setGripperClamp(double a){gripperClamp.setDouble(a); Updated[7].setBoolean(true);}
    public void resetUpdated(){
        for(int i = 0; i < 8; i++){
            Updated[i].setBoolean(false);
        }
    }
    public void updateVariables(){
        //
        // Left Stick to move the robot forward, backward, left, or right.
        //
        if(controller.getLeftAnalogUpdated())
        {
            //
            // joystick that will control lateral movement
            // This is the left controller and is controlled by 'values'
            //
            double xVal = controller.getXValue();//from -1 to 1
            double yVal = controller.getYValue();//from -1 to 1
            double rotation = Math.atan2(yVal,xVal); //radians, from -pi to pi
            double magnitude = Math.sqrt(xVal * xVal + yVal * yVal);
            int power = (int)((double)65 * magnitude * powerScaling);
            //
            // determine region (1 = forward, 2 = forward and right, 3 = right, 4 = back and right, 5 = back, 6 = back and left, 7 = left, 8 = forward and left)
            //
            int region = 0;
            if(rotation <= (5.0*pi)/8.0 && rotation >= (3.0*pi)/8.0){
                region = 1;
            }
            if(rotation <= (3.0*pi)/8.0 && rotation >= pi/8.0){
                region = 2;
            }
            if(rotation <= pi/8.0 && rotation >= -pi/8.0){
                region = 3;
            }
            if(rotation <= -pi/8.0 && rotation >= (-3.0*pi)/8.0){
                region = 4;
            }
            if(rotation <= (-3.0*pi)/8.0 && rotation >= (-5.0*pi)/8.0){
                region = 5;
            }
            if(rotation <= (-5.0*pi)/8.0 && rotation >= (-7.0*pi)/8.0){
                region = 6;
            }
            if(rotation <= (-7.0*pi)/8.0 || rotation >= (7.0*pi)/8.0){
                region = 7;
            }
            if(rotation <= (7.0*pi)/8.0 && rotation >= (5.0*pi)/8.0){
                region = 8;
            }
            switch(region){
                case 1:
                {
                    //forward
                    AdjFL(power);
                    AdjFR(power);
                    AdjBL((-1 * power) + 90);
                    AdjBR((-1 * power) + 90);
                    break;
                }
                case 2:
                {
                    //forward right
                    AdjFL(((int)((double)power * reverseEfficencyHandicap)) + 90);
                    AdjBR((-1 * power) + 90);
                    break;
                }
                case 3:
                {
                    //right
                    AdjFL(power + 90);
                    AdjFR((-1 *power) + 90);
                    AdjBL(power + 90);
                    AdjBR((-1 *power) + 90);
                    break;
                }
                case 4:
                {
                    //backward right
                    AdjFR((-1 * power) + 90);
                    AdjBL(((int)((double)power * reverseEfficencyHandicap)) + 90);
                    break;
                }
                case 5:
                {
                    //backward
                    AdjFL((-1 * power) + 90);
                    AdjFR((-1 * power) + 90);
                    AdjBL(power + 90);
                    AdjBR(power + 90);
                    break;
                }
                case 6:
                {
                    //backward left
                    AdjFL((-1 * power) + 90);
                    AdjBR(((int)((double)power * reverseEfficencyHandicap)) + 90);
                    break;
                }
                case 7:
                {
                    //left
                    AdjFL((-1 * power) + 90);
                    AdjFR(power + 90);
                    AdjBL((-1 * power) + 90);
                    AdjBR(power + 90);
                    break;
                }
                case 8:
                {
                    //forward left
                    AdjFR(((int)((double)power * reverseEfficencyHandicap)) + 90);
                    AdjBL((-1 * power) + 90);
                    break;
                }
            }


        }
        //
        // Right Stick controls turning left n' right and up n' down
        //
        if (controller.getRightAnalogUpdated())
        {
            //joystick that will contol vertical movement and turning
            double xVal = controller.getXRotation();//from -1 to 1
            double yVal = controller.getYRotation();//from -1 to 1
            double rotation = Math.atan2(yVal,xVal);//radians, from -pi to pi
            double magnitude = Math.sqrt(xVal*xVal + yVal * yVal);
            int power = (int)((double)65 * magnitude * powerScaling);
            //determine region (1 = up, 2 = turn right, 3 = down, 4 = turn left)
            int region = 0;
            if(rotation <= (3.0*pi)/4.0 && rotation >= pi/4.0)
            {
                region = 1;
            }
            if(rotation <= pi/4.0 && rotation >= (-1.0*pi)/4.0)
            {
                region = 2;
            }
            if(rotation <= (-1.0*pi)/4.0 && rotation >= (-3.0*pi)/4.0)
            {
                region = 3;
            }
            if(rotation <= (-3.0*pi)/4.0 || rotation >= (3.0*pi)/4.0)
            {
                region = 4;
            }
            switch(region)
            {
                case 1:
                {
                    //up
                    AdjVL(power + 90);
                    AdjVL(power + 90);
                    break;
                }
                case 2:
                {
                    //turn right
                    AdjFL(power + 90);
                    AdjFR((-1 * power) + 90);
                    AdjBL((-1 * power) + 90);
                    AdjBR(power + 90);
                    break;
                }
                case 3:
                {
                    //down
                    AdjVL((-1 * power) + 90);
                    AdjVR((-1 * power) + 90);
                    break;
                }
                case 4:
                {
                    //turn left
                    AdjFL((-1 * power) + 90);
                    AdjFR(power + 90);
                    AdjBL(power + 90);
                    AdjBR((-1 * power) + 90);
                    break;
                }
            }
        }
        //
        // D-Pad controls the Gripper control
        //
        if (controller.getUpdated(15))
        {
            // updated D-Pad
            if(controller.getDPadLeft())
            {
                AdjGripperRotation(60);
            }
            else if (controller.getDPadRight())
            {
                AdjGripperRotation(120);
            }
            else
            {
                AdjGripperRotation(90);
            }
            if (controller.getDPadUp())
            {
                AdjGripperClamp(17);
            }
            else if (controller.getDPadDown())
            {
                AdjGripperClamp(13);
            }
            else
            {
                AdjGripperClamp(15);
            }
        }
    }
}
