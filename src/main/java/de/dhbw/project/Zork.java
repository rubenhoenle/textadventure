package de.dhbw.project;

import com.google.gson.Gson;

import de.dhbw.project.levelEditor.Editor;
import de.dhbw.project.nls.Commands;
import de.dhbw.project.nls.commands.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

// Main class for the project
public class Zork {
    // Class variables; gson for reading and saving the jsons (library in folder libs)
    private static Game game;
    private static Editor editor;
    private static Gson gson = new Gson();
    private static String gameDir;
    private static Commands commands = new Commands();

    // The game is started from here
    public static void main(String[] args) {
        Splash.print();
        menu();
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
    public static Game loadGame(String jsonFile) {
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

        if (!Files.exists(path)) {
            System.out.println("There's no saved game to load.");
            return null;
        } else {
            Game storedGame = Zork.parseData(path.toAbsolutePath().toString());
            game = storedGame;
            registerCommands();
            System.out.println("Game loaded.");

            if (game.player.getHealth() < 0) {
                game.player.setHealth(10);
            }

            return game;
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

    private static void registerCommands() {
        commands = new Commands();
        commands.register(new LookCommand(game));
        commands.register(new MoveCommand(game));
        commands.register(new TakeCommand(game));
        commands.register(new DropCommand(game));
        commands.register(new CraftCommand(game));
        commands.register(new UseCommand(game));
        commands.register(new InvestigateCommand(game));
        commands.register(new EquipCommand(game));
        commands.register(new StripOffCommand(game));
        commands.register(new SwitchCommand(game));

        commands.register(new TalkCommand(game));
        commands.register(new AttackCommand(game));
        commands.register(new EatCommand(game));
        commands.register(new AcceptQuestCommand(game));
        commands.register(new ShowQuestCommand(game));

        commands.register(new InventoryCommand(game));
        commands.register(new ShowEquipmentCommand(game));
        commands.register(new HelpCommand());
        commands.register(new ShowPlaceCommand(game));
        commands.register(new ExitCommand());

        commands.register(new SaveCommand(game));
        commands.register(new LoadCommand());

        commands.register(new ReadBookCommand(game));
        commands.register(new TranslateStoneTabletsCommand(game));

        commands.register(new RuinsRiddleCommand(game));

        commands.register(new ShowHealthCommand(game));
        commands.register(new ShowStrengthCommand(game));

        game.commands = commands;
    }

    private static void playNewGame() {
        game = loadGame(Constants.NEW_GAME);
        if (null != game) {
            registerCommands();
            game.play(game.player);
        }
    }

    public static void playLoadedGame() {
        if (null != game) {
            registerCommands();
            game.play(game.player);
        }
    }

    private static void edit() {
        game = loadGame(Constants.NEW_GAME);
        if (null != game) {
            registerCommands();
            editor = new Editor();
            editor.edit(game);
        }
    }

    // Methode zum Speichern von Änderungen an der database.json
    public static void saveModel(Game g) {
        // Serialization
        String savedGame = gson.toJson(g);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(gameDir + Constants.NEW_GAME));
            writer.write(savedGame);
            writer.close();
            System.out.println("Saved.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern in Datei.");
        }
    }

    private static void menu() {
        boolean Continue = false;
        while (!Continue) {
            System.out.println("Please enter 'play' to start the game or enter 'edit' to start the level editor!");
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine();
            input.toLowerCase();
            if (input.equals("play")) {
                Continue = true;
                playNewGame();
            } else if (input.equals("edit")) {
                Continue = true;
                edit();
            }
        }
    }
}