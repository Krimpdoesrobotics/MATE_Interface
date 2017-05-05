package com.company;

import com.company.RandomStuff.BooleanH;
import com.company.RandomStuff.DoubleH;
import static com.company.RandomStuff.DoubleH.newDoubleH;

class RobotInfo {
    // class to organize the variables of the robot
    private DoubleH motorSpeeds[] = new DoubleH[6]; // 0 to 5
    private DoubleH gripperRotation = newDoubleH(0); // 6
    private DoubleH gripperClamp = newDoubleH(0); //7
    private DoubleH cameraPan = newDoubleH(0);// 8
    private DoubleH cameraTilt = newDoubleH(0);// 9
    private BooleanH Updated[] = new BooleanH[10];
    RobotInfo(){
        for(int i = 0; i < 6; i++)
            motorSpeeds[i]= newDoubleH(0);
        for(int i = 0; i < 10; i++)
            Updated[i] = BooleanH.newBooleanH(false);
    }
    private double constrain(double x){
        if(x < -1)
            return -1;
        else if(x > 1)
            return 1;
        else
            return x;
    }
    DoubleH getMotorSpeed(int index){return motorSpeeds[index];}
    DoubleH getGripperRotation(){return gripperRotation;}
    DoubleH getGripperClamp(){return gripperClamp;}
    DoubleH getCameraPan(){return cameraPan;}
    DoubleH getCameraTilt(){return cameraTilt;}
    BooleanH getUpdated(int index){return Updated[index];}
    void setMotorSpeed(int index, double a){motorSpeeds[index].setDouble(constrain(a)); Updated[index].setBoolean(true);}
    void setGripperRotation(double a){gripperRotation.setDouble(constrain(a)); Updated[6].setBoolean(true);}
    void setGripperClamp(double a){gripperClamp.setDouble(constrain(a)); Updated[7].setBoolean(true);}
    void setCameraPan(double a){cameraPan.setDouble(constrain(a)); Updated[8].setBoolean(true);}
    void setCameraTilt(double a){cameraTilt.setDouble(constrain(a)); Updated[9].setBoolean(true);}
    void resetUpdated(){
        for(int i = 0; i < 10; i++)
            Updated[i].setBoolean(false);
    }
}
