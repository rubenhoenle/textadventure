package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.interactive.InteractiveObject;

import java.util.List;

public class InvestigateCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "interactiveObject")
    private List<String> interactiveObject;

    public InvestigateCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (interactiveObject == null || interactiveObject.size() == 0) {
            System.out.println("You have to say which interactive object you want to use.");
            return;
        }

        String interactiveObjectName = String.join(" ", interactiveObject);

        if (!game.getCurrentRoom().getRoomInteractiveObjectsNameList().contains(interactiveObjectName)) {
            System.out.println("There is no interactive object with the name " + interactiveObjectName + ".");
            return;
        }

        InteractiveObject io = game.getCurrentRoom().getRoomInteractiveObjectByName(interactiveObjectName);

        // get quest
        if (io.getQuest() != null) {
            // TODO add quest to quest inventory
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "investigate", "investigate on <interactiveObject>+" };
        return patterns;
    }
}
