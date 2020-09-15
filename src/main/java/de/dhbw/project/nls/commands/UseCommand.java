package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Way;
import de.dhbw.project.WayState;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.Item;

import java.util.ArrayList;
import java.util.List;

public class UseCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "item")
    private List<String> item;
    @PatternName(key = "interactiveObject")
    private List<String> interactiveObject;

    public UseCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (item == null || item.size() == 0) {
            System.out.println("You have to say which item you want to use.");
            return;
        }
        if (interactiveObject == null || interactiveObject.size() == 0) {
            System.out.println("You have to say which interactive object you want to use.");
            return;
        }

        String itemName = String.join(" ", item);
        String interactiveObjectName = String.join(" ", interactiveObject).toLowerCase();

        if (!game.getCurrentRoom().getRoomInteractiveObjectsLowerNameList().contains(interactiveObjectName)) {
            System.out.println("There is no interactive object with the name \'" + interactiveObjectName + "\'.");
            return;
        }

        if (game.player.getItem(itemName) == null) {
            System.out.println("There is no item with the name \'" + itemName + "\' in your inventory");
            return;
        }

        InteractiveObject io = game.getCurrentRoom().getRoomInteractiveObjectByName(interactiveObjectName);
        Item i = game.player.getItem(itemName);

        // enable blocked way
        if (io.getRequiredItem().getName().equalsIgnoreCase(game.player.getItem(itemName).getName())) {
            if (io.getWayName() != null && io.getWayName().length() != 0) {
                List<Way> wayList = new ArrayList<>();
                // collect all ways in current room
                game.getCurrentRoom().getRoomWayList().forEach(element -> {
                    if (element.getName().equals(io.getWayName())) {
                        wayList.add(element);
                    }
                });
                if (wayList.size() > 0) {
                    wayList.forEach(element -> {
                        element.setState(WayState.ACTIVE);
                        //set all ways back to active
                        for (Way wb : game.getRoom(element.getTo()).getRoomWayList()) {
                            if (element.getTo().equals(wb.getFrom()) && element.getFrom().equals(wb.getTo())) {
                                wb.setState(WayState.ACTIVE);
                            }
                        }
                    });

                    if (io.isRemoveRequiredItem()) {
                        game.player.removeItem(i);
                    }
                    System.out.println("The way " + wayList.get(0).getName() + " in the direction \'"
                            + wayList.get(0).getDirection() + "\' is now walkable!");
                    game.getCurrentRoom().getRoomInteractiveObjectsList().remove(io);
                    game.incTurn();
                }
            }
            // get reward
            else if (io.getReward() != null && io.getReward().getAllItemList().size() > 0) {
                for (Item rewardItem : io.getReward().getAllItemList()) {
                    game.player.addItem(rewardItem);
                    System.out.println("\'" + rewardItem.getName() + "\' was added to your inventory!");
                }
                game.getCurrentRoom().getRoomInteractiveObjectsList().remove(io);
                game.incTurn();
            }
        } else {
            System.out.println("You can not use \'" + i.getName() + "\' on \'" + io.getName() + "\'.");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "use <item>+ (on|at)[ the] <interactiveObject>+", "use <item>+", "use" };
        return patterns;
    }
}
