package main.java.io.quicksiiver.drillx.field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
        this.formation = formation;
    }
    public SquadFormation(SquadFormation formation) {
        this.formation = formation.formation;
    }

    // load formations from json
    public static SquadFormation loadFormation(Gson gson, Path path) throws IOException {
        // read data
        String json = Files.readString(path);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        double[][] values = gson.fromJson(jsonObject.get("points"), double[][].class);

        // create point array
        Point[] points = new Point[values.length];
        for (int i = 0; i < values.length; i++) {
            points[i] = new Point(values[i][0], values[i][1]);
        }

        // create SquadFormation and return it
        return new SquadFormation(points);
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
