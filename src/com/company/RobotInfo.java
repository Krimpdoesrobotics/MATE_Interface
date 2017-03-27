package com.company;

import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;

import static com.company.RandomStuff.DoubleH.newDoubleH;

/**
 * Created by julia on 3/27/2017.
 */
public class RobotInfo {
    private DoubleH motorSpeeds[] = new DoubleH[6];
    private DoubleH gripperRotation = newDoubleH(0);
    private DoubleH gripperClamp = newDoubleH(0);
    private BooleanH Updated[] = new BooleanH[8];
    public RobotInfo(){
        for(int i = 0; i < 6; i++){
            motorSpeeds[i]= newDoubleH(0);
        }
        for(int i = 0; i < 8; i++){
            Updated[i] = BooleanH.newBooleanH(false);
        }
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
}
