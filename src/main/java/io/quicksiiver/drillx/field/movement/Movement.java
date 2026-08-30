package main.java.io.quicksiiver.drillx.field.movement;

public class Movement {
    public final String KEY; // shorthand when writing it down
    public int duration;

    // classifiers
    public static final String FORWARD_MARCH_KEY = "FM";
    public static final String MARK_TIME_KEY = "MT";

    public Movement(String key, int duration) {
        KEY = key;
        this.duration = duration;
    }

    // calculates the number of beats it will take for a movement sequence to execute
    public static int calculateDuration(Movement[] movements) {
        int totalDuration = 0;

        // add up all of the durations
        for (Movement movement : movements) {
            totalDuration += movement.duration;
        }

        return totalDuration;
    }
}
