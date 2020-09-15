package de.dhbw.project;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.Decision;

public class Way extends Thing {
    @SerializedName("direction")
    private String direction;
    @SerializedName("from")
    private String from;
    @SerializedName("to")
    private String to;
    @SerializedName("state")
    private WayState state;
    @SerializedName("hint")
    private String hint;
    @SerializedName("condition")
    private String conditionalItem;

    // Constructor for a way - calls the super constructor of the parent (thing) and adds the way-specific variables
    public Way(String name, String description, String direction, String from, String to, WayState state, String hint) {
        super(name, description);
        this.direction = direction;
        this.from = from;
        this.to = to;
        this.state = state;
        this.hint = hint;
    }

    // Constructor for a way - calls the super constructor of the parent (thing) and adds the way-specific variables
    public Way(String name, String description, String direction, String from, String to, WayState state, String hint,
            String condition) {
        super(name, description);
        this.direction = direction;
        this.from = from;
        this.to = to;
        this.state = state;
        this.hint = hint;
        this.conditionalItem = condition;
    }

    // Method simplifies the default output for a way object
    @Override
    public String toString() {
        return "There's a " + getName() + " going " + direction + ". " + getDescription();
    }

    // Getters and setters for a way

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public WayState getState() {
        return (state == null ? WayState.ACTIVE : state);
    }

    public void setState(WayState state) {
        this.state = state;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public static Way createWay(Room from, List<Room> rooms) {
        boolean exit = false;
        while (!exit) {
            String name, description, direction, hint;
            direction = "up";
            Room toRoom;
            WayState state;
            name = SimpleUserInput.addMethod("Name");
            description = SimpleUserInput.addMethod("Description");
            toRoom = SimpleUserInput.addMethodRoom("Destination room", rooms);
            if (null == toRoom) {
                return null;
            }
            boolean exit2 = false;
            while (!exit2) {
                direction = SimpleUserInput.addMethod("Direction");
                if (Constants.DIRECTIONS.contains(direction)) {
                    exit2 = true;
                } else {
                    System.out.println("There is no direction '" + direction + "'.");
                    System.out.println("The following directions are available:");
                    Constants.DIRECTIONS.forEach(d -> System.out.println("  -" + d));
                }
            }
            state = SimpleUserInput.addMethodWayState("Way State");
            if (null == state) {
                return null;
            }
            hint = SimpleUserInput.addMethod("Hint");
            Decision d = SimpleUserInput.storeDialogue("Way");
            switch (d) {
            case SAVE:
                return new Way(name, description, direction, from.getName(), toRoom.getName(), state, hint);
            case AGAIN:
                break;
            case CANCEL:
                return null;
            }
        }

        return null;
    }

    public String getConditionalItem() {
        return conditionalItem;
    }

    public void setConditionalItem(String condition) {
        this.conditionalItem = condition;
    }

    public static Way editWay(Way way, List<Room> rooms) {
        boolean exit = false;
        while (!exit) {
            String name, description, direction, hint;
            direction = "up";
            Room toRoom;
            WayState state;
            name = SimpleUserInput.editMethod("Name", way.getName());
            description = SimpleUserInput.editMethod("Description", way.getDescription());
            System.out.println("Current destination room: " + way.getTo());
            toRoom = SimpleUserInput.addMethodRoom("Destination room", rooms);
            if (null == toRoom) {
                return null;
            }
            boolean exit2 = false;
            while (!exit2) {
                System.out.println("Current Direction: " + way.getDirection());
                direction = SimpleUserInput.addMethod("Direction");
                if (Constants.DIRECTIONS.contains(direction)) {
                    exit2 = true;
                } else {
                    System.out.println("There is no direction '" + direction + "'.");
                    System.out.println("The following directions are available:");
                    Constants.DIRECTIONS.forEach(d -> System.out.println("  -" + d));
                }
            }
            System.out.println("Current WayState: " + way.getState());
            state = SimpleUserInput.addMethodWayState("Way State");
            if (null == state) {
                return null;
            }
            hint = SimpleUserInput.editMethod("Hint", way.getHint());
            Decision d = SimpleUserInput.storeDialogue("Way");
            switch (d) {
            case SAVE:
                return new Way(name, description, direction, way.getFrom(), toRoom.getName(), state, hint);
            case AGAIN:
                break;
            case CANCEL:
                return null;
            }
        }

        return null;
    }

}
