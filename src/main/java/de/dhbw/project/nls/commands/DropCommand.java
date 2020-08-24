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
            Item dropItem = game.player.getItem(itemName.trim());
            if (null == dropItem) { // condition item name is valid and in inventory
                System.out.println("The item " + itemName + " was not found in the inventory and cannot be dropped.");
            } else {
                game.player.removeItem(dropItem);
                game.getCurrentRoom().addItem(dropItem);
                dropItem.setWhere(game.getCurrentRoom().getDefaultItemLocation());
                System.out.println("The item " + dropItem.getName() + " was dropped in room '"
                        + game.getCurrentRoom().getName() + "'.");
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "drop <item>+", "drop" };
        return patterns;
    }

}