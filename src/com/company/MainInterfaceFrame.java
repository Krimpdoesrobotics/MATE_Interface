package com.company;
/**
 * Created by Richard on 2/17/2017.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class MainInterfaceFrame extends JFrame
{
    private final int NumGraphics = 22;
    private static HashMap componentMap;
    private SerialCommunications SerialCommunication;
    private CustomPanel contentPane;
    private ControllerInput LogitechController = new ControllerInput();
    private Timer ControllerRefreshTimer;
    private static DefaultListModel<String> modelSerialSent = new DefaultListModel<>();
    private static DefaultListModel<String> modelSerialReceived = new DefaultListModel<>();
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run(){
            // TODO Auto-generated method stub
            //
            // Invoke your function here
            //
            LogitechController.UpdateController1Components();
            if(SerialCommunication.isOpen() && LogitechController.getController(0).isConnected()) {
                SerialCommunication.getControllerRobot().updateVariables();
            }
            contentPane.Refresh();
            if(SerialCommunication.isOpen()) {
                SerialCommunication.getControllerRobot().resetUpdated();
                SerialCommunication.sendRobotInfo();
            }
            if(LogitechController.getController(0).isConnected()){LogitechController.getController(0).resetUpdated();}
        }
    };
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run()  {
                try {
                    MainInterfaceFrame frame = new MainInterfaceFrame();
                    frame.setVisible(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Create the frame.
     */
    public MainInterfaceFrame()    {
        SerialCommunication = new SerialCommunications(LogitechController.getController(0),LogitechController.getController(1));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() + 60, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        setLocation(-10,0);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        contentPane = new CustomPanel(NumGraphics);
        //Left Analog stick display
        contentPane.InterfaceElements[0] = new Paintings(175,525,200,200,0,Color.BLACK,LogitechController.getController(0).getLeftAnalogUpdatedH());
        contentPane.InterfaceElements[0].setReferenceType0(LogitechController.getController(0).getXValueH(),LogitechController.getController(0).getYValueH(),Color.BLACK,Color.CYAN);
        //Right Analog stick display
        contentPane.InterfaceElements[1] = new Paintings(425,525,200,200,0,Color.BLACK,LogitechController.getController(0).getRightAnalogUpdatedH());
        contentPane.InterfaceElements[1].setReferenceType0(LogitechController.getController(0).getXRotationH(),LogitechController.getController(0).getYRotationH(),Color.BLACK,Color.CYAN);
        //Up or down
        contentPane.InterfaceElements[2] = new Paintings(300,250,200,50,3,Color.BLACK,LogitechController.getController(0).getUpdatedH(4));
        contentPane.InterfaceElements[2].setReferenceType34(LogitechController.getController(0).getZAxisH(),Color.WHITE,Color.CYAN);
        //button maybe A
        contentPane.InterfaceElements[3] = new Paintings(620,440,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(5));
        contentPane.InterfaceElements[3].setReferenceType2(LogitechController.getController(0).getButtonH(0),Color.WHITE, Color.CYAN);
        //button maybe B
        contentPane.InterfaceElements[4] = new Paintings(690,370,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(6));
        contentPane.InterfaceElements[4].setReferenceType2(LogitechController.getController(0).getButtonH(1),Color.WHITE, Color.CYAN);
        //button maybe x
        contentPane.InterfaceElements[5] = new Paintings(550,370,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(7));
        contentPane.InterfaceElements[5].setReferenceType2(LogitechController.getController(0).getButtonH(2),Color.WHITE, Color.CYAN);
        //button maybe y
        contentPane.InterfaceElements[6] = new Paintings(620,300,60,60,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(8));
        contentPane.InterfaceElements[6].setReferenceType2(LogitechController.getController(0).getButtonH(3),Color.WHITE, Color.CYAN);
        //button maybe L
        contentPane.InterfaceElements[7] = new Paintings(50,250,200,30,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(9));
        contentPane.InterfaceElements[7].setReferenceType2(LogitechController.getController(0).getButtonH(4),Color.WHITE, Color.CYAN);
        //button maybe R
        contentPane.InterfaceElements[8] = new Paintings(550,250,200,30,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(10));
        contentPane.InterfaceElements[8].setReferenceType2(LogitechController.getController(0).getButtonH(5),Color.WHITE, Color.CYAN);
        //button maybe back
        contentPane.InterfaceElements[9] = new Paintings(300,350,80,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(11));
        contentPane.InterfaceElements[9].setReferenceType2(LogitechController.getController(0).getButtonH(6),Color.WHITE, Color.CYAN);
        //button maybe start
        contentPane.InterfaceElements[10] = new Paintings(420,350,80,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(12));
        contentPane.InterfaceElements[10].setReferenceType2(LogitechController.getController(0).getButtonH(7),Color.WHITE, Color.CYAN);
        //button maybe L-stick
        contentPane.InterfaceElements[11] = new Paintings(50,525,75,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(13));
        contentPane.InterfaceElements[11].setReferenceType2(LogitechController.getController(0).getButtonH(8),Color.WHITE, Color.CYAN);
        //button maybe R-stick
        contentPane.InterfaceElements[12] = new Paintings(675,525,75,100,2,Color.BLACK,LogitechController.getController(0).getUpdatedH(14));
        contentPane.InterfaceElements[12].setReferenceType2(LogitechController.getController(0).getButtonH(9),Color.WHITE, Color.CYAN);
        //d-pad display
        contentPane.InterfaceElements[13] = new Paintings(50,300,200,200,1,Color.BLACK,LogitechController.getController(0).getUpdatedH(15));
        contentPane.InterfaceElements[13].setReferenceType1(LogitechController.getController(0).getDPadH(),Color.BLACK, Color.CYAN);
        //motor 1 display
        contentPane.InterfaceElements[14] = new Paintings(110,755,80,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(0));
        contentPane.InterfaceElements[14].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(0),Color.CYAN,Color.BLACK);
        //motor 2 display
        contentPane.InterfaceElements[15] = new Paintings(210,755,80,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(1));
        contentPane.InterfaceElements[15].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(1),Color.CYAN,Color.BLACK);
        //motor 3 display
        contentPane.InterfaceElements[16] = new Paintings(310,755,80,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(2));
        contentPane.InterfaceElements[16].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(2),Color.CYAN,Color.BLACK);
        //motor 4 display
        contentPane.InterfaceElements[17] = new Paintings(410,755,80,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(3));
        contentPane.InterfaceElements[17].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(3),Color.CYAN,Color.BLACK);
        //motor 5 display
        contentPane.InterfaceElements[18] = new Paintings(510,755,80,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(4));
        contentPane.InterfaceElements[18].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(4),Color.CYAN,Color.BLACK);
        //motor 6 display
        contentPane.InterfaceElements[19] = new Paintings(610,755,80,200,4,Color.BLACK,SerialCommunication.getRobot().getUpdated(5));
        contentPane.InterfaceElements[19].setReferenceType34(SerialCommunication.getRobot().getMotorSpeed(5),Color.CYAN,Color.BLACK);
        //gripper rotation
        contentPane.InterfaceElements[20] = new Paintings(710,755,200,30,3,Color.BLACK,SerialCommunication.getRobot().getUpdated(6));
        contentPane.InterfaceElements[20].setReferenceType34(SerialCommunication.getRobot().getGripperRotation(),Color.CYAN,Color.BLACK);
        //gripper clamp
        contentPane.InterfaceElements[21] = new Paintings(710,855,200,30,3,Color.BLACK,SerialCommunication.getRobot().getUpdated(7));
        contentPane.InterfaceElements[21].setReferenceType34(SerialCommunication.getRobot().getGripperClamp(),Color.CYAN,Color.BLACK);

        setContentPane(contentPane);
        setBackground(new Color(0, 100, 100, 255));

        getContentPane().setLayout(null);
        // serial port label
        JLabel lblChooseSerialPort = new JLabel("Choose Serial Port");
        lblChooseSerialPort.setName("lblChooseSerialPort");
        lblChooseSerialPort.setBounds(new Rectangle(50, 50, 130, 20));
        contentPane.add(lblChooseSerialPort, BorderLayout.CENTER);
        // serial port selection box
        JComboBox serialComboBox = new JComboBox();
        serialComboBox.setBounds(new Rectangle(50, 80, 130, 30));
        serialComboBox.setName("serialComboBox");
        contentPane.add(serialComboBox, BorderLayout.CENTER);
        // refresh serial port selection box button
        JButton btnSerialRefresh = new JButton("Refresh");
        btnSerialRefresh.setBounds(new Rectangle(200, 50, 100, 40));
        btnSerialRefresh.setName("btnSerialRefresh");
        btnSerialRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialRefreshClicked();}});
        contentPane.add(btnSerialRefresh, BorderLayout.CENTER);
        // connect serial port button
        JButton btnSerialConnect = new JButton("Connect");
        btnSerialConnect.setBounds(new Rectangle(200, 100, 100, 40));
        btnSerialConnect.setName("btnSerialConnect");
        btnSerialConnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialConnectClicked();}});
        contentPane.add(btnSerialConnect, BorderLayout.CENTER);
        // disconnect serial port button
        JButton btnSerialDisconnect = new JButton("Disconnect");
        btnSerialDisconnect.setBounds(new Rectangle(200, 150, 100, 40));
        btnSerialDisconnect.setName("btnSerialDisconnect");
        btnSerialDisconnect.setVisible(false);
        btnSerialDisconnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialDisconnectClicked();}});
        contentPane.add(btnSerialDisconnect, BorderLayout.CENTER);
        // send refresh button
        /*JButton btnSerialSendRefresh= new JButton("Send Refresh");
        btnSerialSendRefresh.setBounds(new Rectangle(75, 150, 100, 40));
        btnSerialSendRefresh.setName("btnSerialSendRefresh");
        btnSerialSendRefresh.setVisible(false);
        btnSerialSendRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.btnSerialSendRefreshClicked();}});
        contentPane.add(btnSerialSendRefresh, BorderLayout.CENTER);*/
        // sent serial messages display label
        JLabel lblSerialSent = new JLabel("Sent Serial Messages");
        lblSerialSent.setName("lblSerialSent");
        lblSerialSent.setBounds(new Rectangle(625, 50, 130, 20));
        contentPane.add(lblSerialSent, BorderLayout.CENTER);
        // sent serial messages display
        JList<String> listSerialSent = new JList<>(modelSerialSent);
        JScrollPane scrollPaneSerialSent = new JScrollPane();
        scrollPaneSerialSent.setViewportView(listSerialSent);
        scrollPaneSerialSent.setName("scrollPaneSerialSent");
        scrollPaneSerialSent.setBounds(new Rectangle(625, 80, 175, 150));
        contentPane.add(scrollPaneSerialSent, BorderLayout.CENTER);
        // recieved serial messages display label
        JLabel lblSerialReceived = new JLabel("Received Serial Messages");
        lblSerialReceived.setName("lblSerialReceived");
        lblSerialReceived.setBounds(new Rectangle(825, 50, 170, 20));
        contentPane.add(lblSerialReceived, BorderLayout.CENTER);
        // recieved serial messages display
        JList<String> listSerialReceived = new JList<>(modelSerialReceived);
        JScrollPane scrollPaneSerialReceived = new JScrollPane();
        scrollPaneSerialReceived.setViewportView(listSerialReceived);
        scrollPaneSerialReceived.setName("scrollPaneSerialReceived");
        scrollPaneSerialReceived.setBounds(new Rectangle(825, 80, 175, 150));
        contentPane.add(scrollPaneSerialReceived, BorderLayout.CENTER);
        // choose controller label
		JLabel lblChooseController = new JLabel("Choose Controller");
		lblChooseController.setName("lblChooseController");
		lblChooseController.setBounds(new Rectangle(350, 50, 130, 20));
		contentPane.add(lblChooseController, BorderLayout.CENTER);
        // choose controller selection box
		JComboBox ControllerComboBox = new JComboBox();
		ControllerComboBox.setBounds(new Rectangle(350, 80, 130, 30));
		ControllerComboBox.setName("ControllerComboBox");
		ControllerComboBox.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.ControllerComboBoxSelection();}});
		contentPane.add(ControllerComboBox, BorderLayout.CENTER);
        // refresh controller selection box button
		JButton btnControllerRefresh = new JButton("Refresh");
		btnControllerRefresh.setBounds(new Rectangle(500, 50, 100, 40));
		btnControllerRefresh.setName("btnControllerRefresh");
		btnControllerRefresh.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnControllerRefreshClicked();}});
		contentPane.add(btnControllerRefresh, BorderLayout.CENTER);
        // connect controller button
		JButton btnControllerConnect = new JButton("Connect");
		btnControllerConnect.setBounds(new Rectangle(500, 100, 100, 40));
		btnControllerConnect.setName("btnControllerConnect");
		btnControllerConnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnController1ConnectClicked();}});
		contentPane.add(btnControllerConnect, BorderLayout.CENTER);
        // disconnect controller button
		JButton btnControllerDisconnect = new JButton("Disconnect");
		btnControllerDisconnect.setBounds(new Rectangle(500, 150, 100, 40));
		btnControllerDisconnect.setName("btnControllerDisconnect");
		btnControllerDisconnect.setVisible(false);
		btnControllerDisconnect.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){LogitechController.btnController1DisconnectClicked();}});
		contentPane.add(btnControllerDisconnect, BorderLayout.CENTER);
        // send serial message text box
		/*final JTextField txtManualSerialSend = new JTextField("");
		txtManualSerialSend.setBounds(new Rectangle(50,200,200,30));
		txtManualSerialSend.setName("txtManualSerialSend");
		contentPane.add(txtManualSerialSend,BorderLayout.CENTER);
        // send serial message button
        JButton btnManualSerialSend = new JButton("Send");
        btnManualSerialSend.setBounds(new Rectangle(270, 200, 100, 30));
        btnManualSerialSend.setName("btnManualSerialSend");
        btnManualSerialSend.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){SerialCommunication.PortSender(txtManualSerialSend.getText().trim());}});
        contentPane.add(btnManualSerialSend, BorderLayout.CENTER);*/

        // line to seperate

        JLabel lblBorder = new JLabel();
        lblBorder.setBounds(1040, 0, 20, (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        lblBorder.setOpaque(true);
        lblBorder.setBackground(Color.BLACK);
        contentPane.add(lblBorder, BorderLayout.CENTER);

        // checkboxes and the tasks variables

        ArrayList<JCheckBox> arrChkTasks = new ArrayList<>();
        ArrayList<JLabel> arrLblTasks = new ArrayList<>();
        ArrayList<Integer> arrTasksPoints = new ArrayList<>();
        JLabel lblTasks = new JLabel("Tasks to be Completed");
        JLabel lblPoints = new JLabel();
        final int totalTasks = 20;

        // makes boxes
        lblTasks.setOpaque(true);
        lblTasks.setBackground(Color.WHITE);
        lblTasks.setBounds(1100, 50, 600, 40);
        lblTasks.setHorizontalAlignment(SwingConstants.CENTER);
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
            contentPane.add(arrLblTasks.get(l), BorderLayout.CENTER);

            // checkboxes
            arrChkTasks.add(new JCheckBox());
            arrChkTasks.get(l).setBackground(Color.BLACK);
            arrChkTasks.get(l).setBounds(1160, 100 + (l * 40), 540, 40);
            contentPane.add(arrChkTasks.get(l), BorderLayout.CENTER);
        }

        lblPoints.setBounds(1100, 910, 600, 40);
        lblPoints.setOpaque(true);
        lblPoints.setBackground(Color.WHITE);
        lblPoints.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblPoints, BorderLayout.CENTER);

        // timer
        ControllerRefreshTimer = new Timer();
        ControllerRefreshTimer.scheduleAtFixedRate(timerTask,1000,20);

        // colour
        contentPane.setBackground(new Color(0, 200, 150, 255));
        createComponentMap();
    }

    public static void scrollDown()    {
        Component SomeComponent = getComponentByName("scrollPaneSerialReceived");
        Component SomeComponent2 = getComponentByName("scrollPaneSerialSent");
        if(SomeComponent instanceof JScrollPane && SomeComponent2 instanceof  JScrollPane){
            JScrollPane ScrollPaneReceived = (JScrollPane) SomeComponent;
            JScrollPane ScrollPaneSent = (JScrollPane) SomeComponent2;
            ScrollPaneReceived.getVerticalScrollBar().setValue(ScrollPaneReceived.getVerticalScrollBar().getMaximum());
            ScrollPaneSent.getVerticalScrollBar().setValue(ScrollPaneSent.getVerticalScrollBar().getMaximum());
        }
    }

    public void createComponentMap()    {
        componentMap = new HashMap<String,Component>();
        Component[] components = getContentPane().getComponents();
        for (int i=0; i < components.length; i++)        {
            componentMap.put(components[i].getName(), components[i]);
            System.out.println(components[i].getName());
        }
    }

    public static Component getComponentByName(String name)    {
        if (componentMap.containsKey(name))  return (Component) componentMap.get(name);
        else return null;
    }

    public static String getSelectedPort()    {
        Component SomeComponent = getComponentByName("serialComboBox");
        if(SomeComponent instanceof JComboBox){
            JComboBox SomeComboBox = (JComboBox) SomeComponent;
            return (String) SomeComboBox.getSelectedItem();
        }
        else return null;
    }

    public static void addSerialSent(String obj)    {
        modelSerialSent.addElement(obj);
        if(modelSerialSent.capacity()> 27){
            modelSerialSent.removeRange(0,13);
        }
    }

    public static void addSerialReceived(String obj)    {
        modelSerialReceived.addElement(obj);
        if(modelSerialReceived.capacity() > 27){
            modelSerialReceived.removeRange(0,13);
        }
    }

    // COMPONENTS LIST //
    // 0: Y AXIS ABSOLUTE ANALOG: Left Stick X
    // 1: X AXIS ABSOLUTE ANALOG: Left Stick Y
    // 2: X ROTATION ABSOLUTE ANALOG: Right Stick X
    // 3: Y ROTATION ABSOLUTE ANALOG: Right Stick Y
    // 4: Z AXIS ABSOLUTE ANALOG: Sum of Left Trigger(+) and Right Trigger(-)
    // 5: BUTTON 0: A Button (PUMP IN)
    // 6: BUTTON 1: B Button (PUMP OUT)
    // 7: BUTTON 2: X Button (YOU ARE DRIVER 1)
    // 8: BUTTON 3: Y Button (YOU ARE DRIVER 2)
    // 9: BUTTON 4: L Button (not working well ATM)
    // 10: BUTTON 5: R Button
    // 11: BUTTON 6: Back Button (Flash Light On/Off)
    // 12: BUTTON 7: Start Button (MAKES YOU DRIVER)
    // 13: BUTTON 8: L Stick Push Down
    // 14: BUTTON 9: R Stick Push Down
    // 15: HAT SWITCH: D Pad 0 Nothing .125 FL .25 F, .375 FR, etc..... 1.0 L
    // --------------- //

}
