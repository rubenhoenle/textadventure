package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Quest;
import de.dhbw.project.character.Character;

public class Friend extends Character {

    @SerializedName("quest")
    private Quest quest;

    public Friend() {

    }

    public Friend(String name, String where, int health, int strengh, String startStatement, String killStatement,
            boolean killed, Quest quest) {
        super(name, where, health, strengh, startStatement, killStatement, killed);
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
}
