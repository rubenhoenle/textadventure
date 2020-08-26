package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import de.dhbw.project.interactive.InteractiveCraftingObject;

import java.util.ArrayList;
import java.util.List;

public class Room extends Thing {
    @SerializedName("ways")
    private List<Way> roomWayList = new ArrayList<>();
    @SerializedName("items")
    private List<Item> roomItemList = new ArrayList<>();
    @SerializedName("interactiveCraftingObjects")
    private List<InteractiveCraftingObject> roomInteractiveCraftingObjectsList = new ArrayList<>();
    @SerializedName("enable")
    private String enabled = "";
    @SerializedName("defaultItemLocation")
    private String defaultItemLocation; // The location the an item gets if it is dropped in this room

    // Constructor for a room object - calls the super constructor of the parent (thing) and adds the room-specific
    // variables
    public Room(String name, String description, String enabled) {
        super(name, description);
        this.enabled = enabled;
    }

    public Room(String name, String description, String enabled, List<InteractiveCraftingObject> craftingObjects) {
        super(name, description);
        this.enabled = enabled;
        this.roomInteractiveCraftingObjectsList = craftingObjects;
    }

    // Method simplifies the default output for a room object
    @Override
    public String toString() {
        return "You are in the area: " + getName() + ".";
    }

    // Getters and setters for a room

    public List<Way> getRoomWayList() {
        return roomWayList;
    }

    public List<Item> getRoomItemList() {
        return roomItemList;
    }

    public List<String> getRoomItemNameList() {
        List<String> itemNames = new ArrayList<String>();
        for (Item i : roomItemList) {
            itemNames.add(i.getName());
        }
        return itemNames;
    }

    public List<InteractiveCraftingObject> getRoomInteractiveObjectsList() {
        return roomInteractiveCraftingObjectsList;
    }

    public List<String> getRoomInteractiveCraftingObjectsNameList() {
        List<String> itemNames = new ArrayList<String>();
        for (InteractiveCraftingObject interactiveCraftingObject : roomInteractiveCraftingObjectsList) {
            itemNames.add(interactiveCraftingObject.getName());
        }
        return itemNames;
    }

    public InteractiveCraftingObject getRoomInteractiveCraftingObjectByName(String name) {
        for (InteractiveCraftingObject interactiveCraftingObject : roomInteractiveCraftingObjectsList) {
            if (interactiveCraftingObject.getName().equalsIgnoreCase(name)) {
                return interactiveCraftingObject;
            }
        }
        return null;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public List<Item> addItem(Item item) {
        roomItemList.add(item);
        return roomItemList;
    }

    public List<Item> removeItem(Item item) {
        roomItemList.remove(item);
        return roomItemList;
    }

    public String getDefaultItemLocation() {
        if (null != defaultItemLocation) {
            return defaultItemLocation;
        } else {
            return Constants.WHERE;
        }
    }
}
