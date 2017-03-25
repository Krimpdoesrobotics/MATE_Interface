package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by julia on 3/25/2017.
 */

public class CustomPanel extends JPanel
{
    public Paintings InterfaceElements[] = new Paintings[1];
    private int NumGraphics;
    private JPanel contentPanel;
    private boolean Condition;
    /* 0:
    *
    *
    *
    */
    public CustomPanel(int NumGraphics1, boolean Condition1) {
        contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(new BorderLayout(0, 0));
        NumGraphics = NumGraphics1;
        InterfaceElements = new Paintings[NumGraphics];
        Condition = Condition1;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (Condition) {
            for (int i = 0; i < NumGraphics; i++) {
                InterfaceElements[i].GenericRepaint(g);
            }
        }
    }
    public void Refresh()
    {
        for(int i = 0; i < NumGraphics; i++){
            if(InterfaceElements[i].getUpdated()){
                repaint(InterfaceElements[i].getRect());
            }
        }
    }
}