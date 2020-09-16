package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;

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
        String[] patterns = { "[get|show|watch|see][ ][me|my][ ](health|strength|stats)" };
        return patterns;
    }
}
