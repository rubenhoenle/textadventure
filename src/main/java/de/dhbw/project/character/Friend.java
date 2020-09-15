package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Quest;

import java.util.ArrayList;
import java.util.List;

public class Friend extends Character {

    @SerializedName("quests")
    private List<Quest> quests;

    public Friend() {

    }

    public Friend(String name, String where, int health, int strength, String startStatement, String killStatement,
            boolean killed, int points, List<Quest> quests) {
        super(name, where, health, strength, startStatement, killStatement, killed, points);
        this.quests = quests;
    }

    public List<Quest> getQuests() {
        if (null == quests) {
            quests = new ArrayList<>();
        }
        return quests;
    }

    public void setQuests(List<Quest> quest) {
        if (null == quests) {
            quests = new ArrayList<>();
        }
        this.quests = quest;
    }

}
