package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.security.InvalidParameterException;

import javax.swing.JPanel;

import client.java.io.quicksiiver.drillx.rendering.renderer.misc.Theme;
import main.java.io.quicksiiver.drillx.coordinates.Point;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Squad;

public class FieldPanel extends JPanel {
    // instance variables
    public Color lineColor, squadMemberColor, textColor, arrowColor;
    public Drill drill;
    public boolean showNumbers, showArrows;

    // these need validation, so stay private
    private String drawMode;

    // classifiers
    public static final String YARD = "yard";
    public static final String COORDINATE = "coordinate";

    public FieldPanel(Color bg, Color line, Color squadMember, Color text, Color arrow, Drill d) { this(bg, line, squadMember, text, arrow, FieldPanel.YARD, d, false, false, Theme.DEFAULT.font); }
    public FieldPanel(Drill d) { this(Theme.DEFAULT.field, Theme.DEFAULT.fieldLine, Theme.DEFAULT.fieldSquad, Theme.DEFAULT.fieldNumber, Theme.DEFAULT.fieldArrow, d); }
    public FieldPanel() { this(null); }
    public FieldPanel(Color bgColor, Color lineColor, Color squadMemberColor, Color textColor, Color arrowColor, String drawMode, Drill drill, boolean showNumbers, boolean showArrows, Font font) { 
        setBackground(bgColor);
        setDrawMode(drawMode);

        this.lineColor = lineColor;
        this.squadMemberColor = squadMemberColor;
        this.textColor = textColor;
        this.arrowColor = arrowColor;
        this.drill = drill;
        this.showNumbers = showNumbers;
        this.showArrows = showArrows;
    }

    // drawing
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // draw the lines
        for (int i = 0; i < 10; i++) {
            // helpers
            int x = i * getWidth() / 10;

            // vertical
            g2d.setColor(lineColor);
            g2d.drawLine(x, 0, x, getHeight());

            // horizontal (only used in coordinate system) and extra lines (8 to 5)
            if (drawMode.equals(COORDINATE)) { 
                g2d.drawLine(x + getWidth() / 20, 0, x + getWidth() / 20, getHeight()); // extra vertical

                
                int y = (int) ( i * getHeight() / 9 );
                g2d.drawLine(0, y, getWidth(), y); // horizontal
            } else { // otherwise draw the hash marks and such
                for (int s = 1; s < 5; s++) { 
                    double hashScale = 17.5 / 55;
                    int dx = s * getWidth() / 50;

                    g2d.drawLine(x + dx, (int) ( getHeight() * ( hashScale + 0.02 ) ), x + dx, (int) ( getHeight() * ( hashScale - 0.02 ) ));
                    g2d.drawLine(x + dx, getHeight() - (int) ( getHeight() * ( hashScale + 0.02 ) ), x + dx, getHeight() - (int) ( getHeight() * ( hashScale - 0.02 ) ));
                }
            }
        }

        // draw the squads
        if (drill != null) {
            for (int i = 0; i < drill.squads.size(); i++) {
                Squad squad = drill.squads.get(i);
                int x = (int) ( squad.getPos().getX() * getWidth() / 160 ); // x of squad
                int y = (int) ( squad.getPos().getY() * getHeight() / ( 640.0 / 9 ) ); // y of squad

                double tx = squad.getFormation().formation[0].getX() + squad.getFormation().formation[1].getX() + squad.getFormation().formation[2].getX() + squad.getFormation().formation[3].getX(); // total x
                double ty = squad.getFormation().formation[0].getY() + squad.getFormation().formation[1].getY() + squad.getFormation().formation[2].getY() + squad.getFormation().formation[3].getY(); // total y
                
                Point squadCenter = new Point(tx / 4 + x, ty / 4 + y);

                if (showNumbers) { 
                    // numbers
                    g2d.setFont(getFont().deriveFont(getWidth() / 500f * getFont().getSize()));
                    g2d.setColor(textColor);
                    g2d.drawString(squad.getKey() + (i + 1), (int) ( squadCenter.getX() * getWidth() / 160 ), (int) ( squadCenter.getY() * getHeight() * 640.0 / 9));
                } if (showArrows) { // should NOT be else if
                    Point arrowHead = new Point(squadCenter);
                    arrowHead.applySimpleDirectionalMovement(squad.getRotationDirection(), getWidth() / 50.0); // TODO: fix this line

                    // System.out.println("arrowHead: " + arrowHead); // debug
                    // System.out.println("squad pos: " + x + y);

                    g2d.setColor(arrowColor);
                    drawArrowLine(g2d, (int) squadCenter.getX(), (int) squadCenter.getY(), (int) arrowHead.getX(), (int) arrowHead.getY(), getWidth() / 100, getWidth() / 150);
                }

                // squad members
                g2d.setColor(squadMemberColor);
                
                for (Point p : squad.getFormation().formation) {
                    int rx = (int) ( p.getX() * getWidth() / 160 ); // relative x
                    int ry = (int) ( p.getY() * getHeight() / ( 640.0 / 9 ) ); // relative y
                    int size = getWidth() / 200; // radius of the circles

                    // System.out.println("rx: " + rx); // debug
                    // System.out.println("ry: " + ry);
                    // System.out.println("Size: " + size);

                    g2d.fillOval(x + rx - size, y + ry - size, size * 2, size * 2);
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

    // Source - https://stackoverflow.com/a/27461352
    // Posted by phibao37, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-09-20, License - CC BY-SA 3.0

    /**
     * Draw an arrow line between two points.
     * @param g2d the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     */
    private void drawArrowLine(final Graphics2D g2d, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g2d.setStroke(new BasicStroke(d / 3));
        g2d.drawLine(x1, y1, (int) ( x1 + 0.8 * ( x2 - x1 ) ), (int) ( y1 + 0.8 * ( y2 - y1 ) ));
        g2d.fillPolygon(xpoints, ypoints, 3);
    }

}
