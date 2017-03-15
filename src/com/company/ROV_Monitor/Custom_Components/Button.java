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
    private Color myBackColor = new Color(80,120,160);
    private Color myForeGroundColor = new Color(255,255,255);

    public Button(String Caption, int HorizontalLength,int Height, int LeftSpacing, int RightSpacing, int TopSpacing, int BottomSpacing){
        this.myCaption = Caption;
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

    public void setBackColor(Color backColor){this.myBackColor = backColor;}

    public void setForeColor(Color foreColor){this.myForeGroundColor = foreColor;}

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

        g.setColor(new Color(50,50,100));
        System.out.println("Left button spacing:" + this.myLeftSpacing + " Right Button Spacing:" +
                this.myRightSpacing + " Top Button Spacing:" + this.myTopSpacing + " Bottom Button Spacing:"
                + this.myBottomSpacing + "Button Length: " + this.myHorizontalLength + "Button Height" +
                this.myVerticalLength);

        g.fillRoundRect(this.myLeftSpacing, this.myTopSpacing,this.myHorizontalLength,
                this.myHeight,25,25);
        g.setColor(this.myBackColor);
        g.fillRoundRect(this.myLeftSpacing, this.myTopSpacing,this.myHorizontalLength - 3,
                this.myHeight - 3,25,25);
        g.setColor(this.myForeGroundColor);
        Font font20Pt = new Font("Helvetica" , Font.PLAIN, 20);
        g.setFont(font20Pt);
        g.drawString(this.myCaption, (((this.myHorizontalLength/2)+this.myLeftSpacing)) -
                (this.myCaption.length()*5), ((this.myHeight/2)+this.myTopSpacing) + 5);
    }





}
