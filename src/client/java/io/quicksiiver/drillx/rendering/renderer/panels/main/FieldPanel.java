package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.Color;
import java.awt.Graphics;
import java.security.InvalidParameterException;

import javax.swing.JPanel;

public class FieldPanel extends JPanel {
    public Color lineColor;
    private String drawMode;

    // classifiers
    public static final String YARD = "yard";
    public static final String COORDINATE = "coordinate";

    public FieldPanel(Color bgColor, Color lineColor, String drawMode) { 
        setBackground(bgColor);
        this.lineColor = lineColor;
        setDrawMode(drawMode);
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
    }

    // setters
    public void setDrawMode(String drawMode) {
        if (drawMode.equals(YARD) || drawMode.equals(COORDINATE)) { this.drawMode = drawMode; }
        else { throw new InvalidParameterException("drawMode must either be YARDS or COORDINATES."); }
    }

    // getters
    public String getDrawMode() { return drawMode; }
}
