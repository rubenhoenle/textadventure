package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    public Player player;
    @SerializedName("rooms")
    private List<Room> rooms;

    // Main playing method with the possible commands and their method call
    public void play(Player player) {
        this.player = player;
        System.out.println(getCurrentRoom());
        System.out.println(getCurrentRoom().getDescription());

        // While-loop for listening to the input commands after each action
        while (true) {
            Scanner userInput = new Scanner(System.in);
            String input = userInput.nextLine();

            if (input.matches("help|info"))
                System.out.println("Available commands: " + Constants.COMMAND_LIST);
            else if (input.equals("quit") || input.equals("exit"))
                System.exit(0);
            else if (input.matches("look|look (.+)")) {
                Pattern p = Pattern.compile("look (.+)");
                Matcher m = p.matcher(input);
                if (m.find()) {
                    look(m.group(1));
                } else
                    System.out.println(
                            "You have to say where you want to look (around, north, south, east, west, up, down).");
            } else if (input.matches("move|move (.+)")) {
                Pattern p = Pattern.compile("move (.+)");
                Matcher m = p.matcher(input);
                if (m.find())
                    move(m.group(1));
                else
                    System.out.println("You have to say where you want to move (north, south, east, west, up, down).");
            } else if (input.matches("take|take (.+)")) {
                Pattern p = Pattern.compile("take (.+)");
                Matcher m = p.matcher(input);
                if (m.find())
                    takeItem(m.group(1));
                else
                    System.out.println("You have to use the whole item name to pick it up.");
            } else if (input.matches("drop|drop (.+)")) {
                Pattern p = Pattern.compile("drop (.+)");
                Matcher m = p.matcher(input);
                if (m.find())
                    dropItem(m.group(1));
                else
                    System.out.println(
                            "You have to name an item you want to drop. Hint: use the command 'show inventory'.");
            } else if (input.equals("?"))
                System.out.println(getCurrentRoom());
            else if (input.equals("save"))
                Zork.saveGame(this);
            else if (input.matches("restore|load"))
                Zork.loadGame(Constants.SAVED_GAME);
            // returns the inventory list of the player => can be changed to a better regex expression
            else if (input.matches("inventory|show inventory"))
                getInventoryText();
            else
                System.out.println("Unknown command.");
        }
    }

    // Look method: shows for the current room: all available items and all available ways
    public void look(String lookingAt) {
        // Boolean that indicates if the command "look around" has been written
        boolean isEachDirection = lookingAt.equals(Constants.EACH_DIRECTION);

        // Entered phrase is not "look around" and is not "look + valid direction"
        if (!isProperInput(lookingAt, Constants.DIRECTIONS) && !isEachDirection)
            System.out.println("No valid direction. Please enter look north / south / west / east / up or down.");

        // The current room has no ways (actually this shouldn't happen as you have to enter the room somehow)
        else if (!hasWays())
            System.out.println("You're stucked in a room. There's no way hiding there.");

        // Entered phrase is "look + valid direction" but there is no way in the chosen direction
        else if (getWayForDirection(lookingAt) == null && !isEachDirection)
            System.out.println("There's nothing in the direction " + lookingAt + ".");

        // Entered phrase is "look around": show everything in the current room (ways, items)
        else if (isEachDirection) {
            // Show available ways in the current room
            for (Way way : getCurrentRoom().getRoomWayList()) {
                System.out.println("There is a " + way.getName() + " going " + way.getDirection() + ". ");
            }
            // Show available items in the current room
            if (getCurrentRoom().getRoomItemList() != null) {
                for (Item item : getCurrentRoom().getRoomItemList()) {
                    System.out.println("A " + item.getName() + " " + item.getWhere() + ".");
                }
            }
        }

        // Entered phrase is "look + valid direction": show way for the selected direction
        else {
            Way resultWay = getWayForDirection(lookingAt);
            System.out.println("There is a " + resultWay.getName() + " going " + lookingAt + ".");
        }
    }

    // Move method: moves in the chosen direction if it's a valid direction and if there's a way in this direction
    public void move(String direction) {
        if (!isProperInput(direction, Constants.DIRECTIONS))
            System.out.println("No valid direction. Please enter move north / south / west / east / up or down.");
        else if (!hasWays())
            System.out.println("You're stucked in a room. There's no way hiding there.");
        else if (getWayForDirection(direction) == null)
            System.out.println("You can't move in this direction.");
        else {
            Way resultWay = getWayForDirection(direction);
            System.out.println("You're taking the " + resultWay.getName() + " " + direction + ". ");
            player.setRoomName(resultWay.getTo());
            System.out.println(getCurrentRoom() + " It's " + getCurrentRoom().getDescription() + ".");
        }
    }

    // Helper method: Checks if a given string is contained in a given list
    private boolean isProperInput(String input, List<String> properInput) {
        if (properInput.contains(input))
            return true;
        else
            return false;
    }

    // Helper method: Checks if the current room has ways
    private boolean hasWays() {
        if (getCurrentRoom().getRoomWayList().size() == 0)
            return false;
        else
            return true;
    }

    // Helper method: Returns the way in the given direction if available (otherwise the way is null)
    private Way getWayForDirection(String direction) {
        Way resultWay = null;
        for (Way way : getCurrentRoom().getRoomWayList()) {
            if (way.getDirection().equals(direction)) {
                resultWay = way;
                break;
            }
        }
        return resultWay;
    }

    // Helper method: Returns the item with the name if available (otherwise the item is null)
    private Item getItemFromCurrentRoom(String itemName) {
        Item resultItem = null;
        for (Item item : getCurrentRoom().getRoomItemList()) {
            if (item.getName().equals(itemName)) {
                resultItem = item;
                break;
            }
        }
        return resultItem;
    }

    // Helper method: Returns the current room object
    private Room getCurrentRoom() {
        Room currentRoom = null;
        for (Room r : rooms) {
            if (r.getName().equals(player.getRoomName()))
                currentRoom = r;
        }
        return currentRoom;
    }

    // Take item method: takes an available item in the room and adds it to inventory
    public void takeItem(String itemName) {
        if (!isProperInput(itemName, getCurrentRoom().getRoomItemNameList()))
            System.out.println("No item found with name " + itemName + " in room " + getCurrentRoom().getName());

        else {
            Item takenItem = getItemFromCurrentRoom(itemName);
            player.addItem(takenItem);
            getCurrentRoom().removeItem(takenItem);
            System.out.println("You took " + takenItem.getName() + " and added it to the inventory.");
        }
    }

    // Drop item method: removes an item from the inventory and adds it to the current room
    public void dropItem(String itemName) {
        Item dropItem = player.getItem(itemName);
        if (null == dropItem) { // condition item name is valid and in inventory
            System.out.println("The item " + itemName + " was not found in the inventory and cannot be dropped.");
        } else {
            player.removeItem(dropItem);
            getCurrentRoom().addItem(dropItem);
            System.out.println(
                    "The item " + dropItem.getName() + " was dropped in room '" + getCurrentRoom().getName() + "'.");
        }
    }

    // Function to show whats in the inventory
    private void getInventoryText() {
        System.out.println("---Inventory---");
        for (int i = 0; i < player.getInventory().size(); i++) {
            System.out.println(
                    player.getInventory().get(i).getName() + " - " + player.getInventory().get(i).getDescription());
            if (player.getInventory().get(i).getStrength() != 0)
                System.out.println(" '- Strength: " + player.getInventory().get(i).getStrength());
        }

        System.out.println("---------------");
    }
}