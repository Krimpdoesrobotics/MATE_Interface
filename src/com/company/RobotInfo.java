package com.company;

import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;

import static com.company.RandomStuff.DoubleH.newDoubleH;

/**
 * Created by julia on 3/27/2017.
 */
public class RobotInfo {
    private DoubleH motorSpeeds[] = new DoubleH[6]; // 0 to 5
    private DoubleH gripperRotation = newDoubleH(0); // 6
    private DoubleH gripperClamp = newDoubleH(0); //7
    private DoubleH cameraPan = newDoubleH(0);// 8
    private DoubleH cameraTilt = newDoubleH(0);// 9
    private BooleanH Updated[] = new BooleanH[10];
    public RobotInfo(){
        for(int i = 0; i < 6; i++)
            motorSpeeds[i]= newDoubleH(0);
        for(int i = 0; i < 10; i++)
            Updated[i] = BooleanH.newBooleanH(false);
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
    public DoubleH getCameraPan(){return cameraPan;}
    public DoubleH getCameraTilt(){return cameraTilt;}
    public BooleanH getUpdated(int index){return Updated[index];}
    public void setMotorSpeed(int index, double a){motorSpeeds[index].setDouble(constrain(a,-1,1)); Updated[index].setBoolean(true);}
    public void setGripperRotation(double a){gripperRotation.setDouble(constrain(a,-1,1)); Updated[6].setBoolean(true);}
    public void setGripperClamp(double a){gripperClamp.setDouble(constrain(a,-1,1)); Updated[7].setBoolean(true);}
    public void setCameraPan(double a){cameraPan.setDouble(constrain(a,-1,1)); Updated[8].setBoolean(true);}
    public void setCameraTilt(double a){cameraTilt.setDouble(constrain(a,-1,1)); Updated[9].setBoolean(true);}
    public void resetUpdated(){
        for(int i = 0; i < 10; i++)
            Updated[i].setBoolean(false);
    }
}
