package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

public class Material {
    @SerializedName("materialName")
    private String name;
    @SerializedName("materialNumber")
    private int number;

    public Material(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }
}
