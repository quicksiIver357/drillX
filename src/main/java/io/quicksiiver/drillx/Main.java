package main.java.io.quicksiiver.drillx;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import client.java.io.quicksiiver.drillx.rendering.renderer.MainRenderer;
// import client.java.io.quicksiiver.drillx.rendering.renderer.MainRenderer;
import main.java.io.quicksiiver.drillx.field.Drill;
import main.java.io.quicksiiver.drillx.field.Formation;
import main.java.io.quicksiiver.drillx.field.FormationAnimation;

public class Main {
    // filepaths
    public static final Path FORMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formations");
    public static final Path FORMATION_ANIMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formation_animations");
    public static final Path DRILL_PATH = Path.of("src", "main", "resources", "data", "drills", "test.json");

    // important stuff like Scanner
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static Scanner scanner = new Scanner(System.in);
    
    // important data
    public static final HashMap<String, Formation[]> ALL_FORMATION_ANIMATIONS = FormationAnimation.loadAllFormationAnimations(gson, FORMATION_ANIMATION_JSON_DIRECTORY_PATH);
    public static final HashMap<String, Formation> ALL_FORMATIONS = Formation.loadAllFormations(gson, FORMATION_JSON_DIRECTORY_PATH);

    public static void main(String[] args) {
        // load drill
        Drill drill = Drill.loadDrill(gson, DRILL_PATH, false);

        // drill.squads.add(new Squad(Squad.NO_KEY)); // add a squad to test stuff

        // init screen
        MainRenderer renderer = MainRenderer.instance;
        renderer.drill = drill;
        
        // main loop (will be exited if the x button is clicked)
        MAIN:
        while (true) {
            // render stuff
            renderer.repaint();
        }


        // save drill
        // drill.save(gson, DRILL_PATH, false);
    } // end of main method
} // end of class file
