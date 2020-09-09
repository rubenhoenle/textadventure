package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.TableList;

public class ShowQuestCommand extends AutoCommand {
    private Game game;

    public ShowQuestCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        // command for the quest inventory
        // Tabelle erstellen
        TableList tabelle = new TableList(2, "Questname", "Required Items / Kill Character").sortBy(0)
                .withUnicode(true);

        //goes trough the quest inventory
        for (int i = 0; i < game.player.getQuestInventory().size(); i++) {
            for (int a = 0; a < game.player.getQuestInventory().get(i).getFulfillmentItems().size(); a++) {
                if (game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName() != null) {
                    // Zeile hinzufügen
                    //adds a row with the quest name
                    if (a == 0) {
                        if (game.player.getQuestInventory().get(i).isMainQuest()) {
                            //adds a main quest row
                            tabelle.addRow("Main Quest - " + game.player.getQuestInventory().get(i).getName(),
                                    game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName());
                        } else {
                            //adds a normal quest row
                            tabelle.addRow(game.player.getQuestInventory().get(i).getName(),
                                    game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName());
                        }
                    } else if (a != 0) {
                        //adds a row without the quest name
                        tabelle.addRow("",
                                game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName());
                    }
                }
            }
            //adds row if you need to kill someone for a quest
            if (game.player.getQuestInventory().get(i).getFulfillmentKill() != null
                    && game.player.getQuestInventory().get(i).getFulfillmentKill().length() > 0) {
                tabelle.addRow(game.player.getQuestInventory().get(i).getName(),
                        game.player.getQuestInventory().get(i).getFulfillmentKill());
            }
        }
        // prints the quest inventory
        tabelle.print(false);
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|watch|see][ ][me|my][ ]quest inventory", "qi" };
        return patterns;
    }
}
