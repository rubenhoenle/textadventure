package de.dhbw.project.item;

import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.Decision;

public class Resource extends Item {

    public Resource(String name, String description, ItemState itemstate, int strength) {
        super(name, description, itemstate, strength);
        // TODO Auto-generated constructor stub
    }

    public static Resource create() {
        boolean exit = false;
        String name, description;
        while (!exit) {
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            Decision d = SimpleUserInput.storeDialogue("Resource");
            switch (d) {
            case SAVE:
                return new Resource(name, description, ItemState.ACTIVE, 0);
            case AGAIN:
                break;
            case CANCEL:
                exit = true;
                break;
            }
        }
        return null;
    }

    public static Resource edit(Resource resource) {
        boolean exit = false;
        String name, description;
        while (!exit) {
            name = SimpleUserInput.editMethod("Name", resource.getName());
            description = SimpleUserInput.editMethod("Description", resource.getDescription());
            Decision d = SimpleUserInput.storeDialogue("Resource");
            switch (d) {
            case SAVE:
                return new Resource(name, description, resource.getItemstate(), resource.getStrength());
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
