package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;

public class InventoryCommand extends AutoCommand {

    private Game game;

    public InventoryCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        System.out.println("---Inventory---");
        for (int i = 0; i < game.player.getInventory().size(); i++) {
            System.out.println(game.player.getInventory().get(i).getName() + " - "
                    + game.player.getInventory().get(i).getDescription());
            if (game.player.getInventory().get(i).getStrength() != 0)
                System.out.println(" '- Strength: " + game.player.getInventory().get(i).getStrength());
        }

        System.out.println("---------------");
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my]( inventory|inventory)" };
        return patterns;
    }

}