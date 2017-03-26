package com.company;

import javax.swing.*;

/**
 * Created by Richard on 3/4/2017.
 */
public class ControllerInputFrame extends JFrame
{
    // instance variables
    // private data
    private JPanel contentPane;

    // constructors
    public ControllerInputFrame()
    {
        contentPane = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 25, 1500, 900);
        setContentPane(contentPane);
        getContentPane().setLayout(null);
    }
}