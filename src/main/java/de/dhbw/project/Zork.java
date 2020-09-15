package de.dhbw.project;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import de.dhbw.project.levelEditor.Editor;
import de.dhbw.project.nls.Commands;
import de.dhbw.project.nls.commands.*;
import de.dhbw.project.score.Score;
import de.dhbw.project.score.ScoreBoard;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

// Main class for the project
public class Zork {
    // Class variables; gson for reading and saving the jsons (library in folder libs)
    private static Game game;
    private static Editor editor;
    private static Gson gson = new Gson();
    private static String gameDir;
    private static Commands commands = new Commands();
    private static Instant startTimer;
    private static Instant finishTimer;
    private static ScoreBoard scoreBoard;

    public static void startTimer() {
        startTimer = Instant.now();
    }

    public static void stopTimer() {
        finishTimer = Instant.now();
        long timeElapsed = Duration.between(startTimer, finishTimer).toMillis();
        game.player.setTimePlayed(game.player.getTimePlayed() + timeElapsed);
    }

    // The game is started from here
    public static void main(String[] args) {
        Splash.print();
        menu();
    }

    // Method for saving the current game state into file savedGame.json (player, rooms with items and ways)
    public static void saveGame(Game g, boolean enableOutput) {
        // Serialization
        stopTimer();
        String savedGame = gson.toJson(g);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(gameDir + Constants.SAVED_GAME));
            writer.write(savedGame);
            writer.close();
            if (enableOutput) {
                System.out.println("Saved.");
            }
            startTimer();
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
            Game storedGame = Zork.parseData(path.toAbsolutePath().toString(), Game.class);
            game = storedGame;
            registerCommands();
            System.out.println("Game loaded.");
            return game;
        }
    }

    // Method is parsing the content of the given json (player, rooms with items and ways) and returns a game with the
    // corresponding settings
    public static <T> T parseData(String file, Class<T> classOfT) {
        Object object = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            // Deserialization
            object = gson.fromJson(sb.toString(), classOfT);

            reader.close();

            return Primitives.wrap(classOfT).cast(object);
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
        commands.register(new MapCommand(game));

        commands.register(new InventoryCommand(game));
        commands.register(new ShowEquipmentCommand(game));
        commands.register(new HelpCommand());
        commands.register(new ShowPlaceCommand(game));
        commands.register(new ExitCommand(game));

        commands.register(new SaveCommand(game));
        commands.register(new LoadCommand());

        commands.register(new ReadBookCommand(game));
        commands.register(new TranslateBooksCommand(game));

        commands.register(new RuinsRiddleCommand(game));

        commands.register(new ShowStatsCommand(game));

        commands.register(new PutCommand(game));

        game.commands = commands;
    }

    private static void playNewGame() {
        game = loadGame(Constants.NEW_GAME);
        List<Room> labyrinth = generateGenericLabyrinth();
        for (Room room : labyrinth) {
            game.addRoom(room);
        }
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

    public static void reloadAfterPlayerDeath() {
        int playerDeaths = game.player.getDeaths();
        game = loadGame(Constants.SAVED_GAME);
        if (null != game) {
            registerCommands();
            game.player.setDeaths(playerDeaths + 1);
            saveGame(game, false);
            game.play(game.player);
        }
    }

    private static void edit() {
        game = loadGame(Constants.NEW_GAME);
        if (null != game) {
            registerCommands();
            editor = new Editor(game);
            editor.edit();
        }
    }

    // Methode zum Speichern von Änderungen an der database.json
    public static boolean saveModel(Game g) {
        // Serialization
        String savedGame = gson.toJson(g);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(gameDir + Constants.NEW_GAME));
            writer.write(savedGame);
            writer.close();
            System.out.println("Saved.");
            return true;
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern in Datei.");
            return false;
        }
    }

    private static void menu() {
        boolean Continue = false;
        while (!Continue) {
            System.out.println(
                    "Please enter 'play' to start the game or enter 'score' to show the scoreboard or enter 'edit' to start the level editor!");
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine().toLowerCase();
            if (input.equals("play")) {
                Continue = true;
                Path saveGamePath = Paths.get(Constants.DEFAULT_PATH + File.separator + Constants.SAVED_GAME);
                if (Files.exists(saveGamePath)) {
                    loadGame(Constants.SAVED_GAME);
                    playLoadedGame();
                } else {
                    playNewGame();
                }
            } else if (input.equals("score")) {
                Continue = false;
                ScoreBoard sb = loadScoreBoard(Constants.SCOREBOARD);
                sb.printScoreBoard();
            } else if (input.equals("edit")) {
                Continue = true;
                edit();
            }
            userInput.close();
        }
    }

    public static void addScoreToScoreBoard(Score s) {
        loadScoreBoard(Constants.SCOREBOARD);
        scoreBoard.getScores().add(s);
    }

    public static void saveScoreBoard() {
        // Serialization
        if (scoreBoard == null) {
            scoreBoard = new ScoreBoard();
        }
        String scores = gson.toJson(scoreBoard);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(gameDir + Constants.SCOREBOARD));
            writer.write(scores);
            writer.close();
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern in Datei.");
        }
    }

    public static ScoreBoard loadScoreBoard(String jsonFile) {
        Path path = Paths.get(Constants.DEFAULT_PATH + File.separator + jsonFile);
        Path databasePath = Paths.get(Constants.DEFAULT_PATH + File.separator + Constants.NEW_GAME);
        if (Files.exists(path) || Files.exists(databasePath)) {
            path = Paths.get(Constants.DEFAULT_PATH + File.separator + jsonFile);
            gameDir = Constants.DEFAULT_PATH + File.separator;
        } else {
            System.out.println("Geben Sie den Ordner der JSON-Dateien an:");
            Scanner in = new Scanner(System.in);
            gameDir = in.nextLine() + File.separator;
            path = Paths.get(gameDir + jsonFile);
        }

        if (!Files.exists(path)) {
            scoreBoard = new ScoreBoard();
            saveScoreBoard();
            return scoreBoard;
        } else {
            ScoreBoard sBoard = Zork.parseData(path.toAbsolutePath().toString(), ScoreBoard.class);
            scoreBoard = sBoard;
            return sBoard;
        }
    }

    public static List<Room> generateGenericLabyrinth() {
        int amountRooms = 5;
        String entryString = "low branches of maple tree";

        List<Room> labyrinthRooms = new ArrayList<Room>();

        int[] roomNumbers = new int[amountRooms];
        for (int i = 0; i < amountRooms - 1; i++) {
            roomNumbers[i] = i;
        }
        roomNumbers[amountRooms - 1] = 100;

        Random random = new Random();
        for (int i = 0; i < amountRooms; i++) {
            int r = random.nextInt(roomNumbers.length);
            while (roomNumbers[r] == i) {
                r = random.nextInt(roomNumbers.length);
            }
            String to = "labyrinth" + roomNumbers[r];
            if (roomNumbers[r] == 100) {
                to = entryString;
            }
            roomNumbers = ArrayUtils.remove(roomNumbers, r);
            String dir1 = Constants.DIRECTIONS.get(random.nextInt(6));
            Way way = new Way("way", "Everywhere around you are branches and leafs!", dir1, "labyrinth" + i, to, null,
                    "");
            Room labyrinthRoom = new Room("labyrinth" + i, "Everywhere around you are branches and leafs!", "", "",
                    null, null, null, false, null, false);
            labyrinthRoom.addWay(way);
            if (i < 4) {
                String dir2 = Constants.DIRECTIONS.get(random.nextInt(6));
                while (dir2.equals(dir1)) {
                    dir2 = Constants.DIRECTIONS.get(random.nextInt(6));
                }
                labyrinthRoom.addWay(new Way("way", "", dir2, "labyrinth" + i, "labyrinth" + (i + 1), null, ""));
            }
            labyrinthRooms.add(labyrinthRoom);
        }

        Way entryWay = new Way("tree way", "Maybe you can go 'west'.", "west", entryString,
                "labyrinth" + (amountRooms - 1), null, "");
        List<Room> resultList = game.getRooms().stream().filter(room -> room.getName().equals(entryString))
                .collect(Collectors.toList());
        if (resultList.size() > 0) {
            Room entry = resultList.get(0);
            entry.addWay(entryWay);
        }
        return labyrinthRooms;
    }
}