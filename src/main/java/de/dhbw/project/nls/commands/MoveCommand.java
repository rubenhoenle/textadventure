package de.dhbw.project.nls.commands;

import de.dhbw.project.Constants;
import de.dhbw.project.Game;
import de.dhbw.project.Way;
import de.dhbw.project.WayState;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.LampState;

import java.util.List;
import java.util.stream.Collectors;

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

        direction = direction.toLowerCase();
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
            if (resultWay.getState() == WayState.ACTIVE) {
                if (!game.getRoom(resultWay.getTo()).isDark()) { // when room is not dark
                    //check if player has a active lamp
                    boolean showMessage = (game.player.getLampState() == LampState.ON);
                    moveInRoom(resultWay);
                    if (showMessage) { // when player has a active lamp
                        System.out.println(
                                "It's bright enough here to see something without a lamp. Don't forget to turn it off!");
                    }
                } else { // if Room is dark
                    switch (game.player.getLampState()) {
                    case ON: // if player has a active lamp, he can move into the room
                        moveInRoom(resultWay);
                        System.out.println(
                                "It's dark in there. But you have a lamp and it's switched on, so that's no problem for you.");
                        break;

                    case OFF: // if player has an inactive lamp, he has to switch it on to get into the room
                        System.out.println(
                                "It's dark in there. You have to switch your lamp on first to move in this direction.");
                        break;

                    case HAS_NO_LAMP: // if player has no lamp, he has to get one first to move into the room
                        System.out.println("It's dark in there. You can't move in this direction without a lamp.");
                        break;
                    }
                }
            }

            else if (resultWay.getState() == WayState.NEED_EQUIPMENT) {
                List<Item> equipedItem = game.player.getEquipment().stream()
                        .filter(i -> i.getName().equalsIgnoreCase(resultWay.getConditionalItem()))
                        .collect(Collectors.toList());
                if (equipedItem.size() > 0) {
                    moveInRoom(resultWay);
                } else {
                    System.out.print("You can't go that way.");
                    if (resultWay.getHint() != null)
                        System.out.println(" Hint: " + resultWay.getHint());
                }
            }

            else if (resultWay.getState() == WayState.INVISIBLE) {
                System.out.println("You can't move in this direction.");
            }

            else {
                System.out.println(
                        "The way is blocked!" + (resultWay.getHint() != null ? "Hint: " + resultWay.getHint() : ""));
            }
        }
    }

    private void moveInRoom(Way resultWay) {
        game.incTurn();
        System.out.println("You're taking the \'" + resultWay.getName() + "\' " + direction + ". ");
        game.player.setRoomName(resultWay.getTo());
        game.getCurrentRoom().setVisited(true);
        game.player.isAttacked(game);
        System.out.println(game.getCurrentRoom());
        System.out.println(game.getCurrentRoom().getDescription());
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(move|go) <direction>", "move" };
        return patterns;
    }
}
