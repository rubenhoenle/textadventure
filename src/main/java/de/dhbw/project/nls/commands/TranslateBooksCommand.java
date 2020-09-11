package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Item;

import java.util.List;

public class TranslateBooksCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "book")
    private List<String> book;

    public TranslateBooksCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        boolean foundAnyItem = false;
        boolean openedBook = false;

        if (book == null || book.size() == 0) {
            System.out.println("Please state which item you want to translate.");
            return;
        }

        String book = String.join(" ", this.book).toLowerCase();

        if (game.getCurrentRoom().getRoomItemLowerNameList().contains(book)) {
            Item roomItem = game.getItemFromCurrentRoom(book);
            foundAnyItem = true;
            if (roomItem instanceof Book) {
                openedBook = true;
                Book bookItem = (Book) roomItem;
                bookItem.openTranslator(game);
            } else {
                System.out.println("This is not a translatable item");
            }
        } else {
            for (Item loopItem : game.player.getInventory()) {
                if (loopItem.getName().equalsIgnoreCase(book)) {
                    foundAnyItem = true;
                    if (loopItem instanceof Book) {
                        openedBook = true;
                        Book bookItem = (Book) loopItem;
                        bookItem.openTranslator(game);
                    } else {
                        System.out.println("This is not a translatable item");
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
