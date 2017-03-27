package com.company;

import com.company.RandomStuff.DoubleH;

import static com.company.RandomStuff.DoubleH.newDoubleH;

/**
 * Created by julia on 3/27/2017.
 */
public class RobotInfo {
    private SerialCommunications SerialCommunication;
    private DoubleH motorSpeeds[] = new DoubleH[6];
    private DoubleH gripperRotation = newDoubleH(0);
    private DoubleH gripperClamp = newDoubleH(0);
    public RobotInfo(){
        for(int i = 0; i < 6; i++){
            motorSpeeds[i]= newDoubleH(0);
        }
    }
    public DoubleH getMotorSpeed(int index){return motorSpeeds[index];}
    public DoubleH getGripperRotation(){return gripperRotation;}
    public DoubleH getGripperClamp(){return gripperClamp;}
    public void setMotorSpeed(int index, double a){motorSpeeds[index].setDouble(a);}
    public void setGripperRotation(double a){gripperRotation.setDouble(a);}
    public void setGripperClamp(double a){gripperClamp.setDouble(a);}

}
