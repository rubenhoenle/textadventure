package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Item;
import de.dhbw.project.regions.ruins.StoneTablet;

import java.util.List;

public class TranslateStoneTabletsCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "book")
    private List<String> book;

    public TranslateStoneTabletsCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        boolean foundAnyItem = false;
        boolean openedBook = false;

        if (book == null || book.size() == 0) {
            System.out.println("Please state which stone tablet you want to translate.");
            return;
        }

        String book = String.join(" ", this.book).toLowerCase();

        if (game.getCurrentRoom().getRoomItemLowerNameList().contains(book)) {
            Item roomItem = game.getItemFromCurrentRoom(book);
            foundAnyItem = true;
            if (roomItem instanceof Book) {
                if (roomItem.getName().contains("stone tablet") && !roomItem.getName().contains("translated")) {
                    openedBook = true;
                    Book bookItem = (Book) roomItem;
                    StoneTablet.openTranslator(game, bookItem);
                } else {
                    System.out.println("This item can't be translated");
                }
            } else {
                System.out.println("This is not a translatable stone tablet");
            }
        } else {
            for (Item loopItem : game.player.getInventory()) {
                if (loopItem.getName().equalsIgnoreCase(book)) {
                    foundAnyItem = true;
                    if (loopItem instanceof Book) {
                        if (loopItem.getName().contains("stone tablet") && !loopItem.getName().contains("translated")) {
                            openedBook = true;
                            Book bookItem = (Book) loopItem;
                            StoneTablet.openTranslator(game, bookItem);
                        } else {
                            System.out.println("This item can't be translated");
                        }
                    } else {
                        System.out.println("This is not a translatable stone tablet");
                    }
                }
            }
        }

        if (openedBook == false && foundAnyItem == false) {
            System.out.println("Found no item with this name");
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "translate <book>+", "translate" };
        return patterns;
    }

}
