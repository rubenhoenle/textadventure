package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;
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

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(UseCommand.class)
public class UseCommandTest {

    @Mock
    Game game;

    UseCommand command = PowerMockito.spy(new UseCommand(game));

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
    public void test1_shouldUseInteractiveObject() throws Exception {
        //before
        Whitebox.setInternalState(command, "item", Arrays.asList("item"));
        Whitebox.setInternalState(command, "interactiveObject", Arrays.asList("interactiveObject"));

        Room r = mock(Room.class);
        Room roomBack = mock(Room.class);
        Item i = mock(Item.class);
        Way w = mock(Way.class);
        InteractiveObject io = mock(InteractiveObject.class);
        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getRoomInteractiveObjectsLowerNameList()).thenReturn(Arrays.asList("interactiveobject"));
        when(player.getItem("item")).thenReturn(i);
        when(r.getRoomInteractiveObjectByName("interactiveobject")).thenReturn(io);
        when(io.getRequiredItem()).thenReturn(i);
        when(io.getRequiredItem().getName()).thenReturn("item");
        when(io.isRemoveRequiredItem()).thenReturn(true);
        when(i.getName()).thenReturn("item");
        when(io.getWayName()).thenReturn("wayName");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getName()).thenReturn("wayName");
        when(w.getTo()).thenReturn("roomBack");
        when(game.getRoom("roomBack")).thenReturn(roomBack);

        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(w).setState(WayState.ACTIVE);
        verify(player).removeItem(i);
        verify(out).println("The way " + w.getName() + " in the direction \'" + w.getDirection() + "\' is now walkable!");
    }

    @Test
    public void test2_shouldUseInteractiveObject() throws Exception {
        //before
        Whitebox.setInternalState(command, "item", Arrays.asList("item"));
        Whitebox.setInternalState(command, "interactiveObject", Arrays.asList("interactiveObject"));

        Room r = mock(Room.class);
        Item i = mock(Item.class);
        Way w = mock(Way.class);
        InteractiveObject io = mock(InteractiveObject.class);
        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getRoomInteractiveObjectsLowerNameList()).thenReturn(Arrays.asList("interactiveobject"));
        when(player.getItem("item")).thenReturn(i);
        when(r.getRoomInteractiveObjectByName("interactiveobject")).thenReturn(io);
        when(io.getRequiredItem()).thenReturn(i);
        when(io.getRequiredItem().getName()).thenReturn("item");
        when(io.isRemoveRequiredItem()).thenReturn(false);
        when(i.getName()).thenReturn("item");
        when(io.getWayName()).thenReturn("wayName");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getName()).thenReturn("wayName");
        when(game.getRoom(any())).thenReturn(r);
        when(w.getTo()).thenReturn("way1");

        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(w).setState(WayState.ACTIVE);
        verify(player,times(0)).removeItem(i);
        verify(out).println("The way " + w.getName() + " in the direction \'" + w.getDirection() + "\' is now walkable!");
    }

    @Test
    public void test3_shouldUseInteractiveObjectGetReward() throws Exception {
        //before
        Whitebox.setInternalState(command, "item", Arrays.asList("item"));
        Whitebox.setInternalState(command, "interactiveObject", Arrays.asList("interactiveObject"));

        Room r = mock(Room.class);
        Item i = mock(Item.class);
        Way w = mock(Way.class);
        ItemList rewardIL = mock(ItemList.class);
        InteractiveObject io = mock(InteractiveObject.class);
        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getRoomInteractiveObjectsLowerNameList()).thenReturn(Arrays.asList("interactiveobject"));
        when(player.getItem("item")).thenReturn(i);
        when(r.getRoomInteractiveObjectByName("interactiveobject")).thenReturn(io);
        when(io.getRequiredItem()).thenReturn(i);
        when(io.getRequiredItem().getName()).thenReturn("item");
        when(io.isRemoveRequiredItem()).thenReturn(false);
        when(i.getName()).thenReturn("item");
        when(io.getWayName()).thenReturn("");
        when(io.getReward()).thenReturn(rewardIL);
        when(rewardIL.getAllItemList()).thenReturn(Arrays.asList(i));

        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(player,times(0)).removeItem(i);
        verify(player).addItem(i);
        verify(out).println("\'" + i.getName() + "\' was added to your inventory!");
    }

}
