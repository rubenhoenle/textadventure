package de.dhbw.project.item;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.State;

public class Clothing extends Item {

    public Clothing(String name, String description, State state, int strength, EquipmentType clothingType) {
        super(name, description, state, strength, clothingType);
    }

}
