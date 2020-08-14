package de.dhbw.project;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Main class for the project
public class Zork {
    // Class variables; gson for reading and saving the jsons (library in folder libs)
    private static Game game;
    private static Gson gson = new Gson();
    private static String gameDir;

    // The game is started from here
    public static void main(String[] args) {
        Splash.print();
        loadGame(Constants.NEW_GAME);
    }

    // Method for saving the current game state into file savedGame.json (player, rooms with items and ways)
    public static void saveGame(Game g) {
        // Serialization
        String savedGame = gson.toJson(g);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(gameDir + Constants.SAVED_GAME));
            writer.write(savedGame);
            writer.close();
            System.out.println("Saved.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern in Datei.");
        }
    }

    // Method for loading the game state from the given file
    // For the start of the game this function is called with database.json
    // If this function is called during the game it will try to load savedGame.json if it exists
    public static void loadGame(String jsonFile) {
        Path path = Paths.get(Constants.DEFAULT_PATH + File.separator + jsonFile);
        if (Files.exists(path)) {
            path = Paths.get(Constants.DEFAULT_PATH + File.separator + jsonFile);
            gameDir = Constants.DEFAULT_PATH + File.separator;
        } else {
            System.out.println("Geben Sie den Ordner der JSON-Dateien an:");
            Scanner in = new Scanner(System.in);
            gameDir = in.nextLine() + File.separator;
            path = Paths.get(gameDir + jsonFile);
        }

        if (!Files.exists(path))
            System.out.println("There's no saved game to load.");
        else {
            Game storedGame = Zork.parseData(path.toAbsolutePath().toString());
            game = storedGame;
            game.play(game.player);
            System.out.println("Game loaded.");
        }
    }

    // Method is parsing the content of the given json (player, rooms with items and ways) and returns a game with the
    // corresponding settings
    public static Game parseData(String file) {
        Game game;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Deserialization
            game = gson.fromJson(sb.toString(), Game.class);

            reader.close();

            return game;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}