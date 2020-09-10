package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.TableList;

public class InventoryCommand extends AutoCommand {

    private Game game;

    public InventoryCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        TableList table = new TableList(4, "Name", "Description", "Strength", "State").sortBy(0).withUnicode(true);
        // goes trough the inventory
        for (int i = 0; i < game.player.getInventory().size(); i++) {
            ItemState state = null;
            String stateAsString = "";
            String strength = "";

            if (game.player.getInventory().get(i).getStrength() != 0) {
                strength = String.valueOf(game.player.getInventory().get(i).getStrength());
            }
            if ((game.player.getInventory().get(i).getItemstate() != null)
                    && (game.player.getInventory().get(i).getItemstate() != ItemState.NOT_USABLE)) {
                state = game.player.getInventory().get(i).getItemstate();
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
            // writes the inventory in the table row
            table.addRow(game.player.getInventory().get(i).getName(),
                    game.player.getInventory().get(i).getDescription(), strength, stateAsString);
        }
        // prints the space you have in your inventory
        System.out.println("--- Used inventory space: "
                + (game.player.getInventorySpace() - game.player.getCurrentInventorySpace()) + "/"
                + game.player.getInventorySpace() + " ---");
        // prints the inventory
        table.print();
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|watch|see][ ][me|my][ ]inventory", "i" };
        return patterns;
    }

}
