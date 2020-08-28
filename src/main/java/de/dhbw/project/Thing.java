package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Thing))
            return false;
        Thing thing = (Thing) o;
        return Objects.equals(name, thing.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}