package de.dhbw.project.item;

import de.dhbw.project.State;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class Resource extends Item {

    public Resource(String name, String description, State state, int strength) {
        super(name, description, state, strength);
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
                return new Resource(name, description, State.NOT_USABLE, 0);
            case AGAIN:
                break;
            case ABBORT:
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
                return new Resource(name, description, resource.getState(), resource.getStrength());
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
