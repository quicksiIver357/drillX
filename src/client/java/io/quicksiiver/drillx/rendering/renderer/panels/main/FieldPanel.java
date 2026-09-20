package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.Graphics;
import java.security.InvalidParameterException;

import javax.swing.JPanel;

import main.java.io.quicksiiver.drillx.coordinates.Point;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Squad;

public class FieldPanel extends JPanel {
    // instance variables
    public Color lineColor;
    public Color squadMemberColor;
    public Color textColor;
    public Drill drill;
    public boolean showNumbers;

    // these need validation, so stay private
    private String drawMode;

    // classifiers
    public static final String YARD = "yard";
    public static final String COORDINATE = "coordinate";

    public FieldPanel() { this(new Color(94, 169, 79), Color.WHITE, Color.RED, Color.BLACK, FieldPanel.YARD, null, false); }
    public FieldPanel(Color bgColor, Color lineColor, Color squadMemberColor, Color textColor, String drawMode, Drill drill, boolean showNumbers) { 
        setBackground(bgColor);
        setDrawMode(drawMode);

        this.lineColor = lineColor;
        this.squadMemberColor = squadMemberColor;
        this.textColor = textColor;
        this.drill = drill;
        this.showNumbers = showNumbers;
    }

    // drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw the lines
        for (int i = 0; i < 10; i++) {
            // helpers
            int x = i * getWidth() / 10;
            int y = i * getHeight() / 10;

            // vertical
            g.setColor(lineColor);
            g.drawLine(x, 0, x, getHeight());

            // horizontal (only used in coordinate system) and extra lines (8 to 5)
            if (drawMode.equals(COORDINATE)) { 
                g.drawLine(x + getWidth() / 20, 0, x + getWidth() / 20, getHeight()); // extra vertical
                g.drawLine(0, y, getWidth(), y); // horizontal
            }

            // otherwise draw the hash marks and such
            else {
                for (int s = 1; s < 5; s++) { 
                    double hashScale = 17.5 / 55;
                    int dx = s * getWidth() / 50;

                    g.drawLine(x + dx, (int) ( getHeight() * ( hashScale + 0.02 ) ), x + dx, (int) ( getHeight() * ( hashScale - 0.02 ) ));
                    g.drawLine(x + dx, getHeight() - (int) ( getHeight() * ( hashScale + 0.02 ) ), x + dx, getHeight() - (int) ( getHeight() * ( hashScale - 0.02 ) ));
                }
            }
        }

        // draw the squads
        if (drill != null) {
            for (int i = 0; i < drill.squads.size(); i++) {
                Squad squad = drill.squads.get(i);

                g.setColor(textColor);
                if (showNumbers) { g.drawString(squad.getKey() + i, (int) squad.getPos().getX(), (int) squad.getPos().getY()); } // numbers

                // squad members
                g.setColor(squadMemberColor);
                for (Point p : squad.getFormation().formation) {
                    int rx = (int) ( p.getX() * getWidth() / 192 ); // relative x
                    int ry = (int) ( p.getY() * getHeight() / 88 ); // relative y
                    int size = getWidth() / 100; // radius of the circles

                    g.drawOval(rx + size, ry + size, rx - size, ry - size);
                }
            }
        }
    }

    // setters
    public void setDrawMode(String drawMode) {
        if (drawMode.equals(YARD) || drawMode.equals(COORDINATE)) { this.drawMode = drawMode; }
        else { throw new InvalidParameterException("drawMode must either be YARDS or COORDINATES."); }
    }

    // getters
    public String getDrawMode() { return drawMode; }
}
