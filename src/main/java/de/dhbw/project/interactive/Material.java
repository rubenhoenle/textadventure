package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Thing;

public class Material extends Thing {
    @SerializedName("number")
    private int number;

    public Material(String name, String description, int number) {
        super(name, description);
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }
}
