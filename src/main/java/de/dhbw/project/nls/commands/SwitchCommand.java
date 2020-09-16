package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;

import java.util.List;

public class SwitchCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "state")
    private String state;

    @PatternName(key = "item")
    private List<String> item;

    public SwitchCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (state == null) {
            System.out.println("Please tell if you want to switch the item on or off.");
        }

        if (item == null || item.size() == 0) {
            System.out.println("You have to use the whole item name to switch it on or off.");
            return;
        }

        if (!state.matches("on|off")) {
            if (item == null || !item.get(item.size() - 1).toLowerCase().matches("on|off")) {
                System.out.println("Please tell if you want to switch the item on or off.");
                return;
            } else {
                item.add(0, state);
                state = item.remove(item.size() - 1);
            }
        }

        String itemName = String.join(" ", item).trim();
        Item switchItem = game.player.getItem(itemName);
        if (switchItem == null) {
            System.out.println("Item \'" + itemName + "\' not found in player inventory.");
        } else if (switchItem.getItemstate() == ItemState.NOT_USABLE || switchItem.getItemstate() == null) {
            System.out.println("Item \'" + itemName + "\' has no state.");
        } else {
            if (state.equals("off")) {
                switchItem.setItemstate(ItemState.INACTIVE);
                System.out.println("Item \'" + itemName + "\' is now " + ItemState.INACTIVE + ".");
            } else {
                switchItem.setItemstate(ItemState.ACTIVE);
                System.out.println("Item \'" + itemName + "\' is now " + ItemState.ACTIVE + ".");
            }
            game.incTurn();
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(switch|turn) <state> <item>+", "(switch|turn) <state>", "(switch|turn)" };
        return patterns;
    }
}
