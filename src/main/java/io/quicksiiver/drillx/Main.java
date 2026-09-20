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

public class Main {
    // filepaths
    public static final Path FORMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formations");
    public static final Path FORMATION_ANIMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formation_animations");
    public static final Path DRILL_PATH = Path.of("src", "main", "resources", "data", "drills", "default.json");

    // important stuff like Scanner
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Scanner SCANNER = new Scanner(System.in);
    
    // important data
    public static final HashMap<String, Formation[]> ALL_FORMATION_ANIMATIONS = FormationAnimation.loadAllFormationAnimations(GSON, FORMATION_ANIMATION_JSON_DIRECTORY_PATH);
    public static final HashMap<String, Formation> ALL_FORMATIONS = Formation.loadAllFormations(GSON, FORMATION_JSON_DIRECTORY_PATH);

    public static void main(String[] args) {
        // load drill
        Drill drill = Drill.loadDrill(GSON, DRILL_PATH, false);

        // drill.squads.add(new Squad()); // add a squad to test stuff

        // init screen
        MainRenderer renderer = MainRenderer.INSTANCE;
        renderer.setDrill(drill);
        
        // main loop (will be exited if the x button is clicked)
        while (true) {
            // render stuff
            renderer.repaint();
        }
    } // end of main method
} // end of class file
