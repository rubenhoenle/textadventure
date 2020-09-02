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
@PrepareForTest(AcceptQuestCommand.class)
public class AcceptQuestCommandTest {

    @Mock
    Game game;

    AcceptQuestCommand command = PowerMockito.spy(new AcceptQuestCommand(game));

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
    public void test1_shouldAcceptQuestFromInteractiveObject() throws Exception {
        //before
        Whitebox.setInternalState(command, "questGiver", Arrays.asList("interactiveObject"));
        Whitebox.setInternalState(command, "quest", Arrays.asList("quest"));
        InteractiveObject io = mock(InteractiveObject.class);
        Quest q = mock(Quest.class);
        Room r = mock(Room.class);
        List<Quest> ql = mock(List.class);
        List<InteractiveObject> iol = mock(List.class);

        when(game.getCharacterFromCurrentRoom(any())).thenReturn(null);
        when(game.getInteractiveObjectFromCurrentRoom("interactiveObject")).thenReturn(io);
        when(io.getQuest()).thenReturn(q);
        when(q.isTalkedOnce()).thenReturn(true);
        when(q.isAccepted()).thenReturn(false);
        when(q.getName()).thenReturn("quest");
        when(player.getQuestInventory()).thenReturn(ql);
        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getRoomInteractiveObjectsList()).thenReturn(iol);

        //when
        command.execute();

        //then
        verify(q).setAccepted(true);
        verify(ql).add(q);
        verify(iol).remove(io);
    }


}
