package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

public class Way extends Thing {
    @SerializedName("direction")
    private String direction;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("enable")
    private String enabled = "";

    // Constructor for a way - calls the super constructor of the parent (thing) and adds the way-specific variables
    public Way(String name, String description, String direction, String from, String to, String enabled) {
        super(name, description);
        this.direction = direction;
        this.from = from;
        this.to = to;
        this.enabled = enabled;
    }

    // Method simplifies the default output for a way object
    @Override
    public String toString() {
        return "There's a " + getName() + " going " + direction + ". " + getDescription();
    }

    // Getters and setters for a way

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
