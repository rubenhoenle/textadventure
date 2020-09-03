package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Quest;
import de.dhbw.project.Room;
import de.dhbw.project.character.Character;
import de.dhbw.project.interactive.InteractiveObject;
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
@PrepareForTest(InvestigateCommand.class)
public class InvestigateCommandTest {

    @Mock
    Game game;

    InvestigateCommand command = PowerMockito.spy(new InvestigateCommand(game));

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
    public void test1_shouldInvestigate() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        InteractiveObject io = mock(InteractiveObject.class);
        Room r = mock(Room.class);
        Quest q = mock(Quest.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getRoomInteractiveObjectsNameList()).thenReturn(Arrays.asList("foo"));
        when(game.getCurrentRoom().getRoomInteractiveObjectByName("foo")).thenReturn(io);
        when(io.getQuest()).thenReturn(q);
        when(q.getTextStart()).thenReturn("QuestStartText");

        //when
        command.execute();

        //then
        verify(game).incTurn();
        verify(out).println("Questname: " + q.getName());
        verify(out).println(q.getTextStart());
        verify(out).println("Type: \"accept <questname> from <interactive object>\" to accept the quest.");
    }

    @Test
    public void test2_shouldInvestigateWrongName() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("bar"));
        InteractiveObject io = mock(InteractiveObject.class);
        Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getRoomInteractiveObjectsNameList()).thenReturn(Arrays.asList("foo"));

        //when
        command.execute();

        //then
        verify(out).println("There is no interactive object with the name \'" + "bar" + "\'.");
     }

    @Test
    public void test3_shouldInvestigateNoName() throws Exception {
        //before

        //when
        command.execute();

        //then
        verify(out).println("You have to say which interactive object you want to use.");
    }

}
