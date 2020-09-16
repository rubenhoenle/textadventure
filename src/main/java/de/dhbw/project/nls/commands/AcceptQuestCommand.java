package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Friend;
import de.dhbw.project.interactive.InteractiveObject;

import java.util.List;

public class AcceptQuestCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "questGiver")
    private List<String> questGiver;
    @PatternName(key = "quest")
    private List<String> quest;

    public AcceptQuestCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (quest == null || quest.size() == 0) {
            System.out.println("You have to tell which quest you want to accept.");
            return;
        }
        if (questGiver == null || questGiver.size() == 0) {
            System.out.println("You have to tell from whom you want to accept the quest.");
            return;
        }

        String characterOrObjectName = String.join(" ", questGiver);
        String questName = String.join(" ", quest);
        Character questCharacter = game.getCharacterFromCurrentRoom(characterOrObjectName);
        InteractiveObject questInteractiveObject = game.getInteractiveObjectFromCurrentRoom(characterOrObjectName);
        Quest q = null;

        // this block gives the right quest into -> q
        if (questCharacter != null) {
            if (questCharacter instanceof Friend && !questCharacter.isKilled()) {
                for (int i = 0; i < ((Friend) questCharacter).getQuests().size(); i++) {
                    if (((Friend) questCharacter).getQuests().get(i).getName().equalsIgnoreCase(questName)) {
                        q = ((Friend) questCharacter).getQuests().get(i);
                    }
                }
            } else {
                if (questCharacter.isKilled()) {
                    System.out.println("You cant talk to " + questCharacter.getName() + " because you killed him!!");
                } else {
                    System.out.println("You can accept quests only from friends.");
                }
                return;
            }
        } else if (questInteractiveObject != null) {
            q = questInteractiveObject.getQuest();
        } else {
            System.out.println("There is no such character or object!");
            return;
        }

        // checks if the if statement is true and accepts the quest
        if (q == null) {
            System.out.println("There is no such quest!");
            return;
        }

        if (q.isTalkedOnce() && !q.isAccepted() && questName.equalsIgnoreCase(q.getName())) {
            q.setAccepted(true);
            game.player.getQuestInventory().add(q);
            System.out.println("Quest \"" + q.getName() + "\" was added to quest inventory");
            if (questInteractiveObject != null) {
                if (null != game.getCurrentRoom().getRoomInteractiveObjectsList()) {
                    game.getCurrentRoom().getRoomInteractiveObjectsList().remove(questInteractiveObject);
                }
            }
        } else {
            System.out.println("You can not do this!");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "accept <quest>+ from <questGiver>+", "accept <quest>+", "accept" };
        return patterns;
    }
}
