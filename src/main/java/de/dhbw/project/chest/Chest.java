package de.dhbw.project.chest;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.*;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;

import java.util.ArrayList;
import java.util.List;

public class Chest extends Thing {

    @SerializedName("place")
    private String place = null;
    @SerializedName("items")
    private ItemList items = new ItemList();
    @SerializedName("size")
    private int size = 10;
    @SerializedName("mysteryChest")
    private boolean mysteryChest = false;

    // Note: When the chest isn't a mystery chest, the items are stored in the 'items' List of this object.
    // But if the chest is a mystery chest, then the contained items are stored in the 'mysteryChestItems' list of the
    // game object insted of in 'items', because all mystery chests have to use the same list of items.

    public Chest(String name, String description, String place, ItemList items, int size, boolean mysteryChest) {
        super(name, description);
        this.place = place;
        this.items = items;
        this.size = size;
        this.mysteryChest = mysteryChest;
    }

    public List<Item> getAllItems(Game game) {
        if (mysteryChest) {
            return game.getMysteryChestItems();
        }

        return this.items.getAllItemList();
    }

    public boolean isMysteryChest() {
        return mysteryChest;
    }

    // returns the number of items, which can fit in the chest
    public int getChestSize() {
        // when chest is a mystery chest, the size of the constant has to be returned so all mystery chests
        // in the whole game have the same size
        if (mysteryChest) {
            return Constants.MYSTERY_CHEST_SIZE;
        }

        // when chest isn't a mystery chest, the size which was set in database.json is returned
        return size;
    }

    public int getNumberOfItemsInChest(Game game) {
        if (mysteryChest) {
            return game.getMysteryChestItems().size();
        }

        return items.getAllItemList().size();
    }

    public String getPlace() {
        if (place != null) {
            return place;
        }
        return "is standing on the floor";
    }

    public void addItem(Game game, Item item) {
        if (mysteryChest) { // when chest is a mystery chest
            game.addMysteryChestItem(item);
        }

        else { // normal chest
            items.addItem(item);
        }
    }

    public void showItems(Game game) {
        TableList table = new TableList(3, "Name", "Description", "Strength").sortBy(0).withUnicode(true);
        List<Item> itemList;

        if (mysteryChest) {
            itemList = game.getMysteryChestItems();
        }

        else {
            itemList = items.getAllItemList();
        }

        for (Item item : itemList) {
            String strength = "";
            if (item.getStrength() != 0) {
                strength = String.valueOf(item.getStrength());
            }

            table.addRow(item.getName(), item.getDescription(), strength);
        }

        System.out.println(
                "--- Used chest space: " + this.getNumberOfItemsInChest(game) + "/" + this.getChestSize() + " ---");
        table.print();

        if (mysteryChest) {
            System.out.println(
                    "This is one of multiple mystery chests spread over the whole world. Items you put in here can also be accessed from every other mystery chest in this world.");
        }
    }

    public String takeItemOutOfChest(Game game, String itemName) {

        List<Item> itemList;

        if (mysteryChest) {
            itemList = game.getMysteryChestItems();
        }

        else {
            itemList = items.getAllItemList();
        }

        for (Item item : itemList) {
            if (itemName.toLowerCase().equals(item.getName().toLowerCase())) {
                if (mysteryChest) {
                    game.removeMysteryChestItem(item);
                } else {
                    items.removeItem(item);
                }
                game.player.addItem(item);
                return "You took \'" + item.getName() + "\' out of \'" + this.getName()
                        + "\' and added it to the inventory.";
            }
        }

        return "No item found with name \'" + itemName + "\' in \'" + this.getName() + "\'.";
    }
}
