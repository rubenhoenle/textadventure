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
@PrepareForTest(MapCommand.class)
public class MapCommandTest {

    @Mock
    Game game;

    MapCommand command = PowerMockito.spy(new MapCommand(game));

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
    public void test1_north() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(true);
        when(w.getDirection()).thenReturn("north");

        //when
        command.execute();

        //then
        verify(out).println("           ┌───────┐          ");
        verify(out).println("           │  bar  │          ");
        verify(out).println("           └───┬───┘          ");
        verify(out).println("               │");
        verify(out).println("           ┌───┴───┐          ");
        verify(out).println("           │ beach │          ");
        verify(out).println("           └───────┘          ");
        verify(out,times(3)).println("                              ");
    }

    @Test
    public void test2_south() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(true);
        when(w.getDirection()).thenReturn("south");

        //when
        command.execute();

        //then
        verify(out,times(3)).println("                              ");
        verify(out).println("                ");
        verify(out).println("           ┌───────┐          ");
        verify(out).println("           │ beach │          ");
        verify(out).println("           └───┬───┘          ");
        verify(out).println("               │");
        verify(out).println("           ┌───┴───┐          ");
        verify(out).println("           │  bar  │          ");
        verify(out).println("           └───────┘          ");
    }

    @Test
    public void test3_west() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(true);
        when(w.getDirection()).thenReturn("west");

        //when
        command.execute();

        //then
        verify(out,times(6)).println("                              ");
        verify(out,times(2)).println("                ");
        verify(out).println(" ┌───────┐ ┌───────┐          ");
        verify(out).println(" │  bar  ├─┤ beach │          ");
        verify(out).println(" └───────┘ └───────┘          ");
    }

    @Test
    public void test4_east() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(true);
        when(w.getDirection()).thenReturn("east");

        //when
        command.execute();

        //then
        verify(out,times(6)).println("                              ");
        verify(out,times(2)).println("                ");
        verify(out).println("           ┌───────┐ ┌───────┐");
        verify(out).println("           │ beach ├─┤  bar  │");
        verify(out).println("           └───────┘ └───────┘");
    }

    @Test
    public void test6_up() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(true);
        when(w.getDirection()).thenReturn("up");

        //when
        command.execute();

        //then
        verify(out).println("                     ┌───────┐");
        verify(out).println("                     │  bar  │");
        verify(out).println("                     └───┬───┘");
        verify(out).println("                   ┌─────┘");
        verify(out).println("           ┌───────┤          ");
        verify(out).println("           │ beach │          ");
        verify(out).println("           └───────┘          ");
        verify(out).println("                ");
        verify(out,times(3)).println("                              ");
    }

    @Test
    public void test7_down() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(true);
        when(w.getDirection()).thenReturn("down");

        //when
        command.execute();

        //then
        verify(out,times(3)).println("                              ");
        verify(out).println("                ");
        verify(out).println("           ┌───────┐          ");
        verify(out).println("           │ beach │          ");
        verify(out).println("           └───────┤          ");
        verify(out).println("                   └─────┐");
        verify(out).println("                     ┌───┴───┐");
        verify(out).println("                     │  bar  │");
        verify(out).println("                     └───────┘");
    }

    @Test
    public void test8_noway() throws Exception {
        //before
        Room r = mock(Room.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList());

        //when
        command.execute();

        //then
        verify(out,times(6)).println("                              ");
        verify(out,times(2)).println("                ");
        verify(out).println("           ┌───────┐          ");
        verify(out).println("           │ beach │          ");
        verify(out).println("           └───────┘          ");
    }

    @Test
    public void test9_unknown_dir() throws Exception {
        //before
        Room r = mock(Room.class);
        Room r2 = mock(Room.class);
        Way w = mock(Way.class);

        when(game.getCurrentRoom()).thenReturn(r);
        when(r.getName()).thenReturn("beach");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w));
        when(w.getTo()).thenReturn("bar");
        when(game.getRoom("bar")).thenReturn(r2);
        when(r2.isVisited()).thenReturn(false);
        when(w.getDirection()).thenReturn("down");

        //when
        command.execute();

        //then
        verify(out,times(3)).println("                              ");
        verify(out).println("                ");
        verify(out).println("           ┌───────┐          ");
        verify(out).println("           │ beach │          ");
        verify(out).println("           └───────┤          ");
        verify(out).println("                   └─────┐");
        verify(out).println("                     ┌───┴───┐");
        verify(out).println("                     │   ?   │");
        verify(out).println("                     └───────┘");
    }
}
