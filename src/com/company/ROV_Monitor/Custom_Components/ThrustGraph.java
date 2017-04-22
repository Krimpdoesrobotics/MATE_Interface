package com.company.ROV_Monitor.Custom_Components;

import javax.swing.*;

/**
 * Created by bandi on 2/18/2017.
 */
public class ThrustGraph extends JPanel {
    private int myThurster;
    private double myPowerPercentage;
    private static final int SCALE_FACTOR = 1;

    public ThrustGraph(int thrusterNum){
        myPowerPercentage = 0;
        myPowerPercentage = 0;
    }
    public void updatePercentage(double expectedPower){
        this.myPowerPercentage = expectedPower;
        this.repaint();
    }

    @Override
    public void repaint(){
    double graphCenter;
    }



}
