package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.character.Character;

import java.util.List;

public class AttackCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "character")
    private List<String> character;

    public AttackCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        if (character == null || character.size() == 0) {
            System.out.println("You have to say to whom you want to attack.");
            return;
        }

        String characterName = String.join(" ", character);

        if (!(game.getCurrentRoom().getCharacterNameList().contains(characterName))) {
            System.out.println("No character named " + characterName + " in area " + game.getCurrentRoom().getName());
        } else {
            Character c = game.getCharacterFromCurrentRoom(characterName);
            game.player.fight(c, game.getCurrentRoom());
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "attack <character>+", "attack" };
        return patterns;
    }

}
