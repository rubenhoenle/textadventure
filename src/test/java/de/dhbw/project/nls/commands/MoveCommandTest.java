package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
import de.dhbw.project.item.Clothing;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.LampState;
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
@PrepareForTest(MoveCommand.class)
public class MoveCommandTest {

    @Mock
    Game game;

    MoveCommand command = PowerMockito.spy(new MoveCommand(game));

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
    public void test1_shouldMove_RoomIsNotDark() throws Exception {
        //before
        String direction = "west";
        String targetRoom = "to";
        Whitebox.setInternalState(command, String.class, direction);
        Way w = new Way("way", "description", direction, "from", targetRoom, WayState.ACTIVE, "hint");
        Room r = mock(Room.class);
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        doReturn(r).when(game).getCurrentRoom();
        when(r.getDescription()).thenReturn("description");
        when(game.getRoom(targetRoom)).thenReturn(r);
        when(r.isDark()).thenReturn(false);


        //when
        command.execute();

        //then
        verify(out).println ("You're taking the \'" + w.getName() + "\' " + direction + ". ");
        verify(game.player).setRoomName(w.getTo());
        verify(game.player).isAttacked(game);
        verify(out).println(game.getCurrentRoom());
        verify(out).println(game.getCurrentRoom().getDescription());
    }

    @Test
    public void test2_shouldNotMove_RoomIsDark_PlayerHasNoLamp() throws Exception {
        //before
        String direction = "west";
        String targetRoom = "to";
        Whitebox.setInternalState(command, String.class, direction);
        Way w = new Way("way", "description", direction, "from", targetRoom, WayState.ACTIVE, "hint");
        Room r = mock(Room.class);
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        doReturn(r).when(game).getCurrentRoom();
        when(r.getDescription()).thenReturn("description");
        when(game.player.getLampState()).thenReturn(LampState.HAS_NO_LAMP);
        when(game.getRoom(targetRoom)).thenReturn(r);
        when(r.isDark()).thenReturn(true);


        //when
        command.execute();

        //then
        verify(out).println ("It's dark in there. You can't move in this direction without a lamp.");
    }

    @Test
    public void test3_shouldNotMove_RoomIsDark_PlayerHasLampButItsOff() throws Exception {
        //before
        String direction = "west";
        String targetRoom = "to";
        Whitebox.setInternalState(command, String.class, direction);
        Way w = new Way("way", "description", direction, "from", targetRoom, WayState.ACTIVE, "hint");
        Room r = mock(Room.class);
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        doReturn(r).when(game).getCurrentRoom();
        when(r.getDescription()).thenReturn("description");
        when(game.player.getLampState()).thenReturn(LampState.OFF);
        when(game.getRoom(targetRoom)).thenReturn(r);
        when(r.isDark()).thenReturn(true);


        //when
        command.execute();

        //then
        verify(out).println ("It's dark in there. You have to switch your lamp on first to move in this direction.");
    }

    @Test
    public void test4_shouldMove_RoomIsDark_PlayerHasLampAndItsOn() throws Exception {
        //before
        String direction = "west";
        String targetRoom = "to";
        Whitebox.setInternalState(command, String.class, direction);
        Way w = new Way("way", "description", direction, "from", targetRoom, WayState.ACTIVE, "hint");
        Room r = mock(Room.class);
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        doReturn(r).when(game).getCurrentRoom();
        when(r.getDescription()).thenReturn("description");
        when(game.player.getLampState()).thenReturn(LampState.ON);
        when(game.getRoom(targetRoom)).thenReturn(r);
        when(r.isDark()).thenReturn(true);


        //when
        command.execute();

        //then
        verify(out).println ("You're taking the \'" + w.getName() + "\' " + direction + ". ");
        verify(game.player).setRoomName(w.getTo());
        verify(game.player).isAttacked(game);
        verify(out).println(game.getCurrentRoom());
        verify(out).println(game.getCurrentRoom().getDescription());
        verify(out).println("It's dark in there. But you have a lamp and it's switched on, so that's no problem for you.");
    }

    @Test
    public void test5_shouldMove_RoomIsNotDark_PlayerHasLampAndItsOn() throws Exception {
        //before
        String direction = "west";
        String targetRoom = "to";
        Whitebox.setInternalState(command, String.class, direction);

        Way w = new Way("way", "description", direction, "from", "to", WayState.ACTIVE, "hint", "condition");

        Room r = mock(Room.class);
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        doReturn(r).when(game).getCurrentRoom();
        when(r.getDescription()).thenReturn("description");
        when(game.player.getLampState()).thenReturn(LampState.ON);
        when(game.getRoom(targetRoom)).thenReturn(r);
        when(r.isDark()).thenReturn(false);


        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(out).println ("You're taking the \'" + w.getName() + "\' " + direction + ". ");
        verify(game.player).setRoomName(w.getTo());
        verify(game.player).isAttacked(game);
        verify(out).println(game.getCurrentRoom());
        verify(out).println(game.getCurrentRoom().getDescription());
        verify(out).println("It's bright enough here to see something without a lamp. Don't forget to turn it off!");
    }



    @Test
    public void test6_shouldBeBlocked() throws Exception {
        //before
        String direction = "west";
        Whitebox.setInternalState(command, String.class, direction);

        Way w = new Way("way", "description", direction, "from", "to", WayState.BLOCKED, "hint", "condition");
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);

        //when
        command.execute();

        //then
        verify(out).println ("The way is blocked!" + (w.getHint() != null ? "Hint: " + w.getHint() : ""));
    }

    @Test
    public void test7_shouldBeConditionalFalse() throws Exception {
        //before
        String direction = "west";
        Whitebox.setInternalState(command, String.class, direction);

        Way w = new Way("way", "description", direction, "from", "to", WayState.NEED_EQUIPMENT, "hint", "condition");
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);

        //when
        command.execute();

        //then
        verify(out).print("You can't go that way.");
        verify(out).println(" Hint: " + w.getHint());
    }

    @Test
    public void test8_shouldBeConditionalTrue() throws Exception {
        //before
        String direction = "west";
        Whitebox.setInternalState(command, String.class, direction);

        Way w = new Way("way", "description", direction, "from", "to", WayState.NEED_EQUIPMENT, "hint", "condition");
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        when(game.player.getEquipment()).thenReturn(Arrays.asList(new Clothing("condition", "", ItemState.NOT_USABLE, 0, EquipmentType.HEAD,0)));
        
        Room r = mock(Room.class);
        doReturn(r).when(game).getCurrentRoom();
        
        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(out).println("You're taking the \'" + w.getName() + "\' " + direction + ". ");
        verify(game.player).setRoomName(w.getTo());
        verify(game.player).isAttacked(game);
        verify(out).println(game.getCurrentRoom());
        verify(out).println(game.getCurrentRoom().getDescription());
    }
}
