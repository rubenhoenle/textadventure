package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;

public class ShowPlaceCommand extends AutoCommand {

    private Game game;

    public ShowPlaceCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        System.out.println(game.getCurrentRoom());
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "?" };
        return patterns;
    }

}
