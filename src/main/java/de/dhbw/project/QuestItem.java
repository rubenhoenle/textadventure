package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.item.*;

public class QuestItem extends Item {

    @SerializedName("type")
    private String type;

    public QuestItem(String name, String description, State state, int strength, EquipmentType equipmentType,
            String type) {
        super(name, description, state, strength, equipmentType);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item questItemToItem() {
        switch (this.getType()) {
        case "cloth":
            return new Clothing(this.getName(), this.getDescription(), this.getState(), this.getStrength(),
                    this.getEquipmentType());
        case "food":
            return new Food(this.getName(), this.getDescription(), this.getState(), this.getStrength());
        case "book":
            return new Book(this.getName(), this.getDescription(), this.getState(), this.getStrength());
        case "resource":
            return new Resource(this.getName(), this.getDescription(), this.getState(), this.getStrength());
        case "weapon":
            return new Weapon(this.getName(), this.getDescription(), this.getState(), this.getStrength(),
                    this.getEquipmentType());
        case "tool":
            return new Tool(this.getName(), this.getDescription(), this.getState(), this.getStrength(),
                    this.getEquipmentType());
        }
        return null;
    }
}
