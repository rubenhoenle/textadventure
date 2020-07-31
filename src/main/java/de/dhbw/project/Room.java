package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Room extends Thing {
    @SerializedName("ways")
    private List<Way> roomWayList = new ArrayList<>();
    @SerializedName("items")
    private List<Item> roomItemList = new ArrayList<>();
    @SerializedName("enable")
    private String enabled = "";

    // Constructor for a room object - calls the super constructor of the parent (thing) and adds the room-specific
    // variables
    public Room(String name, String description, String enabled) {
        super(name, description);
        this.enabled = enabled;
    }

    // Method simplifies the default output for a room object
    @Override
    public String toString() {
        return "You are in the Room " + getName() + ".";
    }

    // Getters and setters for a room

    public List<Way> getRoomWayList() {
        return roomWayList;
    }

    public List<Item> getRoomItemList() {
        return roomItemList;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
