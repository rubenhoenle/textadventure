package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
import de.dhbw.project.interactive.Createable;
import de.dhbw.project.interactive.InteractiveCraftingObject;

import java.util.List;

public class CraftCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "object")
    private List<String> object;
    @PatternName(key = "createable")
    private List<String> createable;

    public CraftCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (object == null || object.size() == 0) {
            System.out.println("You have to say which crafting object you want to use.");
            return;
        }

        String objectName = String.join(" ", object);

        if (!(game.getCurrentRoom().getRoomInteractiveCraftingObjectsNameList().contains(objectName)))
            System.out.println("No crafting object found with name " + objectName + " in room "
                    + game.getCurrentRoom().getName() + ".");

        else {
            InteractiveCraftingObject interactiveCraftingObject = game.getCurrentRoom()
                    .getRoomInteractiveCraftingObjectByName(objectName);
            if (interactiveCraftingObject != null) {
                if (createable == null || createable.isEmpty()) {
                    interactiveCraftingObject.print();
                }

                else {
                    String createableName = String.join(" ", createable);
                    Createable createable = interactiveCraftingObject.getCreateableByName(createableName);

                    if (createable != null) {
                        System.out.println(interactiveCraftingObject.createItem(game.player, createable));
                    } else {
                        System.out.println("You can't create a " + createableName + " with this "
                                + interactiveCraftingObject.getName() + ".");
                    }
                }

            }

        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "craft <object>", "craft", "craft <object> <createable>+" };
        return patterns;
    }
}
