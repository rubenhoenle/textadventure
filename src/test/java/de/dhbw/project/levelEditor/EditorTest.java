package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.BDDMockito.Then;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.dhbw.project.*;
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Clothing;
import de.dhbw.project.item.Food;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;
import de.dhbw.project.item.Resource;
import de.dhbw.project.item.Tool;
import de.dhbw.project.item.Weapon;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({SimpleUserInput.class,Zork.class,Way.class, Book.class, Clothing.class, Food.class, Resource.class, Tool.class, Tool.class, Weapon.class})
public class EditorTest {

    @Mock
    PrintStream out;

    @Mock
    Game game;

    @Mock
    Room room;

    @Mock
    Player player;

    @Mock
    ItemListEditor itemListEditor;

    @Mock
    RoomEditor roomEditor;

    @Mock
    PlayerEditor playerEditor;

    @Spy
    Editor spyEditor = new Editor(game);

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test01EditMethod() {
        // before
        String input = "edit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        Game game = mock(Game.class);
        doReturn(false).when(spyEditor).menu(any());
        doReturn(false).when(spyEditor).save(any());

        // when
        spyEditor.setChanged();
        spyEditor.edit();

        // then
        verify(spyEditor).menu(any());
        verify(spyEditor).save(any());
        verify(out).println("Welcome to the level editor! :)");
        verify(out).println("Disclaimer: You have to do the editing in the right order:");
        verify(out).println("Enter 'edit' to view and edit the exitsting game or 'new' to discard the old one and generate a new one from scratch:");
        verify(out).println("The level editor says 'Goodbye'!");
    }

    @Test
    public void test02MenuRoom() {
        // before
        String testInput = "room";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(game.getRooms()).thenReturn(Arrays.asList(room));
        when(roomEditor.startRoomEditor(game.getRooms())).thenReturn(Arrays.asList(room));
        when(playerEditor.startPlayerEditor(any())).thenReturn(player);
        doReturn(roomEditor).when(spyEditor).getRoomEditor();

        // when
        Boolean feedback = spyEditor.menu(game);

        // then
        assertEquals(true, feedback);
        assertEquals(Arrays.asList(room), game.getRooms());
        verify(roomEditor).startRoomEditor(game.getRooms());
        verify(out).println("Enter 'room' to edit rooms or 'player' to edit the player:");
    }

    @Test
    public void test03MenuPlayer() {
        // before
        String testInput = "player";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doReturn(playerEditor).when(spyEditor).getPlayerEditor();
        when(playerEditor.startPlayerEditor(any())).thenReturn(player);
        //doReturn(false).when(spyEditor).editPlayer(game);

        // when
        Boolean feedback = spyEditor.menu(game);

        // then
        assertEquals(true, feedback);
        verify(playerEditor).startPlayerEditor(any());
        verify(game).setPlayer(player);
        verify(out).println("Enter 'room' to edit rooms or 'player' to edit the player:");
    }

    @Test
    public void test04MenuQuit() {
        // before
        String testInput = "quit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);

        // when
        Boolean feedback = spyEditor.menu(game);

        // then
        assertEquals(false, feedback);
        verify(out).println("Enter 'room' to edit rooms or 'player' to edit the player:");
    }

    @Test
    public void test05MenuFailure() {
        // before
        String testInput = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);

        // when
        Boolean feedback = spyEditor.menu(game);

        // then
        assertEquals(true, feedback);
        verify(out).println("Enter 'room' to edit rooms or 'player' to edit the player:");
    }
    
    @Test
    public void test18SaveYes(){
          // before
          String testInput = "yes";
          PowerMockito.mockStatic(SimpleUserInput.class);
          PowerMockito.mockStatic(Zork.class);
          when(SimpleUserInput.scan()).thenReturn(testInput);
          doReturn(true).when(spyEditor).isChanged();
          when(Zork.saveModel(game)).thenReturn(true);
  
          // when
          boolean feedback = spyEditor.save(game);
  
          // then
          assertEquals(false, feedback);
    }

    @Test
    public void test19SaveNo(){
          // before
          String testInput = "no";
          PowerMockito.mockStatic(SimpleUserInput.class);
          when(SimpleUserInput.scan()).thenReturn(testInput);
          doReturn(true).when(spyEditor).isChanged();
  
          // when
          boolean feedback = spyEditor.save(game);
  
          // then
          assertEquals(false, feedback);
          verify(out).println("Your changes won't be saved.");
    }

    @Test
    public void test20SaveFailure(){
          // before
          String testInput = "golem";
          PowerMockito.mockStatic(SimpleUserInput.class);
          when(SimpleUserInput.scan()).thenReturn(testInput);
          doReturn(true).when(spyEditor).isChanged();
  
          // when
          boolean feedback = spyEditor.save(game);
  
          // then
          assertEquals(true, feedback);
    }

    @Test
    public void test33DeleteWayTrue(){
        //before
        PowerMockito.mockStatic(SimpleUserInput.class);
        Room r = mock(Room.class);
        Way w = mock(Way.class);
        String userInput = "testRoom";
        when(SimpleUserInput.scan()).thenReturn(userInput);
        when(r.getRoomWayByName(userInput)).thenReturn(w);
        when(w.getDirection()).thenReturn("north");
        when(w.getTo()).thenReturn("testDestinationRoom");
        when(SimpleUserInput.deleteDialogue("way",
        "leading " + w.getDirection() + " to " + w.getTo())).thenReturn(true);

        //when
        spyEditor.deleteWay(r);

        //then
        verify(r).deleteWay(w);
        verify(out).println("Please enter the name of the name of the way you want to delete:");
        verify(spyEditor).setChanged();
        verify(r).getRoomWayByName(userInput);
    }

    @Test
    public void test34DeleteWayFalse(){
        //before
        PowerMockito.mockStatic(SimpleUserInput.class);
        Room r = mock(Room.class);
        Way w = mock(Way.class);
        String userInput = "testRoom";
        when(SimpleUserInput.scan()).thenReturn(userInput);
        when(r.getRoomWayByName(userInput)).thenReturn(w);
        when(w.getDirection()).thenReturn("north");
        when(w.getTo()).thenReturn("testDestinationRoom");
        when(SimpleUserInput.deleteDialogue("way",
        "leading " + w.getDirection() + " to " + w.getTo())).thenReturn(false);

        //when
        spyEditor.deleteWay(r);

        //then
        verify(r,never()).deleteWay(w);
        verify(out).println("Please enter the name of the name of the way you want to delete:");
        verify(spyEditor,never()).setChanged();
        verify(r).getRoomWayByName(userInput);
    }

    @Test
    public void test35DeleteWayNoWay(){
        //before
        PowerMockito.mockStatic(SimpleUserInput.class);
        Room r = mock(Room.class);
        Way w = mock(Way.class);
        String userInput = "testRoom";
        when(SimpleUserInput.scan()).thenReturn(userInput);
        when(r.getRoomWayByName(userInput)).thenReturn(null);
        when(w.getDirection()).thenReturn("north");
        when(w.getTo()).thenReturn("testDestinationRoom");
        when(SimpleUserInput.deleteDialogue("way",
        "leading " + w.getDirection() + " to " + w.getTo())).thenReturn(false);

        //when
        spyEditor.deleteWay(r);

        //then
        verify(r,never()).deleteWay(w);
        verify(out).println("Please enter the name of the name of the way you want to delete:");
        verify(spyEditor,never()).setChanged();
        verify(r).getRoomWayByName(userInput);
        verify(out).println("In this room '" + r.getName() + "' there is no way with the name '" + userInput + "'!");
    }

    @Test
    public void voidtest36ListWaysSuccess(){
        //before
        Room r = mock(Room.class);
        Way w1 = mock(Way.class);
        Way w2 = mock(Way.class);
        when(w1.getName()).thenReturn("testWay1");
        when(w2.getName()).thenReturn("testWay2");
        when(w1.getDirection()).thenReturn("north");
        when(w2.getDirection()).thenReturn("south");
        when(w1.getTo()).thenReturn("testDestination1");
        when(w2.getTo()).thenReturn("testDestination2");
        when(r.getRoomWayList()).thenReturn(Arrays.asList(w1,w2));

        //when
        spyEditor.listWays(r);

        //then
        verify(out).println( "A " + w1.getName() + " is leading " + w1.getDirection() + " to " + w1.getTo() + ".");
        verify(out).println( "A " + w2.getName() + " is leading " + w2.getDirection() + " to " + w2.getTo() + ".");
    } 

    @Test
    public void voidtest37ListWaysNullRoom(){
        //before

        //when
        spyEditor.listWays(null);

        //then
        verify(out).println("Error: the parameter room is null!");
    } 

    @Test
    public void test38EditWayYes(){
        //before
        Room r = mock(Room.class);
        Way way = mock(Way.class);
        Way newWay = mock(Way.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        String userInput = "testWay";
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Way.class);
        when(SimpleUserInput.scan()).thenReturn(userInput);
        when(Way.editWay(way, rooms)).thenReturn(newWay);
        when(r.getRoomWayByName(userInput)).thenReturn(way);

        //when
        spyEditor.editWay(r, rooms);

        //then
        verify(spyEditor).setChanged();
        verify(r).addWay(newWay);
        verify(r).deleteWay(way);
        verify(out).println("Please enter the name of the name of the way you want to delete:");
    }

    @Test
    public void test39EditWayNull(){
        //before
        Room r = mock(Room.class);
        Way way = mock(Way.class);
        Way newWay = mock(Way.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        String userInput = "testWay";
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Way.class);
        when(SimpleUserInput.scan()).thenReturn(userInput);
        when(r.getRoomWayByName(userInput)).thenReturn(null);
        when(Way.editWay(way, rooms)).thenReturn(newWay);

        //when
        spyEditor.editWay(r, rooms);

        //then
        verify(spyEditor,never()).setChanged();
        verify(r,never()).addWay(newWay);
        verify(r,never()).deleteWay(way);
        verify(out).println("Please enter the name of the name of the way you want to delete:");
        verify(out).println("In this room '" + r.getName() + "' there is no way with the name '" + userInput + "'!");
    }

    @Test
    public void test40EditWayNo(){
        //before
        Room r = mock(Room.class);
        Way way = mock(Way.class);
        Way newWay = mock(Way.class);
        String userInput = "testWay";
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Way.class);
        when(SimpleUserInput.scan()).thenReturn(userInput);
        when(r.getRoomWayByName(userInput)).thenReturn(newWay);
        when(Way.editWay(way, rooms)).thenReturn(null);

        //when
        spyEditor.editWay(r, rooms);

        //then
        verify(spyEditor,never()).setChanged();
        verify(r,never()).addWay(newWay);
        verify(r,never()).deleteWay(way);
        verify(out).println("Please enter the name of the name of the way you want to delete:");
        verify(out,never()).println("In this room '" + r.getName() + "' there is no way with the name '" + userInput + "'!");
    }

    @Test
    public void test41AddWayYes(){
        //before
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(Way.class);
        Way w = mock(Way.class);
        when(Way.createWay(r, rooms)).thenReturn(w);

        //when
        spyEditor.addWay(r,rooms);

        //then
        verify(r).addWay(w);
        verify(spyEditor).setChanged();
    }

    @Test
    public void test41AddWayNo(){
        //before
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(Way.class);
        Way w = mock(Way.class);
        when(Way.createWay(r, rooms)).thenReturn(null);

        //when
        spyEditor.addWay(r,rooms);

        //then
        verify(r,never()).addWay(w);
        verify(spyEditor,never()).setChanged();
    }

    @Test
    public void test42WaysMenuList(){
        //before
        String input = "list";
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyEditor.waysMenu(r, rooms);

        //then
        assertEquals(true, feedback);
        verify(spyEditor).listWays(r);
        verify(out).println("Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
    }

    @Test
    public void test43WaysMenuEdit(){
        //before
        String input = "edit";
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyEditor.waysMenu(r, rooms);

        //then
        assertEquals(true, feedback);
        verify(spyEditor).editWay(r, rooms);
        verify(out).println("Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
    }

    @Test
    public void test44WaysMenuAdd(){
        //before
        String input = "add";
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyEditor.waysMenu(r, rooms);

        //then
        assertEquals(true, feedback);
        verify(spyEditor).addWay(r, rooms);
        verify(out).println("Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
    }

    @Test
    public void test45WaysMenuDelete(){
        //before
        String input = "delete";
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyEditor.waysMenu(r, rooms);

        //then
        assertEquals(true, feedback);
        verify(spyEditor).deleteWay(r);
        verify(out).println("Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
    }

    @Test
    public void test46WaysMenuQuit(){
        //before
        String input = "quit";
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyEditor.waysMenu(r, rooms);

        //then
        assertEquals(false, feedback);
        verify(out).println("Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
    }

    @Test
    public void test47WaysMenuFailure(){
        //before
        String input = "golem";
        Room r = mock(Room.class);
        List<Room> rooms = new ArrayList<>();
        rooms.add(r);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        //when
        boolean feedback = spyEditor.waysMenu(r, rooms);

        //then
        assertEquals(true, feedback);
        verify(out).println("Enter 'list' to list all ways starting in this room,'edit' to edit a way starting in this room, 'delete' to delete a way starting in this room and 'add' to add a way:");
    }

   
}
