package main.java.io.quicksiiver.drillx.field;

import java.util.Arrays;
import java.util.HashMap;

import main.java.io.quicksiiver.drillx.coordinates.Point;
import main.java.io.quicksiiver.drillx.field.movement.Movement;

public class Squad {
    // variables
    private RotationDirection rotationDirection;
    private final Point[] SQUAD_MEMBER_POSITIONS; // marcher -> position
    private final Point POS;

    private final String KEY;
    private final int NUMBER;

    // class variables
    public static String NO_KEY = "";
    private static HashMap<String, Integer> keyNumberMap = new HashMap<>();

    // CONSTRUCTORS
    public Squad(final RotationDirection rotationDirection, final Point[] squadMemberPositions, final String key, final Point pos) {
        setRotationDirection(rotationDirection);
        SQUAD_MEMBER_POSITIONS = squadMemberPositions;
        KEY = key;
        POS = pos;
        
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
        this(RotationDirection.NORTH, new Point[4], key, new Point(8, 8));
    }
     

    // GETTERS
    public RotationDirection getRotationDirection() {
        return rotationDirection;
    }
    public Point[] getSquadMemberPositions() {
        return SQUAD_MEMBER_POSITIONS.clone();
    }
    public String getKey() {
        return KEY;
    }
    public int getNumber() {
        return NUMBER;
    }
    public Point getPos() {
        return new Point(POS);
    }
    public int getNumberOfSquadMembers() {
        return SQUAD_MEMBER_POSITIONS.length;
    }

    // SETTERS
    private void setRotationDirection(RotationDirection rotationDirection) {
        this.rotationDirection = rotationDirection;
    }
    private void setSquadMemberPositions(Point[] squadMemberPositions) {
        // data validation
        if (squadMemberPositions.length == SQUAD_MEMBER_POSITIONS.length) {
            for (int i = 0; i < SQUAD_MEMBER_POSITIONS.length; i++) {
                SQUAD_MEMBER_POSITIONS[i].setPos(squadMemberPositions[i]);
            }
        } else {
            throw new IllegalArgumentException("Point[] squadMemberPositions must be of length " + SQUAD_MEMBER_POSITIONS.length);
        }
    }
    

    // functions
    // applies a movement, returns true if there is still some left, returns false if the movement is complete
    public boolean applyMovement(Movement movement) {
        // check each movement type
        switch (movement.KEY) {
            case Movement.FORWARD_MARCH_KEY -> { translateSquad(rotationDirection, movement.duration); }
        }

        movement.duration--;


        // return
        if (movement.duration == 0) {
            return false;
        } else {
            return true;
        }
    }
    // translates the squad member positions
    public void translateSquadMemberPositions(Point[] tranlationPoints) {
        // data validation
        if (!validatePointArray(tranlationPoints)) { 
            throw new IllegalArgumentException("Point[] translationPoints must be of length " + getNumberOfSquadMembers()); 
        }

        Point[] newPositions = new Point[SQUAD_MEMBER_POSITIONS.length];

        // loop through each squad member and copy the positions to a new point
        for (int i = 0; i < SQUAD_MEMBER_POSITIONS.length; i++) {
            newPositions[i] = new Point(SQUAD_MEMBER_POSITIONS[i].getX() + tranlationPoints[i].getX(), SQUAD_MEMBER_POSITIONS[i].getY() + tranlationPoints[i].getY());
        }

        setSquadMemberPositions(newPositions); // apply changes
    }
    // validate a point array by comparing its length agains the number of squad members
    private boolean validatePointArray(Point[] points) {
        return points.length == getNumberOfSquadMembers();
    }

    private void translateSquad(RotationDirection rotationDirection, int movementAmount) {
        if (Arrays.stream(RotationDirection.NORTH_DIRECTIONS).anyMatch(rotationDirection::equals)) { POS.translate(0, -movementAmount); }
        if (Arrays.stream(RotationDirection.SOUTH_DIRECTIONS).anyMatch(rotationDirection::equals)) { POS.translate(0, movementAmount); }
        if (Arrays.stream(RotationDirection.EAST_DIRECTIONS).anyMatch(rotationDirection::equals)) { POS.translate(movementAmount, 0); }
        if (Arrays.stream(RotationDirection.WEST_DIRECTIONS).anyMatch(rotationDirection::equals)) { POS.translate(-movementAmount, 0); }
    }
}
