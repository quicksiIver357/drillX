package main.java.io.quicksiiver.drillx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.google.gson.Gson;

import main.java.io.quicksiiver.drillx.field.SquadFormation;

public class Main {
    public static final Path FORMATION_JSON_PATH = Path.of("src", "main", "resources", "data", "formations");
    public static void main(String[] args) {
        // create useful stuff like Gson and Scanner
        Gson gson = new Gson();
        // Scanner scanner = new Scanner(); 


        // load data
        // for each file in the formations folder, load it to a SquadFormationa and add it to the array of formations
        
        try (Stream<Path> FILES = Files.list(FORMATION_JSON_PATH)) {
            // create array
            Map<String, SquadFormation> formations = new HashMap<>();

            // loop through each file
            formations.forEach(null);
        } catch (IOException e) {

        }
        try {
            
        } catch (Exception e) {
            System.out.println("Error: failed to load path json for filepath " + FORMATION_JSON_PATH.toString());
            e.printStackTrace();
        }
    }
}
