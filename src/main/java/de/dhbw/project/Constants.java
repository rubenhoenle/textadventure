package de.dhbw.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Class with some constant variables
public class Constants {
    // Constans for the possible directions in the game
    public static final List<String> DIRECTIONS = new ArrayList<>(
            Arrays.asList("north", "east", "west", "south", "up", "down"));
    public static final List<String> SHORT_DIRECTIONS = new ArrayList<>(Arrays.asList("n", "e", "s", "w", "u", "d"));
    public static final String EACH_DIRECTION = "around";

    // Possible commands for the game - has to be extended if there are new commands added to the game
    public static final List<String> COMMAND_LIST = new ArrayList<>(Arrays.asList(
            "move north / east / west / south / up / down", "look around / north / east / west / south / up / down",
            "take <item>", "take <item> from <chest>", "put <item> in <chest>", "drop <item>",
            "equip <item>", "strip off <item>", "inventory", "equipment", "stats", "talk to <character>",
            "attack <character>", "accept <quest> from <character / interactive object>",
            "quest inventory", "switch on/off <item>", "eat <item>", "craft <crafting object>",
            "investigate <interactive object / chest>", "read / open <item>", "translate <stone tablet>",
            "ruin(s) riddle", "quit or exit", "?", "map", "help or info"));

    // Location of the jsons for a new or a saved game
    public static final String DEFAULT_PATH = "src/main/resources";
    public static final String SAVED_GAME = "savedGame.json";
    public static final String NEW_GAME = "database.json";
    public static final String SCOREBOARD = "scoreBoard.json";

    // Position of the items inside a room
    public static final String WHERE = "is lying on the floor";

    // Number of letters / white spaces before automatic line break
    public static final int ANCIENTFONT_AUTONL = 20;

    // Number of needed letters to translate a stone tablet successfully
    public static final int STONETABLET_REQ_CORRECT = 12;

    // Names of lamps which can be used to enter dark rooms
    public static final List<String> LAMP_NAMES = new ArrayList<>(
            Arrays.asList("freshly polished lamp", "oil lamp", "oil lamp", "pocket lamp"));

    // Number of commands before enemies are rotated
    public static final int ROTATION_INTERVAL = 5;

    // Size of mystery chests
    public static final int MYSTERY_CHEST_SIZE = 30;
}