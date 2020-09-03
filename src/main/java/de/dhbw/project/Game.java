package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.RoamingEnemy;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.LampState;
import de.dhbw.project.nls.Commands;

import java.util.ArrayList;
import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class Game {
    public Player player;
    @SerializedName("rooms")
    private List<Room> rooms;
    public transient Commands commands;
    @SerializedName("turn")
    private int turn;
    private boolean gameEnd = false;
    private int counter = 0;

    // Main playing method with the possible commands and their method call
    public void play(Player player) {
        this.player = player;

        if (player.getName() == null) {
            player.enterPlayerName();
        }

        System.out.println("Hello " + player.getName() + "!");
        System.out.println(getCurrentRoom());
        System.out.println(getCurrentRoom().getDescription());

        // While-loop for listening to the input commands after each action
        while (!gameEnd) {
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine();

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(input), null);

            if (input.length() == 1) {
                if (Constants.SHORT_DIRECTIONS.contains(input))
                    input = "move " + input;
                else if (input.equalsIgnoreCase("l"))
                    input = "look around";
            }

            if (playerCanSeeSomething(input)) {
                commands.execute(input + "\0");
                if (turn % Constants.ROTATION_INTERVAL == 0) {
                    turn++;
                    roamAllRoamingEnemies();
                }
                player.isAttacked(this);
            }
        }
    }

    private boolean playerCanSeeSomething(String input) {
        boolean showInventory = (input.toLowerCase().contains("inventory") || input.trim().toLowerCase().equals("i"));
        boolean switchLamp = (input.toLowerCase().contains("switch"));
        if ((getCurrentRoom().isDark()) && (player.getLampState() == LampState.OFF) && !switchLamp && !showInventory) {
            System.out.println(
                    "It's dark here. You must switch your lamp on again to see something before you can do any action.");
            return false;
        }

        else if (getCurrentRoom().isDark() && input.toLowerCase().contains("drop")) {
            for (String lampname : Constants.LAMP_NAMES) {
                Item lamp = player.getItem(lampname);
                if ((lamp != null) && input.toLowerCase().contains(lampname)
                        && (lamp.getItemstate() == ItemState.ACTIVE)) {
                    System.out.println(
                            "I'm sorry but you can't drop this. It's dark in here and without this lamp you couldn't see anything.");
                    return false;
                }
            }
        }

        return true;
    }

    // Helper method: Checks if the current room has ways
    public boolean hasWays() {
        return getCurrentRoom().getRoomWayList().size() > 0;
    }

    // Helper method: Returns the way in the given direction if available (otherwise the way is null)
    // If there are several ways in the same direction, there is one randomly chosen
    public Way getWayForDirection(String direction) {
        List<Way> wayList = new ArrayList<>();
        for (Way way : getCurrentRoom().getRoomWayList()) {
            if (way.getDirection().equals(direction)) {
                wayList.add(way);
            }
        }
        if (1 == wayList.size()) {
            return wayList.get(0);
        } else if (1 < wayList.size()) {
            float randomFloat = (float) (Math.random() * wayList.size());
            return wayList.get((int) randomFloat);
        }
        return null;
    }

    // Helper method: Returns the item with the name if available (otherwise the item is null)
    public Item getItemFromCurrentRoom(String itemName) {
        for (Item item : getCurrentRoom().getRoomItemList()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public Character getCharacterFromCurrentRoom(String characterName) {
        for (Character c : getCurrentRoom().getCharacterList()) {
            if (c.getName().equals(characterName)) {
                return c;
            }
        }
        return null;
    }

    public Character getCharacter(String characterName) {
        for (Room r : getRooms()) {
            for (Character c : r.getCharacterList()) {
                if (c.getName().equals(characterName)) {
                    return c;
                }
            }
        }
        return null;
    }

    public InteractiveObject getInteractiveObjectFromCurrentRoom(String interactiveObjectName) {
        for (InteractiveObject io : getCurrentRoom().getRoomInteractiveObjectsList()) {
            if (io.getName().equals(interactiveObjectName)) {
                return io;
            }
        }
        return null;
    }

    public void roamAllRoamingEnemies() {
        // list of already moved RoamingEnemies
        List<RoamingEnemy> moved = new ArrayList<>();
        // move RoamingEnemy room by room
        for (Room room : rooms) {
            // check and move every enemy in room
            List<RoamingEnemy> tmp = new ArrayList<>();
            tmp.addAll(room.getRoamingEnemyList());
            for (RoamingEnemy e : tmp) {
                // RoamingEnemy was just moved to this room
                if (moved.contains(e) || e.isKilled())
                    continue;
                // RoamingEnemy is moved and added to list
                Room next = getRoom(e.getNextRoom(room.getName()));
                room.removeRoamingEnemy(e);
                next.addRoamingEnemy(e);
                moved.add(e);
            }
        }
    }

    // Helper method: Returns the current room object
    public Room getCurrentRoom() {
        for (Room r : rooms) {
            if (r.getName().equals(player.getRoomName()))
                return r;
        }
        return null;
    }

    public Room getRoom(String name) {
        for (Room r : rooms) {
            if (r.getName().equals(name))
                return r;
        }
        return null;
    }

    // Helper method: Returns full direction
    public String getFullDirection(char direction) {
        switch (direction) {
        case 'n':
            return "north";
        case 'e':
            return "east";
        case 's':
            return "south";
        case 'w':
            return "west";
        case 'u':
            return "up";
        case 'd':
            return "down";
        default:
            return java.lang.Character.toString(direction);
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public boolean deleteRoom(Room room) {
        if (rooms.contains(room)) {
            rooms.remove(room);
            return true;
        } else {
            return false;
        }
    }

    public void incTurn() {
        this.turn++;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {

        EndScreen.print();
        System.out.println("Congratulations!!! You successfully finished the game!");
        System.out.println("You managed to repair your ship and find the treasure full of gold!");
        System.out.println(
                "As soon as you enter the ship you wake up and find yourself lying next to a math book. THE TEST IS IN 1 HOUR!!");

        this.gameEnd = gameEnd;
    }

    public int getMainQuestNumber() {
        if (getRooms() != null) {
            for (int i = 0; i < getRooms().size(); i++) {
                if (getRooms().get(i).getRoomInteractiveObjectsList() != null) {
                    for (int a = 0; a < getRooms().get(i).getRoomInteractiveObjectsList().size(); a++) {
                        if (getRooms().get(i).getRoomInteractiveObjectsList().get(a).getQuest() != null) {
                            if (getRooms().get(i).getRoomInteractiveObjectsList().get(a).getQuest().isMainQuest()) {
                                counter++;
                            }
                        }
                    }
                }
                if (getRooms().get(i).getFriendList() != null) {
                    for (int b = 0; b < getRooms().get(i).getFriendList().size(); b++) {
                        if (getRooms().get(i).getFriendList().get(b).getQuests() != null) {
                            for (int c = 0; c < getRooms().get(i).getFriendList().get(b).getQuests().size(); c++) {
                                if (getRooms().get(i).getFriendList().get(b).getQuests() != null) {
                                    if (getRooms().get(i).getFriendList().get(b).getQuests().get(c).isMainQuest()) {
                                        counter++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return counter;
    }
}