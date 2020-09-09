package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.Clothing;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.nls.DataStorage;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(EquipCommand.class)
public class EquipmentTest {

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
    public void test1_shouldEquipOnlyFirstItemFromRoom() throws Exception {
        //before
        String itemName1 = "TestItem1";
        String itemName2 = "TestItem2";
        
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        doReturn(Arrays.asList(itemName1.toLowerCase(), itemName2.toLowerCase())).when(currentRoom).getRoomItemLowerNameList();

        Item item1 = new Clothing(itemName1, "TestItem", ItemState.ACTIVE, 0, EquipmentType.HEAD, 0);
        Item item2 = new Clothing(itemName2, "TestItem", ItemState.ACTIVE, 0, EquipmentType.HEAD, 0);

        PowerMockito.doReturn(true).when(player, "addEquipment", item1);
        PowerMockito.doReturn(false).when(player, "addEquipment", item2);
        
        PowerMockito.doReturn(item1).when(game, "getItemFromCurrentRoom", itemName1.toLowerCase());
        PowerMockito.doReturn(item2).when(game, "getItemFromCurrentRoom", itemName2.toLowerCase());

        EquipCommand equipCommand = new EquipCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", itemName1+",");
        data.add("item", itemName2);
        equipCommand.setData(data);
        
        //when
        equipCommand.execute();

        //then
        verify(player).addEquipment(item1);
        verify(game).incTurn();
        verify(currentRoom).removeItem(item1);
        verify(out).println("You added \'" + item1.getName() + "\' to your equipment.");
        verify(out).println("The section " + item1.getEquipmentType().getDescription() + " is already taken.");
    }
    
    @Test
    public void test2_shouldEquipItemFromInventory() throws Exception {
        //before
        String itemName = "test2";
        Room currentRoom = mock(Room.class);
        PowerMockito.doReturn(currentRoom).when(game, "getCurrentRoom");

        Item item = new Clothing(itemName, "TestItem", ItemState.ACTIVE, 0, EquipmentType.WEAPON,0);
        player.addItem(item);

        PowerMockito.doReturn(Arrays.asList(item)).when(player, "getInventory");
        PowerMockito.doReturn(item).when(player, "getItem", item.getName());
        PowerMockito.doReturn(true).when(player, "addEquipment", item);

        EquipCommand equipCommand = new EquipCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", itemName);
        equipCommand.setData(data);
        
        //when
        equipCommand.execute();

        //then
        verify(game).incTurn();
        verify(player).addEquipment(item);
        verify(player).removeItem(item);
        verify(out).println("You added \'" + item.getName() + "\' to your equipment.");
    }

    @Test
    public void test3_shouldStripOffItem() throws Exception {
    	//before
        String itemName1 = "TestItem1";
        String itemName2 = "TestItem2";

        Item item1 = new Clothing(itemName1, "TestItem", ItemState.ACTIVE, 0, EquipmentType.HEAD,0);
        Item item2 = new Clothing(itemName2, "TestItem", ItemState.ACTIVE, 0, EquipmentType.HEAD,0);

        PowerMockito.doReturn(item1).when(player, "getItemFromEquipment", item1.getName());
        PowerMockito.doReturn(null).when(player, "getItemFromEquipment", item2.getName());

        StripOffCommand stripOffCommand = new StripOffCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", itemName1+",");
        data.add("item", itemName2);
        stripOffCommand.setData(data);
        when(player.getInventorySpace()).thenReturn(10);
        when(player.getCurrentInventorySpace()).thenReturn(3);
        Player p = new Player();
        int inventoryCount = 2;
        p.setInventorySpace(3);
        p.addItem(item1);

        //when
        stripOffCommand.execute();

        //then
        verify(game).incTurn();
        verify(player).removeEquipment(item1);
        verify(player).addItem(item1);
        assertEquals(p.getCurrentInventorySpace(),inventoryCount);
        verify(out).println("The item \'" + item1.getName() + "\' was shifted to inventory.");
        verify(out).println("The item \'" + item2.getName() + "\' was not found in equipment and cannot be stripped off.");
    }

    @Test
    public void test4_shouldNotStripOffItem() throws Exception {
        //before
        String itemName1 = "TestItem1";
        String itemName2 = "TestItem2";

        Item item1 = new Clothing(itemName1, "TestItem", ItemState.ACTIVE, 0, EquipmentType.HEAD,0);
        Item item2 = new Clothing(itemName2, "TestItem", ItemState.ACTIVE, 0, EquipmentType.HEAD,0);

        PowerMockito.doReturn(item1).when(player, "getItemFromEquipment", item1.getName());
        PowerMockito.doReturn(null).when(player, "getItemFromEquipment", item2.getName());

        StripOffCommand stripOffCommand = new StripOffCommand(game);
        DataStorage data = new DataStorage();
        data.add("item", itemName1+",");
        data.add("item", itemName2);
        stripOffCommand.setData(data);
        when(player.getInventorySpace()).thenReturn(10);
        when(player.getCurrentInventorySpace()).thenReturn(0);

        //when
        stripOffCommand.execute();

        //then
        verify(out).println("You can not strip off the \'" + item1.getName()
                + "\' because you will not have enough inventory space afterwards.");
        verify(out).println("The item \'" + item2.getName() + "\' was not found in equipment and cannot be stripped off.");
    }

    //TODO: removed by Ruben... because: TableList is used for output now and cannot be tested such easily...
    /*@Test
    public void test4_showEquipment() throws Exception {
    	//before
    	Item item1 = new Clothing("testItem", "test", State.ACTIVE, 0, EquipmentType.LOWER_BODY);
    	Item item2 = new Clothing("testItem2", "test2", State.ACTIVE, 5, EquipmentType.UPPER_BODY);
        ShowEquipmentCommand showEquipmentCommand = new ShowEquipmentCommand(game);

    	when(game.player.getEquipment()).thenReturn(Arrays.asList(item1, item2));
    	
        //when
        showEquipmentCommand.execute();

        //then
        verify(out).println("---Equipment---");
        verify(out).print(item1.getName() + " - " + item1.getDescription());
        verify(out).print(" (Section: " + item1.getEquipmentType().getDescription());
        
        verify(out).print(item2.getName() + " - " + item2.getDescription());
        verify(out).print(" (Section: " + item1.getEquipmentType().getDescription());
        verify(out).print(", Strength: " + item2.getStrength());
        verify(out).println("---------------");
    }*/
}
