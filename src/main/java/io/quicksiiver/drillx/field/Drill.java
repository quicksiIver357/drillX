package main.java.io.quicksiiver.drillx.field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Drill {
    // variables
    public ArrayList<Squad> squads = new ArrayList<Squad>();
    public int length = 0; // the number of 8s

    // CONSTRUCTORS
    public Drill() {} // create a new drill with no special properties
 
    // LOADING
    public static Drill loadDrill(Gson gson, Path path, boolean printInfo) {
        // load drill
        Drill drill;

        // try to load
        try {
            String json = Files.readString(path);
            drill = gson.fromJson(json, Drill.class);

            if (printInfo) { System.out.println("Drill loaded successfully!"); } // print that it loaded fine

        } catch (IOException e) { // in case of no drill, create a new one
            if (printInfo) { System.out.println("Could not load drill. Creating a new one. . ."); }

            drill = new Drill(); // create a new drill

            if (printInfo) { System.out.println("Drill created successfully!"); }
        }

        return drill;
    }
    public static Drill loadDrill(Gson gson, Path path) { return loadDrill(gson, path, false); } // default to no info printed

    // SAVE DRILL
    public void save(Gson gson, Path path, boolean printInfo) {
        try {
            // convert to String
            String json = gson.toJson(this);
            
            // write to file
            Files.writeString(path, json);

            // print info
            if (printInfo) { System.out.println("Drill saved successfully!"); }
        } catch (IOException e) {
            if (printInfo) { System.out.println("Drill failed to save. "); }
        }
    }
    public void save(Gson gson, Path path) { save(gson, path, false); } // default no info printed

    
}
