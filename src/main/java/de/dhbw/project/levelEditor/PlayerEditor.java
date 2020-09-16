package de.dhbw.project.levelEditor;

import de.dhbw.project.Player;

public class PlayerEditor {
    private Editor editor;

    public PlayerEditor(Editor editor) {
        this.editor = editor;
    }

    public Editor getEditor() {
        return editor;
    }

    public Player startPlayerEditor(Player player) {
        boolean goOn = false;
        if (null == player) {
            player = new Player();
        }
        do {
            goOn = editPlayer(player);
        } while (goOn);
        return player;
    }

    public boolean editPlayer(Player player) {
        boolean goOn = true;
        String input;
        System.out.println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
        input = SimpleUserInput.scan().toLowerCase();
        switch (input) {
        case "view":
            System.out.println("Player-Attributes:");
            printPlayerAttributes(player);
            return true;
        case "edit":
            do {
                goOn = editPlayerAttributes(player);
            } while (goOn);
            return true;
        case "item":
            player.setInventory(getEditor().getItemListEditor().startItemListEditor(player.getItemlist()));
            return true;
        case "equipment":
            player.setEquipment(getEditor().getItemListEditor().startItemListEditor(player.getEquipmentItemList()));
            return true;
        case "q":
            return false;
        case "quit":
            return false;
        default:
            SimpleUserInput.printUserInput(input);
            return true;
        }

    }

    // this Method is not tested
    public void printPlayerAttributes(Player player) {
        System.out.println("Name: " + player.getName());
        System.out.println("Points: " + player.getPoints());
        System.out.println("Strength: " + player.getStrength());
        System.out.println("Health: " + player.getHealth());
        System.out.println("Current Room: " + player.getRoomName());
        System.out.println("InventorySpace: " + player.getInventorySpace());
    }

    public boolean editPlayerAttributes(Player player) {
        String newRoomName;
        int newPoints, newStrength, newHealth, inventorySpace;
        newPoints = SimpleUserInput.editMethod("Points", player.getPoints());
        newStrength = SimpleUserInput.editMethod("Strength", player.getStrength());
        newHealth = SimpleUserInput.editMethod("Health", player.getHealth());
        newRoomName = SimpleUserInput.editMethodRoomName("Room", player.getRoomName(),
                getEditor().getGame().getRooms());
        inventorySpace = SimpleUserInput.editMethod("Inventory Space", player.getInventorySpace());
        Decision d;
        d = SimpleUserInput.storeDialogue("Player");
        switch (d) {
        case SAVE:
            player.setPoints(newPoints);
            player.setStrength(newStrength);
            player.setRoomName(newRoomName);
            player.setHealth(newHealth);
            player.setInventorySpace(inventorySpace);
            getEditor().setChanged();
            return false;
        case AGAIN:
            return true;
        case CANCEL:
            return false;
        default:
            return false;
        }
    }
}
