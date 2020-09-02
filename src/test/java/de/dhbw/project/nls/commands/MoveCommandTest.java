package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
import de.dhbw.project.item.Clothing;

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
    public void test1_shouldMove() throws Exception {
        //before
        String direction = "west";
        Whitebox.setInternalState(command, String.class, direction);

        Way w = new Way("way", "description", direction, "from", "to", WayState.ACTIVE, "hint", "condition");
        Room r = mock(Room.class);
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        doReturn(r).when(game).getCurrentRoom();
        when(r.getDescription()).thenReturn("description");

        //when
        command.execute();

        //then
        verify(out).println ("You're taking the " + w.getName() + " " + direction + ". ");
        verify(game.player).setRoomName(w.getTo());
        verify(game.player).isAttacked(game);
        verify(out).println(game.getCurrentRoom());
        verify(out).println(game.getCurrentRoom().getDescription());
    }

    @Test
    public void test2_shouldBeBlocked() throws Exception {
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
    public void test3_shouldBeConditionalFalse() throws Exception {
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
    public void test4_shouldBeConditionalTrue() throws Exception {
        //before
        String direction = "west";
        Whitebox.setInternalState(command, String.class, direction);

        Way w = new Way("way", "description", direction, "from", "to", WayState.NEED_EQUIPMENT, "hint", "condition");
        when(game.hasWays()).thenReturn(true);
        when(game.getWayForDirection(direction)).thenReturn(w);
        when(game.player.getEquipment()).thenReturn(Arrays.asList(new Clothing("condition", "", State.NOT_USABLE, 0, EquipmentType.HEAD)));
        
        Room r = mock(Room.class);
        doReturn(r).when(game).getCurrentRoom();
        
        //when
        command.execute();

        //then
        verify(out).println ("You're taking the " + w.getName() + " " + direction + ". ");
        verify(game.player).setRoomName(w.getTo());
        verify(game.player).isAttacked(game);
        verify(out).println(game.getCurrentRoom());
        verify(out).println(game.getCurrentRoom().getDescription());
    }
}
