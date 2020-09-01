package de.dhbw.project.item;

import de.dhbw.project.State;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class Book extends Item {

    public Book(String name, String description, State state, int strength) {
        super(name, description, state, strength);
        // TODO Auto-generated constructor stub
    }

    public static Book create() {
        boolean exit = false;
        String name, description;
        while (!exit) {
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            Decision d = SimpleUserInput.storeDialogue("Book");
            switch (d) {
            case SAVE:
                return new Book(name, description, State.NOT_USABLE, 0);
            case AGAIN:
                break;
            case ABBORT:
                exit = true;
                break;
            }
        }
        return null;
    }

    public static Book edit(Book book) {
        boolean exit = false;
        String name, description;
        while (!exit) {
            name = SimpleUserInput.editMethod("Name", book.getName());
            description = SimpleUserInput.editMethod("Description", book.getDescription());
            Decision d = SimpleUserInput.storeDialogue("book");
            switch (d) {
            case SAVE:
                return new Book(name, description, book.getState(), book.getStrength());
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