package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Room;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
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
@PrepareForTest(LookCommand.class)
public class LookCommandTest {

    @Mock
    Game game;

    LookCommand command = PowerMockito.spy(new LookCommand(game));

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
    public void test1_shouldNotLookNoTarget() throws Exception {
        //before not needed
        //when
        command.execute();

        //then
        verify(out).println("You have to say where you want to look (around, north, south, east, west, up, down).");
    }

    @Test
    public void test2_shouldNotLookNoTarget() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "");

        //when
        command.execute();

        //then
        verify(out).println("You have to say where you want to look (around, north, south, east, west, up, down).");
    }

    @Test
    public void test3_shouldBeStuck() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "around");

        when(game.hasWays()).thenReturn(false);

        //when
        command.execute();

        //then
        verify(out).println ("You're stucked in a room. There's no way hiding there.");
    }

    @Test
    public void test4_shouldSeeEnemy() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "around");
        Character e = mock(Enemy.class);
        Room r = mock(Room.class);

        when(game.hasWays()).thenReturn(true);
        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getRoomWayList()).thenReturn(Arrays.asList());
        when(r.getRoomItemList()).thenReturn(null);
        when(r.getCharacterList()).thenReturn(Arrays.asList(e));
        when(e.isKilled()).thenReturn(false);
        when(e.getName()).thenReturn("Pete");
        when(e.getWhere()).thenReturn("dancing on a corps");

        //when
        command.execute();

        //then
        verify(out).println("A angry looking Pete is dancing on a corps.");
    }

    @Test
    public void test5_shouldSeeFriend() throws Exception {
        //before
        Whitebox.setInternalState(command, String.class, "around");
        Character f = mock(Friend.class);
        Room r = mock(Room.class);

        when(game.hasWays()).thenReturn(true);
        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getRoomWayList()).thenReturn(Arrays.asList());
        when(r.getRoomItemList()).thenReturn(null);
        when(r.getCharacterList()).thenReturn(Arrays.asList(f));
        when(f.isKilled()).thenReturn(false);
        when(f.getName()).thenReturn("Shia");
        when(f.getWhere()).thenReturn("surprised on a trampoline");

        //when
        command.execute();

        //then
        verify(out).println("A friendly looking Shia is surprised on a trampoline.");
    }
}
