package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Item;

import java.util.List;

public class ShowEquipmentCommand extends AutoCommand {

    private Game game;

    public ShowEquipmentCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        System.out.println("---Equipment---");
        List<Item> equipment = game.player.getEquipment();
        for (Item item : equipment) {
            System.out.print(item.getName() + " - " + item.getDescription());
            System.out.print(" (Section: " + item.getEquipmentType().getDescription());
            if (item.getStrength() != 0)
                System.out.print(", Strength: " + item.getStrength());
            System.out.println(")");
        }
        System.out.println("---------------");
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my] equipment", "equipment", "eq" };
        return patterns;
    }

}
