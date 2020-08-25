package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

public class Item extends Thing {
    @SerializedName("state")
    private State state;
    @SerializedName("strength")
    private int strength;
    @SerializedName("place")
    private String place;

    // Constructor for an item - calls the super constructor of the parent (thing) and adds the item-specific variables
    public Item(String name, String description, State state, int strength) {
        super(name, description);
        this.strength = strength;
        this.state = state;
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
}
