package de.dhbw.project.interactive;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.Thing;

import java.util.ArrayList;
import java.util.List;

public class Createable extends Thing
{
    @SerializedName("neededMaterials")
    private List<Material> createableNeededMaterialList = new ArrayList<>();

    public Createable(String name, String description, List<Material> materials) {
        super(name, description);
        this.createableNeededMaterialList = materials;
    }

    public List<Material> getCreateableNeededMaterialList() {
        return createableNeededMaterialList;
    }

    public String getNeededMaterialAsString() {
        StringBuilder str = new StringBuilder();
        for(Material material: createableNeededMaterialList) {
            str.append(material.getNumber() + "x " + material.getName());
            str.append(", ");
        }
        str.delete(str.length() - 2, str.length());
        return str.toString();
    }
}
