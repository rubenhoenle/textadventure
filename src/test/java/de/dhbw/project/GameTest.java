package de.dhbw.project;

import de.dhbw.project.character.*;
import de.dhbw.project.character.Character;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.Resource;
import de.dhbw.project.nls.DataStorage;
import de.dhbw.project.nls.commands.DropCommand;
import de.dhbw.project.nls.commands.TakeCommand;
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
import java.util.List;

import static org.junit.Assert.*;
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

        Item item = new Resource(itemName, "TestItem", ItemState.NOT_USABLE, 99);
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
        verify(out).println("You took \'" + item.getName() + "\' and added it to the inventory.");
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
        verify(out).println("No item found with name \'" + itemName + "\' in room " + currentRoom.getName());
    }

    @Test
    public void test3_getItemFromCurrentRoom() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Resource("TestItem", "TestItem", ItemState.NOT_USABLE, 99);
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

        Item item = new Resource("TestItem", "TestItem", ItemState.NOT_USABLE, 99);
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
        verify(out).println("The item \'" + item.getName() + "\' was dropped in room \'" + currentRoom.getName() + "\'.");
    }

    @Test
    public void test5_shouldNotDropItem() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Resource("TestItem", "TestItem", ItemState.NOT_USABLE, 99);

        DropCommand dropCommand = new DropCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", item.getName());
        dropCommand.setData(data);

        //when
        dropCommand.execute();

        //then
        verify(player).getItem(item.getName());
        verify(out).println("The item \'" + item.getName() + "\' was not found in inventory or equipment and cannot be dropped.");
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

    @Test
    public void test8_shouldGetInteractiveObjectFromCurrentRoom() throws Exception {
        //before
        Room currentRoom = mock(Room.class);
        InteractiveObject io = mock(InteractiveObject.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");
        PowerMockito.when(currentRoom.getRoomInteractiveObjectsList()).thenReturn(Arrays.asList(io));
        PowerMockito.when(io.getName()).thenReturn("interactiveObject");

        //when
        InteractiveObject rio = game.getInteractiveObjectFromCurrentRoom("interactiveObject");

        //then
        assertNotNull(rio);
        assertEquals(rio,io);
    }

    @Test
    public void test9_incTurn() throws Exception {
        //before
        Whitebox.setInternalState(game, "turn", 10);
        //when
        game.incTurn();
        //then
        assertEquals(game.getTurn(), 11);
    }

    @Test
    public void test10_getTurn() throws Exception {
        //before
        Whitebox.setInternalState(game, "turn", 10);
        //when
        int res = game.getTurn();
        //then
        assertEquals(res, 10);
    }

    @Test
    public void test11_roamAllRoamingEnemies() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        List<Room> rooms = Arrays.asList(r);
        Whitebox.setInternalState(game, List.class, rooms);
        RoamingEnemy e = mock(RoamingEnemy.class);
        List<RoamingEnemy> el = Arrays.asList(e);
        PowerMockito.doReturn(el).when(r, "getRoamingEnemyList");
        PowerMockito.doReturn(false).when(e, "isKilled");
        PowerMockito.doReturn("foo").when(r, "getName");
        PowerMockito.doReturn("bar").when(e, "getNextRoom","foo");
        PowerMockito.doReturn(r2).when(game, "getRoom", "bar");
        //when
        game.roamAllRoamingEnemies();
        //then
        verify(e).isKilled();
        verify(e).getNextRoom("foo");
        verify(r).removeRoamingEnemy(e);
        verify(r2).addRoamingEnemy(e);

    }

    @Test
    public void test12_roamAllRoamingEnemies() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        List<Room> rooms = Arrays.asList(r);
        Whitebox.setInternalState(game, List.class, rooms);
        RoamingEnemy e = mock(RoamingEnemy.class);
        List<RoamingEnemy> el = Arrays.asList(e);
        PowerMockito.doReturn(el).when(r, "getRoamingEnemyList");
        PowerMockito.doReturn(true).when(e, "isKilled");
        //when
        game.roamAllRoamingEnemies();
        //then
        verify(e).isKilled();
        verify(e,never()).getNextRoom("foo");
        verify(r,never()).removeRoamingEnemy(e);
        verify(r2,never()).addRoamingEnemy(e);
    }
}
