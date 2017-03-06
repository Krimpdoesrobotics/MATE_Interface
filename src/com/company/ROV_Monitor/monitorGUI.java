package com.company.ROV_Monitor;

import com.company.ROV_Monitor.Custom_Components.BackGroundPanel;
import com.company.ROV_Monitor.Custom_Components.Button;
import com.company.ROV_Monitor.Custom_Components.GradientPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bandi on 2/18/2017.
 */
public class monitorGUI extends JFrame {
    JPanel backGroundPanel = new GradientPanel();
    JLabel lblStatus = new JLabel("Status:");

    Button test = new Button("Test",100,50,
            80,20,600,20);


    public monitorGUI(){
        setSize(new Dimension(300,300));
        Container container = getContentPane();
        container.add(test);
    }
}
