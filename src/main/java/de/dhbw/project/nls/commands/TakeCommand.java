package de.dhbw.project.nls.commands;

import java.util.List;

import de.dhbw.project.Game;
import de.dhbw.project.Item;

public class TakeCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;

    public TakeCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (item == null || item.size() == 0) {
            System.out.println("You have to use the whole item name to pick it up.");
            return;
        }

        String itemName = String.join(" ", item);

        if (!(game.getCurrentRoom().getRoomItemNameList().contains(itemName)))
            System.out.println("No item found with name " + itemName + " in room " + game.getCurrentRoom().getName());

        else {
            Item takenItem = game.getItemFromCurrentRoom(itemName);
            game.player.addItem(takenItem);
            game.getCurrentRoom().removeItem(takenItem);
            System.out.println("You took " + takenItem.getName() + " and added it to the inventory.");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "take <item>+", "take" };
        return patterns;
    }
}
