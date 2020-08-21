package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
import de.dhbw.project.interactive.InteractiveCraftingObject;

public class LookCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "direction")
    private String direction;

    public LookCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (direction == null || direction == "") {
            System.out.println("You have to say where you want to look (around, north, south, east, west, up, down).");
            return;
        }

        if (direction.length() == 1) {
            direction = game.getFullDirection(direction.charAt(0));
        }

        // Boolean that indicates if the command "look around" has been written
        boolean isEachDirection = direction.equals(Constants.EACH_DIRECTION);

        // Entered phrase is not "look around" and is not "look + valid direction"
        if (!(Constants.DIRECTIONS).contains(direction) && !isEachDirection)
            System.out.println("No valid direction. Please enter look north / south / west / east / up or down.");

        // The current room has no ways (actually this shouldn't happen as you have to enter the room somehow)
        else if (!game.hasWays())
            System.out.println("You're stucked in a room. There's no way hiding there.");

        // Entered phrase is "look + valid direction" but there is no way in the chosen direction
        else if (game.getWayForDirection(direction) == null && !isEachDirection)
            System.out.println("There's nothing in the direction " + direction + ".");

        // Entered phrase is "look around": show everything in the current room (ways, items)
        else if (isEachDirection) {
            // Show available ways in the current room
            for (Way way : game.getCurrentRoom().getRoomWayList()) {
                System.out.println("There is a " + way.getName() + " going " + way.getDirection() + ". ");
            }
            // Show available items in the current room
            if (game.getCurrentRoom().getRoomItemList() != null) {
                for (Item item : game.getCurrentRoom().getRoomItemList()) {
                    System.out.println("A " + item.getName() + " " + item.getWhere() + ".");
                }
            }

            // Show available interactive objects in the current room
            if (game.getCurrentRoom().getRoomInteractiveObjectsList() != null) {
                for (InteractiveCraftingObject interactiveCraftingObject : game.getCurrentRoom()
                        .getRoomInteractiveObjectsList()) {
                    System.out.println("A " + interactiveCraftingObject.getName() + " "
                            + interactiveCraftingObject.getWhere() + ". " + interactiveCraftingObject.getUsage());
                }
            }
        }

        // Entered phrase is "look + valid direction": show way for the selected direction
        else {
            Way resultWay = game.getWayForDirection(direction);
            System.out.println("There is a " + resultWay.getName() + " going " + direction + ".");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "look <direction>", "look" };
        return patterns;
    }

}
