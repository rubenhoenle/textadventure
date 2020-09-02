package de.dhbw.project.item;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class Tool extends Item {

    public Tool(String name, String description, ItemState itemstate, int strength) {
        super(name, description, itemstate, strength);
        // TODO Auto-generated constructor stub
    }

    public Tool(String name, String description, ItemState itemstate, int strength, EquipmentType equipmentType) {
        super(name, description, itemstate, strength, equipmentType);
    }

    public static Tool create() {
        boolean exit = false;
        String name, description;
        int strength;
        EquipmentType typ;
        while (!exit) {
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            strength = SimpleUserInput.addMethodInt("Strength");
            typ = SimpleUserInput.addMethodEquipmentType("Equipment-Typ");
            Decision d = SimpleUserInput.storeDialogue("Tool");
            switch (d) {
            case SAVE:
                return new Tool(name, description, ItemState.NOT_USABLE, strength, typ);
            case AGAIN:
                break;
            case ABBORT:
                exit = true;
                break;
            }
        }
        return null;
    }

    public static Tool edit(Tool tool) {
        boolean exit = false;
        String name, description;
        int strength;
        EquipmentType typ;
        while (!exit) {
            name = SimpleUserInput.editMethod("Name", tool.getName());
            description = SimpleUserInput.editMethod("Description", tool.getDescription());
            strength = SimpleUserInput.editMethod("Strength", tool.getStrength());
            typ = SimpleUserInput.editMethod("Equipment-Type", tool.getEquipmentType());
            Decision d = SimpleUserInput.storeDialogue("Tool");
            switch (d) {
            case SAVE:
                return new Tool(name, description, tool.getItemstate(), strength, typ);
            case AGAIN:
                break;
            case ABBORT:
                exit = true;
                break;
            }
        }
        return null;
    }
}
