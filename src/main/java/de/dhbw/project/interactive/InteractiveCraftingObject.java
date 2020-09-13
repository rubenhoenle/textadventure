package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Player;
import de.dhbw.project.TableList;
import de.dhbw.project.Thing;
import de.dhbw.project.item.*;

import java.util.ArrayList;
import java.util.List;

public class InteractiveCraftingObject extends Thing {

    // FYI: The interactive crafting objects were implemented before the interactive objects were implemented.
    // They have nothing in common except of the name :)

    // Note: with a interactive crafting object the player can craft different items. Every interactive crafting
    // object has a list of 'Createables'. This are the items that can be crafted with this interactive crafting
    // objects. Every Createable has a own list with the needed materials to craft it.

    @SerializedName("state")
    private String state;
    @SerializedName("place")
    private String place;
    @SerializedName("usage")
    private String usage;
    @SerializedName("action")
    private String action;

    @SerializedName("createables")
    private List<Createable> interactiveObjectCreateablesList = new ArrayList<>();

    public InteractiveCraftingObject(String name, String description, String state, String place, String usage,
            String action) {
        super(name, description);
        this.state = state;
        this.place = place;
        this.usage = usage;
        this.action = action;
    }

    public String getWhere() {
        return place;
    }

    public String getUsage() {
        return usage;
    }

    public List<Createable> getInteractiveObjectCreateablesList() {
        return interactiveObjectCreateablesList;
    }

    // Method to craft a item with the help of this interactive crafting object.
    public String createItem(Player player, Createable createable) {
        System.out.println("You want to create a " + createable.getName() + ". For this you gonna need "
                + createable.getNeededMaterialAsString() + ".");

        // check if player has all needed materials in his inventory
        for (Material material : createable.getCreateableNeededMaterialList()) {
            Item item = player.getItem(material.getName());
            int count = player.getNumberOfItem(material.getName());
            if ((item == null) || (count < 0) || ((count - material.getNumber()) < 0)) {
                return "You don't have all needed materials for this in your inventory.";
            }
            // when player has all needed materials in his inventory: remove the needed materials from inventory
            for (int i = 0; i < material.getNumber(); i++) {
                player.removeItem(item);
                item = player.getItem(material.getName());
            }
        }

        // Create the item with the informations from Createable
        switch (createable.getType()) {
        case "cloth":
            player.addItem(new Clothing(createable.getName(), createable.getDescription(), createable.getItemstate(),
                    createable.getStrength(), createable.getEquipmentType(), 0));
            break;

        case "food":
            player.addItem(new Food(createable.getName(), createable.getDescription(), createable.getItemstate(),
                    createable.getStrength()));
            break;

        case "resource":
            player.addItem(new Resource(createable.getName(), createable.getDescription(), createable.getItemstate(),
                    createable.getStrength()));
            break;

        case "tool":
            player.addItem(new Tool(createable.getName(), createable.getDescription(), createable.getItemstate(),
                    createable.getStrength(), createable.getEquipmentType()));
            break;

        case "weapon":
            player.addItem(new Weapon(createable.getName(), createable.getDescription(), createable.getItemstate(),
                    createable.getStrength(), createable.getEquipmentType()));
            break;

        default:
            return "An error occured. Createable has unknown item type in JSON database.";
        }

        return "Congratulations. You created a " + createable.getName() + ".";
    }

    // Show a table with the Items the player can craft with this interactive crafting object.
    public void print() {
        if (interactiveObjectCreateablesList.isEmpty()) {
            System.out.print("This " + this.getName() + " can not be used.");
        }

        else {
            System.out.println("This " + this.getName() + " can be used for " + this.action + " following things:");

            TableList tl = new TableList(2, "name", "needed materials").sortBy(0).withUnicode(true);
            // from a list
            for (Createable createable : interactiveObjectCreateablesList) {
                tl.addRow(createable.getName(), createable.getNeededMaterialAsString());
            }
            tl.print();

            System.out.println("To create an item of this list type: \"craft " + this.getName() + " <name of item>\"");
        }
    }

    public Createable getCreateableByName(String name) {
        for (Createable createable : interactiveObjectCreateablesList) {
            if (createable.getName().equalsIgnoreCase(name)) {
                return createable;
            }
        }
        return null;
    }
}
