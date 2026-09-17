package main.java.io.quicksiiver.drillx.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import main.java.io.quicksiiver.drillx.coordinates.Point;

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
    public Squad(final String key) {
        this(RotationDirection.NORTH, new Formation(new Point[4], Formation.NO_FILENAME), key, new Point(16, 16));
    }
     

    // GETTERS
    public RotationDirection getRotationDirection() {
        return rotationDirection;
    }
    public Formation getFormation() {
        return new Formation(formation, formation.FILENAME);
    }
    public String getKey() {
        return KEY;
    }
    public int getNumber() {
        return NUMBER;
    }
    public Point getPos() {
        return new Point(pos);
    }
    public int getNumberOfSquadMembers() {
        return formation.formation.length;
    }

    // SETTERS
    private void setRotationDirection(RotationDirection rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
    private void setSquadMemberPositions(Formation squadMemberPositions) {
        // data validation
        if (squadMemberPositions.formation.length == getNumberOfSquadMembers()) {
            formation.formation = squadMemberPositions.formation;
        } else {
            throw new IllegalArgumentException("SquadFormation squadMemberPositions must be of length " + formation.formation.length);
        }
    }
    

    // functions
    // applies a movement
    public void applyMovement(MovementType movement) {
        // check each movement type
        if (movement.KEY.equals(MovementType.FORWARD_MARCH.KEY)) {
            if (getFormation().formation.equals(Formation.HORIZONTAL_BOTTOM.formation))
            translateSquad(rotationDirection, 1);
        }
    }
    // translates the squad member positions
    private void translateSquadMemberPositions(Point[] tranlationPoints) {
        // data validation
        if (!validatePointArray(tranlationPoints)) { 
            throw new IllegalArgumentException("Point[] translationPoints must be of length " + getNumberOfSquadMembers()); 
        }

        Formation newPositions = new Formation(new Point[formation.formation.length], Formation.NO_FILENAME);

        // loop through each squad member and copy the positions to a new point
        for (int i = 0; i < formation.formation.length; i++) {
            newPositions.formation[i] = new Point(formation.formation[i].getX() + tranlationPoints[i].getX(), formation.formation[i].getY() + tranlationPoints[i].getY());
        }

        setSquadMemberPositions(newPositions); // apply changes
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
}
