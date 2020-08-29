package de.dhbw.project.item;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.State;

public class Tool extends Item {

    public Tool(String name, String description, State state, int strength) {
        super(name, description, state, strength);
        // TODO Auto-generated constructor stub
    }

    public Tool(String name, String description, State state, int strength, EquipmentType equipmentType) {
        super(name, description, state, strength, equipmentType);
    }

}
