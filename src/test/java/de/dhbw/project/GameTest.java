package de.dhbw.project;

import de.dhbw.project.character.Character;
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

import de.dhbw.project.nls.DataStorage;
import de.dhbw.project.nls.commands.DropCommand;
import de.dhbw.project.nls.commands.TakeCommand;

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

        Item item = new Item(itemName, "TestItem", State.NOT_USABLE, 99);
        PowerMockito.doReturn(item).when(game, "getItemFromCurrentRoom", itemName);

        TakeCommand takeCommand = new TakeCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", itemName);
        takeCommand.setData(data);
        
        //when
        takeCommand.execute();

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
        
        TakeCommand takeCommand = new TakeCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", itemName);
        takeCommand.setData(data);

        //when
        takeCommand.execute();

        //then
        verify(out).println("No item found with name " + itemName + " in room " + currentRoom.getName());
    }

    @Test
    public void test3_getItemFromCurrentRoom() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Item("TestItem", "TestItem", State.NOT_USABLE, 99);
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

        Item item = new Item("TestItem", "TestItem", State.NOT_USABLE, 99);
        PowerMockito.doReturn(item).when(player, "getItem", item.getName());

        DropCommand dropCommand = new DropCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", item.getName());
        dropCommand.setData(data);

        //when
        dropCommand.execute();
        
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

        Item item = new Item("TestItem", "TestItem", State.NOT_USABLE, 99);

        DropCommand dropCommand = new DropCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", item.getName());
        dropCommand.setData(data);

        //when
        dropCommand.execute();

        //then
        verify(player).getItem(item.getName());
        verify(out).println("The item " + item.getName() + " was not found in inventory or equipment and cannot be dropped.");
    }

    @Test
    public void test6_shouldGetCharacterFromCurrentRoom() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");
        Character c = new Character();
        c.setName("foo");
        PowerMockito.when(currentRoom.getCharacterList()).thenReturn(Arrays.asList(c));

        //when
        Character r = game.getCharacterFromCurrentRoom("foo");

        //then
        assertEquals(r, c);
    }

    @Test
    public void test7_shouldGetNoCharacterFromCurrentRoom() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");
        Character c = new Character();
        c.setName("bar");
        PowerMockito.when(currentRoom.getCharacterList()).thenReturn(Arrays.asList(c));

        //when
        Character r = game.getCharacterFromCurrentRoom("foo");

        //then
        assertNull(r);
    }
}
