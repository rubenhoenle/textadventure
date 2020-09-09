package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Item;

import java.util.List;

public class StripOffCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;

    public StripOffCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (item == null || item.size() == 0) {
            System.out
                    .println("You have to name an item you want to strip off. Hint: use the command 'show equipment'.");
            return;
        }

        String[] items = String.join(" ", item).split(",");

        for (String itemName : items) {
            Item stripOffItem = game.player.getItemFromEquipment(itemName.trim());
            if (stripOffItem != null) {
                // Inventory Space
                // test if current inventory space is enough without the "expand inventory space" from the item you want
                // to strip off
                if (game.player.getCurrentInventorySpace() > stripOffItem.getExpandInventorySpace()) {
                    // test if you got enough space to strip off an item
                    if (game.player.getCurrentInventorySpace() >= 1) {
                        game.player.removeEquipment(stripOffItem);
                        game.player.addItem(stripOffItem);
                        System.out.println("The item \'" + stripOffItem.getName() + "\' was shifted to inventory.");
                        game.incTurn();
                    } else {
                        System.out.println("You do not have enough space in your inventory to strip off the \'"
                                + stripOffItem.getName() + "\'.");
                    }
                } else {
                    System.out.println("You can not strip off the \'" + stripOffItem.getName()
                            + "\' because you will not have enough inventory space afterwards.");
                }
            } else {
                System.out.println(
                        "The item \'" + itemName.trim() + "\' was not found in equipment and cannot be stripped off.");
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(strip off|strip|disarm|uncloth|undress) <item>+", "strip off" };
        return patterns;
    }

}
