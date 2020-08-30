package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("accepted")
    private boolean accepted;
    @SerializedName("talkedOnce")
    private boolean talkedOnce;

    public Quest() {

    }

    public Quest(String name, String textStart, String textAccept, String textMid, String textEnd, boolean completed,
            List<QuestItem> reward, List<QuestItem> fulfillmentItems, boolean accepted, boolean talkedOnce) {
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

    public boolean isTalkedOnce() {
        return talkedOnce;
    }

    public void setTalkedOnce(boolean talkedOnce) {
        this.talkedOnce = talkedOnce;
    }

    public boolean checkCompleted(Player p) {

        for (QuestItem qi : fulfillmentItems) {
            Item item = p.getItemFromEquipment(qi.getName());
            if (item != null) {
                System.out.println("You need to strip the " + qi.getName() + " off to complete the quest!");
            }
        }
        for (QuestItem qi : fulfillmentItems) {
            Item item = p.getItem(qi.getName());
            if (item == null) {
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
}
