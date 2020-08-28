package de.dhbw.project.character;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Quest;

import java.util.List;

public class Friend extends Character {

    @SerializedName("quests")
    private List<Quest> quests;

    public Friend() {

    }

    public Friend(String name, String where, int health, int strengh, String startStatement, String killStatement,
            boolean killed, List<Quest> quests) {
        super(name, where, health, strengh, startStatement, killStatement, killed);
        this.quests = quests;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quest) {
        this.quests = quest;
    }
}
