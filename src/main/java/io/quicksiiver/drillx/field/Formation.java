package main.java.io.quicksiiver.drillx.field;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.gson.Gson;

public class Formation {
    public Point[] formation;
    public final String FILENAME;

    // classifiers
    public static final String NO_FILENAME = "N/A";

    public static final String HORIZONTAL_TOP = "horizontal_top.json";
    public static final String HORIZONTAL_BOTTOM = "horizontal_bottom.json";
    public static final String VERTICAL_LEFT = "vertical_left.json";
    public static final String VERTICAL_RIGHT = "vertical_right.json";
    public static final String LEFT_SLANT = "left_slant.json";
    public static final String RIGHT_SLANT = "right_slant.json";

    // public static final Formation[] ALL_FORMATIONS = {HORIZONTAL_TOP, HORIZONTAL_BOTTOM, VERTICAL_LEFT, VERTICAL_RIGHT, LEFT_SLANT, RIGHT_SLANT};



    // // transitions
    // public static final Formation[] HORIZONTAL_TO_LEFT_SLANT = {
    //     new Formation(HORIZONTAL_TOP)
    // };

    // constructors
    public Formation(Point[] formation, String filename) {
        // automatically handle creating new points if the array is nulls
        if (formation[0] == null) {
            for (int i = 0; i < formation.length; i++) { formation[i] = new Point(); }
        }

        this.formation = formation.clone();
        FILENAME = filename;
    }
    public Formation(Formation formation, String filename) {
        this.formation = formation.formation.clone();
        FILENAME = filename;
    }

    // LOADING
    public static Formation loadFormation(Gson gson, Path path) throws IOException {
        // read the data into a double[][] and then convert to a Point[] and then to Formation
        // read data
        String json = Files.readString(path);
        int[][] formationArray = gson.fromJson(json, int[][].class);

        // create point array and store values
        Point[] pointArray = new Point[formationArray.length];
        for (int i = 0; i < pointArray.length; i++) { pointArray[i] = new Point(formationArray[i][0], formationArray[i][1]); }

        // convert to Formation and return
        return new Formation(pointArray, path.getFileName().toString());
    }
    public static HashMap<String, Formation> loadAllFormations(Gson gson, Path path) {
        // for each file in the formations folder, load it to a Formation and store it
        // create a HashMap to store the Formations based on the file names
        // create HashMap
        HashMap<String, Formation> formations = new HashMap<>();

        // get a list of all the files in the directory
        // up until end of for loop is from https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
        File formationDir = new File(path.toString());
        File[] formationDirectoryListing = formationDir.listFiles();

        // iterate through them
        if (formationDirectoryListing != null) {
            for (File child : formationDirectoryListing) {
                try {
                    // load from path and then put it in the hashmap
                    Formation formation = Formation.loadFormation(gson, child.toPath());
                    formations.put(child.getName(), formation);
                } catch (IOException e) {
                    // error message
                    System.out.println("Error: failed to load path json for filepath " + child.toString());
                    e.printStackTrace();
                }
            }
        }

        // return them
        return formations;
    }

    // misc
    public boolean equals(Object obj) {
        if (this == obj) { return true; } // same reference
        if (!(obj instanceof Formation)) { return false; } // not same type
        // otherwise continue
        Formation f = (Formation) obj;

        // make sure all of the points are the same
        for (int i = 0; i < formation.length; i++) {
            if (!formation[i].equals(f.formation[i])) { return false; } // if they dont match
        }

        return true; // if they all match return true
    }
    public Formation copy() { return new Formation(this, FILENAME); }

    // debug
    public void printInfo() {
        for (Point p : formation) {
            System.out.println("Pos: [" + p.x + ", " + p.y + "]");
        }

        System.out.println("Filename: " + FILENAME);
    }
    public String toString() {
        // convert to char[] without the .json at the end
        String trimmedFilename = FILENAME.substring(0, FILENAME.length() - 5);
        char[] charArray = trimmedFilename.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            // replace underscores with spaces
            if (charArray[i] == '_') { charArray[i] = ' '; }
        }

        // convert back to String and return
        return new String(charArray); // wowza this is smth new i learned
    }
}
