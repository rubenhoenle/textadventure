package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.chest.Chest;
import de.dhbw.project.item.Item;

import java.util.ArrayList;
import java.util.List;

public class TakeCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;
    @PatternName(key = "target")
    private List<String> target;

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

        if (target == null || target.size() == 0) {
            for (String itemName : items) {
                itemName = itemName.trim().toLowerCase();
                if (!(game.getCurrentRoom().getRoomItemLowerNameList().contains(itemName)))
                    System.out.println(
                            "No item found with name \'" + itemName + "\' in room " + game.getCurrentRoom().getName());
                else {
                    Item takenItem = game.getItemFromCurrentRoom(itemName);

                    if (game.player.getCurrentInventorySpace() >= 1) {
                        game.player.addItem(takenItem);
                        game.getCurrentRoom().removeItem(takenItem);
                        System.out.println("You took \'" + takenItem.getName() + "\' and added it to the inventory.");
                    } else {
                        System.out.println("You can not pick up the \'" + takenItem.getName()
                                + "\' to your inventory because it is already full.");
                    }

                    // quest handling
                    List<Quest> questInventory = new ArrayList<Quest>();
                    questInventory.addAll(game.player.getQuestInventory());
                    for (Quest q : questInventory) {
                        if (q.isAutoComplete() && q.getFulfillmentItems() != null && q.getFulfillmentItems().size() >= 1
                                && q.checkCompleted(game)) {
                            q.finishQuest(game, q.isRemoveFulfillmentItems());
                        }
                    }
                    game.incTurn();
                }
            }
        }

        else {
            String targetName = String.join(" ", target).trim();
            for (String itemName : items) {
                Chest chest = findChest(targetName); // search for a chest with name of targetName
                if (chest == null) { // if no chest with this name was found
                    System.out.println("There is no \'" + targetName + "\' in this place.");
                }

                else { // if a chest with this name was found in this room
                    if (game.player.getCurrentInventorySpace() >= 1) {
                        System.out.println(chest.takeItemOutOfChest(game, itemName.trim()));
                    } else {
                        System.out.println("You can not take the \'" + itemName.trim()
                                + "\' into your inventory because it is already full.");
                    }

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
        String[] patterns = { "take <item>+ from <target>+", "take <item>+ out of <target>+", "take <item>+", "take" };
        return patterns;
    }
}
