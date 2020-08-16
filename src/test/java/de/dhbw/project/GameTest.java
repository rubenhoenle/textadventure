package de.dhbw.project;

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

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Game.class)
public class GameTest {

    Game game = PowerMockito.spy(new Game());

    @Mock
    Player player;

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
        game.player = player;
    }

    @Test
    public void test1_shouldAddItemToInventory() throws Exception {
        //before
        String itemName = "TestItem";
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        doReturn(Arrays.asList(itemName)).when(currentRoom).getRoomItemNameList();

        Item item = new Item(itemName, "TestItem", "TestState", 99);
        PowerMockito.doReturn(item).when(game, "getItemFromCurrentRoom", itemName);

        //when
        game.takeItem(itemName);

        //then
        verify(player).addItem(item);
        verify(currentRoom).removeItem(item);
        verify(out).println("You took " + item.getName() + " and added it to the inventory.");
    }

    @Test
    public void test2_shouldNotAddItemToInventory() throws Exception {
        //before
        String itemName = "NonExistingItemName";
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        doReturn("TestRoom").when(currentRoom).getName();

        //when
        game.takeItem(itemName);

        //then
        verify(out).println("No item found with name " + itemName + " in room " + currentRoom.getName());
    }

    @Test
    public void test3_getItemFromCurrentRoom() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Item("TestItem", "TestItem", "TestState", 99);
        when(currentRoom.getRoomItemList()).thenReturn(Arrays.asList(item));

        //when
        Item returnItem = Whitebox.invokeMethod(game,
                "getItemFromCurrentRoom", "TestItem");

        //then
        assertEquals(item.getName(), returnItem.getName());
    }

    @Test
    public void test4_shouldDropItem() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Item("TestItem", "TestItem", "TestState", 99);
        PowerMockito.doReturn(item).when(player, "getItem", item.getName());

        //when
        game.dropItem(item.getName());

        //then
        verify(player).getItem(item.getName());
        verify(player).removeItem(item);
        verify(currentRoom).addItem(item);
        verify(out).println("The item " + item.getName() + " was dropped in room '" + currentRoom.getName() + "'.");
    }

    @Test
    public void test5_shouldNotDropItem() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Item("TestItem", "TestItem", "TestState", 99);

        //when
        game.dropItem(item.getName());

        //then
        verify(player).getItem(item.getName());
        verify(out).println("The item " + item.getName() + " was not found in the inventory and cannot be dropped.");
    }

    @Test
    public void test6_shouldShowInventory() throws Exception {
        //before
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        when(player.getInventory()).thenReturn(Arrays.asList(item1,item2));

        //when
        Whitebox.invokeMethod(game,
                "getInventoryText");

        //then
        verify(item1).getName();
        verify(item2).getName();
        verify(item1).getStrength();
        verify(item2).getStrength();
        verify(item1).getDescription();
        verify(item2).getDescription();
        verify(out).println("------------");
    }

    @Test
    public void test7_shouldShowEmptyInventory() throws Exception {
        //before

        when(player.getInventory()).thenReturn(Arrays.asList());

        //when
        Whitebox.invokeMethod(game,
                "getInventoryText");

        //then
        verify(player).getInventory();
        verify(out).println("------------");

    }
}
