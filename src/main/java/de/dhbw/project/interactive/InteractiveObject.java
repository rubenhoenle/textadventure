package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Quest;
import de.dhbw.project.Thing;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;

import java.util.Arrays;

public class InteractiveObject extends Thing {

    @SerializedName("place")
    private String place;
    @SerializedName("quest")
    private Quest quest;
    @SerializedName("requiredItem")
    private ItemList requiredItem;
    @SerializedName("removeRequiredItem")
    private boolean removeRequiredItem;
    @SerializedName("wayName")
    private String wayName;
    @SerializedName("hint")
    private String hint;
    @SerializedName("reward")
    private ItemList reward;

    public InteractiveObject(String name, String description, String place, Quest quest, ItemList requiredItem,
            boolean removeRequiredItem, String wayName, String hint, ItemList reward) {
        super(name, description);
        this.place = place;
        this.quest = quest;
        this.requiredItem = requiredItem;
        this.removeRequiredItem = removeRequiredItem;
        this.wayName = wayName;
        this.hint = hint;
        this.reward = reward;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Item getRequiredItem() {
        return requiredItem.getAllItemList().get(0);
    }

    public void setRequiredItem(Item requiredItem) {
        this.requiredItem.addItem(requiredItem);
    }

    public boolean isRemoveRequiredItem() {
        return removeRequiredItem;
    }

    public void setRemoveRequiredItem(boolean removeRequiredItem) {
        this.removeRequiredItem = removeRequiredItem;
    }

    public String getWayName() {
        return wayName;
    }

    public void setWayName(String wayName) {
        this.wayName = wayName;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setRequiredItem(ItemList requiredItem) {
        this.requiredItem = requiredItem;
    }

    public ItemList getReward() {
        if (reward == null) {
            return new ItemList();
        } else {
            return reward;
        }
    }

    public void setReward(ItemList reward) {
        this.reward = reward;
    }
}
