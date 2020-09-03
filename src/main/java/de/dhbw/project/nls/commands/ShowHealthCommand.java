package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.TableList;
import de.dhbw.project.interactive.Createable;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ShowHealthCommand extends AutoCommand {

    private Game game;

    public ShowHealthCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        System.out.println("Your health is: " + game.player.getHealth());
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "[get|show|look|watch|see][ me| my] health", "health"};
        return patterns;
    }
}
