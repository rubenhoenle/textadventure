package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.LampState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Player {
    @SerializedName("name")
    private String name = null;
    @SerializedName("timePlayed")
    private long timePlayed;
    @SerializedName("points")
    private int points = 0;
    @SerializedName("deaths")
    private int deaths = 0;
    @SerializedName("strength")
    private int strength = 5;
    @SerializedName("health")
    private int health = 20;
    @SerializedName("inventory")
    private ItemList inventory = new ItemList();
    @SerializedName("questInventory")
    private List<Quest> questInventory = new ArrayList<>();
    @SerializedName("roomName")
    private String roomName;
    @SerializedName("equipment")
    private ItemList equipment = new ItemList();
    @SerializedName("inventorySpace")
    private int inventorySpace;

    public void enterPlayerName() {
        Scanner tastatureingabe = new Scanner(System.in);
        System.out.println("Please enter your name: ");
        do {
            try {
                this.name = tastatureingabe.nextLine();
                this.name = this.name.trim();
            }

            catch (Exception ex) {
                ex.printStackTrace();
            }
        } while ((this.name == null) || (this.name.equals("")));
    }

    // Getters and setters for a player
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void addHealth(int health) {
        this.health += health;
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
        return equipment.getAllItemList();
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

    public void setEquipment(ItemList liste) {
        this.equipment = liste;
    }

    public void setInventory(ItemList liste) {
        this.inventory = liste;
    }

    // getItem Method: returns an item, if an item with itemName was found, else returns null
    public Item getItem(String itemName) {
        return inventory.getItem(itemName);
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
            List<Item> weaponList = equipment.getAllItemList().stream()
                    .filter(i -> i.getEquipmentType() == EquipmentType.WEAPON).collect(Collectors.toList());
            if (weaponList.size() == 0) {
                System.out.println("It'll be a hard fight without any weapon!");
            }

            System.out.println("You fight with " + c.getName() + "!");
            if (c.getStartStatement() != null && c.getStartStatement() != "") {
                System.out.println(c.getName() + ": " + c.getStartStatement());
            }

            int enemyHealth = calculateFightAgainst(c);

            if (health <= 0) {
                System.out.println("You lose the fight against " + c.getName() + "! You faint!");
                printGameOverScreen();
                System.out.println("Last save game will be loaded! \n");
                Zork.reloadAfterPlayerDeath();
            } else if (enemyHealth <= 0) {
                if (c.getKillStatement() != null && c.getKillStatement() != "") {
                    System.out.println(c.getName() + ": " + c.getKillStatement());
                }
                System.out.println("You win the fight against " + c.getName() + "!");
                if (c instanceof Enemy) {
                    for (Item i : ((Enemy) c).getDropItemListElements()) {
                        System.out.println(c.getName() + " drops '" + i.getName() + "'");
                        r.addItem(i);
                    }
                }
                c.setKilled(true);
            } else {
                System.out.println("Your opponent is bruised, but you also got a few scratches.");
            }
        }
    }

    public void printStats() {
        int health = this.getHealth();

        // Strength = player strength + weapon
        Item w = this.getEquipment().stream().filter(item -> item.getEquipmentType() == EquipmentType.WEAPON)
                .findFirst().orElse(null);
        int strength = this.getStrength() + (w != null ? w.getStrength() : 0);

        // Protection = all equiped expect weapon
        int protection = 0;
        for (Item i : this.getEquipment()) {
            protection = protection + i.getStrength();
        }
        protection = protection - (w != null ? w.getStrength() : 0);

        // Time played
        Zork.stopTimer();
        String timePlayed = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(getTimePlayed()),
                TimeUnit.MILLISECONDS.toMinutes(getTimePlayed())
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(getTimePlayed())),
                TimeUnit.MILLISECONDS.toSeconds(getTimePlayed())
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getTimePlayed())));
        Zork.startTimer();

        System.out.println("Health: " + health + " | Strength: " + strength + " | Protection: " + protection
                + " | Points: " + points + " | Deaths: " + deaths + "\nTime played: " + timePlayed);
    }

    // calculates how many "health points" each opponent loses
    private int calculateFightAgainst(Character enemy) {
        int weaponStrength = 0;

        int damage = enemy.getStrength();
        for (Item item : equipment.getAllItemList()) {
            if (item.getEquipmentType() == EquipmentType.WEAPON)
                weaponStrength = item.getStrength();
            else
                damage -= item.getStrength();
        }
        addHealth((-1) * (damage < 0 ? 0 : damage));

        int enemyHealth = enemy.getHealth() - (strength + weaponStrength);
        enemy.setHealth(enemyHealth);
        return enemyHealth;
    }

    // counts how often the player has a specific item in his inventory (returns -1 when item is not found)
    public int getNumberOfItem(String itemName) {
        return inventory.getNumberOfItem(itemName);
    }

    // getItem Method: returns an item, if an item with itemName was found, else returns null
    public Item getItemFromEquipment(String itemName) {
        for (Item i : equipment.getAllItemList()) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }

    public int getInventorySpace() {
        return inventorySpace;
    }

    public void setInventorySpace(int inventorySpace) {
        this.inventorySpace = inventorySpace;
    }

    public int getCurrentInventorySpace() {
        // gives the currently free space in the inventory
        if (getInventory() == null) {
            return 0;
        } else {
            return (getInventorySpace() - getInventory().size());
        }
    }

    public void addInventorySpace(Item item) {
        // adds space to the inventory (as much space as the item has)
        if (item.getExpandInventorySpace() > 0) {
            setInventorySpace(getInventorySpace() + item.getExpandInventorySpace());
        }
    }

    public void removeInventorySpace(Item item) {
        // removes space to the inventory (as much space as the item has)
        if (item.getExpandInventorySpace() > 0) {
            setInventorySpace(getInventorySpace() - item.getExpandInventorySpace());
        }
    }

    public boolean addEquipment(Item newItem) {
        if (newItem.getEquipmentType() == null)
            return false;
        for (Item item : equipment.getAllItemList()) {
            if (item.getEquipmentType() == newItem.getEquipmentType())
                return false;
        }
        // InventorySpace
        addInventorySpace(newItem);
        // InventorySpace end
        equipment.addItem(newItem);
        return true;
    }

    public List<Item> removeEquipment(Item item) {
        // InventorySpace
        removeInventorySpace(item);
        // InventorySpace end
        equipment.removeItem(item);
        return equipment.getAllItemList();
    }

    public ItemList getItemlist() {
        return inventory;
    }

    public ItemList getEquipmentItemList() {
        return equipment;
    }

    public LampState getLampState() {
        if (Constants.LAMP_NAMES.isEmpty()) {
            return LampState.HAS_NO_LAMP;
        }

        boolean hasActiveLamp = false;
        boolean hasInactiveLamp = false;

        for (String lampname : Constants.LAMP_NAMES) {
            Item lamp = this.getItem(lampname);

            if (lamp != null) {
                if (lamp.getItemstate() == ItemState.ACTIVE) { // lamp is switched on
                    hasActiveLamp = true;
                } else {
                    hasInactiveLamp = true;
                }
            }
        }

        if (hasActiveLamp) {
            return LampState.ON;
        }

        if (hasInactiveLamp) {
            return LampState.OFF;
        }

        return LampState.HAS_NO_LAMP;
    }

    public void printGameOverScreen() {
        System.out.println("                     .ed\"\"\"\" \"\"\"$$$$be.");
        System.out.println("                   -\"           ^\"\"**$$$e.");
        System.out.println("                 .\"                   '$$$c");
        System.out.println("                /                      \"4$$b");
        System.out.println("               d  3                      $$$$");
        System.out.println("               $  *                   .$$$$$$");
        System.out.println("              .$  ^c           $$$$$e$$$$$$$$.");
        System.out.println("              d$L  4.         4$$$$$$$$$$$$$$b");
        System.out.println("              $$$$b ^ceeeee.  4$$ECL.F*$$$$$$$");
        System.out.println("  e$\"\"=.      $$$$P d$$$$F $ $$$$$$$$$- $$$$$$");
        System.out.println(" z$$b. ^c     3$$$F \"$$$$b   $\"$$$$$$$  $$$$*\"      .=\"\"$c");
        System.out.println("4$$$$L        $$P\"  \"$$b   .$ $$$$$...e$$        .=  e$$$.");
        System.out.println("^*$$$$$c  %..   *c    ..    $$ 3$$$$$$$$$$eF     zP  d$$$$$");
        System.out.println("  \"**$$$ec   \"   %ce\"\"    $$$  $$$$$$$$$$*    .r\" =$$$$P\"\"");
        System.out.println("        \"*$b.  \"c  *$e.    *** d$$$$$\"L$$    .d\"  e$$***\"");
        System.out.println("          ^*$$c ^$c $$$      4J$$$$$% $$$ .e*\".eeP\"");
        System.out.println("             \"$$$$$$\"'$=e....$*$$**$cz$$\" \"..d$*\"");
        System.out.println("               \"*$$$  *=%4.$ L L$ P3$$$F $$$P\"");
        System.out.println("                  \"$   \"%*ebJLzb$e$$$$$b $P\"");
        System.out.println("                    %..      4$$$$$$$$$$ \"");
        System.out.println("                     $$$e   z$$$$$$$$$$%");
        System.out.println("                      \"*$c  \"$$$$$$$P\"");
        System.out.println("                       .\"\"\"*$$$$$$$$bc");
        System.out.println("                    .-\"    .$***$$$\"\"\"*e.");
        System.out.println("                 .-\"    .e$\"     \"*$c  ^*b.");
        System.out.println("          .=*\"\"\"\"    .e$*\"          \"*bc  \"*$e..");
        System.out.println("        .$\"        .z*\"               ^*$e.   \"*****e.");
        System.out.println("        $$ee$c   .d\"                     \"*$.        3.");
        System.out.println("        ^*$E\")$..$\"                         *   .ee==d%");
        System.out.println("           $.d$$$*                           *  J$$$e*");
        System.out.println("            \"\"\"\"\"                              \"$$$\"");
    }
}