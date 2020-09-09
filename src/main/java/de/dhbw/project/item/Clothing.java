package de.dhbw.project.item;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class Clothing extends Item {

    public Clothing(String name, String description, ItemState itemstate, int strength, EquipmentType clothingType,
            int addInventorySpace) {
        super(name, description, itemstate, strength, clothingType, addInventorySpace);
    }

    public static Clothing create() {
        boolean exit = false;
        String name, description;
        int strength;
        EquipmentType typ;
        while (!exit) {
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            strength = SimpleUserInput.addMethodInt("Strength");
            typ = SimpleUserInput.addMethodEquipmentType("Equipment-Typ");
            Decision d = SimpleUserInput.storeDialogue("Clothing");
            switch (d) {
            case SAVE:
                return new Clothing(name, description, ItemState.NOT_USABLE, strength, typ, 0);
            case AGAIN:
                break;
            case ABBORT:
                exit = true;
                break;
            }
        }
        return null;
    }

    public static Clothing edit(Clothing clothing) {
        boolean exit = false;
        String name, description;
        int strength;
        EquipmentType typ;
        while (!exit) {
            name = SimpleUserInput.editMethod("Name", clothing.getName());
            description = SimpleUserInput.editMethod("Description", clothing.getDescription());
            strength = SimpleUserInput.editMethod("Strength", clothing.getStrength());
            typ = SimpleUserInput.editMethod("Equipment-Typ", clothing.getEquipmentType());
            Decision d = SimpleUserInput.storeDialogue("Clothing");
            switch (d) {
            case SAVE:
                return new Clothing(name, description, clothing.getItemstate(), strength, typ, 0);
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
