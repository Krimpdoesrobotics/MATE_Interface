package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomPanel extends JPanel{
    // basically a JPanel except with the Paintings Class to allow for easy painting
    Paintings InterfaceElements[];
    private int NumGraphics;
    CustomPanel(int NumGraphics1) {
        JPanel contentPanel = new JPanel();
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
    void Refresh()    {
        for(int i = 0; i < NumGraphics; i++) {
            if(InterfaceElements[i].getUpdated()){
                repaint(InterfaceElements[i].getRect());
            }
        }
    }
}