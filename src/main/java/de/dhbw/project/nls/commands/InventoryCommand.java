package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.State;
import de.dhbw.project.TableList;

public class InventoryCommand extends AutoCommand {

    private Game game;

    public InventoryCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        TableList table = new TableList(4, "Name", "Description", "Strength", "State").sortBy(0).withUnicode(true);
        for (int i = 0; i < game.player.getInventory().size(); i++) {
            State state = null;
            String stateAsString = "";
            String strength = "";

            if (game.player.getInventory().get(i).getStrength() != 0) {
                strength = String.valueOf(game.player.getInventory().get(i).getStrength());
            }
            if ((game.player.getInventory().get(i).getState() != null)
                    && (game.player.getInventory().get(i).getState() != State.NOT_USABLE)) {
                state = game.player.getInventory().get(i).getState();
                switch (state) {
                case ACTIVE:
                    stateAsString = "active";
                    break;
                case INACTIVE:
                    stateAsString = "inactive";
                    break;
                default:
                    stateAsString = "";
                    break;
                }
            }

            table.addRow(game.player.getInventory().get(i).getName(),
                    game.player.getInventory().get(i).getDescription(), strength, stateAsString);
        }

        table.print();
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my]( inventory|inventory)", "i" };
        return patterns;
    }

}
