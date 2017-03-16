package com.company.ROV_Monitor.Custom_Components;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Created by bandi on 2/18/2017.
 */
public class GradientPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(0,50,60);
        Color color2 = new Color(70,70,100);
        GradientPaint gp = new GradientPaint(0, 0, color2, 0, h, color1);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
