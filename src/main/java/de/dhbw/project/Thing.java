package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

// Abstract class for all occuring things in the game (ways, items, ...)
public abstract class Thing {
    // Each thing has at least a name and a description
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;

    // Constructor for a thing
    public Thing(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters for things

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}