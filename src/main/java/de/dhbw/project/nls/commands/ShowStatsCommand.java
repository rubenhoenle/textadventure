package de.dhbw.project.nls.commands;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.TableList;
import de.dhbw.project.interactive.Createable;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.Weapon;

import java.util.ArrayList;
import java.util.List;

public class ShowStatsCommand extends AutoCommand {

    private Game game;

    public ShowStatsCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        game.player.printStats();
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my] health", "health",
                "[get|show|look|watch|see][ me| my] strength", "strength", "[get|show|look|watch|see][ me| my] stats",
                "stats" };
        return patterns;
    }
}
