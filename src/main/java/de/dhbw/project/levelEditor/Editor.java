package de.dhbw.project.levelEditor;

import static de.dhbw.project.levelEditor.SimpleUserInput.editMethod;

import java.util.List;

import de.dhbw.project.*;
import de.dhbw.project.item.*;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

import java.util.List;

import static de.dhbw.project.levelEditor.SimpleUserInput.editMethod;

public class Editor {

    private boolean changed = false;

    private ItemListEditor itemListEditor;
    private RoomEditor roomEditor;
    private QuestItemEditor questItemEditor;
    private QuestsEditor questEditor;
    private FriendEditor friendEditor;
    private EnemyEditor enemyEditor;
    private PlayerEditor playerEditor;
    private Game game;

    public boolean isChanged() {
        return changed;
    }

    public void setChanged() {
        changed = true;
    }

    public Editor(Game game) {
        this.game = game;
        itemListEditor = new ItemListEditor(this);
        roomEditor = new RoomEditor(this);
        questItemEditor = new QuestItemEditor(this);
        questEditor = new QuestsEditor(this);
        friendEditor = new FriendEditor(this);
        enemyEditor = new EnemyEditor(this);
        playerEditor = new PlayerEditor(this);
    }

    public ItemListEditor getItemListEditor() {
        return itemListEditor;
    }

    public RoomEditor getRoomEditor() {
        return roomEditor;
    }

    public QuestItemEditor getQuestItemEditor() {
        return questItemEditor;
    }

    public QuestsEditor getQuestEditor() {
        return questEditor;
    }

    public FriendEditor getFriendEditor() {
        return friendEditor;
    }

    public EnemyEditor getEnemyEditor() {
        return enemyEditor;
    }

    public PlayerEditor getPlayerEditor() {
        return playerEditor;
    }

    public Game getGame() {
        return game;
    }

    public void edit() {
        Boolean goOn = false;
        System.out.println("Welcome to the level editor! :)");
        System.out.println("Disclaimer: You have to do the editing in the right order:");
        System.out.println(
                "Example: you first have to create two rooms, before you can create a way between the two rooms.");
        do {
            System.out.println(
                    "Enter 'edit' to view and edit the exitsting game or 'new' to discard the old one and generate a new one from scratch:");
            String input = SimpleUserInput.scan().toLowerCase();
            switch (input) {
            case "edit":
                break;
            case "new":
                game = new Game();
                break;
            default:
                SimpleUserInput.printUserInput(input);
            }
        } while (goOn);
        do {
            goOn = menu(game);
        } while (goOn);

        if (changed) {
            do {
                goOn = save(game);
            } while (goOn);
        }
        System.out.println("The level editor says 'Goodbye'!");
    }

    public Boolean menu(Game game) {
        Boolean goOn = true;
        System.out.println("Enter 'room' to edit rooms or 'player' to edit the player:");
        String input = SimpleUserInput.scan();
        input.toLowerCase();
        switch (input) {
        case "room":
            game.setRooms(getRoomEditor().startRoomEditor(game.getRooms()));
            return true;
        case "player":
            game.setPlayer(getPlayerEditor().startPlayerEditor(game.getPlayer()));
            return true;
        case "quit":
            return false;
        case "q":
            return false;
        case "exit":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            return true;
        }

    }

    public boolean waysMenu(Room room, List<Room> rooms) {
        if (null != room) {
            String input;
            System.out.println(
                    "Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
            input = SimpleUserInput.scan().toLowerCase();
            switch (input) {
            case "list":
                listWays(room);
                return true;
            case "edit":
                editWay(room, rooms);
                return true;
            case "add":
                addWay(room, rooms);
                return true;
            case "delete":
                deleteWay(room);
                return true;
            case "quit":
                return false;
            case "q":
                return false;
            default:
                SimpleUserInput.printUserInput(input);
                return true;
            }
        } else {
            return false;
        }
    }

    public void addWay(Room room, List<Room> rooms) {
        Way way = Way.createWay(room, rooms);
        if (null != way) {
            room.addWay(way);
            setChanged();
        }
    }

    public void editWay(Room room, List<Room> rooms) {
        if (null != room) {
            System.out.println("Please enter the name of the name of the way you want to delete:");
            String input = SimpleUserInput.scan();
            Way way = room.getRoomWayByName(input);
            if (null != way) {
                Way newWay = Way.editWay(way, rooms);
                if (null != newWay) {
                    room.deleteWay(way);
                    room.addWay(newWay);
                    setChanged();
                }
            } else {
                System.out.println(
                        "In this room '" + room.getName() + "' there is no way with the name '" + input + "'!");
            }
        }
    }

    public void listWays(Room room) {
        if (null != room) {
            List<Way> wayList = room.getRoomWayList();
            if (wayList.size() > 0) {
                wayList.forEach(way -> {
                    System.out.println(
                            "A " + way.getName() + " is leading " + way.getDirection() + " to " + way.getTo() + ".");
                });
            } else {
                System.out.println("There are no ways to list yet!");
            }
        } else {
            System.out.println("Error: the parameter room is null!");
        }
    }

    public void deleteWay(Room room) {
        if (null != room) {
            System.out.println("Please enter the name of the name of the way you want to delete:");
            String input = SimpleUserInput.scan();
            Way way = room.getRoomWayByName(input);
            if (null != way) {
                boolean delete = SimpleUserInput.deleteDialogue("way",
                        "leading " + way.getDirection() + " to " + way.getTo());
                if (delete) {
                    room.deleteWay(way);
                    setChanged();
                }
            } else {
                System.out.println(
                        "In this room '" + room.getName() + "' there is no way with the name '" + input + "'!");
            }
        }
    }

    public boolean save(Game game) {
        if (isChanged()) {
            String input;
            System.out.println("Do you want to save your changes? (yes/no)");
            input = SimpleUserInput.scan().toLowerCase();
            if (input.equals("yes") | input.equals("y")) {
                Zork.saveModel(game);
                return false;
            } else if (input.equals("no") || (input.equals("n"))) {
                System.out.println("Your changes won't be saved.");
                return false;
            } else {
                SimpleUserInput.printUserInput(input);
                return true;
            }
        } else {
            return false;
        }
    }

}