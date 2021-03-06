package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class EquipCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;

    public EquipCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        if (item == null || item.size() == 0) {
            System.out.println("You have to say what item to equip.");
            return;
        }

        String[] items = String.join(" ", item).split(",");

        for (String entry : items) {
            final String itemName = entry.trim().toLowerCase();
            if (game.getCurrentRoom().getRoomItemLowerNameList().contains(itemName)) {
                Item takenItem = game.getItemFromCurrentRoom(itemName);

                if (takenItem.getEquipmentType() == null) {
                    System.out.println("There's no section for this item.");
                    return;
                }

                boolean equip = game.player.addEquipment(takenItem);
                if (equip) {
                    game.getCurrentRoom().removeItem(takenItem);
                    System.out.println("You added \'" + takenItem.getName() + "\' to your equipment.");
                    game.incTurn();
                } else {
                    System.out.println(
                            "The section " + takenItem.getEquipmentType().getDescription() + " is already taken.");
                }
            } else if (game.player.getInventory().stream().filter(i -> i.getName().equalsIgnoreCase(itemName))
                    .collect(Collectors.toList()).size() > 0) {
                Item takenItem = game.player.getItem(itemName);

                if (takenItem.getEquipmentType() == null) {
                    System.out.println("There's no section for this item.");
                    return;
                }

                boolean equip = game.player.addEquipment(takenItem);
                if (equip) {
                    game.player.removeItem(takenItem);
                    System.out.println("You added \'" + takenItem.getName() + "\' to your equipment.");
                    game.incTurn();
                } else {
                    System.out.println(
                            "The section " + takenItem.getEquipmentType().getDescription() + " is already taken.");
                }
            } else
                System.out.println("No item found with name \'" + itemName + "\'");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(equip|supply|put on|wear) <item>+", "equip" };
        return patterns;
    }

}
