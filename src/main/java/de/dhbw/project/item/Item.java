package de.dhbw.project.item;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Constants;
import de.dhbw.project.EquipmentType;
import de.dhbw.project.State;
import de.dhbw.project.Thing;

public abstract class Item extends Thing {
    @SerializedName("state")
    private State state;
    @SerializedName("strength")
    private int strength;
    @SerializedName("place")
    private String place;
    @SerializedName("equipment")
    private EquipmentType equipment;

    // Constructor for an item - calls the super constructor of the parent (thing) and adds the item-specific variables
    public Item(String name, String description, State state, int strength) {
        super(name, description);
        this.strength = strength;
        this.state = state;
    }

    public Item(String name, String description, State state, int strength, EquipmentType equipmentType) {
        super(name, description);
        this.strength = strength;
        this.state = state;
        this.equipment = equipmentType;
    }

    // Getters and setters for an item

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (state == null)
            state = State.NOT_USABLE;
        this.state = state;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getWhere() {
        if (place != null) {
            return place;
        } else {
            return Constants.WHERE;
        }
    }

    public void setWhere(String location) {
        place = location;
    }

    public EquipmentType getEquipmentType() {
        return equipment;
    }

    public void setEquipmentType(EquipmentType equipment) {
        this.equipment = equipment;
    }
}
