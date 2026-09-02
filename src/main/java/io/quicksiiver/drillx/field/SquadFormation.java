package main.java.io.quicksiiver.drillx.field;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;

import main.java.io.quicksiiver.drillx.coordinates.Point;

public class SquadFormation {
    public Point[] formation;

    // preset (relative to top left of squad)
    public static final SquadFormation HORIZONTAL_TOP = new SquadFormation(new Point[] {new Point(1, 0), new Point(3, 0), new Point(5, 0), new Point(7, 0)});
    public static final SquadFormation HORIZONTAL_BOTTOM = new SquadFormation(new Point[] {new Point(1, 8), new Point(3, 8), new Point(5, 8), new Point(7, 8)});
    public static final SquadFormation VERTICAL = new SquadFormation(new Point[] {new Point(0, 1), new Point(0, 3), new Point(0, 5), new Point(0, 7)});
    public static final SquadFormation LEFT_SLANT = new SquadFormation(new Point[] {new Point(1, 1), new Point(3, 3), new Point(5, 5), new Point(7, 7)});
    public static final SquadFormation RIGHT_SLANT = new SquadFormation(new Point[] {new Point(7, 1), new Point(5, 3), new Point(3, 5), new Point(1, 7)});

    // transitions
    public static final SquadFormation[] HORIZONTAL_TO_LEFT_SLANT = {
        new SquadFormation(HORIZONTAL_TOP)
    };

    // constructors
    public SquadFormation(Point[] formation) {
        this.formation = formation.clone();
    }
    public SquadFormation(SquadFormation formation) {
        this.formation = formation.formation;
    }

    // LOADING
    public static SquadFormation loadFormation(Gson gson, Path path) throws IOException {
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
        return new SquadFormation(pointArray);
    }
    public static SquadFormation[] loadFormationAnimation(Gson gson, Path path) throws IOException {
        // read the data into a double[][] and then convert to a Point[] and then to SquadFormation
        // read data
        String json = Files.readString(path);
        double[][][] formationArray = gson.fromJson(json, double[][][].class);

        // create point array and store values
        Point[][] pointArray = new Point[formationArray.length][formationArray[0].length];
        for (int i = 0; i < pointArray.length; i++) {
            for (int j = 0; j < pointArray[i].length; j++) {
                pointArray[i][j] = new Point(formationArray[i][j]);
            }
        }

        // convert to SquadFormation and return
        SquadFormation[] squadFormationAnimation = new SquadFormation[pointArray.length];
        for (int i = 0; i < pointArray.length; i++) {
            squadFormationAnimation[i] = new SquadFormation(pointArray[i]);
        }

        return squadFormationAnimation;
    }
    public static HashMap<String, SquadFormation> loadAllFormations(Gson gson, Path path) {
        // for each file in the formations folder, load it to a SquadFormation and store it
        // create a HashMap to store the SquadFormations based on the file names
        // create HashMap
        HashMap<String, SquadFormation> formations = new HashMap<>();

        // get a list of all the files in the directory
        // up until end of for loop is from https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
        File formationDir = new File(path.toString());
        File[] formationDirectoryListing = formationDir.listFiles();

        // iterate through them
        if (formationDirectoryListing != null) {
            for (File child : formationDirectoryListing) {
                try {
                    // load from path and then put it in the hashmap
                    SquadFormation formation = SquadFormation.loadFormation(gson, child.toPath());
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
    public static HashMap<String, SquadFormation[]> loadAllFormationAnimations(Gson gson, Path path) {
        // for each file in the formation animations folder, load it to a SquadFormation[] and store it
        // create a HashMap to store the SquadFormations based on the file names
        // create HashMap
        HashMap<String, SquadFormation[]> formations = new HashMap<>();

        // get a list of all the files in the directory
        // up until end of for loop is from https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
        File formationDir = new File(path.toString());
        File[] formationDirectoryListing = formationDir.listFiles();

        // iterate through them
        if (formationDirectoryListing != null) {
            for (File child : formationDirectoryListing) {
                try {
                    // load from path and then put it in the hashmap
                    SquadFormation[] formation = SquadFormation.loadFormationAnimation(gson, child.toPath());
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

    // tools and such
    public static String getFormationAnimationFileNameFromFormationFileNames(String formationName1, String formationName2) {
        // remove the .json from the first one
        String formationAnimationName = formationName1.substring(0, formationName1.length() - 5) + "_to_" + formationName2;
        return formationAnimationName;
    }

    // debug
    public void printInfo() {
        for (Point p : formation) {
            System.out.println("Pos: " + Arrays.toString(p.getPos()));
        }
    }
    

    // // SETTERS
    // private void setFormation(Point[] formation) {
    //     this.formation = formation;
    // }
    // private void setFormation(Point point, int squadMemberIndex) {
    //     formation[squadMemberIndex] = point;
    // }

    // // GETTERS
    // public Point[] getFormation() {
    //     return formation.clone();
    // }
    // public Point getFormation(int squadMemberIndex) {
    //     return new Point(formation[squadMemberIndex]);
    // }
    // public int getNumberOfSquadMembers() {
    //     return formation.length;
    // }
}
