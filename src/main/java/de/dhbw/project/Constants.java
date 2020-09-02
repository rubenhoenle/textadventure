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
            "move + north / east / west / south / up / down", "look + around / north / east / west / south / up / down",
            "take + <item name>", "drop + <item name>", "equip <item name>", "strip off <item name>", "inventory",
            "talk + <character name>", "attack + <character name>",
            "accept + <quest> + from + <character name /interactive object name>+", "quest inventory",
            "investigate <interactive object name>", "quit or exit", "?", "help or info"));

    // Location of the jsons for a new or a saved game
    public static final String DEFAULT_PATH = "src/main/resources";
    public static final String SAVED_GAME = "savedGame.json";
    public static final String NEW_GAME = "database.json";

    // Position of the items inside a room
    public static final String WHERE = "is lying on the floor";

    // Number of letters / white spaces before automatic line break
    public static final int ANCIENTFONT_AUTONL = 20;

    public static final List<String> LAMP_NAMES = new ArrayList<>(Arrays.asList("lamp", "pocket lamp"));
}