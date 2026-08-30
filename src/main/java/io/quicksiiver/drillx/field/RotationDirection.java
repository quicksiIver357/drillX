package main.java.io.quicksiiver.drillx.field;

public class RotationDirection {
    // variables
    public final String KEY;
    public final int DEGREE;

    // CONSTRUCTOR
    private RotationDirection(String key, int degree) {
        this.KEY = key;
        this.DEGREE = degree % 360;
    }

    // presets
    public static final RotationDirection NORTH = new RotationDirection("NORTH", 0);
    public static final RotationDirection NORTHEAST = new RotationDirection("NORTHEAST", 45);
    public static final RotationDirection EAST = new RotationDirection("EAST", 90);
    public static final RotationDirection SOUTHEAST = new RotationDirection("SOUTHEAST", 135);
    public static final RotationDirection SOUTH = new RotationDirection("SOUTH", 180);
    public static final RotationDirection SOUTHWEST = new RotationDirection("SOUTHWEST", -135);
    public static final RotationDirection WEST = new RotationDirection("WEST", -90);
    public static final RotationDirection NORTHWEST = new RotationDirection("NORTHWEST", -45);

    public static final RotationDirection[] ROTATION_DIRECTIONS = {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
    public static final RotationDirection[] NORTH_DIRECTIONS = {NORTH, NORTHEAST, NORTHWEST};
    public static final RotationDirection[] SOUTH_DIRECTIONS = {SOUTH, SOUTHEAST, SOUTHWEST};
    public static final RotationDirection[] EAST_DIRECTIONS = {EAST, NORTHEAST, SOUTHEAST};
    public static final RotationDirection[] WEST_DIRECTIONS = {WEST, NORTHWEST, SOUTHWEST};

    // FUNCTIONS
    public static RotationDirection getRotationDirectionFromKey(String key) {
        // check each rotation directions
        for (RotationDirection rotationDirection : ROTATION_DIRECTIONS) {
            if (key.equals(rotationDirection.KEY)) {
                return rotationDirection;
            }
        }
        // fallback to null
        return null;
    }
    public static RotationDirection getRotationDirectionFromDegree(int degree) {
        // check each rotation direction preset
        for (RotationDirection rotationDirection : ROTATION_DIRECTIONS) {
            if ((degree >= 0 && degree % 360 == rotationDirection.DEGREE) || (360 - degree % 360 == rotationDirection.DEGREE)) {
                return rotationDirection;
            }
        }
        // fallback to null
        return null;
    }
    // apply a RotationAction to the RotationDirection
    public RotationDirection apply(RotationAction rotationAction) {
        return getRotationDirectionFromDegree(this.DEGREE + rotationAction.DEGREE);
    }
}
