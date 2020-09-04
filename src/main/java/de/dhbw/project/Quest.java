package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.item.Item;

import java.util.List;

public class Quest {
    @SerializedName("name")
    private String name;
    @SerializedName("textStart")
    private String textStart;
    @SerializedName("textAccept")
    private String textAccept;
    @SerializedName("textMid")
    private String textMid;
    @SerializedName("textEnd")
    private String textEnd;
    @SerializedName("completed")
    private boolean completed;
    @SerializedName("reward")
    private List<QuestItem> reward;
    @SerializedName("fulfillmentItems")
    private List<QuestItem> fulfillmentItems;
    @SerializedName("fulfillmentKill")
    private String fulfillmentKill;
    @SerializedName("accepted")
    private boolean accepted;
    @SerializedName("talkedOnce")
    private boolean talkedOnce;
    @SerializedName("autoComplete")
    private boolean autoComplete = false;
    @SerializedName("mainQuest")
    private boolean mainQuest = false;

    public Quest() {

    }

    public Quest(String name, String textStart, String textAccept, String textMid, String textEnd, boolean completed,
            List<QuestItem> reward, List<QuestItem> fulfillmentItems, boolean accepted, boolean talkedOnce,
            boolean mainQuest, String fulfillmentKill, boolean autoComplete) {
        this.name = name;
        this.textStart = textStart;
        this.textAccept = textAccept;
        this.textMid = textMid;
        this.textEnd = textEnd;
        this.completed = completed;
        this.reward = reward;
        this.fulfillmentItems = fulfillmentItems;
        this.accepted = accepted;
        this.talkedOnce = talkedOnce;
        this.mainQuest = mainQuest;
        this.fulfillmentKill = fulfillmentKill;
        this.autoComplete = autoComplete;
    }

    public String getName() {
        return name;
    }

    public String getTextStart() {
        return textStart;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTextStart(String textStart) {
        this.textStart = textStart;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<QuestItem> getReward() {
        return reward;
    }

    public void setReward(List<QuestItem> reward) {
        this.reward = reward;
    }

    public String getTextAccept() {
        return textAccept;
    }

    public String getTextMid() {
        return textMid;
    }

    public String getTextEnd() {
        return textEnd;
    }

    public void setTextAccept(String textAccept) {
        this.textAccept = textAccept;
    }

    public void setTextMid(String textMid) {
        this.textMid = textMid;
    }

    public void setTextEnd(String textEnd) {
        this.textEnd = textEnd;
    }

    public List<QuestItem> getFulfillmentItems() {
        return fulfillmentItems;
    }

    public void setFulfillmentItems(List<QuestItem> fulfillmentItems) {
        this.fulfillmentItems = fulfillmentItems;
    }

    public String getFulfillmentKill() {
        return fulfillmentKill;
    }

    public void setFulfillmentKill(String fulfillmentKill) {
        this.fulfillmentKill = fulfillmentKill;
    }

    public boolean isAutoComplete() {
        return autoComplete;
    }

    public void setAutoComplete(boolean autoComplete) {
        this.autoComplete = autoComplete;
    }

    public boolean isTalkedOnce() {
        return talkedOnce;
    }

    public void setTalkedOnce(boolean talkedOnce) {
        this.talkedOnce = talkedOnce;
    }

    public boolean checkCompleted(Game g) {

        for (QuestItem qi : fulfillmentItems) {
            Item item = g.player.getItemFromEquipment(qi.getName());
            if (item != null) {
                System.out.println("You need to strip the " + qi.getName() + " off to complete the quest!");
                return false;
            }
        }
        for (QuestItem qi : fulfillmentItems) {
            Item item = g.player.getItem(qi.getName());
            if (item == null) {
                return false;
            }
        }

        if (fulfillmentKill != null && fulfillmentKill.length() > 0) {
            Character c = g.getCharacter(fulfillmentKill);
            if (c != null && !c.isKilled()) {
                return false;
            }
        }

        return true;

        /*
         * for (Item i : fulfillmentItems) { for(Item a : p.getInventory()){ if (!a.getName().equals(i.getName())) {
         * return false; } } }
         */
        // return true;
    }

    public boolean isMainQuest() {
        return mainQuest;
    }

    public void setMainQuest(boolean mainQuest) {
        this.mainQuest = mainQuest;
    }

    public void finishQuest(Game game, boolean removeItems) {
        setCompleted(true);
        game.player.getQuestInventory().remove(this);
        if (removeItems) {
            for (QuestItem qi : getFulfillmentItems()) {
                game.player.removeItem(game.player.getItem(qi.getName()));
            }
        }
        System.out.println(getTextEnd());

        if (this.getReward() != null && this.getReward().size() >= 1) {
            // Tabelle erstellen
            TableList tabelle = new TableList(1, "Quest Rewards").sortBy(0).withUnicode(true);
            // System.out.println("Quest Rewards: ");
            for (int a = 0; a < getReward().size(); a++) {
                // Zeile hinzufügen
                tabelle.addRow(getReward().get(a).getName());
                // System.out.println(" '-: " + q.getReward().get(i).getName());
                if (getReward().get(a).questItemToItem() != null) {
                    game.player.addItem(getReward().get(a).questItemToItem());
                }
            }
            // Tabelle ausgeben
            tabelle.print();
        }

        // Checks if all main quests are completed -> game end
        if (this.isCompleted() && this.isMainQuest()) {
            if (game.getMainQuestNumber() == game.getCompletedMainQuestNumber()) {
                game.setGameEnd(true);
                return;
            }
        }
    }

}
