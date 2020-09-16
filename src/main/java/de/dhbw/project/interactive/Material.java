package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Thing;

public class Material extends Thing {
    @SerializedName("number")
    private int number;

    // A material is a number of items, needed to craft a Item (called Createable) with help of an interactive
    // crafting Object. The number Attribute of an Material says, how often one Item is needed for the
    // crafting proccess.

    public Material(String name, String description, int number) {
        super(name, description);
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }
}
