package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.java.io.quicksiiver.drillx.field.Squad;

public class SquadPanel extends JPanel {
    private Squad squad;

    // stuff for drawing
    JTextArea noSquadSelectedText = new JTextArea("No squad selected. Click on a squad to select it.");

    public SquadPanel(final Color c) { this(c, null); }
    public SquadPanel(final Color c, Squad s) {
        setBackground(c);

        squad = s; // apply the reference

        // rendering
        noSquadSelectedText.setEditable(false);

        add(noSquadSelectedText);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;


    }

    // getters
    public Squad getSquad() { return new Squad(squad); }

    // setters
    public void setSelectedSquad(Squad s) {
        squad = s; 

        if (squad == null ^ noSquadSelectedText.isVisible()) { // if squad is null XOR squadSelected text is visible
            for (Component c : getComponents()) { c.setVisible(!c.isVisible()); } // swap visibility
        }

        // refresh screen
        revalidate();
        repaint();
    }
}
