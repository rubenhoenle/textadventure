package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.Item;

import java.util.List;

public class SwitchCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;

    public SwitchCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (item == null || item.size() == 0) {
            System.out.println("You have to use the whole item name to switch it on or off.");
            return;
        }

        String itemName = String.join(" ", item).trim();
        Item item = game.player.getItem(itemName);
        if (item == null) {
            System.out.println("Item " + itemName + " not found in player inventory.");
        } else if (item.getItemstate() == ItemState.NOT_USABLE || item.getItemstate() == null) {
            System.out.println("Item " + itemName + " has no state.");
        } else {
            if (item.getItemstate() == ItemState.ACTIVE) {
                item.setItemstate(ItemState.INACTIVE);
                System.out.println("Item " + itemName + " is now " + ItemState.INACTIVE + ".");
            } else {
                item.setItemstate(ItemState.ACTIVE);
                System.out.println("Item " + itemName + " is now " + ItemState.ACTIVE + ".");
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(switch|turn) <item>+", "(switch|turn)" };
        return patterns;
    }
}
