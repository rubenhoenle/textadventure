package de.dhbw.project.nls.commands;

import java.util.List;

import de.dhbw.project.Game;
import de.dhbw.project.Item;

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
                game.player.removeEquipment(stripOffItem);
                game.player.addItem(stripOffItem);
                System.out.println("The item " + stripOffItem.getName() + " was shifted to inventory.");
            } else {
                System.out.println(
                        "The item " + itemName.trim() + " was not found in equipment and cannot be stripped off.");
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(strip off|strip|disarm|uncloth|undress) <item>+", "strip off" };
        return patterns;
    }

}
