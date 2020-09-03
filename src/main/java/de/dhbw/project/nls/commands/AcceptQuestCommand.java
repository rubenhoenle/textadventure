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

        String characterOrObjectName = String.join(" ", questGiver);
        String questName = String.join(" ", quest);
        Character questCharacter = game.getCharacterFromCurrentRoom(characterOrObjectName);
        InteractiveObject questInteractiveObject = game.getInteractiveObjectFromCurrentRoom(characterOrObjectName);
        Quest q = null;

        if (questCharacter != null || questInteractiveObject != null) {
            if (questCharacter != null && questCharacter instanceof Friend && !questCharacter.isKilled()) {
                for (int i = 0; i < ((Friend) questCharacter).getQuests().size(); i++) {
                    if (((Friend) questCharacter).getQuests().get(i).getName().equals(questName)) {
                        q = ((Friend) questCharacter).getQuests().get(i);
                    }
                }
            } else if (questInteractiveObject != null) {
                q = questInteractiveObject.getQuest();
            }

            if (q != null && q.isTalkedOnce() && !q.isAccepted() && (questName.equals(q.getName()))) {
                q.setAccepted(true);
                game.player.getQuestInventory().add(q);
                System.out.println("Quest \"" + q.getName() + "\" was added to quest inventory");
                if (questInteractiveObject != null) {
                    if (null != game.getCurrentRoom().getRoomInteractiveObjectsList()) {
                        game.getCurrentRoom().getRoomInteractiveObjectsList().remove(questInteractiveObject);
                    }
                }
            } else if(questCharacter.isKilled() && questCharacter != null){
                System.out.println("You cant talk to " + questCharacter.getName() + " because you killed him!!");
            }
            else {
                System.out.println("You can not do this!");
            }
        } else {
            System.out.println("You can not do this!");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "accept <quest>+ from <questGiver>+" };
        return patterns;
    }
}
