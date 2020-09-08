package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.item.Item;

import java.util.ArrayList;
import java.util.List;

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

        String[] items = String.join(" ", item).split(",");

        for (String itemName : items) {
            itemName = itemName.trim().toLowerCase();
            if (!(game.getCurrentRoom().getRoomItemLowerNameList().contains(itemName)))
                System.out.println(
                        "No item found with name \'" + itemName + "\' in room " + game.getCurrentRoom().getName());
            else {
                Item takenItem = game.getItemFromCurrentRoom(itemName);
                game.player.addItem(takenItem);
                game.getCurrentRoom().removeItem(takenItem);
                System.out.println("You took \'" + takenItem.getName() + "\' and added it to the inventory.");

                // quest handling
                List<Quest> questInventory = new ArrayList<Quest>();
                questInventory.addAll(game.player.getQuestInventory());
                for (Quest q : questInventory) {
                    if (q.isAutoComplete() && q.getFulfillmentItems() != null && q.getFulfillmentItems().size() >= 1
                            && q.checkCompleted(game)) {
                        q.finishQuest(game, false);
                    }
                }
                game.incTurn();
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "take <item>+", "take" };
        return patterns;
    }
}
