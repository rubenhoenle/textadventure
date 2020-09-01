package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    @SerializedName("name")
    private String name = null;
    @SerializedName("points")
    private int points = 0;
    @SerializedName("strength")
    private int strength = 20;
    @SerializedName("inventory")
    private ItemList inventory = new ItemList();
    @SerializedName("questInventory")
    private List<Quest> questInventory = new ArrayList<>();
    @SerializedName("roomName")
    private String roomName;
    @SerializedName("equipment")
    private List<Item> equipment = new ArrayList<>();

    public void enterPlayerName()
    {
        Scanner tastatureingabe = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        do {
            try
            {
                this.name = tastatureingabe.nextLine();
                this.name = this.name.trim();
            }

            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        } while((this.name == null) || (this.name.equals("")));
    }

    // Getters and setters for a player
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return inventory.getAllItemList();
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

    public List<Quest> getQuestInventory() {
        return questInventory;
    }

    public void setQuestInventory(List<Quest> questInventory) {
        this.questInventory = questInventory;
    }

    public List<Item> addItem(Item item) {
        inventory.addItem(item);
        return inventory.getAllItemList();
    }

    public List<Item> removeItem(Item item) {
        inventory.removeItem(item);
        return inventory.getAllItemList();
    }

    // getItem Method: returns an item, if an item with itemName was found, else returns null
    public Item getItem(String itemName) {
        return inventory.getItem(itemName);
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
                    for (Item i : ((Enemy) c).getDropItemListElements()) {
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
        return inventory.getNumberOfItem(itemName);
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

    public ItemList getItemlist() {
        return inventory;
    }
}