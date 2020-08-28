package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.State;
import de.dhbw.project.Thing;

import java.util.ArrayList;
import java.util.List;

public class Createable extends Thing {
    @SerializedName("type")
    private String type;
    @SerializedName("clothingType")
    private EquipmentType clothingType;
    @SerializedName("state")
    private State state;
    @SerializedName("strength")
    private int strength;
    @SerializedName("neededMaterials")
    private List<Material> createableNeededMaterialList = new ArrayList<>();

    public Createable(String name, String description, int strength, String type, List<Material> materials) {
        super(name, description);
        this.strength = strength;
        this.type = type;
        this.createableNeededMaterialList = materials;
    }

    public List<Material> getCreateableNeededMaterialList() {
        return createableNeededMaterialList;
    }

    public String getNeededMaterialAsString() {
        StringBuilder str = new StringBuilder();
        for (Material material : createableNeededMaterialList) {
            str.append(material.getNumber() + "x " + material.getName());
            str.append(", ");
        }
        str.delete(str.length() - 2, str.length());
        return str.toString();
    }

    public int getStrength() {
        return strength;
    }

    public String getType() {
        return type;
    }

    public EquipmentType getClothingType() {
        return clothingType;
    }

    public State getState() {
        return state;
    }
}
