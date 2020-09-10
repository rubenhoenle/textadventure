package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Food;
import de.dhbw.project.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class EatCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "food")
    private List<String> food;

    public EatCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        if (food == null || food.size() == 0) {
            System.out.println("You have to say to what you want to eat.");
            return;
        }

        String[] foodNames = String.join(" ", food).split(",");

        for (String entry : foodNames) {
            final String foodName = entry.trim().toLowerCase();
            if (game.getCurrentRoom().getRoomItemLowerNameList().contains(foodName)) {
                Item foodItem = game.getItemFromCurrentRoom(foodName);
                eat(foodItem);
                game.getCurrentRoom().removeItem(foodItem);
            } else if (game.player.getInventory().stream().filter(i -> i.getName().equalsIgnoreCase(foodName))
                    .collect(Collectors.toList()).size() > 0) {
                Item foodItem = game.player.getItem(foodName);
                eat(foodItem);
                game.player.removeItem(foodItem);
            } else
                System.out.println("No item found with name \'" + foodName + "\'");
        }
    }

    private void eat(Item foodItem) {
        if (!(foodItem instanceof Food)) {
            System.out.println("You really want to eat a \'" + foodItem.getName() + "\'?!");
            return;
        }

        game.player.addHealth(foodItem.getStrength());
        System.out.println("The \'" + foodItem.getName() + "\' tasted delicious!");
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "eat <food>+", "eat", "drink <food>+", "drink" };
        return patterns;
    }

}
