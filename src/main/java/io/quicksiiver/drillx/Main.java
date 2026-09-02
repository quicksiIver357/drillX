package main.java.io.quicksiiver.drillx;

import java.nio.file.Path;
import java.util.Map;

import com.google.gson.Gson;
import main.java.io.quicksiiver.drillx.field.SquadFormation;

public class Main {
    // filepaths
    public static final Path FORMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formations");
    public static final Path FORMATION_ANIMATION_JSON_DIRECTORY_PATH = Path.of("src", "main", "resources", "data", "formation_animations");


    public static void main(String[] args) {
        // create useful stuff like Gson and Scanner
        Gson gson = new Gson();
        // Scanner scanner = new Scanner(); 


        // ---- load data ----
        Map<String, SquadFormation> formations = SquadFormation.loadAllFormations(gson, FORMATION_JSON_DIRECTORY_PATH);
        Map<String, SquadFormation[]> formationAnimations = SquadFormation.loadAllFormationAnimations(gson, FORMATION_ANIMATION_JSON_DIRECTORY_PATH);

        
    } // end of main method
} // end of class file
