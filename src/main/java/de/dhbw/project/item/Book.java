package de.dhbw.project.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import de.dhbw.project.State;
import de.dhbw.project.regions.ruins.AlienFont;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

public class Book extends Item {

    @SerializedName("pages")
    private List<String> pages;

    public Book(String name, String description, State state, int strength, List<String> pages) {
        super(name, description, state, strength);
        this.pages = pages;
    }

    public int getPageAmmount() {
        return pages.size();
    }

    public void printPage(int page) {
        if (page > 0 && page <= pages.size()) {
            if (pages.get(page - 1).length() == 0) {
                System.out.println("This page is empty.");
            } else {
                String decorationline = "";
                // System.out.println("kurz vor Rahmenberechnung");
                for (int i = 0; i < AlienFont.getWidthOfMixedOutput(pages.get(page - 1)); i++) {
                    decorationline = decorationline + "-";
                }
                // System.out.println("nach Rahmenberechnung");
                System.out.println(decorationline);
                if (this.getName().equals("bible")) {
                    AlienFont.mixedDisplay(pages.get(page - 1), true);
                } else {
                    AlienFont.mixedDisplay(pages.get(page - 1));
                }
                System.out.println(decorationline);
            }
        } else {
            System.out.println("No page was found with this number.");
            System.out.println("Please enter a number between 1 and " + pages.size());
        }
    }

    public void openBookReader() {
        if (pages.size() == 0) {
            System.out.println(this.getName() + " has no pages.");
        } else {
            this.bookReader("init");
            ;
        }
    }

    private void bookReader(String input) {
        if (input.equals("exit") || input.equals("stop") || input.equals("close") || input.equals("put away")) {
            System.out.println("Closed the " + this.getName());
        }
        // Only display first page if book has only 1 page
        else if (input.equals("init") && pages.size() == 1) {
            printPage(1);
        } else {
            if (input.equals("init")) {
                System.out.println("You are reading the " + this.getName() + ".");
                System.out.println("Enter a number between 1 and " + pages.size() + " to read a page.");
                System.out.println("Enter 'close' to put " + this.getName() + " away.");

            } else {

                int readPage = 0;
                try {
                    readPage = Integer.parseInt(input);
                    if (readPage < 1 || readPage > pages.size()) {
                        System.out.println("No page was found with this number.");
                        System.out.println("Please enter a number between 1 and " + pages.size());
                    } else {
                        printPage(readPage);
                        System.out.println("Page " + input + " of " + pages.size() + ".");
                        System.out.println("Enter 'close' to put " + this.getName() + " away.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("No page was found with this number");
                    System.out.println("Please enter a number between 1 and " + pages.size() + ".");
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String nextcommand;
            try {
                nextcommand = reader.readLine();
                bookReader(nextcommand);
            } catch (IOException e) {
                System.out.println("An error occured reading the book.");
            }
        }
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
                return new Book(name, description, State.NOT_USABLE, 0, Arrays.asList(""));
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
                return new Book(name, description, book.getState(), book.getStrength(), book.pages);
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