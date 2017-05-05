package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainInterfaceFrame extends JFrame{
    private static HashMap componentMap; // Parallel array of components on the form and their corresponding name (allows other classes access)
    private SerialCommunications SerialCommunication; // One instance of SerialCommunications class, which handles the output to serial
    private CustomPanel contentPane; // One instance of CustomPanel class (extends JFrame)
    private ControllerInput LogitechController = new ControllerInput(); //One instance of ControllerInputClass, which handles controllers
    private static DefaultListModel<String> modelSerialSent = new DefaultListModel<>(); // List Model for the Sent Serial Commands (debugging)
    private static DefaultListModel<String> modelSerialReceived = new DefaultListModel<>(); // List Model for the Received Serial Commands
    private int SelectedControllerIndex = 0; // Current Controller Selected
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run()  {
                try {
                    MainInterfaceFrame frame = new MainInterfaceFrame(); // declaration statement for the Frame
                    frame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    // simple subroutine to create components on the Form
    private void newComponent(String type, String name, Rectangle Bounds, String content, ActionListener listener, boolean visibility, CustomPanel panel){
        switch(type){
            case "JLabel":
                JLabel toReturn = new JLabel(content);
                toReturn.setName(name);
                toReturn.setBounds(Bounds);
                toReturn.setVisible(visibility);
                panel.add(toReturn,BorderLayout.CENTER);
            case "JComboBox":
                JComboBox obj = new JComboBox();
                obj.setName(name);
                obj.setBounds(Bounds);
                obj.setVisible(visibility);
                panel.add(obj,BorderLayout.CENTER);
            case "JButton":
                JButton button = new JButton(content);
                button.setName(name);
                button.setBounds(Bounds);
                button.addActionListener(listener);
                button.setVisible(visibility);
                panel.add(button,BorderLayout.CENTER);
        }
    }
    /**
     * Create the frame.
     */
    private MainInterfaceFrame()    {
        // setup of SerialCommunications instance
        SerialCommunication = new SerialCommunications(LogitechController.getController(0),LogitechController.getController(1));
        
        //setup of form
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() + 60, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        setLocation(-10,0);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        contentPane = new CustomPanel(25);
        
        // setup of Graphics via the CustomPanel class
        //Left Analog stick display
        contentPane.InterfaceElements[0] = new Paintings(150,575,200,200,0,Color.BLACK,LogitechController.getController(0).getLeftAnalogUpdatedH());
        contentPane.InterfaceElements[0].setReferenceType0(LogitechController.getController(0).getXValueH(),LogitechController.getController(0).getYValueH(),Color.BLACK,Color.CYAN);
        //Right Analog stick display
        contentPane.InterfaceElements[1] = new Paintings(400,575,200,200,0,Color.BLACK,LogitechController.getController(0).getRightAnalogUpdatedH());
        contentPane.InterfaceElements[1].setReferenceType0(LogitechController.getController(0).getXRotationH(),LogitechController.getController(0).getYRotationH(),Color.BLACK,Color.CYAN);
        //Up or down
        contentPane.InterfaceElements[2] = new Paintings(275,300,200,50,3,Color.BLACK,LogitechController.getController(0).getUpdatedH(4));
        contentPane.InterfaceElements[2].setReferenceType34(LogitechController.getController(0).getZAxisH(),Color.WHITE,Color.CYAN);
        //button maybe A
        contentPane.InterfaceElements[3] = new Paintings(570,490,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(5));
        contentPane.InterfaceElements[3].setReferenceType2(LogitechController.getController(0).getButtonH(0),Color.WHITE, Color.CYAN);
        //button maybe B
        contentPane.InterfaceElements[4] = new Paintings(640,420,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(6));
        contentPane.InterfaceElements[4].setReferenceType2(LogitechController.getController(0).getButtonH(1),Color.WHITE, Color.CYAN);
        //button maybe x
        contentPane.InterfaceElements[5] = new Paintings(500,420,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(7));
        contentPane.InterfaceElements[5].setReferenceType2(LogitechController.getController(0).getButtonH(2),Color.WHITE, Color.CYAN);
        //button maybe y
        contentPane.InterfaceElements[6] = new Paintings(570,350,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(8));
        contentPane.InterfaceElements[6].setReferenceType2(LogitechController.getController(0).getButtonH(3),Color.WHITE, Color.CYAN);
        //button maybe L
        contentPane.InterfaceElements[7] = new Paintings(50,300,200,30,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(9));
        contentPane.InterfaceElements[7].setReferenceType2(LogitechController.getController(0).getButtonH(4),Color.WHITE, Color.CYAN);
        //button maybe R
        contentPane.InterfaceElements[8] = new Paintings(500,300,200,30,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(10));
        contentPane.InterfaceElements[8].setReferenceType2(LogitechController.getController(0).getButtonH(5),Color.WHITE, Color.CYAN);
        //button maybe back
        contentPane.InterfaceElements[9] = new Paintings(275,400,75,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(11));
        contentPane.InterfaceElements[9].setReferenceType2(LogitechController.getController(0).getButtonH(6),Color.WHITE, Color.CYAN);
        //button maybe start
        contentPane.InterfaceElements[10] = new Paintings(400,400,75,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(12));
        contentPane.InterfaceElements[10].setReferenceType2(LogitechController.getController(0).getButtonH(7),Color.WHITE, Color.CYAN);
        //button maybe L-stick
        contentPane.InterfaceElements[11] = new Paintings(50,575,75,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(13));
        contentPane.InterfaceElements[11].setReferenceType2(LogitechController.getController(0).getButtonH(8),Color.WHITE, Color.CYAN);
        //button maybe R-stick
        contentPane.InterfaceElements[12] = new Paintings(625,575,75,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(14));
        contentPane.InterfaceElements[12].setReferenceType2(LogitechController.getController(0).getButtonH(9),Color.WHITE, Color.CYAN);
        //d-pad display
        contentPane.InterfaceElements[13] = new Paintings(50,350,200,200,1,Color.BLACK,LogitechController.getController(0).getUpdatedH(15));
        contentPane.InterfaceElements[13].setReferenceType1(LogitechController.getController(0).getDPadH(),Color.BLACK, Color.CYAN);
        //motor 1 display
        contentPane.InterfaceElements[14] = new Paintings(750,300,50,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(0));
        contentPane.InterfaceElements[14].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(0),Color.CYAN,Color.BLACK);
        //motor 2 display
        contentPane.InterfaceElements[15] = new Paintings(950,300,50,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(1));
        contentPane.InterfaceElements[15].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(1),Color.CYAN,Color.BLACK);
        //motor 3 display
        contentPane.InterfaceElements[16] = new Paintings(750,525,50,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(2));
        contentPane.InterfaceElements[16].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(2),Color.CYAN,Color.BLACK);
        //motor 4 display
        contentPane.InterfaceElements[17] = new Paintings(950,525,50,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(3));
        contentPane.InterfaceElements[17].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(3),Color.CYAN,Color.BLACK);
        //motor 5 display
        contentPane.InterfaceElements[18] = new Paintings(815,410,50,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(4));
        contentPane.InterfaceElements[18].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(4),Color.CYAN,Color.BLACK);
        //motor 6 display
        contentPane.InterfaceElements[19] = new Paintings(885,410,50,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(5));
        contentPane.InterfaceElements[19].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(5),Color.CYAN,Color.BLACK);
        //gripper rotation
        contentPane.InterfaceElements[20] = new Paintings(750,750,115,35,3,Color.BLACK,SerialCommunication.getRobot().getUpdated(6));
        contentPane.InterfaceElements[20].setReferenceType34(SerialCommunication.getRobot().getGripperRotation(),Color.CYAN,Color.BLACK);
        //gripper clamp
        contentPane.InterfaceElements[21] = new Paintings(885,750,115,35,3,Color.BLACK,SerialCommunication.getRobot().getUpdated(7));
        contentPane.InterfaceElements[21].setReferenceType34(SerialCommunication.getRobot().getGripperClamp(),Color.CYAN,Color.BLACK);
        //camera pan
        contentPane.InterfaceElements[22] = new Paintings(750,800,115,35,3,Color.BLACK,SerialCommunication.getRobot().getUpdated(8));
        contentPane.InterfaceElements[22].setReferenceType34(SerialCommunication.getRobot().getCameraPan(),Color.CYAN,Color.BLACK);
        //camera tilt
        contentPane.InterfaceElements[23] = new Paintings(885,800,115,35,3,Color.BLACK,SerialCommunication.getRobot().getUpdated(9));
        contentPane.InterfaceElements[23].setReferenceType34(SerialCommunication.getRobot().getCameraTilt(),Color.CYAN,Color.BLACK);
        //received serial flash
        contentPane.InterfaceElements[24] = new Paintings(845, 10, 100, 30,2,Color.BLACK,SerialCommunication.getSerialReceivedU());
        contentPane.InterfaceElements[24].setReferenceType2(SerialCommunication.getSerialReceived(),Color.WHITE,Color.CYAN);

        setContentPane(contentPane);
        setBackground(new Color(0, 100, 100, 255));

        getContentPane().setLayout(null);
        // serial port label
        newComponent("JLabel", "lblChooseSerialPort", new Rectangle(50, 50, 130, 20), "Choose Serial Port", null, true, contentPane);
        // serial port selection box
        newComponent("JComboBox", "serialComboBox", new Rectangle(50, 80, 130, 30), null, null, true,contentPane);
        // refresh serial port selection box button
        newComponent("JButton", "btnSerialRefresh", new Rectangle(200, 50, 100, 40), "Refresh", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SerialCommunication.refreshPortList();
            }
        }, true, contentPane);
        // connect serial port button
        newComponent("JButton", "btnSerialConnect", new Rectangle(200, 100, 100, 40), "Connect", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SerialCommunication.btnSerialConnectClicked();
            }
        }, true, contentPane);
        // disconnect serial port button
        newComponent("JButton", "btnSerialDisconnect", new Rectangle(200, 150, 100, 40), "Disconnect", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SerialCommunication.btnSerialDisconnectClicked();
            }
        }, false, contentPane);
        // sent serial messages display label
        newComponent("JLabel", "lblSerialSent", new Rectangle(625, 50, 130, 20), "Sent Serial Messages", null, true, contentPane);
        // sent serial messages display
        JList<String> listSerialSent = new JList<>(modelSerialSent);
        JScrollPane scrollPaneSerialSent = new JScrollPane();
        scrollPaneSerialSent.setViewportView(listSerialSent);
        scrollPaneSerialSent.setName("scrollPaneSerialSent");
        scrollPaneSerialSent.setBounds(new Rectangle(625, 80, 175, 150));
        contentPane.add(scrollPaneSerialSent, BorderLayout.CENTER);
        // recieved serial messages display label
        newComponent("JLabel", "lblSerialReceived", new Rectangle(825, 50, 170, 20), "Received Serial Messages", null, true, contentPane);
        // recieved serial messages display
        JList<String> listSerialReceived = new JList<>(modelSerialReceived);
        JScrollPane scrollPaneSerialReceived = new JScrollPane();
        scrollPaneSerialReceived.setViewportView(listSerialReceived);
        scrollPaneSerialReceived.setName("scrollPaneSerialReceived");
        scrollPaneSerialReceived.setBounds(new Rectangle(825, 80, 175, 150));
        contentPane.add(scrollPaneSerialReceived, BorderLayout.CENTER);
        // choose controller label
        newComponent("JLabel", "lblChooseController", new Rectangle(350, 50, 130, 20), "Choose Controller", null, true, contentPane);
        // choose controller selection box
        JComboBox ControllerComboBox = new JComboBox();
        ControllerComboBox.setBounds(new Rectangle(350, 80, 130, 30));
        ControllerComboBox.setName("ControllerComboBox");
        ControllerComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogitechController.ControllerComboBoxSelection();
            }
        });
        contentPane.add(ControllerComboBox, BorderLayout.CENTER);
        // refresh controller selection box button
        newComponent("JButton", "btnControllerRefresh", new Rectangle(500, 50, 100, 40), "Refresh", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogitechController.btnControllerRefreshClicked();
            }
        }, true, contentPane);
        // connect controller button
        newComponent("JButton", "btnControllerConnect", new Rectangle(500, 100, 100, 40), "Connect", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogitechController.btnControllerConnectClicked(SelectedControllerIndex);updateVisibility();
            }
        }, true, contentPane);
        // disconnect controller button
        newComponent("JButton", "btnControllerDisconnect", new Rectangle(500, 150, 100, 40), "Disconnect", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogitechController.btnControllerDisconnectClicked(SelectedControllerIndex);updateVisibility();
            }
        }, false, contentPane);
        // radio buttons for controller selection
        JRadioButton Controller1RadioButton = new JRadioButton("Cont. 1");
        Controller1RadioButton.setBounds(380,200,100,40);
        Controller1RadioButton.setName("Controller1RadioButton");
        Controller1RadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                SelectedControllerIndex = 0;
                JRadioButton stuff = (JRadioButton) getComponentByName("Controller2RadioButton");
                if(stuff != null){
                    stuff.setSelected(false);
                }
                updateVisibility();
            }
        });
        Controller1RadioButton.setSelected(true);
        contentPane.add(Controller1RadioButton, BorderLayout.CENTER);
        JRadioButton Controller2RadioButton = new JRadioButton("Cont. 2");
        Controller2RadioButton.setBounds(500,200,100,40);
        Controller2RadioButton.setName("Controller2RadioButton");
        Controller2RadioButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                SelectedControllerIndex = 1;
                JRadioButton stuff = (JRadioButton) getComponentByName("Controller1RadioButton");
                if(stuff != null){
                    stuff.setSelected(false);
                }
                updateVisibility();
            }
        });
        contentPane.add(Controller2RadioButton, BorderLayout.CENTER);
        //Evan and Zach's displays
        JLabel lblBorder = new JLabel();
        lblBorder.setBounds(1040, 0, 20, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        lblBorder.setOpaque(true);
        lblBorder.setBackground(Color.BLACK);
        contentPane.add(lblBorder, BorderLayout.CENTER);

        JLabel lblHBorder = new JLabel();
        lblHBorder.setBounds(0, 250, 1050, 20);
        lblHBorder.setOpaque(true);
        lblHBorder.setBackground(Color.BLACK);
        contentPane.add(lblHBorder, BorderLayout.CENTER);

        // checkboxes and the tasks variables

        final ArrayList<JCheckBox> arrChkTasks = new ArrayList<>();
        ArrayList<JLabel> arrLblTasks = new ArrayList<>();
        ArrayList<Integer> arrTasksPoints = new ArrayList<>();
        JLabel lblTasks = new JLabel("Tasks to be Completed");
        final JLabel lblPoints = new JLabel("0");
        final int totalTasks = 17;

        // single tasks
        lblTasks.setOpaque(true);
        lblTasks.setBackground(Color.WHITE);
        lblTasks.setBounds(1100, 50, 780, 40);
        lblTasks.setHorizontalAlignment(SwingConstants.CENTER);
        lblTasks.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        contentPane.add(lblTasks, BorderLayout.CENTER);

        for (int l = 0; l < totalTasks; l++) {
            // points
            arrTasksPoints.add(0);

            // labels
            arrLblTasks.add(new JLabel());
            arrLblTasks.get(l).setOpaque(true);
            arrLblTasks.get(l).setBackground(Color.WHITE);
            arrLblTasks.get(l).setBounds(1100, 100 + (l * 40), 50, 40);
            arrLblTasks.get(l).setHorizontalAlignment(SwingConstants.CENTER);
            arrLblTasks.get(l).setText(String.valueOf(arrTasksPoints.get(l)));
            arrLblTasks.get(l).setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            contentPane.add(arrLblTasks.get(l), BorderLayout.CENTER);

            // checkboxes
            arrChkTasks.add(new JCheckBox());
            arrChkTasks.get(l).setBackground(Color.BLACK);
            arrChkTasks.get(l).setBounds(1160, 100 + (l * 40), 720, 40);
            arrChkTasks.get(l).setFont(new Font("Comic Sans MS", Font.PLAIN, 16 ));
            arrChkTasks.get(l).setForeground(Color.WHITE);
            contentPane.add(arrChkTasks.get(l), BorderLayout.CENTER);
        }

        lblPoints.setBounds(1100, 900, 780, 40);
        lblPoints.setOpaque(true);
        lblPoints.setBackground(Color.WHITE);
        lblPoints.setHorizontalAlignment(SwingConstants.CENTER);
        lblPoints.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        contentPane.add(lblPoints, BorderLayout.CENTER);

        arrChkTasks.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(0).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 20;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(1).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(2).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 10;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(3).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(4).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(4).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 10;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(5).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(5).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(6).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(6).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(7).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(7).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(8).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(8).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(9).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(9).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 10;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(10).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(10).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 10;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(11).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(11).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(12).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(12).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(13).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(13).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 15;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(14).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(14).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(15).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(15).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 10;
                lblPoints.setText("Total Points: " + points);
            }
        });
        arrChkTasks.get(16).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points = 0;
                arrChkTasks.get(16).setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 10;
                lblPoints.setText("Total Points: " + points);
            }
        });

        arrLblTasks.get(0).setText("20");
        arrLblTasks.get(1).setText("5");
        arrLblTasks.get(2).setText("10");
        arrLblTasks.get(3).setText("5");
        arrLblTasks.get(4).setText("10");
        arrLblTasks.get(5).setText("5");
        arrLblTasks.get(6).setText("5");
        arrLblTasks.get(7).setText("5");
        arrLblTasks.get(8).setText("5");
        arrLblTasks.get(9).setText("10");
        arrLblTasks.get(10).setText("10");
        arrLblTasks.get(11).setText("5");
        arrLblTasks.get(12).setText("5");
        arrLblTasks.get(13).setText("15");
        arrLblTasks.get(14).setText("5");
        arrLblTasks.get(15).setText("10");
        arrLblTasks.get(16).setText("10");

        arrChkTasks.get(0).setText("Installing the frame onto the baseplate");
        arrChkTasks.get(1).setText("Removing a pin to release the chains holding the frame");
        arrChkTasks.get(2).setText("Transporting and positioning the hose for pouring concrete into the frame");
        arrChkTasks.get(3).setText("Disconnecting the power cable from the platform");
        arrChkTasks.get(4).setText("Turning the valve to stop the flow of water to the platform");
        arrChkTasks.get(5).setText("Disengaging the locking mechanism at the base of the fountain");
        arrChkTasks.get(6).setText("Removing the old fountain");
        arrChkTasks.get(7).setText("Installing the new fountain");
        arrChkTasks.get(8).setText("Re-engaging the locking mechanism at the base of the fountain");
        arrChkTasks.get(9).setText("Turning the valve to restore the flow of water to the platform");
        arrChkTasks.get(10).setText("Reconnecting the power cable to the platform");
        arrChkTasks.get(11).setText("Returning the old fountain to the surface, side of the pool");
        arrChkTasks.get(12).setText("Using a simulated Raman spectrometer to determine if contaminants are present in two areas");
        arrChkTasks.get(13).setText("Placing a cap over the contaminated area");
        arrChkTasks.get(14).setText("Locating the four cargo containers");
        arrChkTasks.get(15).setText("Attaching a buoy marker to the U-bolt on the container with the highest-risk cargo");
        arrChkTasks.get(16).setText("Using the distance and direction to make a survey map of the incident site");

        // multiple tasks
        JLabel lblRebar = new JLabel("2 Rebar inserts");
        lblRebar.setBounds(1100, 790, 200, 25);
        lblRebar.setOpaque(true);
        lblRebar.setBackground(Color.WHITE);
        lblRebar.setHorizontalAlignment(SwingConstants.CENTER);
        lblRebar.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblRebar, BorderLayout.CENTER);

        JLabel lblBeacons = new JLabel("3 Pos. Beacons");
        lblBeacons.setBounds(1100, 815, 200, 25);
        lblBeacons.setOpaque(true);
        lblBeacons.setBackground(Color.WHITE);
        lblBeacons.setHorizontalAlignment(SwingConstants.CENTER);
        lblBeacons.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblBeacons, BorderLayout.CENTER);

        JLabel lblClams = new JLabel("2 Clams");
        lblClams.setBounds(1100, 840, 200, 25);
        lblClams.setOpaque(true);
        lblClams.setBackground(Color.WHITE);
        lblClams.setHorizontalAlignment(SwingConstants.CENTER);
        lblClams.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblClams, BorderLayout.CENTER);

        JLabel lblSediment = new JLabel("Amount of Sediment");
        lblSediment.setBounds(1100, 865, 200, 25);
        lblSediment.setOpaque(true);
        lblSediment.setBackground(Color.WHITE);
        lblSediment.setHorizontalAlignment(SwingConstants.CENTER);
        lblSediment.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblSediment, BorderLayout.CENTER);

        JLabel lblSensors = new JLabel("4 Sensors");
        lblSensors.setBounds(1500, 790, 200, 25);
        lblSensors.setOpaque(true);
        lblSensors.setBackground(Color.WHITE);
        lblSensors.setHorizontalAlignment(SwingConstants.CENTER);
        lblSensors.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblSensors, BorderLayout.CENTER);

        JLabel lblRFID = new JLabel("4 FRID's");
        lblRFID.setBounds(1500, 815, 200, 25);
        lblRFID.setOpaque(true);
        lblRFID.setBackground(Color.WHITE);
        lblRFID.setHorizontalAlignment(SwingConstants.CENTER);
        lblRFID.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblRFID, BorderLayout.CENTER);

        JLabel lblLengths = new JLabel("3 Lengths");
        lblLengths.setBounds(1500, 840, 200, 25);
        lblLengths.setOpaque(true);
        lblLengths.setBackground(Color.WHITE);
        lblLengths.setHorizontalAlignment(SwingConstants.CENTER);
        lblLengths.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblLengths, BorderLayout.CENTER);

        JLabel lblWhite = new JLabel();
        lblWhite.setBounds(1500, 865, 200, 25);
        lblWhite.setOpaque(true);
        lblWhite.setBackground(Color.WHITE);
        lblWhite.setHorizontalAlignment(SwingConstants.CENTER);
        lblWhite.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        contentPane.add(lblWhite, BorderLayout.CENTER);

        final JCheckBox ChkRebar1 = new JCheckBox();
        ChkRebar1.setBounds(1310, 790, 30, 25);
        ChkRebar1.setOpaque(true);
        ChkRebar1.setBackground(new Color(0, 200, 150, 255));
        ChkRebar1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkRebar1, BorderLayout.CENTER);

        final JCheckBox ChkRebar2 = new JCheckBox();
        ChkRebar2.setBounds(1340, 790, 30, 25);
        ChkRebar2.setOpaque(true);
        ChkRebar2.setBackground(new Color(0, 200, 150, 255));
        ChkRebar2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkRebar2, BorderLayout.CENTER);

        final JCheckBox ChkBeacon1 = new JCheckBox();
        ChkBeacon1.setBounds(1310, 815, 30, 25);
        ChkBeacon1.setOpaque(true);
        ChkBeacon1.setBackground(new Color(0, 200, 150, 255));
        ChkBeacon1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkBeacon1, BorderLayout.CENTER);

        final JCheckBox ChkBeacon2 = new JCheckBox();
        ChkBeacon2.setBounds(1340, 815, 30, 25);
        ChkBeacon2.setOpaque(true);
        ChkBeacon2.setBackground(new Color(0, 200, 150, 255));
        ChkBeacon2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkBeacon2, BorderLayout.CENTER);

        final JCheckBox ChkBeacon3 = new JCheckBox();
        ChkBeacon3.setBounds(1370, 815, 30, 25);
        ChkBeacon3.setOpaque(true);
        ChkBeacon3.setBackground(new Color(0, 200, 150, 255));
        ChkBeacon3.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkBeacon3, BorderLayout.CENTER);

        final JCheckBox ChkClam1 = new JCheckBox();
        ChkClam1.setBounds(1310, 840, 30, 25);
        ChkClam1.setOpaque(true);
        ChkClam1.setBackground(new Color(0, 200, 150, 255));
        ChkClam1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkClam1, BorderLayout.CENTER);

        final JCheckBox ChkClam2 = new JCheckBox();
        ChkClam2.setBounds(1340, 840, 30, 25);
        ChkClam2.setOpaque(true);
        ChkClam2.setBackground(new Color(0, 200, 150, 255));
        ChkClam2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkClam2, BorderLayout.CENTER);

        final JCheckBox ChkSensor1 = new JCheckBox();
        ChkSensor1.setBounds(1710, 790, 30, 25);
        ChkSensor1.setOpaque(true);
        ChkSensor1.setBackground(new Color(0, 200, 150, 255));
        ChkSensor1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkSensor1, BorderLayout.CENTER);

        final JCheckBox ChkSensor2 = new JCheckBox();
        ChkSensor2.setBounds(1740, 790, 30, 25);
        ChkSensor2.setOpaque(true);
        ChkSensor2.setBackground(new Color(0, 200, 150, 255));
        ChkSensor2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkSensor2, BorderLayout.CENTER);

        final JCheckBox ChkSensor3 = new JCheckBox();
        ChkSensor3.setBounds(1770, 790, 30, 25);
        ChkSensor3.setOpaque(true);
        ChkSensor3.setBackground(new Color(0, 200, 150, 255));
        ChkSensor3.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkSensor3, BorderLayout.CENTER);

        final JCheckBox ChkSensor4 = new JCheckBox();
        ChkSensor4.setBounds(1800, 790, 30, 25);
        ChkSensor4.setOpaque(true);
        ChkSensor4.setBackground(new Color(0, 200, 150, 255));
        ChkSensor4.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkSensor4, BorderLayout.CENTER);

        final JCheckBox ChkFRID1 = new JCheckBox();
        ChkFRID1.setBounds(1710, 815, 30, 25);
        ChkFRID1.setOpaque(true);
        ChkFRID1.setBackground(new Color(0, 200, 150, 255));
        ChkFRID1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkFRID1, BorderLayout.CENTER);

        final JCheckBox ChkFRID2 = new JCheckBox();
        ChkFRID2.setBounds(1740, 815, 30, 25);
        ChkFRID2.setOpaque(true);
        ChkFRID2.setBackground(new Color(0, 200, 150, 255));
        ChkFRID2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkFRID2, BorderLayout.CENTER);

        final JCheckBox ChkFRID3 = new JCheckBox();
        ChkFRID3.setBounds(1770, 815, 30, 25);
        ChkFRID3.setOpaque(true);
        ChkFRID3.setBackground(new Color(0, 200, 150, 255));
        ChkFRID3.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkFRID3, BorderLayout.CENTER);

        final JCheckBox ChkFRID4 = new JCheckBox();
        ChkFRID4.setBounds(1800, 815, 30, 25);
        ChkFRID4.setOpaque(true);
        ChkFRID4.setBackground(new Color(0, 200, 150, 255));
        ChkFRID4.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkFRID4, BorderLayout.CENTER);

        final JCheckBox ChkLength1 = new JCheckBox();
        ChkLength1.setBounds(1710, 840, 30, 25);
        ChkLength1.setOpaque(true);
        ChkLength1.setBackground(new Color(0, 200, 150, 255));
        ChkLength1.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkLength1, BorderLayout.CENTER);

        final JCheckBox ChkLength2 = new JCheckBox();
        ChkLength2.setBounds(1740, 840, 30, 25);
        ChkLength2.setOpaque(true);
        ChkLength2.setBackground(new Color(0, 200, 150, 255));
        ChkLength2.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkLength2, BorderLayout.CENTER);

        final JCheckBox ChkLength3 = new JCheckBox();
        ChkLength3.setBounds(1770, 840, 30, 25);
        ChkLength3.setOpaque(true);
        ChkLength3.setBackground(new Color(0, 200, 150, 255));
        ChkLength3.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(ChkLength3, BorderLayout.CENTER);

        // mL input

        final JTextField txtML = new JTextField();
        txtML.setBounds(1310, 865, 90, 25);
        txtML.setOpaque(true);
        txtML.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        txtML.setBackground(Color.WHITE);
        txtML.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(txtML, BorderLayout.CENTER);

        final JButton btnML = new JButton("CALC");
        btnML.setBounds(1400, 865, 90, 25);
        btnML.setOpaque(true);
        btnML.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        btnML.setBackground(Color.WHITE);
        btnML.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(btnML, BorderLayout.CENTER);

        ChkRebar1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkRebar1.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkRebar2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkRebar2.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkBeacon1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkBeacon1.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkBeacon2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkBeacon2.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkBeacon3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkBeacon3.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkClam1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkClam1.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkClam2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkClam2.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkSensor1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkSensor1.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkSensor2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkSensor2.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkSensor3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkSensor3.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkSensor4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkSensor4.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkFRID1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkFRID1.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkFRID2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkFRID2.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkFRID3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkFRID3.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkFRID4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkFRID4.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkLength1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkLength1.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkLength2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkLength2.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        ChkLength3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                ChkLength3.setSelected(true);
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                points += 5;
                lblPoints.setText("Total Points: " + points);
            }
        });
        btnML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int points;
                if (lblPoints.getText().length() < 2) {
                    points = 0;
                } else {
                    points = Integer.parseInt(lblPoints.getText().substring(14, lblPoints.getText().length()));
                }
                //
                // Calc points
                //
                if (Integer.parseInt(txtML.getText()) < 10) {
                    points += 0;
                } else if (Integer.parseInt(txtML.getText()) >= 10 && Integer.parseInt(txtML.getText()) < 100) {
                    points += 10;
                } else if (Integer.parseInt(txtML.getText()) >= 100) {
                    points += 30;
                }
                lblPoints.setText("Total Points: " + points);
            }
        });

        Timer FrameRefreshTimer = new Timer(); // Timer to refresh items on the form
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() { // Timertask for the FrameRefresh Timer
                contentPane.Refresh();       // Calls the Refresh subroutine, which updates all changed components via .paint method
                LogitechController.getController(0).resetUpdated(); // resets the updated components to false
                SerialCommunication.resetSerialReceivedU(); // There is a blinker above the Controller Selection Objects which blinks whenever Telemetry is Received
                SerialCommunication.resetSerialReceived();  // These 2 subroutines reset the blinker to its normal state
                SerialCommunication.getRobot().resetUpdated(); // resets the updated robot information from telemetry
            }
        };
        FrameRefreshTimer.schedule(timerTask,50);

        // colour
        contentPane.setBackground(new Color(0, 200, 150, 255));
        createComponentMap();
    }

    public void createComponentMap()    {
        componentMap = new HashMap<String,Component>();
        Component[] components = getContentPane().getComponents();
        for (int i=0; i < components.length; i++)        {
            componentMap.put(components[i].getName(), components[i]);
            System.out.println(components[i].getName());
        }
    }

    static Component getComponentByName(String name)    {
        if (componentMap.containsKey(name))  return (Component) componentMap.get(name);
        else return null;
    }

    static String getSelectedPort()    {
        Component SomeComponent = getComponentByName("serialComboBox");
        if(SomeComponent instanceof JComboBox){
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            return (String) SomeComboBox.getSelectedItem();
        }
        else return null;
    }

    static void addSerialSent(String obj)    {
        modelSerialSent.addElement(obj);
        if(modelSerialSent.getSize()>10){
            modelSerialSent.removeElementAt(0);
        }
    }

    static void addSerialReceived(String obj)    {
        modelSerialReceived.addElement(obj);
        if(modelSerialReceived.getSize()>10){
            modelSerialReceived.removeElementAt(0);
        }
    }

    private void updateVisibility(){
        if(LogitechController.getController(SelectedControllerIndex).isConnected()){
            setVisibility(getComponentByName("btnControllerConnect"),false);
            setVisibility(getComponentByName("btnControllerDisconnect"),true);
        }else{
            setVisibility(getComponentByName("btnControllerConnect"),true);
            setVisibility(getComponentByName("btnControllerDisconnect"),false);
        }
    }
    static void setVisibility(Component comp, boolean Visibility){
        if(comp != null){
            comp.setVisible(Visibility);
        }
    }
    // COMPONENTS LIST //
    // 0: Y AXIS ABSOLUTE ANALOG: Left Stick X
    // 1: X AXIS ABSOLUTE ANALOG: Left Stick Y
    // 2: X ROTATION ABSOLUTE ANALOG: Right Stick X
    // 3: Y ROTATION ABSOLUTE ANALOG: Right Stick Y
    // 4: Z AXIS ABSOLUTE ANALOG: Sum of Left Trigger(+) and Right Trigger(-)
    // 5: BUTTON 0: A Button (FL Test temporarily)
    // 6: BUTTON 1: B Button (FR Test temporarily)
    // 7: BUTTON 2: X Button (BL Test temporarily)
    // 8: BUTTON 3: Y Button (BR Test temporarily)
    // 9: BUTTON 4: L Button (FL Test temporarily)
    // 10: BUTTON 5: R Button (FR Test temporarily)
    // 11: BUTTON 6: Back Button
    // 12: BUTTON 7: Start Button (Stops everything when held)
    // 13: BUTTON 8: L Stick Push Down (Decrease throttle to 1/4 of usual)
    // 14: BUTTON 9: R Stick Push Down (Decrease throttle to 1/4 of usual)
    // 15: HAT SWITCH: D Pad 0 Nothing .125 FL .25 F, .375 FR, etc..... 1.0 L
    // --------------- //

}
