package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.item.Item;
import de.dhbw.project.nls.Commands;

import java.util.List;
import java.util.Scanner;

public class Game {
    public Player player;
    @SerializedName("rooms")
    private List<Room> rooms;
    public transient Commands commands;

    // Main playing method with the possible commands and their method call
    public void play(Player player) {
        this.player = player;
        System.out.println(getCurrentRoom());
        System.out.println(getCurrentRoom().getDescription());

        // While-loop for listening to the input commands after each action
        while (true) {
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine();

            if (input.length() == 1) {
                if (Constants.SHORT_DIRECTIONS.contains(input))
                    input = "move " + input;
                else if (input.equalsIgnoreCase("l"))
                    input = "look around";
            }

            commands.execute(input + "\0");
        }
    }

    // Helper method: Checks if the current room has ways
    public boolean hasWays() {
        return getCurrentRoom().getRoomWayList().size() > 0;
    }

    // Helper method: Returns the way in the given direction if available (otherwise the way is null)
    public Way getWayForDirection(String direction) {
        for (Way way : getCurrentRoom().getRoomWayList()) {
            if (way.getDirection().equals(direction)) {
                return way;
            }
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

    // Helper method: Returns the current room object
    public Room getCurrentRoom() {
        for (Room r : rooms) {
            if (r.getName().equals(player.getRoomName()))
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

    public boolean deleteRoom(Room room) {
        if (rooms.contains(room)) {
            rooms.remove(room);
            return true;
        } else {
            return false;
        }
    }

}