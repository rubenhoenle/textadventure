package de.dhbw.project.nls.commands;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Quest;
import de.dhbw.project.Room;
import de.dhbw.project.character.Character;
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
@PrepareForTest(TalkCommand.class)
public class TalkCommandTest {

    @Mock
    Game game;

    TalkCommand command = PowerMockito.spy(new TalkCommand(game));

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
    public void test1_shouldNotTalkNoTarget() throws Exception {
        //before not needed
        //when
        command.execute();

        //then
        verify(out).println("You have to say to whom you want to talk to.");
    }

    @Test
    public void test2_shouldNotTalkNoTarget() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList());

        //when
        command.execute();

        //then
        verify(out).println("You have to say to whom you want to talk to.");
    }

    @Test
    public void test3_shouldNotTalkCharacterNotFound() throws Exception {
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

    @Test
    public void test4_shouldTalk() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Character c = mock(Character.class);
        Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getCharacterNameList()).thenReturn(Arrays.asList("foo"));
        when(game.getCharacterFromCurrentRoom("foo")).thenReturn(c);
        when(c.getStartStatement()).thenReturn("baz");

        //when
        command.execute();

        //then
        verify(c).getStartStatement();
        verify(out).println("baz");
    }

    @Test
    public void test5_shouldTalkOfferQuest() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Friend c = mock(Friend.class);
        Room r = mock(Room.class);
        Quest q = mock(Quest.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getCharacterNameList()).thenReturn(Arrays.asList("foo"));
        when(game.getCharacterFromCurrentRoom("foo")).thenReturn(c);
        when(c.getStartStatement()).thenReturn("baz");
        when(c.getQuests()).thenReturn(Arrays.asList(q));
        when(q.isAccepted()).thenReturn(false);
        when(q.isCompleted()).thenReturn(false);
        when(q.getName()).thenReturn("QName");
        when(q.getTextStart()).thenReturn("QTextStart");

        //when
        command.execute();

        //then
        verify(c).getStartStatement();
        verify(out).println("baz");
        verify(q).setTalkedOnce(true);
        verify(out).println("I got a task for you:");
        verify(out).println("Type: \"accept <questname> from <character>\" to accept the quest.");
    }

    @Test
    public void test6_shouldTalkFinishQuest() throws Exception {
        //before
        Whitebox.setInternalState(command, List.class, Arrays.asList("foo"));
        Friend c = mock(Friend.class);
        Room r = mock(Room.class);
        Quest q = mock(Quest.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(game.getCurrentRoom().getCharacterNameList()).thenReturn(Arrays.asList("foo"));
        when(game.getCharacterFromCurrentRoom("foo")).thenReturn(c);
        when(c.getStartStatement()).thenReturn("baz");
        when(c.getQuests()).thenReturn(Arrays.asList(q));
        when(q.isAccepted()).thenReturn(true);
        when(q.isCompleted()).thenReturn(true);
        when(q.checkCompleted(player)).thenReturn(true);

        //when
        command.execute();

        //then
        verify(c).getStartStatement();
        verify(out).println("baz");
        verify(q).finishQuest(player,true);
    }

}
