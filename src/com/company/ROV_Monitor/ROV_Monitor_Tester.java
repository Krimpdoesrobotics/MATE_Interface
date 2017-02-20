package com.company.ROV_Monitor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bandi on 2/18/2017.
 */
public class ROV_Monitor_Tester {
    public static void main(String[]args){
        JFrame GUIWindow = new monitorGUI();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GUIWindow.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        GUIWindow.setTitle("Tech Sharks ROV Pilot");
        GUIWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIWindow.setVisible(true);
    }
}
