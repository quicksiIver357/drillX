package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import client.java.io.quicksiiver.drillx.rendering.renderer.layout.MainLayout;

public class MainPanel extends JPanel {
    public MainPanel(MainLayout b, Color bgColor) {
        super(b);
        setBackground(bgColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
    }
}