package main.java.io.quicksiiver.drillx;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import client.java.io.quicksiiver.drillx.rendering.renderer.MainRenderer;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.FormationAnimation;

/**
 * This is the main runner file. Running this file will
 * result in running the entire engine. 
 */
public class Main {
    // important filepaths for loading and saving from
    public static final Path FORMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formations");
    public static final Path FORMATION_ANIMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formation_animations");
    public static final Path DRILL_PATH = Path.of("src", "main", "resources", "data", "drills", "default.json");

    // stuff used for input and output. (prevents creating more than one when not necessary)
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Scanner SCANNER = new Scanner(System.in);
    
    // important data used by other files that should not be loaded more than once.
    public static final HashMap<String, Formation[]> ALL_FORMATION_ANIMATIONS = FormationAnimation.loadAllFormationAnimations(GSON, FORMATION_ANIMATION_JSON_DIRECTORY_PATH);
    public static final HashMap<String, Formation> ALL_FORMATIONS = Formation.loadAllFormations(GSON, FORMATION_JSON_DIRECTORY_PATH);

    /**
     * This is the main method, when it is run it will initialize the
     * instance of the MainRenderer class and open the window.
     * @param args : Not implemented.
     */
    public static void main(String[] args) {
        // load drill from defualt filepath
        Drill drill = Drill.loadDrill(GSON, DRILL_PATH, false);

        // Initalized a instance of the MainRenderer by loading the class.
        // This will launch the GUI and display the selected drill on the field.
        // This also creates a seperate thread from the main one, which runs the
        // program and once that program is done, you can find the closing stuff in 
        // MainWindowListener.java, which calls the stop method from this class.
        MainRenderer.INSTANCE.setDrill(drill);
    } // end of main method

    /**
     * This method is called by the MainWindowListener class and should
     * only be called by that class. This method should be executed in the 
     * windowClosing method, and what it does is save everything and perform
     * actions such as closing the Scanner once no longer needed.
     */
    public static void stop() {
        // SAVING
        // save the drill
        MainRenderer.INSTANCE.getDrill().save(GSON);

        // MISCELLANEOUS
        // close the window, scanner, etc.
        MainRenderer.INSTANCE.dispose();
        SCANNER.close();
    } // end of stop method
} // end of class file
