package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Richard on 3/25/2017.
 */

public class CustomPanel extends JPanel{
    // basically a JPanel except with the Paintings Class to allow for easy painting
    public Paintings InterfaceElements[];
    private int NumGraphics;
    private JPanel contentPanel;
    public CustomPanel(int NumGraphics1) {
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new BorderLayout(0, 0));
        NumGraphics = NumGraphics1;
        InterfaceElements = new Paintings[NumGraphics];
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < NumGraphics; i++) {
            InterfaceElements[i].GenericRepaint(g);
        }
    }
    public void Refresh()    {
        for(int i = 0; i < NumGraphics; i++) {
            if(InterfaceElements[i].getUpdated()){
                repaint(InterfaceElements[i].getRect());
            }
        }
    }
}