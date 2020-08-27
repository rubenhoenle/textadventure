package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Character;

import java.util.ArrayList;
import java.util.List;

public class Player {
    @SerializedName("name")
    private String name = "Hugo";
    @SerializedName("points")
    private int points = 0;
    @SerializedName("strength")
    private int strength = 20;
    @SerializedName("inventory")
    private List<Item> inventory = new ArrayList<>();
    @SerializedName("roomName")
    private String roomName;
    @SerializedName("equipment")
    private List<Item> equipment = new ArrayList<>();

    // Getters and setters for a player

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Item> getEquipment() {
        return equipment;
    }

    public List<Item> addItem(Item item) {
        inventory.add(item);
        return inventory;
    }

    public List<Item> removeItem(Item item) {
        inventory.remove(item);
        return inventory;
    }

    // getItem Method: returns an item, if an item with itemName was found, else returns null
    public Item getItem(String itemName) {
        for (Item i : inventory) {
            if (i.getName().equals(itemName)) {
                return i;
            }
        }
        return null;
    }

    public boolean winsFight(Character c) {
        // TODO find better algorithm to fight -> player health, equipped weapons, ...
        return getStrength() >= c.getStrength();
    }

    public void isAttacked(Game g) {
        Room r = g.getCurrentRoom();
        if (r.getEnemyList() != null) {
            for (Enemy e : r.getEnemyList()) {
                if (!e.isKilled() && e.isAutoAttack()) {
                    System.out.println(
                            "Just after you entered " + r.getName() + " you are attacked by " + e.getName() + "!");
                    fight(e, r);
                }
            }
        }
    }

    public void fight(Character c, Room r) {
        if (!c.isKilled()) {
            System.out.println("You fight with " + c.getName() + "!");
            System.out.println(c.getName() + ": " + c.getStartStatement());
            if (winsFight(c)) {
                System.out.println(c.getName() + ": " + c.getKillStatement());
                System.out.println("You win the fight against " + c.getName() + "!");
                if (c instanceof Enemy) {
                    for (Item i : ((Enemy) c).getDropItemList()) {
                        System.out.println(c.getName() + " drops " + i.getName());
                        r.addItem(i);
                    }
                }
                c.setKilled(true);
            } else {
                System.out.println("You lose the fight against " + c.getName() + "! You faint!");
                System.out.println("----------");
                System.out.println("Last save game will be loaded! \n");
                Zork.loadGame(Constants.SAVED_GAME);
            }
        }
    }

    // counts how often the player has a specific item in his inventory (returns -1 when item is not found)
    public int getNumberOfItem(String itemName) {
        int count = 0;
        for (Item i : inventory) {
            if (i.getName().equals(itemName)) {
                count++;
            }
        }

        if (count > 0) {
            return count;
        }

        return -1;
    }

    // getItem Method: returns an item, if an item with itemName was found, else returns null
    public Item getItemFromEquipment(String itemName) {
        for (Item i : equipment) {
            if (i.getName().equals(itemName)) {
                return i;
            }
        }
        return null;
    }

    public boolean addEquipment(Item newItem) {
        if (newItem.getEquipmentType() == null)
            return false;
        for (Item item : equipment) {
            if (item.getEquipmentType() == newItem.getEquipmentType())
                return false;
        }
        equipment.add(newItem);
        return true;
    }

    public List<Item> removeEquipment(Item item) {
        equipment.remove(item);
        return equipment;
    }
}