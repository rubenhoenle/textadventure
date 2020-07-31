package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Player {
    @SerializedName("name")
    private String name = "Hugo";
    @SerializedName("points")
    private int points = 0;
    @SerializedName("strength")
    private int strength = 20;
    @SerializedName("inventory")
    private List<Item> inventory = new ArrayList<>();
    @SerializedName("roomName")
    private String roomName;

    // Getters and setters for a player

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}