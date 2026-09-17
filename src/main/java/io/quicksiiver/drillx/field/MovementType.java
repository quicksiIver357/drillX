// Defines an MovmentType class, with different FormationAnimations to perform based on
// the current state of the Squad. 


package main.java.io.quicksiiver.drillx.field;

import java.util.HashMap;

public class MovementType {
    public final String KEY; // shorthand when writing it down
    public final FormationAnimation[] SUPPORTED_FMA;

    // classifiers

    

    // movement types
    public static final MovementType FORWARD_MARCH = new MovementType("FM", new FormationAnimation[] {
        new FormationAnimation(0, Formation.HORIZONTAL_BOTTOM.FILENAME, Formation.HORIZONTAL_TOP.FILENAME, RotationDirection.NORTH),
        new FormationAnimation(0, Formation.HORIZONTAL_TOP.FILENAME, Formation.HORIZONTAL_BOTTOM.FILENAME, RotationDirection.SOUTH),
        new FormationAnimation(0, Formation.VERTICAL_LEFT.FILENAME, Formation.VERTICAL_RIGHT.FILENAME, RotationDirection.EAST),
        new FormationAnimation(0, Formation.VERTICAL_RIGHT.FILENAME, Formation.VERTICAL_LEFT.FILENAME, RotationDirection.WEST),
        new FormationAnimation(-8, Formation.HORIZONTAL_BOTTOM.FILENAME, Formation.HORIZONTAL_TOP.FILENAME, RotationDirection.SOUTH),
        new FormationAnimation(-8, Formation.HORIZONTAL_TOP.FILENAME, Formation.HORIZONTAL_BOTTOM.FILENAME, RotationDirection.NORTH),
        new FormationAnimation(-8, Formation.VERTICAL_LEFT.FILENAME, Formation.VERTICAL_RIGHT.FILENAME, RotationDirection.WEST),
        new FormationAnimation(-8, Formation.VERTICAL_RIGHT.FILENAME, Formation.VERTICAL_LEFT.FILENAME, RotationDirection.EAST)
    });
    // public static final MovementType LEFT_SLANT = new MovementType("SL", new String[] {
    //     SquadFormationAnimation.getFMAFilename(HORIZONTAL_BOTTOM_FILENAME, SLANT_LEFT_FILENAME),
    //     SquadFormationAnimation.getFMAFilename(HORIZONTAL_TOP_FILENAME, SLANT_LEFT_FILENAME),
    //     SquadFormationAnimation.getFMAFilename(VERTICAL_LEFT_FILENAME, SLANT_RIGHT_FILENAME),
    //     SquadFormationAnimation.getFMAFilename(VERTICAL_RIGHT_FILENAME, SLANT_RIGHT_FILENAME)
    // });
    // public static final MovementType RIGHT_SLANT = new MovementType("SL", new String[] {
    //     SquadFormationAnimation.getFMAFilename(HORIZONTAL_BOTTOM_FILENAME, SLANT_RIGHT_FILENAME),
    //     SquadFormationAnimation.getFMAFilename(HORIZONTAL_TOP_FILENAME, SLANT_RIGHT_FILENAME),
    //     SquadFormationAnimation.getFMAFilename(VERTICAL_LEFT_FILENAME, SLANT_LEFT_FILENAME),
    //     SquadFormationAnimation.getFMAFilename(VERTICAL_RIGHT_FILENAME, SLANT_LEFT_FILENAME)
    // });


    // public static final String MARK_TIME_KEY = "MT";
    // public static final String FORWARD_MARCH_KEY = "FM";
    // public static final String COLUMN_LEFT_KEY = "CL";
    // public static final String COLUMN_RIGHT_KEY = "CR";

    public MovementType(String key, FormationAnimation[] controlled_formation_animations) {
        KEY = key;
        SUPPORTED_FMA = controlled_formation_animations.clone();
    }

    // calculates the number of beats it will take for a movement sequence to execute
    public static int calculateDuration(HashMap<MovementType, Integer> movements) {
        int totalDuration = 0;

        // add up all of the durations
        for (int duration : movements.values()) {
            totalDuration += duration;
        }

        return totalDuration;
    }

    // returns the applicable FormationAnimation based on the current Formation and RotationDirection
    public FormationAnimation getFMA(Formation formation, RotationDirection rotationDirection) {
        for (FormationAnimation fma : SUPPORTED_FMA) { // loop through each supported formation animation
            if (fma.REQUIRED_ROTATION_DIRECTION.equals(rotationDirection) && fma.FMA[0].equals(formation)) { // check if it will work
                return fma; // if so return the correct FormationAnimation
            }
        }

        // if nothing matches return null
        return null;
    }
}
