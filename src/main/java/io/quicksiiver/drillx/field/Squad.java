package main.java.io.quicksiiver.drillx.field;

import java.util.Arrays;
import java.util.HashMap;

import main.java.io.quicksiiver.drillx.coordinates.Point;

public class Squad {
    // variables
    private RotationDirection rotationDirection;
    private SquadFormation formation; // marcher -> position
    private Point pos;

    private final String KEY;
    private final int NUMBER;

    // class variables
    public static String NO_KEY = "";
    private static HashMap<String, Integer> keyNumberMap = new HashMap<>();

    // CONSTRUCTORS
    public Squad(final RotationDirection rotationDirection, final SquadFormation formation, final String key, final Point pos) {
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
        this(RotationDirection.NORTH, new SquadFormation(new Point[4]), key, new Point(8, 8));
    }
     

    // GETTERS
    public RotationDirection getRotationDirection() {
        return rotationDirection;
    }
    public SquadFormation getFormation() {
        return new SquadFormation(formation);
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
    private void setSquadMemberPositions(SquadFormation squadMemberPositions) {
        // data validation
        if (squadMemberPositions.formation.length == getNumberOfSquadMembers()) {
            formation.formation = squadMemberPositions.formation;
        } else {
            throw new IllegalArgumentException("SquadFormation squadMemberPositions must be of length " + formation.formation.length);
        }
    }
    

    // functions
    // applies a movement, returns true if there is still some left, returns false if the movement is complete
    public boolean applyMovement(MovementType movement) {
        // check each movement type
        switch (movement.KEY) {
            case MovementType.FORWARD_MARCH_KEY -> { translateSquad(rotationDirection, movement.duration); }
            case MovementType.COLUMN_LEFT_KEY -> {
                // if ()

                // switch (movement.duration) {
                //     case 8 -> {  }
                // }
            } 
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
    private void translateSquadMemberPositions(Point[] tranlationPoints) {
        // data validation
        if (!validatePointArray(tranlationPoints)) { 
            throw new IllegalArgumentException("Point[] translationPoints must be of length " + getNumberOfSquadMembers()); 
        }

        SquadFormation newPositions = new SquadFormation(new Point[formation.formation.length]);

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
