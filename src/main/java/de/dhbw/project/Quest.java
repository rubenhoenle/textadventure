package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Quest {
    @SerializedName("name")
    private String name;
    @SerializedName("questText")
    private String questText;
    @SerializedName("completed")
    private boolean completed;
    @SerializedName("reward")
    private List<Item> reward;

    public Quest() {

    }

    public Quest(String name, String questText, boolean completed, List<Item> reward) {
        this.name = name;
        this.questText = questText;
        this.completed = completed;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestText() {
        return questText;
    }

    public void setQuestText(String questText) {
        this.questText = questText;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public List<Item> getReward() {
        return reward;
    }

    public void setReward(List<Item> reward) {
        this.reward = reward;
    }
}
