package com.company;

import javax.swing.*;

/**
 * Created by julia on 3/4/2017.
 */
public class ControllerInputFrame extends JFrame {
    private JPanel contentPane;
    public ControllerInputFrame(){
        contentPane = new JPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(25, 25, 1500, 900);
        setContentPane(contentPane);

        getContentPane().setLayout(null);

    }
}