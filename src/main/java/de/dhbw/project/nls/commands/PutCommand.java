package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.chest.Chest;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;

import java.util.List;

public class PutCommand extends AutoCommand {

    @PatternName(key = "item")
    private List<String> item;
    @PatternName(key = "target")
    private List<String> target;

    private Game game;

    public PutCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        if (target == null) {
            System.out.println("Please tell where you want to put this item.");
            return;
        }

        if (item == null || item.size() == 0) {
            System.out.println("You have to use the whole item name to put it somewhere.");
            return;
        }

        String[] items = String.join(" ", item).split(",");
        String targetName = String.join(" ", target).trim();

        Chest chest = findChest(targetName);

        if (chest == null) {
            System.out.println("There is no \'" + targetName + "\' in this place.");
            return;
        }

        for (String itemName : items) {
            Item putItem = game.player.getItem(itemName.trim());
            if (putItem == null) {
                System.out.println("Item \'" + itemName.trim() + "\' not found in player inventory.");

            }

            else {
                if ((chest.getChestSize() - chest.getNumberOfItemsInChest(game)) >= 1) {
                    chest.addItem(game, putItem);
                    game.player.removeItem(putItem);
                    System.out.println("You took \'" + itemName.trim() + "\' out of your inventory and added it to \'"
                            + targetName + "\'.");
                } else {
                    System.out.println("There is no place left in \'" + targetName + "\' to put this in.");
                }

            }
        }
    }

    private Chest findChest(String name) {
        for (Chest chest : game.getCurrentRoom().getChests()) {
            if (chest.getName().trim().toLowerCase().equals(name.trim().toLowerCase())) {
                return chest;
            }
        }

        return null;
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(put|lay)[ down] <item>+ on <target>+", "(put|lay)[ down] <item>+ in <target>+",
                "(put|lay)[ down] <item>+ into <target>+" };
        /* , "(put down|lay down|drop) <item>+" */
        return patterns;
    }
}