package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.WayState;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.regions.ruins.StoneTablet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class RuinsRiddleCommand extends AutoCommand {

    private Game game;

    public RuinsRiddleCommand(Game game) {
        this.game = game;
    }

    @Override
    public void execute() {
    	if (!game.player.getRoomName().equals("ruins cellar")) {
    		System.out.println("This command is not available here.");
    	}
    	else 
    	{
    		if (game.getCurrentRoom().getRoomWayByName("passage").getState() == WayState.ACTIVE) 
    		{
    			System.out.println("You already solved the riddle");
    		}
    		else 
    		{
    		boolean running = true;
    		String currentcommand = "init";
    		String currentpasscode = "";
    		String passcode = "54231";
    		Book[] paragraphs = 
    			{
    					
        				new Book("1", "", ItemState.INACTIVE, 0, Arrays.asList("[ALFONT]herbs and trees[ALFONT]")),
        				new Book("2", "", ItemState.INACTIVE, 0, Arrays.asList("[ALFONT]division of water[ALFONT]")),
        				new Book("3", "", ItemState.INACTIVE, 0, Arrays.asList("[ALFONT]land and sea[ALFONT]")),
        				new Book("4", "", ItemState.INACTIVE, 0, Arrays.asList("[ALFONT]day and night[ALFONT]")),
        				new Book("5", "", ItemState.INACTIVE, 0, Arrays.asList("[ALFONT]earth and heavens[ALFONT]"))
    			};
    		/*
    		korrekt:
			Earth and heavens
			day and night
			ground and sky
			land and sea
			herbs and trees
			*/
	    		while (running) {
	    			if (currentcommand.equals("quit or leave")) {
	    				running = false;
	    			}
	    			else if (currentcommand.equals("init")||currentcommand.equals("help")) {
	    				System.out.println("You see " + paragraphs.length + " sentences and " + paragraphs.length + " buttons");
	    				System.out.println("You should press the buttons in the correct order (with 'press 2', for example)");
	    				System.out.println("To read a sentence, use 'read <number>'");
	    				System.out.println("If you want to translate a paragraph, use 'translate <number>'");
	    				System.out.println("If you need a hint, use 'hint'");
	    			}
	    			else if (currentcommand.startsWith("press ")) {
	    				currentcommand = currentcommand.replace("press ", "");
	    				int sentence = 0;
	    				try {
	    					sentence = Integer.parseInt(currentcommand);
	    					if (sentence < 1 || sentence > paragraphs.length) {
	    						System.out.println("There is no paragraph with this number. Please use a number between 1 and " + paragraphs.length);
	    					}
	    					else {
	    						if (currentpasscode.contains("" + sentence)) {
	    							System.out.println("This button has been already pressed");
	    						}
	    						else {
	    							System.out.println("You pressed the button " + sentence);
	    							currentpasscode = currentpasscode.concat("" + sentence);
	    							if (currentpasscode.length() == passcode.length()) {
	    								if (currentpasscode.equals(passcode)) {
	    									game.getCurrentRoom().getRoomWayByName("passage").setState(WayState.ACTIVE);
	    									game.getCurrentRoom().getRoomWayByName("passage").setHint("");;
	    									System.out.println("Congratulations! The passage has been opened.");
	    									running = false;
	    								}
	    								else {
	    									System.out.println("Nothing happened. Please try again.");
	    									currentpasscode = "";
	    								}
	    							}
	    						}
	    					}
	    				}
	    				catch (NumberFormatException e) {
	    					System.out.println("Number was not recognized");
	    					System.out.println("Please use 'press 2', for example");
	    				}
	    			}
	    			else if (currentcommand.startsWith("read ")) {
	    				currentcommand = currentcommand.replace("read ", "");
	    				int sentence = 0;
	    				try {
	    					sentence = Integer.parseInt(currentcommand);
	    					if (sentence < 1 || sentence > paragraphs.length) {
	    						System.out.println("There is no paragraph with this number. Please use a number between 1 and " + paragraphs.length);
	    					}
	    					else {
	    						if (paragraphs[sentence-1].getItemstate() == ItemState.ACTIVE) {
	    						paragraphs[sentence-1].printPage(1,true);	
	    						}
	    						else {
	    							paragraphs[sentence-1].printPage(1,false);	
	    						}
	    					}
	    				}
	    				catch (NumberFormatException e) {
	    					System.out.println("Number was not recognized");
	    					System.out.println("Please use 'read 2', for example");
	    				}
	    				
	    			}
	    			else if (currentcommand.startsWith("translate ")) {
	    				currentcommand = currentcommand.replace("translate ", "");
	    				int sentence = 0;
	    				try {
	    					sentence = Integer.parseInt(currentcommand);
	    					if (sentence < 1 || sentence > paragraphs.length) {
	    						System.out.println("There is no paragraph with this number. Please use a number between 1 and " + paragraphs.length);
	    					}
	    					else {
	    						if (paragraphs[sentence-1].getItemstate() == ItemState.ACTIVE) {
	    							System.out.println("This paragraph is already translated");
	    						}
	    						else {
	    							paragraphs[sentence-1].printPage(1,false);
	    							System.out.println("Please enter the translation");
	    							String correctstring = paragraphs[sentence-1].getPages().get(0).replace("[ALFONT]", "");

	    		                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    		                    String answer;
	    		                    try {
	    		                        answer = reader.readLine();
	    		                        if (correctstring.toLowerCase().equals(answer.toLowerCase())) {
	    		                        	paragraphs[sentence-1].setItemstate(ItemState.ACTIVE);
	    		                        	System.out.println("Correct!");
	    		                        }
	    		                        else {
	    		                        	System.out.println("This wasn't the correct translation.");
	    		                        }
	    		                    } catch (IOException e) {
	    		                        System.out.println("An error occured reading the stone tablet.");
	    		                    }
	    						}
	    					}
	    				}
	    				catch (NumberFormatException e) {
	    					System.out.println("Number was not recognized");
	    					System.out.println("Please use 'translate 2', for example");
	    				}
	    			}
	    			else if (currentcommand.equals("hint")) {
	    				System.out.println("The paragraphs describe 5 stages of the creation, according to the bible.");
	    				System.out.println("Press the buttons in the correct sequence of these stages");
	    			}
	    			else {
	    				System.out.println("Input not recognized.");
	    				System.out.println("There are " + paragraphs.length + "sentences and " + paragraphs.length + " buttons");
	    				System.out.println("You should press the buttons in the correct order (with 'press 2', for example)");
	    				System.out.println("To read a sentence, use 'read <number>'");
	    				System.out.println("If you want to translate a paragraph, use 'translate'");
	    				System.out.println("If you need a hint, use 'hint'");
	    			}
	    			
	    			if (running) {
	                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	                    String nextcommand;
	                    try {
	                        nextcommand = reader.readLine();
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
