package de.dhbw.project.item;

import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.Decision;

public class Food extends Item {

    public Food(String name, String description, ItemState itemstate, int strength) {
        super(name, description, itemstate, strength);
        // TODO Auto-generated constructor stub
    }

    public static Item create() {
        boolean exit = false;
        String name, description;
        while (!exit) {
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            Decision d = SimpleUserInput.storeDialogue("Food");
            switch (d) {
            case SAVE:
                return new Food(name, description, ItemState.NOT_USABLE, 0);
            case AGAIN:
                break;
            case CANCEL:
                exit = true;
                break;
            }
        }
        return null;
    }

    public static Food edit(Food food) {
        boolean exit = false;
        String name, description;
        while (!exit) {
            name = SimpleUserInput.editMethod("Name", food.getName());
            description = SimpleUserInput.editMethod("Description", food.getDescription());
            Decision d = SimpleUserInput.storeDialogue("food");
            switch (d) {
            case SAVE:
                return new Food(name, description, food.getItemstate(), food.getStrength());
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
