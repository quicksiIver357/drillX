package main.java.io.quicksiiver.drillx.field;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.gson.Gson;

import main.java.io.quicksiiver.drillx.Main;

public record FormationAnimation(int shift, String filename, String startFormationFilename, String endFormationFilename, Formation[] fma, RotationDirection requiredRotationDirection) {

    public FormationAnimation(int shift, String startFilename, String endFilename, RotationDirection requiredRotationDirection) {
        String completeFilename = getFMAFilename(startFilename, endFilename);

        this(shift, completeFilename, startFilename, endFilename, Main.ALL_FORMATION_ANIMATIONS.get(completeFilename), requiredRotationDirection);
    }
    
    // LOADING 
    public static HashMap<String, Formation[]> loadAllFormationAnimations(Gson gson, Path path) {
        // for each file in the formation animations folder, load it to a SquadFormation[] and store it
        // create a HashMap to store the SquadFormations based on the file names
        // create HashMap
        HashMap<String, Formation[]> formations = new HashMap<>();

        // get a list of all the files in the directory
        // up until end of for loop is from https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
        File formationDir = new File(path.toString());
        File[] formationDirectoryListing = formationDir.listFiles();

        // iterate through them
        if (formationDirectoryListing != null) {
            for (File child : formationDirectoryListing) {
                try {
                    // load from path and then put it in the hashmap
                    Formation[] formation = loadFormationAnimation(gson, child.toPath());
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
    public static Formation[] loadFormationAnimation(Gson gson, Path path) throws IOException {
        // read the data into a int[][] and then convert to a Point[] and then to SquadFormation
        // read data
        String json = Files.readString(path);
        int[][][] formationArray = gson.fromJson(json, int[][][].class);

        // create point array and store values
        Point[][] pointArray = new Point[formationArray.length][formationArray[0].length];
        for (int i = 0; i < pointArray.length; i++) {
            for (int j = 0; j < pointArray[i].length; j++) {
                pointArray[i][j] = new Point(formationArray[i][j][0], formationArray[i][j][1]);
            }
        }

        // convert to SquadFormation and return
        Formation[] squadFormationAnimation = new Formation[pointArray.length];
        for (int i = 0; i < pointArray.length; i++) {
            squadFormationAnimation[i] = new Formation(pointArray[i], "");
        }

        return squadFormationAnimation;
    }
    
    // tools and such
    public static String getFMAFilename(String formationName1, String formationName2) {
        // get the filename of a formation animation from the two filenames of the formations that it is comprised from
        // remove the .json from the first one
        return formationName1.substring(0, formationName1.length() - 5) + "_to_" + formationName2;
    }
}