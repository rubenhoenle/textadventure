package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
import de.dhbw.project.character.RoamingEnemy;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;

import java.util.ArrayList;
import java.util.List;

public class Room extends Thing {
    @SerializedName("ways")
    private List<Way> roomWayList = new ArrayList<>();
    @SerializedName("isDark")
    private boolean isDark = false;
    @SerializedName("items")
    private ItemList roomItemList = new ItemList();
    @SerializedName("interactiveCraftingObjects")
    private List<InteractiveCraftingObject> roomInteractiveCraftingObjectsList = new ArrayList<>();
    @SerializedName("enable")
    private String enabled = "";
    @SerializedName("condition")
    private String conditionalItem;
    @SerializedName("defaultItemLocation")
    private String defaultItemLocation; // The location the an item gets if it is dropped in this room
    @SerializedName("enemies")
    private List<Enemy> enemyList = new ArrayList<>();
    @SerializedName("friends")
    private List<Friend> friendList = new ArrayList<>();
    @SerializedName("roamingEnemies")
    private List<RoamingEnemy> roamingEnemyList = new ArrayList<>();
    @SerializedName("interactiveObjects")
    private List<InteractiveObject> interactiveObjects = new ArrayList<>();

    // Constructor for a room object - calls the super constructor of the parent (thing) and adds the room-specific
    // variables
    public Room(String name, String description, String enabled, String defaultItemLocation,
            List<InteractiveCraftingObject> craftingObjects, List<Friend> friendList, List<Enemy> enemyList,
            boolean isDark) {
        super(name, description);
        this.enabled = enabled;
        this.defaultItemLocation = defaultItemLocation;
        this.isDark = isDark;
        this.friendList = friendList;
        this.enemyList = enemyList;
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

    public ItemList getRoomsItemList() {
        return roomItemList;
    }

    public List<String> getRoomItemNameList() {
        if (null != roomItemList) {
            List<String> itemNames = new ArrayList<String>();
            for (Item i : roomItemList.getAllItemList()) {
                itemNames.add(i.getName());
            }
            return itemNames;
        } else {
            return new ArrayList<String>();
        }

    }

    public List<InteractiveObject> getRoomInteractiveObjectsList() {
        if (null == interactiveObjects) {
            interactiveObjects = new ArrayList<>();
        }
        return interactiveObjects;
    }

    public void setRoomInteractiveObjectsList(List<InteractiveObject> interactiveObjects) {
        this.interactiveObjects = interactiveObjects;
    }

    public List<String> getRoomInteractiveObjectsNameList() {
        List<String> interactiveObjectNames = new ArrayList<String>();
        for (InteractiveObject interactiveObject : interactiveObjects) {
            interactiveObjectNames.add(interactiveObject.getName());
        }
        return interactiveObjectNames;
    }

    public InteractiveObject getRoomInteractiveObjectByName(String name) {
        for (InteractiveObject interactiveObject : interactiveObjects) {
            if (interactiveObject.getName().equalsIgnoreCase(name)) {
                return interactiveObject;
            }
        }
        return null;
    }

    public List<InteractiveCraftingObject> getRoomInteractiveCraftingObjectsList() {
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

    public boolean isDark() {
        return isDark;
    }

    public String getConditionalItem() {
        return conditionalItem;
    }

    public void setConditionalItem(String condition) {
        this.conditionalItem = condition;
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

    public void setDefaultItemLocation(String location) {
        defaultItemLocation = location;
    }

    public List<Enemy> getEnemyList() {
        List<Enemy> eList = new ArrayList<>();
        if (enemyList != null) {
            eList.addAll(enemyList);
        }
        if (roamingEnemyList != null) {
            eList.addAll(roamingEnemyList);
        }
        return eList;
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
        if (roamingEnemyList != null) {
            characters.addAll(roamingEnemyList);
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

    public void addWay(Way way) {
        roomWayList.add(way);
    }

    public Way getWay(String direction) {
        for (Way way : roomWayList) {
            if (way.getDirection().equals(direction)) {
                return way;
            }
        }
        return null;
    }

    public List<String> getRoomWayNameList() {
        List<String> wayNames = new ArrayList<>();
        for (Way w : roomWayList) {
            wayNames.add(w.getName());
        }
        return wayNames;
    }

    public Way getRoomWayByName(String name) {
        for (Way w : roomWayList) {
            if (w.getName().equalsIgnoreCase(name)) {
                return w;
            }
        }
        return null;
    }

    public boolean deleteWay(Way way) {
        if (roomWayList.contains(way)) {
            roomWayList.remove(way);
            return true;
        } else {
            return false;
        }
    }

    public List<RoamingEnemy> getRoamingEnemyList() {
        if (roamingEnemyList == null) {
            roamingEnemyList = new ArrayList<>();
        }
        return roamingEnemyList;
    }

    public void removeRoamingEnemy(RoamingEnemy e) {
        roamingEnemyList.remove(e);
    }

    public void addRoamingEnemy(RoamingEnemy e) {
        roamingEnemyList.add(e);
    }
}
