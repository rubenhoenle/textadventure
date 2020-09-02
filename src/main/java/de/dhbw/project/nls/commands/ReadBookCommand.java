package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadBookCommand extends AutoCommand {

    private Game game;

    @PatternName(key = "book")
    private List<String> book;

    public ReadBookCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {

        boolean foundAnyItem = false;
        boolean openedBook = false;

        if (book == null || book.size() == 0) {
            System.out.println("Please state which book you want to read");
            return;
        }

        String book = String.join(" ", this.book);

        if (game.getCurrentRoom().getRoomItemNameList().contains(book)) {
            Item roomItem = game.getItemFromCurrentRoom(book);
            foundAnyItem = true;
            if (roomItem instanceof Book) {
                openedBook = true;
                Book bookItem = (Book) roomItem;
                bookItem.openBookReader();
            } else {
                System.out.println("This is not a book");
            }
        } else {
            for (Item loopItem : game.player.getInventory()) {
                if (loopItem.getName().equals(book)) {
                    foundAnyItem = true;
                    if (loopItem instanceof Book) {
                        openedBook = true;
                        Book bookItem = (Book) loopItem;
                        bookItem.openBookReader();
                    } else {
                        System.out.println("This is not a book");
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
        String[] patterns = { "(read|open) <book>+", "(read|open)" };
        return patterns;
    }

}
