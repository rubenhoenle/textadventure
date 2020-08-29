package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.TableList;
import de.dhbw.project.item.Item;

import java.util.List;

public class ShowEquipmentCommand extends AutoCommand {

    private Game game;

    public ShowEquipmentCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        TableList table = new TableList(4, "Name", "Description", "Section", "Strength").sortBy(0).withUnicode(true);
        List<Item> equipment = game.player.getEquipment();
        for (Item item : equipment) {
            String strength = "";
            if (item.getStrength() != 0)
            {
                strength = String.valueOf(item.getStrength());
            }

            table.addRow(item.getName(), item.getDescription(), item.getEquipmentType().getDescription(), strength);
        }
        table.print();
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my] equipment", "equipment", "eq" };
        return patterns;
    }

}
