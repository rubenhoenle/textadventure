package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Room;
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
@PrepareForTest(AttackCommand.class)
public class AttackCommandTest {

    @Mock
    Game game;

    AttackCommand command = PowerMockito.spy(new AttackCommand(game));

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
    public void test1_shouldAttack() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Character c = mock(Character.class);
        Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getCharacterNameList()).thenReturn(Arrays.asList("foo"));
        when(game.getCharacterFromCurrentRoom("foo")).thenReturn(c);

        //when
        command.execute();

        //then
        verify(game.player).fight(c, r);
    }

    @Test
    public void test2_shouldNotAttackNoTarget1() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList());

        //when
        command.execute();

        //then
        verify(out).println("You have to say to whom you want to attack.");
    }

    @Test
    public void test3_shouldNotAttackNoTarget2() throws Exception {
        //before not needed
        //when
        command.execute();

        //then
        verify(out).println("You have to say to whom you want to attack.");
    }

    @Test
    public void test4_shouldNotAttackCharacterNotFound() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Character c = mock(Character.class);
        Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getCharacterNameList()).thenReturn(Arrays.asList("bar"));
        when(game.getCurrentRoom().getName()).thenReturn("bar");

        //when
        command.execute();

        //then
        verify(r).getName();
        verify(out).println("No character named foo in area bar");
    }
}
