package main.java.io.quicksiiver.drillx.field;

import java.util.HashMap;

public class MovementType {
    public final String KEY; // shorthand when writing it down
    public final String[] CONTROLLED_FORMATION_ANIMATIONS;

    // classifiers
    public static final MovementType FORWARD_MARCH = new MovementType("FM", new String[] {
        SquadFormation.getFormationAnimationFileNameFromFormationFileNames("horizontal_bottom.json", "horizontal_top.json"),
        SquadFormation.getFormationAnimationFileNameFromFormationFileNames("horizontal_top.json", "horizontal_bottom.json"),
        SquadFormation.getFormationAnimationFileNameFromFormationFileNames("vertical_left.json", "vertical_right.json"),
        SquadFormation.getFormationAnimationFileNameFromFormationFileNames("vertical_right.json", "vertical_left.json")
    });


    // public static final String MARK_TIME_KEY = "MT";
    // public static final String FORWARD_MARCH_KEY = "FM";
    // public static final String COLUMN_LEFT_KEY = "CL";
    // public static final String COLUMN_RIGHT_KEY = "CR";

    public MovementType(String key, String[] controlled_formation_animations) {
        KEY = key;
        CONTROLLED_FORMATION_ANIMATIONS = controlled_formation_animations;
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
}
