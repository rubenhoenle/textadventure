package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.chest.Chest;
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
            System.out.println("You have to say which interactive object or chest you want to investigate.");
            return;
        }

        String interactiveObjectName = String.join(" ", interactiveObject).toLowerCase();

        Chest chest = game.getCurrentRoom().getChest(interactiveObjectName);
        if (chest != null) {
            chest.showItems(game);
            game.incTurn();
            return;
        }

        if (!game.getCurrentRoom().getRoomInteractiveObjectsLowerNameList().contains(interactiveObjectName)) {
            System.out.println(
                    "There is no interactive object or chest with the name \'" + interactiveObjectName + "\'.");
            return;
        }

        InteractiveObject io = game.getCurrentRoom().getRoomInteractiveObjectByName(interactiveObjectName);

        if (io.getHint() != null && io.getHint().length() > 0) {
            System.out.println(io.getHint());
        }

        // get quest
        if (io.getQuest() != null) {
            Quest q = io.getQuest();
            System.out.println("Questname: " + q.getName());
            System.out.println(q.getTextStart());
            System.out.println("Type: \"accept " + q.getName() + " from " + io.getName() + "\" to accept the quest.");
            game.incTurn();
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "investigate <interactiveObject>+", "inv <interactiveObject>+", "investigate", "inv" };
        return patterns;
    }
}
