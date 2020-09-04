package de.dhbw.project.levelEditor;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.EnumUtils;

import de.dhbw.project.*;

public class SimpleUserInput {
    public static Scanner userInput = new Scanner(System.in);

    public enum Decision {
        SAVE, AGAIN, ABBORT
    }

    public static String editMethod(String name, String currentValue) {
        if (null != currentValue) {
            System.out.println("Current " + name + ": " + currentValue);
        }
        System.out.println("Please enter the new " + name + ":");
        return userInput.nextLine();
    }

    public static EquipmentType editMethod(String name, EquipmentType currentValue) {
        if (null != currentValue) {
            System.out.println("Current " + name + ": " + currentValue.name());
        }
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter the new " + name + ":");
            String input = userInput.nextLine();
            if (EnumUtils.isValidEnum(EquipmentType.class, input)) {
                exit = true;
                return EquipmentType.valueOf(input);
            } else {
                System.out.println("The following types are allowed: ");
                System.out.println(String.join(", ", EquipmentType.getAllDescriptions()));
            }
        }
        return null;
    }

    public static int editMethod(String name, int currentValue) {
        System.out.println("Current " + name + ": " + currentValue);
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter the new " + name + ":");
            String input = userInput.nextLine();
            try {
                int returnValue = Integer.parseInt(input);
                exit = true;
                return returnValue;
            } catch (NumberFormatException nfe) {
                System.out.println("You have to enter an integer! You've entered '" + input + "'.");
            }
        }
        return 0;
    }

    public static int addMethodInt(String name) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter the " + name + ":");
            String input = userInput.nextLine();
            try {
                int returnValue = Integer.parseInt(input);
                exit = true;
                return returnValue;
            } catch (NumberFormatException nfe) {
                System.out.println("You have to enter an integer! You've entered '" + input + "'.");
            }
        }
        return 0;
    }

    public static String addMethod(String name) {
        System.out.println("Please enter the " + name + ":");
        return userInput.nextLine();
    }

    public static EquipmentType addMethodEquipmentType(String name) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter the " + name + ":");
            String input = userInput.nextLine();
            if (EnumUtils.isValidEnum(EquipmentType.class, input)) {
                exit = true;
                return EquipmentType.valueOf(input);
            } else {
                System.out.println("The following types are allowed: ");
                System.out.println(String.join(", ", EquipmentType.getAllDescriptions()));
            }
        }
        return null;
    }

    public static Room addMethodRoom(String name, Game g) {
        if (null != g) {
            boolean exit = false;
            List<Room> roomList = g.getRooms();
            while (!exit) {
                System.out.println("Please enter the " + name + ":");
                String input = userInput.nextLine();
                for (int i = 0; i < roomList.size(); i++) {
                    Room room = roomList.get(i);
                    if (room.getName().equals(input)) {
                        return room;
                    }
                }
                System.out.println("There is no room available with the name '" + input + "'.");
                System.out.println("The following rooms are available:");
                roomList.forEach(room -> {
                    System.out.println("  -" + room.getName());
                });

            }
        }
        return null;
    }

    public static WayState addMethodWayState(String name) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Please enter the " + name + ":");
            String input = userInput.nextLine();
            if (EnumUtils.isValidEnum(WayState.class, input)) {
                return WayState.valueOf(input);
            } else {
                System.out.println("'" + input + "' isn't a valid WayState!");
                System.out.println("Valid WayStates are: " + String.join(", ", WayState.getAllNames()));
            }

        }
        return null;
    }

    public static Decision storeDialogue(String object) {
        while (true) {
            System.out.println("Enter 'store' to store the " + object
                    + ", 'edit' to enter the details again or 'q' to this operation");
            String input = SimpleUserInput.userInput.nextLine();
            input.toLowerCase();
            switch (input) {
            case "store":
                return Decision.SAVE;
            case "edit":
                return Decision.AGAIN;
            case "q":
                return Decision.ABBORT;
            }
        }
    }

    public static boolean deleteDialogue(String object, String description) {
        while (true) {
            System.out.println("Are you sure, you want delete the " + object + " " + description + "? (yes/no)");
            String input = SimpleUserInput.userInput.nextLine().toLowerCase();
            input.toLowerCase();
            switch (input) {
            case "yes":
                return true;
            case "y":
                return true;
            case "no":
                return false;
            case "n":
                return false;
            default:
                System.out.println("You've entered '" + input + "'.");
            }
        }
    }

}
