package main.java.io.quicksiiver.drillx.field;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * This is a class used to save marching band drills.
 * It has no drawing functionality, that is handled in FieldPanel.java and DrilPanel.java
 * However, it does have functionality for saving and loading them to files.
 */
public class Drill {
    // instance variables
    private ArrayList<Squad> squads = new ArrayList<Squad>();
    private int length = 0; // the number of 8s
    private String name;

    // TODO: Add implementation for saving of usedNames.
    /** This is a list of all of the currently taken filenames for drills. Saving is unimplemented. */
    public static ArrayList<String> usedNames = new ArrayList<>();

    // CONSTRUCTORS
    /** Creates a new drill with the name default. */
    public Drill() { this("default"); }
    /** 
     * Creates a new drill.
     * @param name the name of the drill, it will be saved to name.json
     * If that name is already taken, it will continue to add underscores until it works.
     */
    public Drill(String name) { while (!setName(name)) { name += "_"; } }

    // LOADING
    /**
     * Loads a drill from a filepath.
     * @param gson : The Gson object to use to read from the file
     * @param path : The path of the file
     * @param printInfo : Whether or not the information should be printed
     * @return The drill from the filepath, or, if there is not a drill 
     * at that location, create a new one.
     */
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
    /**
     * Loads a drill from a filepath. 
     * @param gson : The Gson object to use to read from the file
     * @param path : The path of the file
     * @return The drill from the filepath, or, if there is not a drill 
     * at that location, create a new one.
     */
    public static Drill loadDrill(Gson gson, Path path) { return loadDrill(gson, path, false); } // default to no info printed

    // SAVE DRILL
    /**
     * Saves a drill to a file.
     * @param gson : The Gson object to use for saving
     * @param path : The path to be saved to
     * @param printInfo Whether or not to print information about the saving
     * (used for debug)
     */
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
    /**
     * Saves a drill to a file.
     * @param gson : The Gson object to use for saving
     * @param path : The path to be saved to
     */
    public void save(Gson gson, Path path) { save(gson, path, false); } // default no info printed
    /**
     * Saves a drill to its respective file in the resources/data/drills folder,
     * with the filename [name].json where [name] is the drill's name.
     * @param gson : The Gson object to use for saving
     */
    public void save(Gson gson) { save(gson, Path.of("src", "main", "resources", "data", "drills", getName() + ".json")); }

    // getters
    /**
     * Gets the name of the drill.
     * @return The name of the drill is what file it is saved to, in that it
     * is saved to [name].json
     */
    public String getName() { return name; }
    /**
     * Gets all of the squads.
     * @return Returns an ArrayList of all of the squads. This should not be modified
     * to avoid tricky errors, instead use the setSquads, addSquad, and removeSquad methods.
     */
    public ArrayList<Squad> getSquads() { return squads; }
    /**
     * Returns the length of the squad.
     * @return The length of the squad is measured in groups of 8 beats, 
     * for example a return amount of 3 would be 3 * 8 = 24 beats. 
     */
    public int getLength() { return length; }

    // setters
    /**
     * Sets the name
     * @param name : The name to set.
     * @return false if the name is already taken, doesn't update the name.
     * @return true if the name is not already taken, updates the name and the usedNames ArrayList.
     */
    public boolean setName(String name) {
        if (usedNames.contains(name)) { return false; }
        // otherwise

        // update array
        usedNames.remove(this.name);
        usedNames.add(name);

        // update variable
        this.name = name;
        return true;
    }
    /**
     * Sets the squads ArrayList to a new value. Be careful with this, if you
     * just need to add or remove a squad then use the addSquad and removeSquad methods.
     * @param squads : The ArrayList to set squads to. Notice that this is an reference assignment,
     * and doesn't create a new copy of the ArrayList. If you want them to be seperate, pass in
     * a copy of your ArrayList. Be careful with this method, as it is easy to mix up the reference
     * and accidentaly modify this.
     */
    public void setSquads(ArrayList<Squad> squads) { this.squads = squads; }
    /**
     * Sets the length of the drill.
     * @param length : The new length to set. This is calculated in 1 length =
     * 8 beats, so if you were to pass in 3 it would be 3 * 8 = 24 beats.
     */
    public void setLength(int length) { this.length = length; }

    // modifiers
    /**
     * Adds a squad to the squads ArrayList.
     * @param squad : This is the new Squad to be added to the end of the ArrayList.
     * For functionality involving inserting a squad a certain point, use insertSquad.
     */
    public void addSquad(Squad squad) { squads.add(squad); }
    /**
     * Removes the specified squad.
     * @param squad : The squad to be removed.
     */
    public void removeSquad(Squad squad) { squads.remove(squad); }
    /**
     * Inserts a squad at an index in the squads ArrayList.
     * @param squad : The squad to be inserted
     * @param index : The index to insert the squad at.
     */
    public void insertSquad(Squad squad, int index) { squads.add(index, squad); }
    /**
     * Increments the length of the drill by an amount.
     * @param amount : The amount to increment the length by, each 1 is 
     * equivalent to 8 beats, so if you passed in 3 the number of beats would
     * increase by 3 * 8 = 24. 
     */
    public void incrementLength(int amount) { setLength(getLength() + amount); }
    /** Increases the length of the drill by 8 beats. */
    public void incrementLength() { incrementLength(1); }
    /**
     * Decrements the length of the drill by an amount.
     * @param amount : The amount to decrement the length by, each 1 is 
     * equivalent to 8 beats, so if you passed in 3 the number of beats would
     * decrease by 3 * 8 = 24. 
     */
    public void decrementLength(int amount) { incrementLength(-amount); }
    /** Decreases the length of th drill by 8 beats. */
    public void decrementLength() { incrementLength(-1); }
}
