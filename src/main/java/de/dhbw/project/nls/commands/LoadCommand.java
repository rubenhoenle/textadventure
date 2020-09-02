package de.dhbw.project.nls.commands;

import de.dhbw.project.Constants;
import de.dhbw.project.Zork;

public class LoadCommand extends AutoCommand {

    @Override
    public void execute() {
        Zork.loadGame(Constants.SAVED_GAME);
        Zork.playLoadedGame();
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(load|restore)" };
        return patterns;
    }
}
