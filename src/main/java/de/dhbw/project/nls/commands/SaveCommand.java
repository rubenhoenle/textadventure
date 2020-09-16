package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Zork;

public class SaveCommand extends AutoCommand {

    private Game game;

    public SaveCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        Zork.saveGame(game, true);
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "save" };
        return patterns;
    }

}
