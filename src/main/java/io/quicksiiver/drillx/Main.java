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
import main.java.io.quicksiiver.drillx.field.Squad;

public class Main {
    // filepaths
    public static final Path FORMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formations");
    public static final Path FORMATION_ANIMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formation_animations");
    public static final Path DRILL_PATH = Path.of("src", "main", "resources", "data", "drills", "test.json");

    // important stuff like Scanner
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Scanner scanner = new Scanner(System.in);
    
    // important data
    public static final HashMap<String, Formation[]> ALL_FORMATION_ANIMATIONS = FormationAnimation.loadAllFormationAnimations(gson, FORMATION_ANIMATION_JSON_DIRECTORY_PATH);
    public static final HashMap<String, Formation> ALL_FORMATIONS = Formation.loadAllFormations(gson, FORMATION_JSON_DIRECTORY_PATH);

    public static void main(String[] args) {
        // load drill
        Drill drill = Drill.loadDrill(gson, DRILL_PATH, false);

        // drill.squads.add(new Squad(Squad.NO_KEY));

        // init screen
        MainRenderer renderer = MainRenderer.getInstance(drill);
        
        // main loop
        MAIN:
        while (true) {
            // render stuff
            renderer.render();
        }


        // save drill
        // drill.save(gson, DRILL_PATH, false);
    } // end of main method
} // end of class file
