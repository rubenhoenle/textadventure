package de.dhbw.project;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Creatable {
    @SerializedName("createableName")
    private String name;
    @SerializedName("neededMaterials")
    private List<Material> creatableNeededMaterialList = new ArrayList<>();

    public Creatable(String name) {
        this.name = name;
    }

    public List<Material> getCreatableNeededMaterialList() {
        return creatableNeededMaterialList;
    }

    public String getName() {
        return name;
    }

    public String getNeededMaterialAsString() {
        StringBuilder str = new StringBuilder();
        for (Material material : creatableNeededMaterialList) {
            str.append(material.getNumber() + "x " + material.getName());
            str.append(", ");
        }
        str.delete(str.length() - 2, str.length() - 1);
        return str.toString();
    }
}
