package com.company;

import java.awt.*;
import com.company.RandomStuff.*;

import static com.company.RandomStuff.DoubleH.newDoubleH;
class Paintings{
    // Class to organize the graphics on the controller, robot telemetry, etc.
    private int x, y, width, height, type;                // Basic private data
    private Color BorderColor;
    private BooleanH Updated;
    Paintings(int x1, int y1, int width1, int height1, int type1, Color BorderColor1, BooleanH Updated1){
        x = x1;                                          // constructor must have essential data
        y = y1;
        width = width1;
        height = height1;
        type = type1;
        BorderColor = BorderColor1;
        Updated = Updated1;
    }
    void GenericRepaint(Graphics g){           // Repaint call from CustomPanel Class
        switch(type){
            case 0: RepaintType0(g);break;              // Depending on the type, different subroutines are used
            case 1: RepaintType1(g);break;
            case 2: RepaintType2(g);break;
            case 3: RepaintType3(g);break;
            case 4: RepaintType4(g);break;
        }
    }
    boolean getUpdated(){
        return Updated.getBoolean();
    }    // subroutines to return private variables
    Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }

    // All of the following types have private variables, setReferenceType subroutines to set those private variables,
    // and also a repaint type.

    // type 0: controller analog stick
    private DoubleH xval, yval;
    private Color BackGroundColor, StickColor;
    void setReferenceType0(DoubleH xval1, DoubleH yval1, Color BackGroundColor1, Color StickColor1) {
        if(type == 0){
            xval = xval1;
            yval = yval1;
            BackGroundColor = BackGroundColor1;
            StickColor = StickColor1;
        }
    }
    private void RepaintType0(Graphics g) {
        if(type == 0 || type ==1){
            g.setColor(BackGroundColor);
            g.fillRect(x,y,width,height);
            g.setColor(StickColor);
            g.drawLine(x+(width/2),y+(height/2),(int)(x+(width/2)+(xval.getDouble()*width/2)),(int)(y+(height/2)+(yval.getDouble()*height/2)));
            if(type == 0){g.drawOval(x+(9*width/20),y+(9*width/20),width/10,height/10);}
            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
    }
    // type 1: DPad
    private IntH DPadValue;

    void setReferenceType1(IntH DPad1, Color BackGroundColor1, Color StickColor1)    {
        if(type == 1)        {
            DPadValue = DPad1;
            BackGroundColor = BackGroundColor1;
            StickColor = StickColor1;
        }
    }
    private void RepaintType1(Graphics g)    {
        if(type == 1) {
            if(DPadValue.getInt() == 1 || DPadValue.getInt() == 7 || DPadValue.getInt() == 8) {
                xval = newDoubleH(-1.0);
            } else if(DPadValue.getInt() == 0 || DPadValue.getInt() == 2 || DPadValue.getInt() == 6){
                xval = newDoubleH(0.0);
            } else {
                xval = newDoubleH(1.0);
            }
            if(DPadValue.getInt() == 1 || DPadValue.getInt() == 2 || DPadValue.getInt() == 3){
                yval = newDoubleH(-1.0);
            } else if(DPadValue.getInt() == 0 || DPadValue.getInt() == 8 || DPadValue.getInt() == 4){
                yval = newDoubleH(0.0);
            } else {
                yval = newDoubleH(1.0);
            }
            RepaintType0(g);
        }
    }
    // type 2: Analog Button
    private Color StateOn, StateOff;
    private BooleanH isOn;

    void setReferenceType2(BooleanH isOn1, Color StateOn1, Color StateOff1){
        if(type == 2) {
            isOn = isOn1;
            StateOn = StateOn1;
            StateOff = StateOff1;
        }
    }
    private void RepaintType2(Graphics g)    {
        if(type == 2) {
            if(isOn.getBoolean()) {
                g.setColor(StateOn);
            } else {
                g.setColor(StateOff);
            }
            g.fillRect(x,y,width,height);
            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
    }
    // type 3: horizontal display
    // type 4: vertical display
    private Color FillColor, OuterColor;
    private DoubleH FillAmount;

    void setReferenceType34(DoubleH FillAmount1, Color FillColor1,Color OuterColor1) {
        if (type == 3 || type == 4) {
            FillAmount = FillAmount1;
            FillColor = FillColor1;
            OuterColor = OuterColor1;
        }
    }
    private void RepaintType3(Graphics g) {
        if(type == 3 || type == 4){
            g.setColor(OuterColor);
            g.fillRect(x,y,width,height);
            g.setColor(FillColor);
            if(FillAmount.getDouble() > 0){
                g.fillRect((x+(width/2)),y,(int)((FillAmount.getDouble()*(width/2))),height);
            } else {
                g.fillRect((int)(((x+(width/2)+(FillAmount.getDouble()*(width/2))))),y,(int)((FillAmount.getDouble()*(width/-2))),height);
            }
            //if(FillAmount.getDouble() != 0){System.out.println(FillAmount);}

            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
    }
    private void RepaintType4(Graphics g){
        if(type == 4){
            g.setColor(OuterColor);
            g.fillRect(x,y,width,height);
            g.setColor(FillColor);
            if(FillAmount.getDouble() > 0){
                g.fillRect(x,(int)(y+(height/2)-(FillAmount.getDouble()*(height/2))),width,(int)(FillAmount.getDouble()*(height/2)));
            } else {
                g.fillRect(x,(y+(height/2)),width,(int)(FillAmount.getDouble()*(height/-2)));
            }
            if(FillAmount.getDouble() != 0){System.out.print("FillAmount is =");System.out.println((int)(FillAmount.getDouble()*10000));}

            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
    }
}
