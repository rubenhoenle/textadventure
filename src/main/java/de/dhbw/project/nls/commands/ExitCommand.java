package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Zork;

public class ExitCommand extends AutoCommand {

    private Game game;

    public ExitCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        Zork.saveGame(game, false);
        System.out.println("Goodbye!");
        System.exit(0);
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(quit|exit)" };
        return patterns;
    }

}
