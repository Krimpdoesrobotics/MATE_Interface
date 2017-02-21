package com.company.ROV_Monitor.Custom_Components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bandi on 2/18/2017.
 */
public class Button extends JComponent {
    private String myCaption;
    private int myVerticalLength;
    private int myHorizontalLength;
    private int myHeight;
    private int myLeftSpacing;
    private int myRightSpacing;
    private int myTopSpacing;
    private int myBottomSpacing;
    private boolean pushed;

    //These default values can be changed if needed
    private Color myBackColor = new Color(70,100,150);
    private Color myForeGroundColor = new Color(255,255,255);

    public Button(String Caption, int VerticalLength, int HorizontalLength,int Height, int LeftSpacing, int RightSpacing, int TopSpacing, int BottomSpacing){
        this.myCaption = Caption;
        this.myVerticalLength  = VerticalLength;
        this.myHorizontalLength = HorizontalLength;
        this.myHeight = Height;
        this.myLeftSpacing = LeftSpacing;
        this.myRightSpacing = RightSpacing;
        this.myTopSpacing = TopSpacing;
        this.myBottomSpacing = BottomSpacing;
        pushed = false;
    }

    public void setCaption(String Caption){
        this.myCaption = Caption;
    }

    public void setVerticalLength(int length){
        this.myVerticalLength = length;
    }

    public void setHeight(int Height){
        this.myHeight = Height;
    }

    public void setLeftSpacing(int LeftSpacing){
        this.myLeftSpacing = LeftSpacing;
    }

    public void setRightSpacing(int RightSpacing){
        this.myRightSpacing = RightSpacing;
    }

    public void setTopSpacing(int TopSpacing){
        this.myTopSpacing = TopSpacing;
    }

    public void setBottomSpacing(int BottomSpacing){
        this.myBottomSpacing = BottomSpacing;
    }

    public void press(){
        this.pushed = true;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Find total height and width to create a transparent background in the panel
        g.setColor(new Color(0,0,0,0));
        int totalHeight = this.myTopSpacing + this.myHeight + this.myBottomSpacing;
        int totalWidth = this.myLeftSpacing + this.myHorizontalLength + this.myRightSpacing;

        g.drawRect(0,0,totalWidth, totalHeight);

        g.setColor(new Color(50,130,130));

        g.drawRoundRect(this.myLeftSpacing, this.myTopSpacing,this.myHorizontalLength, this.myHeight,25,25);
        g.setColor(this.myBackColor);
        g.drawRoundRect(this.myLeftSpacing, this.myTopSpacing,this.myHorizontalLength, this.myHeight,25,25);
        g.setColor(this.myForeGroundColor);
       //TODO: g.drawString(this.myCaption, (((this.myHorizontalLength/2)+this.myLeftSpacing),(((this.myHeight/2))));

    }





}
