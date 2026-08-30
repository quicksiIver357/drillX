package main.java.io.quicksiiver.drillx.field;

public class RotationAction {
    // presets
    public static final RotationAction HALF_LEFT_FACE = new RotationAction("HLF", -45);
    public static final RotationAction HALF_RIGHT_FACE = new RotationAction("HRF", 45);
    public static final RotationAction THREE_HALF_LEFT_FACE = new RotationAction("3HLF", -135);
    public static final RotationAction THREE_HALF_RIGHT_FACE = new RotationAction("3HRF", 135);
    public static final RotationAction LEFT_FACE = new RotationAction("LF", -90);
    public static final RotationAction RIGHT_FACE = new RotationAction("RF", 90);
    public static final RotationAction TO_THE_REAR = new RotationAction("TTR", 180);

    // variables
    public final String KEY; // this is the abbreviation that you write down
    public final int DEGREE; // positive is clockwise (right), negative is counterclockwise (left)

    // CONSTRUCTORS
    private RotationAction(final String key, final int degree) {
        this.KEY = key;
        this.DEGREE = degree % 360;
    }

    // apply a RotationAction to a RotationDirection
    public RotationDirection apply(final RotationDirection rotationDirection) {
        int degree = this.DEGREE + rotationDirection.DEGREE;
        RotationDirection rotationDirectionToReturn = RotationDirection.getRotationDirectionFromDegree(degree);

        return rotationDirectionToReturn;
    }
}
