package de.dhbw.project.nls.commands;

import de.dhbw.project.Constants;
import de.dhbw.project.Game;
import de.dhbw.project.Way;

public class MoveCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "direction")
    private String direction;

    public MoveCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (direction == null || direction == "") {
            System.out.println("You have to say where you want to move (north, south, east, west, up, down).");
            return;
        }

        if (direction.length() == 1) {
            direction = game.getFullDirection(direction.charAt(0));
        }

        if (!(Constants.DIRECTIONS).contains(direction))
            System.out.println("No valid direction. Please enter move north / south / west / east / up or down.");
        else if (!game.hasWays())
            System.out.println("You're stucked in a room. There's no way hiding there.");
        else if (game.getWayForDirection(direction) == null)
            System.out.println("You can't move in this direction.");
        else {
            Way resultWay = game.getWayForDirection(direction);
            System.out.println("You're taking the " + resultWay.getName() + " " + direction + ". ");
            game.player.setRoomName(resultWay.getTo());
            System.out.println(game.getCurrentRoom());
            System.out.println(game.getCurrentRoom().getDescription());
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(move|go) <direction>", "move" };
        return patterns;
    }

}
