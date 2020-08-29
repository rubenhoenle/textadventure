package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.item.Item;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(InventoryCommand.class)
public class InventoryCommandTest {

    Game game = new Game();

    InventoryCommand inventoryCommand = PowerMockito.spy(new InventoryCommand(game));

    @Mock
    Player player;

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
        game.player = player;
    }

    //TODO: removed by Ruben... because: TableList is used for output now and cannot be tested such easily...
    /*@Test
    public void test1_shouldShowInventory() throws Exception {
        //before
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(game.player.getInventory()).thenReturn(Arrays.asList(item1,item2));

        //when
        inventoryCommand.execute();

        //then
        verify(item1).getName();
        verify(item2).getName();
        verify(item1).getStrength();
        verify(item2).getStrength();
        verify(item1).getDescription();
        verify(item2).getDescription();
        verify(out).println("---------------");
    }

    @Test
    public void test2_shouldShowEmptyInventory() throws Exception {
        //before
        when(player.getInventory()).thenReturn(Arrays.asList());

        //when
        inventoryCommand.execute();

        //then
        verify(player).getInventory();
        verify(out).println("---------------");
    }*/
}
