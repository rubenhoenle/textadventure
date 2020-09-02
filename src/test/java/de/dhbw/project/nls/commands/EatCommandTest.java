package de.dhbw.project.nls.commands;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Room;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.Food;
import de.dhbw.project.item.Tool;

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(EatCommand.class)
public class EatCommandTest {
	
	@Mock
    Game game;

    EatCommand command = PowerMockito.spy(new EatCommand(game));

    @Mock
    Player player;

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
        game.player = player;
        Whitebox.setInternalState(command, Game.class, game);
    }

    @Test
    public void test1_shouldEatItem() throws Exception {
    	//before
    	String itemName = "food";
    	Whitebox.setInternalState(command, List.class, Arrays.asList(itemName));
    	
    	Food food = new Food(itemName, "desc", ItemState.NOT_USABLE, 5);
    	Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
    	when(game.getCurrentRoom().getRoomItemNameList()).thenReturn(Arrays.asList(itemName));
    	when(game.getItemFromCurrentRoom(itemName)).thenReturn(food);
    	
    	//when
        command.execute();

        //then
        verify(game.player).addHealth(food.getStrength());
        verify(out).println("The " + itemName + " tasted delicious!");
        verify(r).removeItem(food);
    }

    @Test
    public void test2_shouldNotEatItem() throws Exception {
    	//before
    	String itemName = "tool";
    	Whitebox.setInternalState(command, List.class, Arrays.asList(itemName));
    	
    	Tool tool = new Tool(itemName, "desc", ItemState.NOT_USABLE, 5);
    	Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
    	when(game.getCurrentRoom().getRoomItemNameList()).thenReturn(Arrays.asList(itemName));
    	when(game.getItemFromCurrentRoom(itemName)).thenReturn(tool);
    	
    	//when
        command.execute();

        //then
        verify(out).println("You really want to eat a " + itemName + "?!");
    }
}
