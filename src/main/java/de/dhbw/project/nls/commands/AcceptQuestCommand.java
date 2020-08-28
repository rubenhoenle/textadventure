package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Friend;

import java.util.List;

public class AcceptQuestCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "character")
    private List<String> character;
    @PatternName(key = "quest")
    private List<String> quest;

    public AcceptQuestCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        String characterName = String.join(" ", character);
        String questName = String.join(" ", quest);
        Character talkedTo = game.getCharacterFromCurrentRoom(characterName);
        Quest q = null;

        if (game.getCurrentRoom().getCharacterNameList().contains(characterName)) {
            if (talkedTo instanceof Friend) {

                for (int i = 0; i < ((Friend) talkedTo).getQuests().size(); i++) {
                    if (((Friend) talkedTo).getQuests().get(i).getName().equals(questName)) {
                        q = ((Friend) talkedTo).getQuests().get(i);
                    }
                }
                if (q != null && q.isTalkedOnce() && !q.isAccepted() && (questName.equals(q.getName()))) {
                    q.setAccepted(true);
                    game.player.getQuestInventory().add(q);
                    System.out.println("Quest \"" + q.getName() + "\" was added to quest inventory");
                } else {
                    System.out.println("You can not do this!");
                }
            } else {
                System.out.println("You can not do this!");
            }
        } else {
            System.out.println("You can not do this!");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "accept <quest>+ from <character>+" };
        return patterns;
    }
}
