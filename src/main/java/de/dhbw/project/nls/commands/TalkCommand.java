package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Quest;
import de.dhbw.project.QuestItem;
import de.dhbw.project.TableList;
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

        String characterName = String.join(" ", character);

        if (!(game.getCurrentRoom().getCharacterNameList().contains(characterName))) {
            System.out.println("No character named " + characterName + " in area " + game.getCurrentRoom().getName());
        } else {
            Character talkedTo = game.getCharacterFromCurrentRoom(characterName);
            System.out.println(talkedTo.getStartStatement());
            // Quest
            if (talkedTo instanceof Friend) {

                for (int i = 0; i < ((Friend) talkedTo).getQuests().size(); i++) {
                    Quest q = ((Friend) talkedTo).getQuests().get(i);
                    // Quest q = ((Friend) talkedTo).getQuests();
                    if (!q.isAccepted() && !q.isCompleted()) {
                        q.setTalkedOnce(true);
                        if (i == 0) {
                            if (((Friend) talkedTo).getQuests().size() == 1) {
                                System.out.println("I got a task for you:");
                            } else {
                                System.out.println("I got tasks for you:");
                            }
                        }
                        System.out.println("Questname: " + q.getName());
                        System.out.println(q.getTextStart());
                        System.out.println("Type: \"accept <questname> from <character>\" to accept the quest.");

                        // q.setAccepted(true);

                    } else {
                        if (q.checkCompleted(game.player)) {
                            q.setCompleted(true);
                            game.player.getQuestInventory().remove(q);
                            for (QuestItem qi : q.getFulfillmentItems()) {
                                game.player.removeItem(game.player.getItem(qi.getName()));
                            }
                            System.out.println(q.getTextEnd());

                            // Tabelle erstellen
                            TableList tabelle = new TableList(1, "Quest Rewards").sortBy(0).withUnicode(true);
                            // System.out.println("Quest Rewards: ");
                            for (int a = 0; a < q.getReward().size(); a++) {
                                // Zeile hinzufügen
                                tabelle.addRow(q.getReward().get(a).getName());
                                // System.out.println(" '-: " + q.getReward().get(i).getName());
                                if (q.getReward().get(a).questItemToItem() != null) {
                                    game.player.addItem(q.getReward().get(a).questItemToItem());
                                }
                            }
                            // Tabelle ausgeben
                            tabelle.print();
                        } else {
                            System.out.println(q.getTextMid());
                        }
                    }

                }

            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "talk to <character>+", "talk <character>+", "talk" };
        return patterns;
    }

}
