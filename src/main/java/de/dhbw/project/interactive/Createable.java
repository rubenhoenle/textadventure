package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.EquipmentType;
import de.dhbw.project.Thing;
import de.dhbw.project.item.ItemState;

import java.util.ArrayList;
import java.util.List;

public class Createable extends Thing {

    // Note: A Createable describes a Item, which the player can craft with the help of an interactive crafting
    // object.

    @SerializedName("type")
    private String type;
    @SerializedName("equipmentType")
    private EquipmentType equipmentType;
    @SerializedName("itemstate")
    private ItemState itemstate;
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

    // Returns a string which can be used for the output of the needed Items for a crafting proccess.
    // for example: 2x iron ingot, 1x wood stick
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

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public ItemState getItemstate() {
        return this.itemstate;
    }
}
