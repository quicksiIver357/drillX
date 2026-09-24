package main.java.io.quicksiiver.drillx.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.java.io.quicksiiver.drillx.Main;

public class Squad {
    // variables
    private RotationDirection rotationDirection;
    private Formation formation; // marcher -> position
    private Point pos;

    // store all of the movements that will be executed during the drill
    public ArrayList<MovementType> movements = new ArrayList<MovementType>();

    private final String KEY;
    private final int NUMBER;

    // class variables
    public static String NO_KEY = "";
    private static HashMap<String, Integer> keyNumberMap = new HashMap<>();

    // CONSTRUCTORS
    public Squad(final RotationDirection rotationDirection, final Formation formation, final String key, final Point pos) {
        setRotationDirection(rotationDirection);
        this.formation = formation;
        KEY = key;
        this.pos = pos;
        
        // update keymap or
        if (keyNumberMap.containsKey(key)) {
            keyNumberMap.put(key, keyNumberMap.get(key) + 1);
            NUMBER = keyNumberMap.get(key);
        } else {
            // create new entry
            keyNumberMap.put(key, 1);
            NUMBER = 1;
        }
    }
    public Squad(final String key) { this(RotationDirection.NORTH, Main.ALL_FORMATIONS.get(Formation.HORIZONTAL_TOP), key, new Point(16, 16)); }
    public Squad() { this(Squad.NO_KEY); }
    public Squad(Squad s) { this(s.getRotationDirection(), s.getFormation(), s.getKey(), s.getPos()); }

    // GETTERS
    public RotationDirection getRotationDirection() { return rotationDirection; }
    public Formation getFormation() { return new Formation(formation, formation.FILENAME); }
    public String getKey() { return KEY; }
    public int getNumber() { return NUMBER; }
    public Point getPos() { return new Point(pos); }
    public int getNumberOfSquadMembers() { return formation.formation.length; }
    public Point getCenterPos() {
        int tx = 0;
        int ty = 0;
        
        for (int i = 0; i < formation.formation.length; i++) {
            tx += formation.formation[i].x;
            ty += formation.formation[i].y;
        }

        return new Point(getPos().x + tx / formation.formation.length, getPos().y + ty / formation.formation.length);
    }
    public Point getBottomRightPos() {
        // offset from getPos()
        int x = 8; 
        int y = 8;

        // change it if required
        if (Math.max(Math.max(formation.formation[0].getX(), formation.formation[1].getX()), Math.max(formation.formation[2].getX(), formation.formation[3].getX())) == 0) { x = 0; }
        if (Math.max(Math.max(formation.formation[0].getY(), formation.formation[1].getY()), Math.max(formation.formation[2].getY(), formation.formation[3].getY())) == 0) { y = 0; }

        return new Point(getPos().x + x, getPos().y + y);
    }

    // SETTERS
    public void setRotationDirection(RotationDirection rotationDirection) { this.rotationDirection = rotationDirection; }
    public void setFormation(Formation f) {
        // data validation
        if (f.formation.length == getNumberOfSquadMembers()) {
            formation.formation = f.formation;
        } else {
            throw new IllegalArgumentException("SquadFormation squadMemberPositions must be of length " + formation.formation.length);
        }
    }
    public void setPos(Point pos) { this.pos = new Point(pos); }
    public void setCenterPos(Point pos) {
        Point centerPos = getCenterPos();
        Point topLeftPos = getPos();

        Point offset = new Point(centerPos.x - topLeftPos.x, centerPos.y - topLeftPos.y);
        setPos(new Point(pos.x - offset.x, pos.y - offset.y));
    }
    public void setX(int x) { this.pos.x = x; }
    public void setY(int y) { this.pos.y = y; }
    public void setBottomRightPos(Point pos) { setPos(new Point(pos.x - getBottomRightPos().x + getPos().x, pos.y - getBottomRightPos().y + getPos().y)); }

    // functions
    // applies a movement
    public void applyMovement(MovementType movement) {
        // check each movement type
        if (movement.KEY.equals(MovementType.FORWARD_MARCH.KEY)) {
            if (getFormation().formation.equals(Main.ALL_FORMATIONS.get(Formation.HORIZONTAL_BOTTOM).formation))
            translateSquad(rotationDirection, 1);
        }
    }
    
    // HELPERS
    // translates the squad member positions
    private void translateSquadMemberPositions(Point[] tranlationPoints) {
        // data validation
        if (!validatePointArray(tranlationPoints)) { 
            throw new IllegalArgumentException("Point[] translationPoints must be of length " + getNumberOfSquadMembers()); 
        }

        Formation newPositions = new Formation(new Point[formation.formation.length], Formation.NO_FILENAME);

        // loop through each squad member and copy the positions to a new point
        for (int i = 0; i < formation.formation.length; i++) {
            newPositions.formation[i] = new Point(formation.formation[i].x + tranlationPoints[i].x, formation.formation[i].y + tranlationPoints[i].y);
        }

        setFormation(newPositions); // apply changes
    }
    // validate a point array by comparing its length agains the number of squad members
    private boolean validatePointArray(Point[] points) {
        return points.length == getNumberOfSquadMembers();
    }

    private void translateSquad(RotationDirection rotationDirection, int movementAmount) {
        if (Arrays.stream(RotationDirection.NORTH_DIRECTIONS).anyMatch(rotationDirection::equals)) { pos.translate(0, -movementAmount); }
        if (Arrays.stream(RotationDirection.SOUTH_DIRECTIONS).anyMatch(rotationDirection::equals)) { pos.translate(0, movementAmount); }
        if (Arrays.stream(RotationDirection.EAST_DIRECTIONS).anyMatch(rotationDirection::equals)) { pos.translate(movementAmount, 0); }
        if (Arrays.stream(RotationDirection.WEST_DIRECTIONS).anyMatch(rotationDirection::equals)) { pos.translate(-movementAmount, 0); }
    }

    // misc
    public boolean equals(Object obj) {
        if (this == obj) { return true; } // check reference
        if (!(obj instanceof Squad)) { return false; } // check instanceof

        // otherwise typecast and check fields
        Squad s = (Squad) obj;
        if (KEY.equals(s.KEY) && NUMBER == s.NUMBER) { return true; }
        else { return false; }
    }
}
