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
        // System.out.println("---Quest Inventory---");
        // Tabelle erstellen
        TableList tabelle = new TableList(2, "Questname", "Required Items").sortBy(0).withUnicode(true);

        for (int i = 0; i < game.player.getQuestInventory().size(); i++) {
            // System.out.println("Quest Name: " + game.player.getQuestInventory().get(i).getName());
            // System.out.println("Required Items:");
            for (int a = 0; a < game.player.getQuestInventory().get(i).getFulfillmentItems().size(); a++) {
                if (game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName() != null) {
                    // Zeile hinzufügen
                    if (a == 0) {
                        tabelle.addRow(game.player.getQuestInventory().get(i).getName(),
                                game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName());
                    } else if (a != 0) {
                        tabelle.addRow("",
                                game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName());
                    }

                    // System.out.println(" '-: " +
                    // game.player.getQuestInventory().get(i).getFulfillmentItems().get(a).getName());
                }
            }
        }
        // Tabelle ausgeben
        tabelle.print(false);
        // System.out.println("---------------------");
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my]( quest inventory|quest inventory)", "qi" };
        return patterns;
    }
}
