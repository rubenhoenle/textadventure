package de.dhbw.project.regions.ruins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import de.dhbw.project.Constants;
import de.dhbw.project.Game;
import de.dhbw.project.Room;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;

public final class StoneTablet {

    public static void openTranslator(Game game, Book bookItem) {
        if (bookItem.getName().contains("stone tablet")
                && (!(bookItem.getName().contains("translated") || bookItem.getName().contains("translation")))) {
            StoneTablet.bookReader(game, bookItem, "init");
        } else {
            System.out.println("This item can't be translated!");
        }
    }

    private static void bookReader(Game game, Book bookItem, String input) {
        if (input.equals("exit") || input.equals("stop") || input.equals("close") || input.equals("put away")) {
            System.out.println("Canceled the translation of the " + bookItem.getName());
        } else {
            // for stopping the translation screen when translation is complete
            boolean finishedtranslation = false;

            if (input.equals("init") || input.equals("help")) {
                System.out.println("You are trying to translate the " + bookItem.getName() + ".");
                System.out.println("Enter 'read' to read the tablet");
                System.out.println("Enter 'translation <translation>' to translate the tablet");
                System.out.println(" to translate the tablet successfully, the first "
                        + Constants.STONETABLET_REQ_CORRECT + " letters");
                System.out.println(" must be correctly translated");
                System.out.println(" use for example 'translation the quick brown fox jumps over the lazy dog'");
                System.out.println("Enter 'close' to put " + bookItem.getName() + " away.");

            } else if (input.startsWith("read")) {
                bookItem.openBookReader();
            } else if (input.startsWith("translation ")) {
                List<String> pages = bookItem.getPages();
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
                        Iterator<String> it = bookItem.getPages().iterator();
                        List<String> newpages = new ArrayList<String>();
                        while (it.hasNext()) {
                            newpages.add(it.next());
                        }
                        for (int i = 0; i < newpages.size(); i++) {
                            newpages.set(i, newpages.get(i).replace("[ALFONT]", ""));
                        }
                        game.player.addItem(new Book("translation of " + bookItem.getName(), "", ItemState.NOT_USABLE,
                                0, newpages));
                        bookItem.setName("translated " + bookItem.getName());
                        System.out.println(
                                "Correct! You wrote down the translation in an extra book. You can find it now in your inventory.");
                        finishedtranslation = true;

                        // check all items in the game: if other book is also translated, grant player ruin knowledge
                        boolean translatedchiseled = false;
                        boolean translateddecorated = false;

                        for (Room loopRaum : game.getRooms()) {
                            if (loopRaum.getRoomItemList() != null) {
                                for (Item loopItem : loopRaum.getRoomItemList()) {
                                    if (loopItem.getName().contains("chiseled stone tablet")
                                            && loopItem.getName().contains("translated")) {
                                        translatedchiseled = true;
                                    }
                                    if (loopItem.getName().contains("decorated stone tablet")
                                            && loopItem.getName().contains("translated")) {
                                        translateddecorated = true;
                                    }
                                }
                            }
                        }

                        for (Item loopItem : game.player.getInventory()) {
                            if (loopItem.getName().contains("chiseled stone tablet")
                                    && loopItem.getName().contains("translated")) {
                                translatedchiseled = true;
                            }
                            if (loopItem.getName().contains("decorated stone tablet")
                                    && loopItem.getName().contains("translated")) {
                                translateddecorated = true;
                            }
                        }

                        if (translatedchiseled && translateddecorated) {
                            System.out.println("You sucessfully translated both stone tablets!");
                            System.out.println(
                                    "You concluded that the treasure must be hidden by stone beneath the castle.");
                            System.out.println("Do not forget to 'use' your knowledge there");
                            game.player.addItem(new Book("treasureknowledge", "desc",
                                    de.dhbw.project.item.ItemState.NOT_USABLE, 0, Arrays.asList(
                                            "You concluded that the treasure is hidden beneath the ruin, somehow behind stone. \n\nYou must now find the described place.")));

                        }

                    } else {
                        System.out.println("The translation was not correct");
                    }
                }

            } else {
                System.out.println("Input was not recognized.");
                System.out.println("Enter 'read' to read the tablet");
                System.out.println("Enter 'translate' to translate the tablet");
                System.out.println(" to translate the tablet successfully, at least "
                        + Constants.STONETABLET_REQ_CORRECT + " letters");
                System.out.println(" must be correctly translated");
                System.out.println("Enter 'close' to put " + bookItem.getName() + " away.");
            }
            if (!finishedtranslation) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String nextcommand;
                try {
                    nextcommand = reader.readLine();
                    StoneTablet.bookReader(game, bookItem, nextcommand);
                } catch (IOException e) {
                    System.out.println("An error occured reading the stone tablet.");
                }
            }
        }
    }

    // List<String> pages = bookItem.getPages();

}
