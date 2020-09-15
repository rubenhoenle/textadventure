package de.dhbw.project.levelEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.dhbw.project.Game;
import de.dhbw.project.Room;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class RoomEditor {

    private Editor editor;

    public Editor getEditor() {
        return editor;
    }

    public RoomEditor(Editor editor) {
        this.editor = editor;
    }

    public List<Room> startRoomEditor(List<Room> rooms) {
        boolean goOn = false;
        if (null == rooms) {
            rooms = new ArrayList<>();
        }
        do {
            goOn = editRooms(rooms);
        } while (goOn);
        return rooms;
    }

    public boolean editRooms(List<Room> rooms) {
        boolean goOn = true;
        System.out.println(
                "Enter 'inspect' to further inspect and edit an existing room, 'add' to add a room, 'delete' to delete a room or 'list' to list all Rooms.");
        String input = SimpleUserInput.scan();
        input.toLowerCase();
        switch (input) {
        case "inspect":
            Room room = SimpleUserInput.addMethodRoom("name of the room you want to inspect", rooms);
            if (null != room) {
                do {
                    goOn = inspect(rooms, room);
                } while (goOn);
            }
            return true;
        case "add":
            do {
                goOn = addRoom(rooms);
            } while (goOn);
            return true;
        case "delete":
            deleteRoom(rooms);
            return true;
        case "list":
            System.out.println("There are the following rooms:");
            listRooms(rooms);
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

    public boolean addRoom(List<Room> rooms) {
        String name, description, defaultItemLocation, conditionalItem;
        Boolean dark;
        name = SimpleUserInput.addMethod("Name");
        description = SimpleUserInput.addMethod("Description");
        dark = SimpleUserInput.addMethodBoolean("is dark");
        conditionalItem = SimpleUserInput
                .addMethod("Name of a conditional item, leave it empty if there is no such one required");
        defaultItemLocation = SimpleUserInput.addMethod("DefaultItemLocation");
        Decision decision = SimpleUserInput.storeDialogue("room");
        switch (decision) {
        case SAVE:
            if (conditionalItem.trim().equals("")) {
                conditionalItem = null;
            }
            rooms.add(new Room(name, description, "true", defaultItemLocation, null, new ArrayList<Friend>(),
                    new ArrayList<Enemy>(), dark, conditionalItem));
            getEditor().setChanged();
            return false;
        case AGAIN:
            return true;
        case ABBORT:
            return false;
        default:
            return false;
        }
    }

    public boolean editRoom(Room room) {
        String newName, newDescription, newDefaultItemLocation, newConditionalItem;
        boolean dark;
        newName = SimpleUserInput.editMethod("Name", room.getName());
        newDescription = SimpleUserInput.editMethod("Description", room.getDescription());
        dark = SimpleUserInput.editMethod("Darkness", room.isDark());
        newConditionalItem = SimpleUserInput.editMethod(
                "Name of a conditional item, leave it empty if there is no such one required",
                room.getConditionalItem());
        newDefaultItemLocation = SimpleUserInput.editMethod("DefaultItemLocation", room.getDefaultItemLocation());
        Decision d = SimpleUserInput.storeDialogue("Room");
        switch (d) {
        case SAVE:
            if (newConditionalItem.trim().equals("")) {
                newConditionalItem = null;
            }
            room.setName(newName);
            room.setDescription(newDescription);
            room.setDefaultItemLocation(newDefaultItemLocation);
            room.setConditionalItem(newConditionalItem);
            room.setIsDark(dark);
            getEditor().setChanged();
            return false;
        case AGAIN:
            return true;
        case ABBORT:
            return false;
        default:
            return false;
        }

    }

    public void deleteRoom(List<Room> rooms) {
        String input;
        Room room;
        System.out.println("Please enter the name of the room you want to delete:");
        input = SimpleUserInput.scan();
        room = getRoom(input, rooms);
        if (null == room) {
            System.out.println("There is no room available  with the name '" + input + "'.");
        } else {
            System.out.println("Room name: " + room.getName() + "\nRoom description: " + room.getDescription());
            Boolean delete = SimpleUserInput.deleteDialogue("Room", room.getName());
            if (delete) {
                rooms.remove(room);
                getEditor().setChanged();
            } else {
                System.out.println("Deletion of room '" + room.getName() + "' aborted.");
            }
        }
    }

    public void listRooms(List<Room> rooms) {
        if ((null != rooms) && (rooms.size() > 0)) {
            rooms.forEach(room -> {
                System.out.println("  -" + room.getName());
            });
        } else {
            System.out.println("There are no rooms to show yet!");
        }
    }

    public Room getRoom(String name, List<Room> rooms) {
        if ((null != name) && (null != rooms)) {
            List<Room> resultList = rooms.stream().filter(e -> e.getName().equals(name)).collect(Collectors.toList());
            if (resultList.size() > 0) {
                return resultList.get(0);
            }
        }
        return null;
    }

    public boolean inspect(List<Room> rooms, Room room) {
        Boolean goOn = true;
        String input;
        System.out.println("name: " + room.getName());
        System.out.println("description: " + room.getDescription());
        System.out.println("Is dark: " + room.isDark());
        System.out.println("Conditionalitem: " + room.getConditionalItem());
        System.out.println("defaultItemLocation: " + room.getDefaultItemLocation());
        System.out.println("Friends: ");
        getEditor().getFriendEditor().listFriends(room.getFriendList());
        System.out.println("Enemies");
        getEditor().getEnemyEditor().listEnemys(room.getEnemyList());
        System.out.println(
                "Enter 'edit' to edit the room, 'way' to edit the ways outgoing from this room, 'item' to edit its items, 'friends' to edit the friends' or 'enemy' to edit the enemies:");
        input = SimpleUserInput.scan().toLowerCase();
        switch (input) {
        case "edit":
            do {
                goOn = editRoom(room);
            } while (goOn);
            return true;
        case "item":
            room.setRoomItemList(getEditor().getItemListEditor().startItemListEditor(room.getRoomsItemList()));
            return true;
        case "way":
            do {
                goOn = getEditor().waysMenu(room, rooms);
            } while (goOn);
            return true;
        case "friend":
            room.setFriendList(getEditor().getFriendEditor().startFriendEditor(room.getFriendList()));
            return true;
        case "enemy":
            room.setEnemyList(getEditor().getEnemyEditor().startEnemyEditor(room.getEnemyList()));
        case "q":
            return false;
        case "quit":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            return true;
        }
    }
}
