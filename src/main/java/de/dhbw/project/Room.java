package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.character.Friend;
import de.dhbw.project.character.Character;
import de.dhbw.project.item.*;

import java.util.ArrayList;
import java.util.List;

public class Room extends Thing {
    @SerializedName("ways")
    private List<Way> roomWayList = new ArrayList<>();
    @SerializedName("items")
    private ItemList roomItemList = new ItemList();
    @SerializedName("interactiveCraftingObjects")
    private List<InteractiveCraftingObject> roomInteractiveCraftingObjectsList = new ArrayList<>();
    @SerializedName("enable")
    private String enabled = "";
    @SerializedName("defaultItemLocation")
    private String defaultItemLocation; // The location the an item gets if it is dropped in this room
    @SerializedName("enemies")
    private List<Enemy> enemyList = new ArrayList<>();
    @SerializedName("friends")
    private List<Friend> friendList = new ArrayList<>();

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
        if (null != roomItemList) {
            return roomItemList.getAllItemList();
        } else {
            return null;
        }
    }

    public List<String> getRoomItemNameList() {
        if (null != roomItemList) {
            List<String> itemNames = new ArrayList<String>();
            for (Item i : roomItemList.getAllItemList()) {
                itemNames.add(i.getName());
            }
            return itemNames;
        } else {
            return null;
        }

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

    public ItemList addItem(Item item) {
        if (null != roomItemList) {
            roomItemList.addItem(item);
        } else {
            roomItemList = new ItemList();
            roomItemList.addItem(item);
        }
        return roomItemList;
    }

    public ItemList removeItem(Item item) {
        roomItemList.removeItem(item);
        return roomItemList;
    }

    public String getDefaultItemLocation() {
        if (null != defaultItemLocation) {
            return defaultItemLocation;
        } else {
            return Constants.WHERE;
        }
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    public List<Character> getCharacterList() {
        List<Character> characters = new ArrayList<>();
        if (friendList != null) {
            characters.addAll(friendList);
        }
        if (enemyList != null) {
            characters.addAll(enemyList);
        }
        return characters;
    }

    public List<String> getCharacterNameList() {
        List<String> characterNames = new ArrayList<>();
        for (Character c : getCharacterList()) {
            characterNames.add(c.getName());
        }
        return characterNames;
    }
}
