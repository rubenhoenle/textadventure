package de.dhbw.project.nls.commands;

import java.util.List;

import de.dhbw.project.Game;
import de.dhbw.project.Item;

public class DropCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;

    public DropCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (item == null || item.size() == 0) {
            System.out.println("You have to name an item you want to drop. Hint: use the command 'show inventory'.");
            return;
        }

        String[] items = String.join(" ", item).split(",");

        for (String itemName : items) {
            Item dropItemInventory = game.player.getItem(itemName.trim());
            Item dropItemEquipment = game.player.getItemFromEquipment(itemName.trim());
            if (null != dropItemInventory) { // condition item name is valid and in inventory
                game.player.removeItem(dropItemInventory);
                game.getCurrentRoom().addItem(dropItemInventory);
                dropItemInventory.setWhere(game.getCurrentRoom().getDefaultItemLocation());
                System.out.println("The item " + dropItemInventory.getName() + " was dropped in room '"
                        + game.getCurrentRoom().getName() + "'.");
            } else if (dropItemEquipment != null) {
                game.player.removeEquipment(dropItemEquipment);
                game.getCurrentRoom().addItem(dropItemEquipment);
                dropItemEquipment.setWhere(game.getCurrentRoom().getDefaultItemLocation());
                System.out.println("The item " + dropItemEquipment.getName() + " was dropped in room '"
                        + game.getCurrentRoom().getName() + "'.");
            } else {
                System.out.println(
                        "The item " + itemName + " was not found in inventory or equipment and cannot be dropped.");
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(drop|remove) <item>+", "drop" };
        return patterns;
    }
}