package de.dhbw.project.levelEditor;

import static de.dhbw.project.levelEditor.SimpleUserInput.editMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.management.Descriptor;
import javax.swing.plaf.TextUI;

import org.w3c.dom.UserDataHandler;

import de.dhbw.project.*;
import de.dhbw.project.item.*;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class Editor {
    private Game game;

    private boolean changed = false;

    public void edit(Game g) {
        game = g;
        menu();
    }

    private void menu() {
        boolean exit = false;
        System.out.println("Welcome to the level editor! :)");
        while (!exit) {
            System.out.println("Enter 'room' to edit rooms or 'player' to edit the player:");
            String input = SimpleUserInput.userInput.nextLine();
            input.toLowerCase();
            switch (input) {
            case "room":
                editRooms();
                break;
            case "player":
                editPlayer();
                break;
            case "q":
                exit = true;
            case "exit":
                exit = true;
            }
        }
        save();
        System.out.println("The level editor says 'Goodbye'!");
    }

    public void editPlayer() {
        boolean exit = false;
        String input;
        while (!exit) {
            System.out
                    .println("Enter 'view' to view his attributes, 'edit' to edit them and 'item' to edit his items:");
            input = SimpleUserInput.userInput.nextLine().toLowerCase();
            switch (input) {
            case "view":
                System.out.println("Player-Attributes:");
                printPlayerAttributes();
                break;
            case "edit":
                editPlayerAttributes();
            case "item":
                editItemList(game.player.getItemlist());
                break;
            case "q":
                exit = true;
                break;
            case "quit":
                exit = true;
                break;
            default:
                System.out.println("You've entered '" + input + "'.'");
                break;
            }
        }
    }

    private void editItemList(ItemList liste) {
        if (liste == null){
            return;
        }
        boolean exit = false;
        String input;
        while (!exit) {
            System.out.println(
                    "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
            input = SimpleUserInput.userInput.nextLine().toLowerCase();
            switch (input) {
            case "list":
                System.out.println("All items:");
                listItems(liste);
                break;
            case "add":
                addItemToItemList(game.player.getItemlist());
                break;
            case "edit":
                editItem(game.player.getItemlist());
                break;
            case "delete":
                deleteItem(game.player.getItemlist());
                break;
            case "quit":
                exit = true;
                break;
            case "q":
                exit = true;
                break;
            default:
                System.out.println("You've entered '" + input + "'.");
                break;
            }
        }
    }

    private void editItem(ItemList liste) {
        System.out.println("Please enter the name of the item you want to edit:");
        String input = SimpleUserInput.userInput.nextLine();
        Item item = liste.getItem(input);
        if (null != item) {
            String typ = item.getClass().getSimpleName();
            switch (typ) {
            case "Book":
                Book book = Book.edit((Book) item);
                if (null != book) {
                    liste.removeItem(item);
                    liste.addItem(book);
                    changed = true;
                }
                break;
            case "Clothing":
                Clothing clothing = Clothing.edit((Clothing) item);
                if (null != clothing) {
                    liste.removeItem(item);
                    liste.addItem(clothing);
                    changed = true;
                }
                break;
            case "Food":
                Food food = Food.edit((Food) item);
                if (null != food) {
                    liste.removeItem(item);
                    liste.addItem(food);
                    changed = true;
                }
                break;
            case "Resource":
                Resource resource = Resource.edit((Resource) item);
                if (null != resource) {
                    liste.removeItem(item);
                    liste.addItem(resource);
                    changed = true;
                }
                break;
            case "Tool":
                Tool tool = Tool.edit((Tool) item);
                if (null != tool) {
                    liste.removeItem(item);
                    liste.addItem(tool);
                    changed = true;
                }
                break;
            case "Weapon":
                Weapon weapon = Weapon.edit((Weapon) item);
                if (null != weapon) {
                    liste.removeItem(item);
                    liste.addItem(weapon);
                    changed = true;
                }
                break;
            default:
                System.out.println("The class '" + typ + "' isn't available in the editor yet.");
            }
        } else {
            System.out.println("There is no item with the name '" + input + "'.");
        }
    }

    private void deleteItem(ItemList liste) {
        System.out.println("Please enter the name of the item you want to delete:");
        String input = SimpleUserInput.userInput.nextLine();
        Item item = liste.getItem(input);
        if (null != item) {
            liste.removeItem(item);
            changed = true;
        } else {
            System.out.println("There is no item with the name '" + input + "'.");
        }
    }

    private void addItemToItemList(ItemList list) {
        String input;
        System.out.println("Enter the type of item you want add (Book/Clothing/Food/Resource/Tool/Weapon)");
        input = SimpleUserInput.userInput.nextLine().toLowerCase();
        switch (input) {
        case "book":
            Book book = Book.create();
            if (null != book) {
                list.addItem(book);
                changed = true;
            }
            break;
        case "clothing":
            Clothing clothing = Clothing.create();
            if (null != clothing) {
                list.addItem(clothing);
                changed = true;
            }
            break;
        case "food":
            Item food = Food.create();
            if (null != food) {
                list.addItem(food);
                changed = true;
            }
            break;
        case "resource":
            Resource resource = Resource.create();
            if (null != resource) {
                list.addItem(resource);
                changed = true;
            }
            break;
        case "tool":
            Tool tool = Tool.create();
            if (null != tool) {
                list.addItem(tool);
                changed = true;
            }
            break;
        case "weapon":
            Weapon weapon = Weapon.create();
            if (null != weapon) {
                list.addItem(weapon);
                changed = true;
            }
            break;
        default:
            System.out.println("You have entered '" + input + "'.");
            break;
        }
    }

    private void listItems(ItemList itemList) {
        if (null != itemList) {
            List<Item> liste = itemList.getAllItemList();
            if (0 == liste.size()) {
                System.out.println("There are no items in this list!");
            } else {
                liste.forEach(element -> {
                    System.out.println("  -" + element.getName());
                });
            }
        } else {
            System.out.println("Error: no ItemList found!!");
        }
    }

    private void printPlayerAttributes() {
        System.out.println("Name: " + game.player.getName());
        System.out.println("Points: " + game.player.getPoints());
        System.out.println("Strength: " + game.player.getStrength());
        System.out.println("Current Room: " + game.player.getRoomName());
    }

    private void editPlayerAttributes() {
        boolean exit = false;
        String newName, newRoomName;
        int newPoints, newStrength;
        Player player = game.player;
        while (!exit) {
            newName = editMethod("Name", game.player.getName());
            newPoints = Integer.parseInt(editMethod("Points", String.valueOf(game.player.getPoints())));
            newStrength = Integer.parseInt(editMethod("Strength", String.valueOf(game.player.getStrength())));
            newRoomName = editMethod("Room", game.player.getRoomName());
            while (null == getRoom(newRoomName)) {
                System.out.println("There is no room with the name '" + newRoomName + "'.'");
                System.out.println("Please enter the name of a valid room!");
                newRoomName = editMethod("Room", game.player.getRoomName());
            }
            System.out.println("You've entered the following values:");
            System.out.println("Name: " + newName);
            System.out.println("Points: " + newPoints);
            System.out.println("Strength: " + newStrength);
            System.out.println("Room:" + newRoomName);
            Decision d;
            d = SimpleUserInput.storeDialogue("Player");
            switch (d) {
            case SAVE:
                player.setName(newName);
                player.setPoints(newPoints);
                player.setStrength(newStrength);
                player.setRoomName(newRoomName);
                changed = true;
                exit = true;
                break;
            case AGAIN:
                break;
            case ABBORT:
                exit = true;
                break;
            }
        }
    }

    private void editRooms() {
        boolean exit = false;
        while (!exit) {
            System.out.println(
                    "Enter 'inspect' to further inspect and edit an existing room, 'add' to add a room, 'delete' to delete a room or 'list' to list all Rooms.");
            String input = SimpleUserInput.userInput.nextLine();
            input.toLowerCase();
            switch (input) {
            case "inspect":
                inspect();
                break;
            case "add":
                addRoom();
                break;
            case "delete":
                deleteRoom();
                break;
            case "list":
                System.out.println("There are the following rooms:");
                listRooms();
                break;
            case "q":
                exit = true;
                break;
            case "exit":
                exit = true;
                break;
            }
        }
    }

    private void inspect() {
        String input;
        System.out.println("Please enter the name of the room you want to inspect:");
        input = SimpleUserInput.userInput.nextLine();
        Room room = getRoom(input);
        if (null == getRoom(input)) {
            System.out.println("There is no room available with the name '" + input + "'.");
        } else {
            System.out.println("name: " + room.getName());
            System.out.println("description: " + room.getDescription());
            System.out.println("defaultItemLocation: " + room.getDefaultItemLocation());
            boolean exit = false;
            while (!exit) {
                System.out.println(
                    "Enter 'edit' to edit the room, 'way' to edit the ways outgoing from this room or 'item' to edit its items:");
                input = SimpleUserInput.userInput.nextLine();
                switch (input) {
                case "edit":
                    editRoom(room);
                    break;
                case "item":
                    if(null != room.getRoomItemList()){
                        editItemList(room.getRoomsItemList());
                    }
                    break;
                case "way":
                    waysMenu(room);
                    break;
                case "q":
                    exit = true;
                    break;
                case "quit":
                    exit = true;
                    break;
                default:
                    System.out.println("You've entered '" + input + "'.");
                    break;
                }
            }
        }
    }

    private void waysMenu(Room room) {
        if (null != room) {
            boolean exit = false;
            String input;
            while (!exit) {
                System.out.println(
                        "Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
                input = SimpleUserInput.userInput.nextLine().toLowerCase();
                switch (input) {
                case "list":
                    listWays(room);
                    break;
                case "edit":
                    editWay(room);
                    break;
                case "add":
                    addWay(room);
                    break;
                case "delete":
                    deleteWay(room);
                case "quit":
                    exit = true;
                    break;
                case "q":
                    exit = true;
                    break;
                default:
                    System.out.println("You'v entered '" + input + "'.");
                    break;
                }
            }
        }
    }

    private void addWay(Room room) {
        Way way = Way.createWay(room, game);
        if (null != way) {
            room.addWay(way);
            changed = true;
        }
    }

    private void editWay(Room room) {
        if (null != room) {
            System.out.println("Please enter the name of the name of the way you want to delete:");
            String input = SimpleUserInput.userInput.nextLine();
            Way way = room.getRoomWayByName(input);
            if (null != way) {
                Way newWay = Way.editWay(way, game);
                if (null != newWay) {
                    room.deleteWay(way);
                    room.addWay(newWay);
                    changed = true;
                }
            } else {
                System.out.println(
                        "In this room '" + room.getName() + "' there is no way with the name '" + input + "'!");
            }
        }
    }

    private void listWays(Room room) {
        if (null != room) {
            List<Way> wayList = room.getRoomWayList();
            wayList.forEach(way -> {
                System.out.println(
                        "A " + way.getName() + " is leading " + way.getDirection() + " to " + way.getTo() + ".");
            });
        }
    }

    private void deleteWay(Room room) {
        if (null != room) {
            System.out.println("Please enter the name of the name of the way you want to delete:");
            String input = SimpleUserInput.userInput.nextLine();
            Way way = room.getRoomWayByName(input);
            if (null != way) {
                boolean delete = SimpleUserInput.deleteDialogue("way",
                        "leading " + way.getDirection() + " to " + way.getTo());
                if (delete) {
                    room.deleteWay(way);
                    changed = true;
                }
            } else {
                System.out.println(
                        "In this room '" + room.getName() + "' there is no way with the name '" + input + "'!");
            }
        }
    }

    private void addRoom() {
        boolean exit = false;
        while (!exit) {
            String name, description, defaultItemLocation;
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            defaultItemLocation = SimpleUserInput.addMethod("DefaultItemLocation");
            System.out.println("Your input: ");
            System.out.println("Name: " + name);
            System.out.println("Description " + description);
            System.out.println("Default Item Location: " + defaultItemLocation);
            Decision decision = SimpleUserInput.storeDialogue("room");
            if (decision == Decision.SAVE) {
                game.addRoom(new Room(name, description, "true", defaultItemLocation, null, null, null, false));
                changed = true;
            }
            if (!(decision == Decision.AGAIN)) {
                exit = true;
            }
        }
    }

    public void editRoom(Room room) {
        boolean exit = false;
        String newName, newDescription, newDefaultItemLocation;
        while (!exit) {
            newName = SimpleUserInput.editMethod("Name", room.getName());
            newDescription = editMethod("Description", room.getDescription());
            newDefaultItemLocation = editMethod("DefaultItemLocation", room.getDefaultItemLocation());
            Decision d = SimpleUserInput.storeDialogue("Room");
            switch (d) {
            case SAVE:
                room.setName(newName);
                room.setDescription(newDescription);
                room.setDefaultItemLocation(newDefaultItemLocation);
                changed = true;
                exit = true;
                break;
            case AGAIN:
                break;
            case ABBORT:
                exit = true;
                break;
            }

        }
    }

    private void deleteRoom() {
        String input;
        Room room;
        System.out.println("Please enter the name of the room you want to delete:");
        input = SimpleUserInput.userInput.nextLine();
        room = getRoom(input);
        if (null == room) {
            System.out.println("There is no room available  with the name '" + input + "'.");
        } else {
            System.out.println("Room name: " + room.getName() + "\nRoom description: " + room.getDescription());
            System.out.println("Are you sure you want to delete the room '" + room.getName() + "'? (yes/no)");
            boolean exit = false;
            input = SimpleUserInput.userInput.nextLine().toLowerCase();
            while (!exit) {
                if (input.equals("yes") || input.equals("y")) {
                    game.deleteRoom(room);
                    changed = true;
                    exit = true;
                } else if (input.equals("no") || input.equals("n")) {
                    System.out.println("Deletion of room '" + room.getName() + "' aborted.");
                    exit = true;
                } else {
                    System.out.println("You've entered '" + "'.");
                }
            }
        }
    }

    private void listRooms() {
        List<Room> rooms = game.getRooms();
        rooms.forEach(room -> {
            System.out.println("  -" + room.getName());
        });
    }

    private Room getRoom(String name) {
        List<Room> liste = game.getRooms();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).getName().equals(name)) {
                return liste.get(i);
            }
        }
        return null;
    }

    private void save() {
        if (changed) {
            boolean exit = false;
            String input;
            while (!exit) {
                System.out.println("Do you want to save your changes? (yes/no)");
                input = SimpleUserInput.userInput.nextLine().toLowerCase();
                if (input.equals("yes") | input.equals("y")) {
                    Zork.saveModel(game);
                    exit = true;
                } else if (input.equals("no") || (input.equals("n"))) {
                    System.out.println("Your changes won't be saved.");
                    exit = true;
                } else {
                    System.out.println("You've entered '" + "'.");
                }
            }
        }
    }

}