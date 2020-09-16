package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.WayState;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.ItemState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class RuinsRiddleCommand extends AutoCommand {

    private Game game;

    public RuinsRiddleCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
        if (!game.player.getRoomName().equals("ruins cellar")) {
            System.out.println("This command is not available here.");
        } else {
            if (game.getCurrentRoom().getRoomWayByName("passage").getState() == WayState.ACTIVE) {
                System.out.println("You already solved the riddle");
            } else {
                boolean running = true;
                String currentcommand = "init";
                String currentpasscode = "";
                String passcode = "54231";
                Book[] paragraphs = {

                        new Book("first paragraph", "", ItemState.INACTIVE, 0,
                                Arrays.asList("[ALFONT]herbs and trees[ALFONT]"), true),
                        new Book("second paragraph", "", ItemState.INACTIVE, 0,
                                Arrays.asList("[ALFONT]division of waters[ALFONT]"), true),
                        new Book("third paragraph", "", ItemState.INACTIVE, 0,
                                Arrays.asList("[ALFONT]land and sea[ALFONT]"), true),
                        new Book("fourth paragraph", "", ItemState.INACTIVE, 0,
                                Arrays.asList("[ALFONT]day and night[ALFONT]"), true),
                        new Book("fifth paragraph", "", ItemState.INACTIVE, 0,
                                Arrays.asList("[ALFONT]earth and heavens[ALFONT]"), true) };
                /*
                 * korrekt: Earth and heavens day and night ground and sky land and sea herbs and trees
                 */
                while (running) {
                    if (currentcommand.equals("quit") || currentcommand.equals("leave")
                            || currentcommand.equals("close")) {
                        System.out.println("You canceled the work on the riddle");
                        running = false;
                    } else if (currentcommand.equals("init") || currentcommand.equals("help")) {
                        System.out.println(
                                "You see " + paragraphs.length + " sentences and " + paragraphs.length + " buttons");
                        System.out.println(
                                "You should press the buttons in the correct order (with 'press 2', for example)");
                        System.out.println("To read a sentence, use 'read <number>'");
                        System.out.println("If you want to translate a paragraph, use 'translate <number>'");
                        System.out.println("If you need a hint, use 'hint'");
                        System.out.println("Enter 'quit' to stop working on the riddle");
                    } else if (currentcommand.startsWith("press ")) {
                        currentcommand = currentcommand.replace("press ", "");
                        int sentence = 0;
                        try {
                            sentence = Integer.parseInt(currentcommand);
                            if (sentence < 1 || sentence > paragraphs.length) {
                                System.out.println(
                                        "There is no paragraph with this number. Please use a number between 1 and "
                                                + paragraphs.length);
                            } else {
                                if (currentpasscode.contains("" + sentence)) {
                                    System.out.println("This button has been already pressed");
                                } else {
                                    System.out.println("You pressed the button " + sentence);
                                    currentpasscode = currentpasscode.concat("" + sentence);
                                    if (currentpasscode.length() == passcode.length()) {
                                        if (currentpasscode.equals(passcode)) {
                                            game.getCurrentRoom().getRoomWayByName("passage").setState(WayState.ACTIVE);
                                            game.getCurrentRoom().getRoomWayByName("passage").setHint("");
                                            ;
                                            System.out.println("Congratulations! The passage has been opened.");
                                            running = false;
                                        } else {
                                            System.out.println("Nothing happened. Please try again.");
                                            currentpasscode = "";
                                        }
                                    }
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Number was not recognized");
                            System.out.println("Please use 'press 2', for example");
                        }
                    } else if (currentcommand.startsWith("read ")) {
                        currentcommand = currentcommand.replace("read ", "");
                        int sentence = 0;
                        try {
                            sentence = Integer.parseInt(currentcommand);
                            if (sentence < 1 || sentence > paragraphs.length) {
                                System.out.println(
                                        "There is no paragraph with this number. Please use a number between 1 and "
                                                + paragraphs.length);
                            } else {
                                paragraphs[sentence - 1].openBookReader();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Number was not recognized");
                            System.out.println("Please use 'read 2', for example");
                        }

                    } else if (currentcommand.startsWith("translate ")) {
                        currentcommand = currentcommand.replace("translate ", "");
                        int sentence = 0;
                        try {
                            sentence = Integer.parseInt(currentcommand);
                            if (sentence < 1 || sentence > paragraphs.length) {
                                System.out.println(
                                        "There is no paragraph with this number. Please use a number between 1 and "
                                                + paragraphs.length);
                            } else {
                                if (paragraphs[sentence - 1].isTranslatable() == false) {
                                    System.out.println("This paragraph is already translated.");
                                } else {
                                    paragraphs[sentence - 1].openTranslator(game);
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Number was not recognized");
                            System.out.println("Please use 'translate 2', for example");
                        }
                    } else if (currentcommand.equals("hint")) {
                        System.out.println("The paragraphs describe 5 stages of the creation, according to the bible.");
                        System.out.println("Press the buttons in the correct sequence of these stages");
                    } else {
                        System.out.println("Input not recognized.");
                        System.out.println(
                                "There are " + paragraphs.length + " sentences and " + paragraphs.length + " buttons");
                        System.out.println(
                                "You should press the buttons in the correct order (with 'press 2', for example)");
                        System.out.println("To read a sentence, use 'read <number>'");
                        System.out.println("If you want to translate a paragraph, use 'translate'");
                        System.out.println("If you need a hint, use 'hint'");
                        System.out.println("Enter 'quit' to stop working on the riddle");
                    }

                    if (running) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        String nextcommand;
                        try {
                            nextcommand = reader.readLine().toLowerCase();
                            currentcommand = nextcommand;
                        } catch (IOException e) {
                            System.out.println("An error occured reading the stone tablet.");
                        }
                    }
                }
            }
        }
    }

    @Override
    public String[] getPattern() {
        String[] patterns = { "(ruins riddle|ruin riddle)" };
        return patterns;
    }

}
