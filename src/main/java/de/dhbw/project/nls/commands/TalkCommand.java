package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Friend;
import java.util.List;

public class TalkCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "character")
    private List<String> character;

    public TalkCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (character == null || character.size() == 0) {
            System.out.println("You have to say to whom you want to talk to.");
            return;
        }

        String characterName = String.join(" ", character).toLowerCase();

        if (!(game.getCurrentRoom().getCharacterLowerNameList().contains(characterName))) {
            System.out
                    .println("No character named \'" + characterName + "\' in area " + game.getCurrentRoom().getName());
        } else {
            Character talkedTo = game.getCharacterFromCurrentRoom(characterName);
            //checks if talked person has not been killed
            if (!talkedTo.isKilled()) {
                System.out.println(talkedTo.getStartStatement());
            } else {
                System.out.println("You cant talk to " + talkedTo.getName() + " because you killed him!!");
            }

            // quest section
            if (talkedTo instanceof Friend && !talkedTo.isKilled()) {
                //goes through all quests of the talked friend
                for (int i = 0; i < ((Friend) talkedTo).getQuests().size(); i++) {
                    Quest q = ((Friend) talkedTo).getQuests().get(i);
                    //checks if quest is not completed and not accepted -> talkedOnce=true
                    if (!q.isAccepted() && !q.isCompleted()) {
                        q.setTalkedOnce(true);
                        if (i == 0) {
                            //checks if more then one quest
                            if (((Friend) talkedTo).getQuests().size() == 1) {
                                System.out.println("I got a task for you:");
                            } else {
                                System.out.println("I got tasks for you:");
                            }
                        }
                        //prints the quest
                        System.out.println("Questname: \'" + q.getName() + "\'");
                        System.out.println(q.getTextStart());
                        System.out.println(
                                "Type: \"accept " + q.getName() + " from " + characterName + "\" to accept the quest.");

                        // q.setAccepted(true);

                    } else {
                        //checks if the quest is completed
                        if (q.checkCompleted(game)) {
                            q.finishQuest(game, q.isRemoveFulfillmentItems());
                        }
                        //checks if the quest has already been completed
                        else if (q.isCompleted() && !game.isGameEnd()) {
                            System.out.println(q.getTextEnd());
                        }
                        //checks if the quest has not been finished yet but is already accepted
                        else {
                            System.out.println(q.getTextMid());
                        }
                    }

                }

            }
            game.incTurn();
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "talk to <character>+", "talk <character>+", "talk" };
        return patterns;
    }

}
