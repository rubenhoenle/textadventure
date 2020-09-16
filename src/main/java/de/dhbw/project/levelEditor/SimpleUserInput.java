package de.dhbw.project.levelEditor;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.Game;
import de.dhbw.project.Room;
import de.dhbw.project.WayState;
import de.dhbw.project.item.ItemState;

import org.apache.commons.lang3.EnumUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SimpleUserInput {

    // Number of Attempts the User has to enter a valid value
    final static int maxAttempts = 5;
    static Scanner userInput = new Scanner(System.in);

    public static String scan() {
        // Scanner userInput = new Scanner(System.in);
        if (userInput.hasNextLine()) {
            String returnString = userInput.nextLine();
            return returnString;
        } else
            return null;
    }

    public static String editMethod(String name, String currentValue) {
        if (null != currentValue) {
            System.out.println("Current " + name + ": " + currentValue);
        }
        System.out.println("Please enter the new " + name + ":");
        return scan();
    }

    // Prompts the user to enter a boolean, after maxAttempts the currentValue will be returned
    public static boolean editMethod(String name, Boolean currentValue) {
        String input;
        if (null != currentValue) {
            System.out.println("Current " + name + ": " + currentValue.toString());
        }
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println("Please enter the new " + name + ":");
            input = scan();
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.valueOf(input);
            } else {
                System.out.println("You're input '" + input + "' isn't a valid boolean!");
                System.out.println("Please enter 'false' or 'true'.");
            }
        }
        return currentValue;
    }

    // Prompts the user to enter an EquipmentType, after maxAttempts the currentValue will be returned
    public static EquipmentType editMethod(String name, EquipmentType currentValue) {
        if (null != currentValue) {
            System.out.println("Current " + name + ": " + currentValue.name());
        }
        if (EnumUtils.getEnumList(EquipmentType.class).size() > 0) {
            for (int i = 0; i < maxAttempts; i++) {
                System.out.println("Please enter the new " + name + ":");
                String input = scan();
                if (EnumUtils.isValidEnum(EquipmentType.class, input)) {
                    return EquipmentType.valueOf(input);
                } else {
                    System.out.println("The following types are allowed: ");
                    System.out.println(String.join(", ", EquipmentType.getAllDescriptions()));
                }
            }
        }
        return currentValue;
    }

    // Prompts the user to enter an Integer, after maxAttempts the currentValue will be returned
    public static int editMethod(String name, int currentValue) {
        System.out.println("Current " + name + ": " + currentValue);
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println("Please enter the new " + name + ":");
            String input = scan();
            try {
                int returnValue = Integer.parseInt(input);
                return returnValue;
            } catch (NumberFormatException nfe) {
                System.out.println("You have to enter an integer! You've entered '" + input + "'.");
            }
        }
        return currentValue;
    }

    // Prompts the user to enter a valid ItemState, after maxAttempts null will be returned
    public static ItemState editMethod(String name, ItemState currentValue) {
        if (null != currentValue) {
            System.out.println("Current " + name + ": " + currentValue.toString());
        }
        if (EnumUtils.getEnumList(ItemState.class).size() > 0) {
            for (int i = 0; i < maxAttempts; i++) {
                System.out.println("Please enter the " + name + ":");
                String input = scan();
                if (EnumUtils.isValidEnum(ItemState.class, input)) {
                    return ItemState.valueOf(input);
                } else {
                    System.out.println("The following types are allowed: ");
                    System.out.println(String.join(", ", ItemState.getAllDescriptions()));
                }
            }
        }
        return currentValue;
    }

    // Prompts the user to enter a room name, checks if there is a room with room name in game
    // after maxAttempts the currentValue will be returned
    public static String editMethodRoomName(String name, String currentValue, List<Room> rooms) {
        if (currentValue != null) {
            System.out.println("Current " + name + ": " + currentValue);
        }
        if ((null != rooms) && (rooms.size() > 0)) {
            for (int z = 0; z < maxAttempts; z++) {
                System.out.println("Please enter the new " + name + ":");
                String input = scan();
                List<Room> resultList = rooms.stream().filter(e -> e.getName().equals(input))
                        .collect(Collectors.toList());
                if (resultList.size() > 0) {
                    return resultList.get(0).getName();
                } else {
                    System.out.println("In this game there is no room with the name: " + input);
                }
            }
            return currentValue;
        } else if (null == rooms) {
            System.out.println("Please enter a new '" + name + "':");
            String input = scan();
            return input;
        }
        return currentValue;

    }

    // Prompts the user to enter an Integer, after maxAttempts 0 will be returned
    public static int addMethodInt(String name) {
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println("Please enter the " + name + ":");
            String input = scan();
            try {
                int returnValue = Integer.parseInt(input);
                return returnValue;
            } catch (NumberFormatException nfe) {
                System.out.println("You have to enter an integer! You've entered '" + input + "'.");
            }
        }
        return 0;
    }

    // Prompts the user to enter an Integer, after maxAttempts false will be returned
    public static boolean addMethodBoolean(String name) {
        String input;
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println("Please enter the new " + name + ":");
            input = scan();
            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.valueOf(input);
            } else {
                System.out.println("You're input '" + input + "' isn't a valid boolean!");
                System.out.println("Please enter 'false' or 'true'.");
            }
        }
        return false;
    }

    public static String addMethod(String name) {
        System.out.println("Please enter the " + name + ":");
        return scan();
    }

    // Prompts the user to enter a valid EquipmentType, after maxAttempts null will be returned
    public static EquipmentType addMethodEquipmentType(String name) {
        if (EnumUtils.getEnumList(EquipmentType.class).size() > 0) {
            for (int i = 0; i < maxAttempts; i++) {
                System.out.println("Please enter the " + name + ":");
                String input = scan();
                if (EnumUtils.isValidEnum(EquipmentType.class, input)) {
                    return EquipmentType.valueOf(input);
                } else {
                    System.out.println("The following types are allowed: ");
                    System.out.println(String.join(", ", EquipmentType.getAllDescriptions()));
                }
            }
        }
        return null;
    }

    // Prompts the user to enter a valid ItemState, after maxAttempts null will be returned
    public static ItemState addMethodItemState(String name) {
        if (EnumUtils.getEnumList(ItemState.class).size() > 0) {
            for (int i = 0; i < maxAttempts; i++) {
                System.out.println("Please enter the " + name + ":");
                String input = scan();
                if (EnumUtils.isValidEnum(ItemState.class, input)) {
                    return ItemState.valueOf(input);
                } else {
                    System.out.println("The following types are allowed: ");
                    System.out.println(String.join(", ", ItemState.getAllDescriptions()));
                }
            }
        }
        return null;
    }

    // Prompts the user to enter an existing room in Game g, after maxAttempts null will be returned
    public static Room addMethodRoom(String name, List<Room> rooms) {
        if (null != rooms) {
            List<Room> roomList = rooms;
            if (roomList.size() > 0) {
                for (int z = 0; z < maxAttempts; z++) {
                    System.out.println("Please enter the " + name + ":");
                    String input = scan();
                    List<Room> resultList = rooms.stream().filter(e -> e.getName().equals(input))
                            .collect(Collectors.toList());
                    if (resultList.size() > 0) {
                        return resultList.get(0);
                    }
                    System.out.println("There is no room available with the name '" + input + "'.");
                    System.out.println("The following rooms are available:");
                    roomList.forEach(room -> {
                        System.out.println("  -" + room.getName());
                    });
                }
            } else {
                System.out.println("There are no rooms available in this game.");
            }
        } else {
            System.out.println("Error: no rooms found!");
        }
        return null;
    }

    // Prompts the user to enter a valid WayState, after maxAttempts null will be returned
    public static WayState addMethodWayState(String name) {
        if (EnumUtils.getEnumList(WayState.class).size() > 0) {
            for (int i = 0; i < maxAttempts; i++) {
                System.out.println("Please enter the " + name + ":");
                String input = scan();
                if (EnumUtils.isValidEnum(WayState.class, input)) {
                    return WayState.valueOf(input);
                } else {
                    System.out.println("'" + input + "' isn't a valid WayState!");
                    System.out.println("Valid WayStates are: " + String.join(", ", WayState.getAllNames()));
                }
            }
        }
        return null;
    }

    // Prompts the user to enter 'store' / 'edit' / 'abbort' and returns the corresponding enum Decision
    public static Decision storeDialogue(String object) {
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println("Enter 'store' to store the " + object
                    + ", 'edit' to enter the details again or 'q' to this operation");
            String input = SimpleUserInput.scan();
            input.toLowerCase();
            switch (input) {
            case "store":
                return Decision.SAVE;
            case "edit":
                return Decision.AGAIN;
            case "q":
                return Decision.CANCEL;
            }
        }
        return Decision.CANCEL;
    }

    // Prompts the user to enter 'yes' or 'no' and returns a boolean
    public static boolean deleteDialogue(String object, String description) {
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println("Are you sure, you want delete the " + object + " " + description + "? (yes/no)");
            String input = SimpleUserInput.scan().toLowerCase();
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
        return false;
    }

    // Prints user input, usefull if the userinput doesn't match the required input
    public static void printUserInput(String input) {
        System.out.println("You have entered '" + input + "'.");
    }

    // Asks the user a question(parameter of this method) and expects yes or no
    // If in maxAttempts neither yes or no is given as an answer, false will be returned
    public static boolean yesNoQuestion(String question) {
        for (int i = 0; i < maxAttempts; i++) {
            System.out.println(question + " (yes/no)");
            String input = SimpleUserInput.scan().toLowerCase();
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
                SimpleUserInput.printUserInput(input);
            }
        }
        return false;
    }
}
