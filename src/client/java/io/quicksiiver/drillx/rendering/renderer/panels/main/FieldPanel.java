package client.java.io.quicksiiver.drillx.rendering.renderer.panels.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.security.InvalidParameterException;

import javax.swing.JPanel;

import client.java.io.quicksiiver.drillx.rendering.renderer.misc.Theme;
import main.java.io.quicksiiver.drillx.coordinates.Point;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.RotationDirection;
import main.java.io.quicksiiver.drillx.field.Squad;

public class FieldPanel extends JPanel {
    // instance variables
    public Color lineColor, squadMemberColor, textColor, arrowColor, selectedSquadColor;
    public Drill drill;
    public boolean showNumbers, showArrows, dragSquads, snapToGrid;
    public int snapToGridSize;

    // these need validation or should not be changed, so stay private
    private String drawMode;
    private Squad selectedSquad;
    private MouseList mouseList = new MouseList();
    private MainPanel mainPanel;

    // classifiers
    public static final String YARD = "yard";
    public static final String COORDINATE = "coordinate";

    public FieldPanel(MainPanel mainPanel, Color bg, Color line, Color squadMember, Color text, Color arrow, Color selectedSquadColor, Drill d) { this(mainPanel, bg, line, squadMember, text, arrow, selectedSquadColor, FieldPanel.YARD, d, false, false, false, false, Theme.DEFAULT.font, 4); }
    public FieldPanel(MainPanel mainPanel, Drill d) { this(mainPanel, Theme.DEFAULT.field, Theme.DEFAULT.fieldLine, Theme.DEFAULT.fieldSquad, Theme.DEFAULT.fieldNumber, Theme.DEFAULT.fieldArrow, Theme.DEFAULT.fieldSelectedSquad, d); }
    public FieldPanel(MainPanel mainPanel) { this(mainPanel, null); }
    public FieldPanel(MainPanel mainPanel, Color bgColor, Color lineColor, Color squadMemberColor, Color textColor, Color arrowColor, Color selectedSquadColor, String drawMode, Drill drill, boolean showNumbers, boolean showArrows, boolean dragSquads, boolean snapToGrid, Font font, int snapToGridSize) { 
        setBackground(bgColor);
        setDrawMode(drawMode);

        this.lineColor = lineColor;
        this.squadMemberColor = squadMemberColor;
        this.textColor = textColor;
        this.arrowColor = arrowColor;
        this.selectedSquadColor = selectedSquadColor;
        this.drill = drill;
        this.showNumbers = showNumbers;
        this.showArrows = showArrows;
        this.snapToGrid = snapToGrid;
        this.dragSquads = dragSquads;
        this.mainPanel = mainPanel;
        this.snapToGridSize = snapToGridSize;

        addMouseListener(mouseList);
        addMouseMotionListener(mouseList);
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
            for (int i = 0; i < drill.getSquads().size(); i++) {
                // CALCULATIONS
                Squad squad = drill.getSquads().get(i);
                // calculate x and y that should be drawn on screen
                Point screenPos = convertToPanelCoords(squad.getPos().getX(), squad.getPos().getY());
                int x = (int) screenPos.getX();
                int y = (int) screenPos.getY();

                // calculate the squad center to be drawn on screen
                Point squadCenter = convertToPanelCoords(squad.getCenterPos());

                // squad members
                if (selectedSquad == squad) { g2d.setColor(selectedSquadColor); } 
                else { g2d.setColor(squadMemberColor); }
                
                
                // DRAWING
                for (Point p : squad.getFormation().formation) {
                    int rx = (int) ( p.getX() * getWidth() / 160 ); // relative x
                    int ry = (int) ( p.getY() * getHeight() / ( 640.0 / 9 ) ); // relative y
                    int size = getWidth() / 200; // radius of the circles

                    // System.out.println("rx: " + rx); // debug
                    // System.out.println("ry: " + ry);
                    // System.out.println("Size: " + size);

                    g2d.fillOval(x + rx - size, y + ry - size, size * 2, size * 2);
                }

                if (showNumbers) { 
                    // numbers
                    g2d.setFont(getFont().deriveFont(getWidth() / 500f * getFont().getSize()));
                    g2d.setColor(textColor);
                    g2d.drawString(squad.getKey() + (i + 1), (int) (squadCenter.getX()), (int) (squadCenter.getY()));
                } if (showArrows) { // should NOT be else if
                    Point arrowHead = new Point(squadCenter);
                    arrowHead.applySimpleDirectionalMovement(squad.getRotationDirection(), getWidth() / 50.0);

                    // System.out.println("arrowHead: " + arrowHead); // debug
                    // System.out.println("squad pos: " + x + y);

                    g2d.setColor(arrowColor);
                    drawArrowLine(g2d, (int) squadCenter.getX(), (int) squadCenter.getY(), (int) arrowHead.getX(), (int) arrowHead.getY(), getWidth() / 100, getWidth() / 150);
                }
            }
        }
    }

    // setters
    public void setDrawMode(String drawMode) {
        if (drawMode.equals(YARD) || drawMode.equals(COORDINATE)) { this.drawMode = drawMode; }
        else { throw new InvalidParameterException("drawMode must either be YARDS or COORDINATES."); }
    }
    public void setSelectedSquad(Squad s) { this.selectedSquad = s; }
    // sets things of the selected Squad
    public void setRotationDirection(RotationDirection rd) { selectedSquad.setRotationDirection(rd); }
    public void setFormation(Formation f) { selectedSquad.setFormation(f); }

    // getters
    public String getDrawMode() { return drawMode; }
    public Squad getSelectedSquad() { return selectedSquad; }

    // HELPERS
    private Point convertToPanelCoords(double x, double y) { return new Point(x * getWidth() / 160, y * getHeight() / 640 * 9); }
    // private Point convertToPanelCoords(int[] pos) { return convertToPanelCoords(pos[0], pos[1]); }
    // private Point convertToPanelCoords(double[] pos) { return convertToPanelCoords(pos[0], pos[1]); }
    private Point convertToPanelCoords(Point pos) { return convertToPanelCoords(pos.getX(), pos.getY()); }

    private Point convertFromPanelCoords(double x, double y) { return new Point(x / getWidth() * 160, y / getHeight() * 640 / 9); }
    // private Point convertFromPanelCoords(int[] pos) { return convertFromPanelCoords(pos[0], pos[1]); }
    // private Point convertFromPanelCoords(double[] pos) { return convertFromPanelCoords(pos[0], pos[1]); }
    private Point convertFromPanelCoords(Point pos) { return convertFromPanelCoords(pos.getX(), pos.getY()); }


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


    // input handling
    private class MouseList implements MouseListener, MouseMotionListener {
        @Override
        public void mousePressed(MouseEvent e) {
            Point pos = new Point(e.getPoint()); // converts from java point to my point

            // System.out.println("clicked!");
            
            if (drill != null) {
                for (Squad s : drill.getSquads()) {
                    // System.out.println("Squad: " + convertToScreenCoords(s.getCenterPos()));
                    // System.out.println("pos: " + pos);

                    if (convertToPanelCoords(s.getCenterPos()).near(pos, getWidth() / 40)) { // check if they clicked near it
                        // System.out.println("test");
                        mainPanel.setSelectedSquad(s);
                        repaint();
                        return;
                    }
                }

                mainPanel.setSelectedSquad(null);
                repaint();
            }
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragSquads && selectedSquad != null) { 
                Point fieldCoords = convertFromPanelCoords(new Point(e.getPoint()));

                if (snapToGrid) { 
                    selectedSquad.setCenterPos(new Point(snapToGridSize * Math.round(fieldCoords.getX() / snapToGridSize), snapToGridSize * Math.round(fieldCoords.getY() / snapToGridSize))); 
                } 
                else { selectedSquad.setCenterPos(fieldCoords); }

                // readjust if needed
                // x
                if (selectedSquad.getPos().getX() < 0) { selectedSquad.setX(0); }
                else if (convertToPanelCoords(selectedSquad.getBottomRightPos()).getX() > getWidth()) { 
                    selectedSquad.setBottomRightPos(new Point(convertFromPanelCoords(getWidth(), 0).getX(), selectedSquad.getBottomRightPos().getY())); 
                }

                // y
                if (selectedSquad.getPos().getY() < 0) { selectedSquad.setY(0); }
                else if (convertToPanelCoords(selectedSquad.getBottomRightPos()).getY() > getHeight()) { 
                    selectedSquad.setBottomRightPos(new Point(selectedSquad.getBottomRightPos().getX(), convertFromPanelCoords(0, getHeight()).getY())); 
                }

                repaint();
            }
        }


        // satisfy implementations
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {}
    }
}
