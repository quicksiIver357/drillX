package main.java.io.quicksiiver.drillx.field;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;

import main.java.io.quicksiiver.drillx.coordinates.Point;

public class Formation {
    public Point[] formation;
    public final String FILENAME;

    // classifiers
    public static final String NO_FILENAME = "N/A";

    // preset (relative to top left of squad)
    public static final Formation HORIZONTAL_TOP = new Formation(new Point[] {
        new Point(1, 0), 
        new Point(3, 0), 
        new Point(5, 0), 
        new Point(7, 0)
    }, "horizontal_top.json");

    public static final Formation HORIZONTAL_BOTTOM = new Formation(new Point[] {
        new Point(1, 8), 
        new Point(3, 8), 
        new Point(5, 8), 
        new Point(7, 8)
    }, "horizontal_bottom.json");

    public static final Formation VERTICAL_LEFT = new Formation(new Point[] {
        new Point(0, 1), 
        new Point(0, 3), 
        new Point(0, 5), 
        new Point(0, 7)
    }, "vertical_left.json");

    public static final Formation VERTICAL_RIGHT = new Formation(new Point[] {
        new Point(8, 1), 
        new Point(8, 3), 
        new Point(8, 5), 
        new Point(8, 7)
    }, "vertical_right.json");

    public static final Formation LEFT_SLANT = new Formation(new Point[] {
        new Point(1, 1), 
        new Point(3, 3), 
        new Point(5, 5), 
        new Point(7, 7)
    }, "left_slant.json");

    public static final Formation RIGHT_SLANT = new Formation(new Point[] {
        new Point(7, 1), 
        new Point(5, 3), 
        new Point(3, 5), 
        new Point(1, 7)
    }, "right_slant.json");

    public static final Formation[] ALL_FORMATIONS = {HORIZONTAL_TOP, HORIZONTAL_BOTTOM, VERTICAL_LEFT, VERTICAL_RIGHT, LEFT_SLANT, RIGHT_SLANT};



    // // transitions
    // public static final SquadFormation[] HORIZONTAL_TO_LEFT_SLANT = {
    //     new SquadFormation(HORIZONTAL_TOP)
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
        this.formation = formation.formation;
        FILENAME = filename;
    }

    // LOADING
    public static Formation loadFormation(Gson gson, Path path) throws IOException {
        // read the data into a double[][] and then convert to a Point[] and then to SquadFormation
        // read data
        String json = Files.readString(path);
        double[][] formationArray = gson.fromJson(json, double[][].class);

        // create point array and store values
        Point[] pointArray = new Point[formationArray.length];
        for (int i = 0; i < pointArray.length; i++) {
            pointArray[i] = new Point(formationArray[i]);
        }

        // convert to SquadFormation and return
        return new Formation(pointArray, path.getFileName().toString());
    }
    public static HashMap<String, Formation> loadAllFormations(Gson gson, Path path) {
        // for each file in the formations folder, load it to a SquadFormation and store it
        // create a HashMap to store the SquadFormations based on the file names
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
            System.out.println("Pos: " + Arrays.toString(p.getPos()));
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
