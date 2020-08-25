package de.dhbw.project.nls.commands;

import de.dhbw.project.*;
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

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(SwitchCommand.class)
public class SwitchCommandTest {

    @Mock
    Game game;

    SwitchCommand command = PowerMockito.spy(new SwitchCommand(game));

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
    public void test1_InputNull() throws Exception {
        //before not needed
        //when
        command.execute();

        //then
        verify(out).println("You have to use the whole item name to switch it on or off.");
    }

    @Test
    public void test2_InputEmpty() throws Exception {
        //before not needed
        Whitebox.setInternalState(command, List.class, Arrays.asList());
        //when
        command.execute();

        //then
        verify(out).println("You have to use the whole item name to switch it on or off.");
    }

    @Test
    public void test3_ItemNotFound() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo", "bar"));
        when(game.player.getItem("foo bar")).thenReturn(null);

        //when
        command.execute();

        //then
        verify(out).println ("Item foo bar not found in player inventory.");
    }

    @Test
    public void test4_ItemStateNotUsable() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getState()).thenReturn(State.NOT_USABLE);

        //when
        command.execute();

        //then
        verify(out).println("Item foo has no state.");
    }

    @Test
    public void test5_ItemStateNull() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getState()).thenReturn(null);

        //when
        command.execute();

        //then
        verify(out).println("Item foo has no state.");
    }

    @Test
    public void test6_shouldSwitchToInactive() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getState()).thenReturn(State.ACTIVE);

        //when
        command.execute();

        //then
        verify(i, times(3)).getState();
        verify(i).setState(State.INACTIVE);
        verify(out).println("Item foo is now " + State.INACTIVE + ".");
    }

    @Test
    public void test7_shouldSwitchToActive() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getState()).thenReturn(State.INACTIVE);

        //when
        command.execute();

        //then
        verify(i, times(3)).getState();
        verify(i).setState(State.ACTIVE);
        verify(out).println("Item foo is now " + State.ACTIVE + ".");
    }
}