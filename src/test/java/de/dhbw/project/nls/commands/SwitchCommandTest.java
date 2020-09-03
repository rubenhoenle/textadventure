package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.item.ItemState;
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
    public void test1_InputStateNull() throws Exception {
        //before not needed
        //when
        command.execute();

        //then
        verify(out).println("Please tell if you want to switch the item on or off.");
    }

    @Test
    public void test2_InputItemEmpty() throws Exception {
        //before not needed
        Whitebox.setInternalState(command, String.class, "on");
        //when
        command.execute();

        //then
        verify(out).println("You have to use the whole item name to switch it on or off.");
    }

    @Test
    public void test3_ItemNotFound() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "on");
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo", "bar"));
        when(game.player.getItem("foo bar")).thenReturn(null);

        //when
        command.execute();

        //then
        verify(out).println ("Item \'foo bar\' not found in player inventory.");
    }

    @Test
    public void test4_ItemStateNotUsable() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "on");
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getItemstate()).thenReturn(ItemState.NOT_USABLE);

        //when
        command.execute();

        //then
        verify(out).println("Item \'foo\' has no state.");
    }

    @Test
    public void test5_ItemStateNull() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "on");
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getItemstate()).thenReturn(null);

        //when
        command.execute();

        //then
        verify(out).println("Item \'foo\' has no state.");
    }

    @Test
    public void test6_shouldSwitchToInactive() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "off");
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getItemstate()).thenReturn(ItemState.ACTIVE);

        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(i).setItemstate(ItemState.INACTIVE);
        verify(out).println("Item \'foo\' is now " + ItemState.INACTIVE + ".");
    }

    @Test
    public void test7_shouldSwitchToActive() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "on");
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Item i = mock(Item.class);

        when(game.player.getItem("foo")).thenReturn(i);
        when(i.getItemstate()).thenReturn(ItemState.INACTIVE);

        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(i).setItemstate(ItemState.ACTIVE);
        verify(out).println("Item \'foo\' is now " + ItemState.ACTIVE + ".");
    }
}