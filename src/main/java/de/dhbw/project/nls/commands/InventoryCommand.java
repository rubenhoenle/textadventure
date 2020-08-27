package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.State;

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
            if (game.player.getInventory().get(i).getState() != null
                    && game.player.getInventory().get(i).getState() != State.NOT_USABLE)
                System.out.println(" '- State: " + game.player.getInventory().get(i).getState());
        }

        System.out.println("---------------");
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my]( inventory|inventory)", "i" };
        return patterns;
    }

}
