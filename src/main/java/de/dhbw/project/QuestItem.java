package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.item.*;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.Decision;

import java.util.Arrays;
import java.util.List;

public class QuestItem extends Item {

    @SerializedName("type")
    private String type;

    public QuestItem(String name, String description, ItemState addItemState, int strength, EquipmentType equipmentType,
            String type, int addInventorySpace) {
        super(name, description, addItemState, strength, equipmentType, addInventorySpace);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item questItemToItem() {
        // converts an rewards to the type he got in the json
        switch (this.getType().toLowerCase()) {
        case "cloth":
            return new Clothing(this.getName(), this.getDescription(), this.getItemstate(), this.getStrength(),
                    this.getEquipmentType(), this.getExpandInventorySpace());
        case "food":
            return new Food(this.getName(), this.getDescription(), this.getItemstate(), this.getStrength());
        case "book":
            List<String> temparray = Arrays.asList("");
            return new Book(this.getName(), this.getDescription(), this.getItemstate(), this.getStrength(), temparray,
                    false);
        case "resource":
            return new Resource(this.getName(), this.getDescription(), this.getItemstate(), this.getStrength());
        case "weapon":
            return new Weapon(this.getName(), this.getDescription(), this.getItemstate(), this.getStrength(),
                    this.getEquipmentType());
        case "tool":
            return new Tool(this.getName(), this.getDescription(), this.getItemstate(), this.getStrength(),
                    this.getEquipmentType());
        }
        return null;
    }
}
