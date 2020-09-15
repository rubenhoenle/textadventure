package de.dhbw.project.item;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.levelEditor.SimpleUserInput;
import de.dhbw.project.levelEditor.Decision;
import de.dhbw.project.regions.ruins.AlienFont;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import de.dhbw.project.Constants;
import de.dhbw.project.Game;
import de.dhbw.project.Room;

public class Book extends Item {

    @SerializedName("pages")
    private List<String> pages;
    @SerializedName("translatable")
    private boolean translatable;
    @SerializedName("translationRewards")
    private ItemList translationRewards = new ItemList();
    @SerializedName("successfullTranslationMessage")
    private String successfullTranslationMessage = "";
    @SerializedName("displayFontTranslated")
    private boolean displayFontTranslated;

    public Book(String name, String description, ItemState itemstate, int strength, List<String> pages,
            boolean translatable) {
        // add empty inventory
        this(name, description, itemstate, strength, pages, translatable, null);
    }

    public Book(String name, String description, ItemState itemstate, int strength, List<String> pages,
            boolean translatable, ItemList translationRewards) {
        // add empty translation message
        this(name, description, itemstate, strength, pages, translatable, translationRewards, null);
    }

    public Book(String name, String description, ItemState itemstate, int strength, List<String> pages,
            boolean translatable, ItemList translationRewards, String successfullTranslationMessage) {
        this(name, description, itemstate, strength, pages, translatable, translationRewards, null, false);
    }

    public Book(String name, String description, ItemState itemstate, int strength, List<String> pages,
            boolean translatable, ItemList translationRewards, String successfullTranslationMessage,
            boolean displayFontTranslated) {
        super(name, description, itemstate, strength);
        this.pages = pages;
        this.translatable = translatable;
        if (translationRewards == null || translationRewards.getAllItemList().size() == 0) {
            this.translationRewards = new ItemList();
        } else {
            this.translationRewards = translationRewards;
        }
        if (successfullTranslationMessage == null || successfullTranslationMessage.length() == 0) {
            this.successfullTranslationMessage = successfullTranslationMessage;
        } else {
            this.successfullTranslationMessage = null;
        }
        this.displayFontTranslated = displayFontTranslated;
    }

    public int getPageAmmount() {
        return pages.size();
    }

    public void printPage(int page, boolean translate) {
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
                AlienFont.mixedDisplay(pages.get(page - 1), this.displayFontTranslated);
                System.out.println(decorationline);
            }
        } else {
            System.out.println("No page was found with this number.");
            System.out.println("Please enter a number between 1 and " + pages.size());
        }
    }

    public void printPage(int page) {
        this.printPage(page, false);
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
                nextcommand = reader.readLine().toLowerCase();
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
                return new Book(name, description, ItemState.NOT_USABLE, 0, Arrays.asList(""), false);
            case AGAIN:
                break;
            case CANCEL:
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
                return new Book(name, description, book.getItemstate(), book.getStrength(), book.pages,
                        book.translatable, book.translationRewards);
            case AGAIN:
                break;
            case CANCEL:
                exit = true;
                break;
            }
        }
        return null;
    }

    public List<String> getPages() {
        List<String> pages = new ArrayList<String>();
        for (int i = 0; i < this.pages.size(); i++) {
            pages.add(i, this.pages.get(i));
        }
        return pages;
    }

    private void bookTranslator(Game game, String input) {
        if (input.equals("exit") || input.equals("stop") || input.equals("close") || input.equals("put away")) {
            System.out.println("Canceled the translation of the " + this.getName());
        } else {
            // for stopping the translation screen when translation is complete
            boolean finishedtranslation = false;

            if (input.equals("init")) {
                System.out.println("You are trying to translate the " + this.getName() + ".");
                System.out.println("Enter 'read' to read the text.");
                System.out.println("Enter 'translation <translation>' to translate the text.");
                System.out.println("To translate the text successfully, the first " + Constants.STONETABLET_REQ_CORRECT
                        + " letters");
                System.out.println(" must be correctly translated");
                System.out.println(" use for example 'translation the quick brown fox jumps over the lazy dog'.");
                System.out.println("Enter 'help' to learn how to translate the text.");
                System.out.println("Enter 'close' to put " + this.getName() + " away.");

            } else if (input.startsWith("read")) {
                this.openBookReader();
            } else if (input.startsWith("translation ")) {
                List<String> pages = this.getPages();
                Iterator<String> pageiterator = pages.iterator();
                String allText = "";
                while (pageiterator.hasNext()) {
                    allText = allText.concat(pageiterator.next());
                }
                // TODO only read chars inside [ALFONT]
                // Assumption made here: Starts and ends with [ALFONT], no mixed input
                allText = allText.replace("[ALFONT]", "");
                allText = allText.replace("\n", "");
                allText = allText.replace(" ", "");

                input = input.replace("translation ", "");
                input = input.replace(" ", "");

                allText = allText.toLowerCase(Locale.ROOT);
                input = input.toLowerCase(Locale.ROOT);

                // System.out.println("alien: " + allText);
                // System.out.println("transl: " + input);

                char[] allTextArray = allText.toCharArray();
                char[] inputArray = input.toCharArray();

                boolean successful = true;

                if (inputArray.length < Constants.STONETABLET_REQ_CORRECT && inputArray.length < allTextArray.length) {
                    System.out.println("You did not enter the required amount of translated chars");
                    successful = false;
                } else {

                    for (int i = 0; i < inputArray.length && i < allTextArray.length
                            && i < Constants.STONETABLET_REQ_CORRECT; i++) {
                        if (inputArray[i] != allTextArray[i]) {
                            successful = false;
                        }
                    }

                    if (successful) {
                        this.translatable = false;
                        this.displayFontTranslated = true;

                        System.out.println("Correct! The translation was successfull.\n");

                        if (this.translationRewards != null && this.translationRewards.getAllItemList().size() > 0) {
                            System.out.println("The translation of the text yielded the following items:");
                            for (Item rewardItem : this.translationRewards.getAllItemList()) {
                                game.getCurrentRoom().addItem(rewardItem);
                                System.out.println(" - A " + rewardItem.getName());
                            }
                            System.out.println("The items were dropped on the ground.\n");
                        }

                        if (this.successfullTranslationMessage != null
                                && this.successfullTranslationMessage.length() > 0) {
                            System.out.println(this.successfullTranslationMessage);
                        }

                        finishedtranslation = true;

                    } else {
                        System.out.println("The translation was not correct");
                    }
                }

            }

            else if (input.startsWith("hint") || input.startsWith("help")) {
                System.out.println("You must first find out for what the symbols stay for.");
                System.out.println("You can find literature in the chaple of the island which");
                System.out.println(" is very helpful for this task.");
                System.out.println("When you have found the meaning of the first " + Constants.STONETABLET_REQ_CORRECT
                        + " symbols,");
                System.out.println(" enter \"translation \", followed by the translation for the first "
                        + Constants.STONETABLET_REQ_CORRECT + " symbols.");
            }

            else {
                System.out.println("Input was not recognized.");
                System.out.println("Enter 'read' to read the text.");
                System.out.println("Enter 'translate' to translate the text.");
                System.out.println("To translate the text successfully, at least " + Constants.STONETABLET_REQ_CORRECT
                        + " letters");
                System.out.println(" must be correctly translated.");
                System.out.println("Enter 'close' to put " + this.getName() + " away.");
            }
            if (!finishedtranslation) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String nextcommand;
                try {
                    nextcommand = reader.readLine();
                    this.bookTranslator(game, nextcommand);
                } catch (IOException e) {
                    System.out.println("An error occured reading the item.");
                }
            }
        }
    }

    public void openTranslator(Game game) {
        if (this.translatable == true) {
            if (this.pages != null && this.pages.size() > 0) {
                Iterator<String> pageiterator = this.getPages().iterator();
                String allText = "";
                while (pageiterator.hasNext()) {
                    allText = allText.concat(pageiterator.next());
                }
                if (allText != null && allText != "" && allText.length() > 0) {
                    this.bookTranslator(game, "init");
                } else {
                    System.out.println("This item has no text to translate!");
                }
            } else {
                System.out.println("This item has no pages to translate!");
            }
        } else {
            System.out.println("This item can't be translated!");
        }
    }

    public boolean isTranslatable() {
        return translatable;
    }

}