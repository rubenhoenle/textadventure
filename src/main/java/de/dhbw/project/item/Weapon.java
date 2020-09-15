package de.dhbw.project.item;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.Decision;

public class Weapon extends Tool {

    public Weapon(String name, String description, ItemState itemstate, int strength) {
        super(name, description, itemstate, strength);
        // TODO Auto-generated constructor stub
    }

    public Weapon(String name, String description, ItemState itemstate, int strength, EquipmentType equipmentType) {
        super(name, description, itemstate, strength, equipmentType);
    }

    public static Weapon create() {
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
                return new Weapon(name, description, ItemState.NOT_USABLE, strength, typ);
            case AGAIN:
                break;
            case CANCEL:
                exit = true;
                break;
            }
        }
        return null;
    }

    public static Weapon edit(Weapon weapon) {
        boolean exit = false;
        String name, description;
        int strength;
        EquipmentType typ;
        while (!exit) {
            name = SimpleUserInput.editMethod("Name", weapon.getName());
            description = SimpleUserInput.editMethod("Description", weapon.getDescription());
            strength = SimpleUserInput.editMethod("Strength", weapon.getStrength());
            typ = SimpleUserInput.editMethod("Equipment-Typ", weapon.getEquipmentType());
            Decision d = SimpleUserInput.storeDialogue("Weapon");
            switch (d) {
            case SAVE:
                return new Weapon(name, description, weapon.getItemstate(), strength, typ);
            case AGAIN:
                break;
            case CANCEL:
                exit = true;
                break;
            }
        }
        return null;
    }
}
