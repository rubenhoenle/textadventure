package de.dhbw.project.nls.commands;

import de.dhbw.project.Constants;
import de.dhbw.project.Zork;

public class LoadCommand extends AutoCommand {

    @Override
    public void execute() {
        Zork.loadGame(Constants.SAVED_GAME);
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(load|restore)" };
        return patterns;
    }
}
