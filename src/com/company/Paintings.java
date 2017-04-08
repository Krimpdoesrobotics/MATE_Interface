package com.company;

import java.awt.*;
import com.company.RandomStuff.*;

import static com.company.RandomStuff.DoubleH.newDoubleH;

/**
 * Created by richard on 3/25/2017.
 */
public class Paintings
{
    private int x, y, width, height, type;
    private Color BorderColor;
    private BooleanH Updated;
    public Paintings(int x1, int y1, int width1, int height1, int type1, Color BorderColor1, BooleanH Updated1)
    {
        x = x1;
        y = y1;
        width = width1;
        height = height1;
        type = type1;
        BorderColor = BorderColor1;
        Updated = Updated1;
    }
    public boolean GenericRepaint(Graphics g)
    {
        switch(type){
            case 0: return RepaintType0(g);
            case 1: return RepaintType1(g);
            case 2: return RepaintType2(g);
            case 3: return RepaintType3(g);
            case 4: return RepaintType4(g);
            default: return false;
        }
    }
    public boolean getUpdated(){
        return Updated.getBoolean();
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }

    // type 0: controller analog stick
    private DoubleH xval, yval;
    private Color BackGroundColor, StickColor;
    public boolean setReferenceType0(DoubleH xval1, DoubleH yval1, Color BackGroundColor1, Color StickColor1)
    {
        if(type == 0)
        {
            xval = xval1;
            yval = yval1;
            BackGroundColor = BackGroundColor1;
            StickColor = StickColor1;
        }
        return (type == 0);
    }
    public boolean RepaintType0(Graphics g)
    {
        if(type == 0)
        {
            g.setColor(BackGroundColor);
            g.fillRect(x,y,width,height);
            g.setColor(StickColor);
            g.drawLine(x+(width/2),y+(height/2),(int)(x+(width/2)+(xval.getDouble()*width/2)),(int)(y+(height/2)+(yval.getDouble()*height/2)));
            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
        return (type == 0);
    }
    // type 1: DPad
    private IntH DPadValue;

    public boolean setReferenceType1(IntH DPad1, Color BackGroundColor1, Color StickColor1)
    {
        if(type == 1)
        {
            DPadValue = DPad1;
            BackGroundColor = BackGroundColor1;
            StickColor = StickColor1;
        }
        return (type == 1);
    }
    public boolean RepaintType1(Graphics g)
    {
        if(type == 1)
        {
            type = 0;
            if(DPadValue.getInt() == 1 || DPadValue.getInt() == 7 || DPadValue.getInt() == 8)
            {
                xval = newDoubleH(-1.0);
            } else if(DPadValue.getInt() == 0 || DPadValue.getInt() == 2 || DPadValue.getInt() == 6)
            {
                xval = newDoubleH(0.0);
            } else {
                xval = newDoubleH(1.0);
            }
            if(DPadValue.getInt() == 1 || DPadValue.getInt() == 2 || DPadValue.getInt() == 3)
            {
                yval = newDoubleH(-1.0);
            } else if(DPadValue.getInt() == 0 || DPadValue.getInt() == 8 || DPadValue.getInt() == 4)
            {
                yval = newDoubleH(0.0);
            } else {
                yval = newDoubleH(1.0);
            }
            RepaintType0(g);
            type = 1;
        }
        return (type == 1);
    }
    // type 2: Analog Button
    private Color StateOn, StateOff;
    private BooleanH isOn;

    public boolean setReferenceType2(BooleanH isOn1, Color StateOn1, Color StateOff1)
    {
        if(type == 2)
        {
            isOn = isOn1;
            StateOn = StateOn1;
            StateOff = StateOff1;
        }
        return (type == 2);
    }
    public boolean RepaintType2(Graphics g)
    {
        if(type == 2)
        {
            if(isOn.getBoolean())
            {
                g.setColor(StateOn);
            } else {
                g.setColor(StateOff);
            }
            g.fillRect(x,y,width,height);
            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
        return (type == 2);
    }
    // type 3: horizontal display
    // type 4: vertical display
    private Color FillColor, OuterColor;
    private DoubleH FillAmount;

    public boolean setReferenceType34(DoubleH FillAmount1, Color FillColor1,Color OuterColor1)
    {
        if (type == 3 || type == 4)
        {
            FillAmount = FillAmount1;
            FillColor = FillColor1;
            OuterColor = OuterColor1;
        }
        return (type == 3 || type == 4);
    }
    public boolean RepaintType3(Graphics g)
    {
        if(type == 3)
        {
            g.setColor(OuterColor);
            g.fillRect(x,y,width,height);
            g.setColor(FillColor);
            if(FillAmount.getDouble() > 0)
            {
                g.fillRect(x+(width/2),y,(int)(FillAmount.getDouble()*(width/2)),height);
            } else {
                g.fillRect((int)(x+(width/2)+(FillAmount.getDouble()*(width/2))),y,(int)(FillAmount.getDouble()*(width/-2)),height);
            }
            //if(FillAmount.getDouble() != 0){System.out.println(FillAmount);}

            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
        return (type == 3);
    }
    public boolean RepaintType4(Graphics g){
        if(type == 4)
        {
            g.setColor(OuterColor);
            g.fillRect(x,y,width,height);
            g.setColor(FillColor);
            if(FillAmount.getDouble() > 0)
            {
                g.fillRect(x,(int)(y+(height/2)-(FillAmount.getDouble()*(height/2))),width,(int)(FillAmount.getDouble()*(height/-2)));
            } else {
                g.fillRect(x,y+height/2,width,(int)(FillAmount.getDouble()*(height/2)));
            }
            //if(FillAmount.getDouble() != 0){System.out.println(FillAmount);}

            g.setColor(BorderColor);
            g.drawRect(x,y,width,height);
        }
        return (type == 4);
    }
}
