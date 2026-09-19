package client.java.io.quicksiiver.drillx.rendering.renderer;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

import client.java.io.quicksiiver.drillx.rendering.ColorPanel;
import main.java.io.quicksiiver.drillx.field.Drill;

// this class is a singleton
public class MainRenderer {
    private static MainRenderer instance;

    // stuff for initialization
    private JFrame screen = new JFrame();
    private ColorPanel panel;
    private Container pane;
    private Graphics graphics;

    public static MainRenderer getInstance(Drill d) {
        // make sure it has not been initialized yet
        if (instance == null) { instance = new MainRenderer(d); }
        return instance;
    }
    public static MainRenderer getInstance() {
        if (instance == null) { throw new IllegalStateException("MainRenderer has not been initialized"); }
        return instance;
    }

    // the private constructor makes it so there can only be one
    private MainRenderer(Drill d) {
        // load screen
        screen.setTitle("drillX");
        screen.setSize(300, 200);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up panel for drawing
        panel = new ColorPanel(Color.WHITE, d);

        // attach panel to contentPane for drawing
        pane = screen.getContentPane();
        pane.add(panel);

        // finish up
        screen.setVisible(true);
        // create graphics context to draw with
        graphics = panel.getGraphics();
    }

    public void render() {
        panel.repaint();
    }
}
