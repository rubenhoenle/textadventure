package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.mockito.Spy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.dhbw.project.Game;
import de.dhbw.project.Player;
import de.dhbw.project.Room;
import de.dhbw.project.Way;
import de.dhbw.project.Zork;
import de.dhbw.project.item.*;
import de.dhbw.project.levelEditor.Decision;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SimpleUserInput.class, Zork.class, Way.class, Book.class, Clothing.class, Food.class, Resource.class,
        Tool.class, Tool.class, Weapon.class })
public class RoomEditorTest {
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
    Editor editor;

    @Mock
    FriendEditor friendEditor;

    @Mock
    EnemyEditor enemyEditor;

    @Spy
    RoomEditor spyRoomEditor = new RoomEditor(editor);

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test21GetRoomSuccess() {
        // before
        String roomName = "testRoomName";
        Room r = mock(Room.class);
        when(r.getName()).thenReturn(roomName);

        // when
        Room result = spyRoomEditor.getRoom(roomName, Arrays.asList(r));

        // then
        assertEquals(r, result);
    }

    @Test
    public void test22GetRoomFailure() {
        // before
        String roomName = "testRoomName";
        Room r = mock(Room.class);
        when(r.getName()).thenReturn("testname");

        // when
        Room result = spyRoomEditor.getRoom(roomName, Arrays.asList(r));

        // then
        assertEquals(null, result);
    }

    @Test
    public void test23ListRooms() {
        // before
        String roomName1 = "testRoomName1";
        String roomName2 = "testRoomName2";
        Room r1 = mock(Room.class);
        Room r2 = mock(Room.class);
        when(r1.getName()).thenReturn(roomName1);
        when(r2.getName()).thenReturn(roomName2);

        // when
        spyRoomEditor.listRooms(Arrays.asList(r1, r2));

        // then
        verify(out).println("  -" + roomName1);
        verify(out).println("  -" + roomName2);
    }

    @Test
    public void test24DeleteRoomYes() {
        // before
        List<Room> rooms = new ArrayList<>();
        String testInput = "roomToDeleteName";
        Room r2 = mock(Room.class);
        when(r2.getName()).thenReturn(testInput);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        rooms.add(r2);
        when(SimpleUserInput.deleteDialogue("Room", r2.getName())).thenReturn(true);
        when(spyRoomEditor.getEditor()).thenReturn(editor);

        // when
        spyRoomEditor.deleteRoom(rooms);

        // then
        verify(out).println("Please enter the name of the room you want to delete:");
        verify(out, never()).println("There is no room available  with the name '" + testInput + "'.");
        assertEquals(true, rooms.isEmpty());
        verify(out, never()).println("Deletion of room '" + r2.getName() + "' aborted.");
        verify(editor).setChanged();
    }

    @Test
    public void test25DeleteRoomNo() {
        // before
        List<Room> rooms = new ArrayList<>();
        String testInput = "roomToDeleteName";
        Room r2 = mock(Room.class);
        when(r2.getName()).thenReturn(testInput);
        rooms.add(r2);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.deleteDialogue("Room", r2.getName())).thenReturn(false);

        // when
        spyRoomEditor.deleteRoom(rooms);

        // then
        verify(out).println("Please enter the name of the room you want to delete:");
        verify(out, never()).println("There is no room available  with the name '" + testInput + "'.");
        assertEquals(true, rooms.contains(r2));
        verify(game, never()).deleteRoom(r2);
        verify(out).println("Deletion of room '" + r2.getName() + "' aborted.");
    }

    @Test
    public void test26DeleteRoomNoRoom() {
        // before
        List<Room> rooms = new ArrayList<>();
        String testInput = "roomToDeleteName";
        Room r2 = mock(Room.class);
        when(r2.getName()).thenReturn(testInput);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        when(SimpleUserInput.deleteDialogue("Room", r2.getName())).thenReturn(false);

        // when
        spyRoomEditor.deleteRoom(rooms);

        // then
        verify(out).println("Please enter the name of the room you want to delete:");
        verify(out).println("There is no room available  with the name '" + testInput + "'.");
        verify(game, never()).deleteRoom(r2);
        verify(out, never()).println("Deletion of room '" + r2.getName() + "' aborted.");
    }

    @Test
    public void test27editRoomSave() {
        // before
        PowerMockito.mockStatic(SimpleUserInput.class);
        Room r = mock(Room.class);
        when(r.getName()).thenReturn("testRoomName");
        when(r.getDescription()).thenReturn("testRoomDescription");
        when(r.isDark()).thenReturn(true);
        when(r.getConditionalItem()).thenReturn("testConditionalItem");
        when(r.getDescription()).thenReturn("testDefaultItemLocation");
        when(SimpleUserInput.editMethod("Name", r.getName())).thenReturn("newName");
        when(SimpleUserInput.editMethod("Description", r.getDescription())).thenReturn("newDescription");
        when(SimpleUserInput.editMethod("Darkness", r.isDark())).thenReturn(false);
        when(SimpleUserInput.editMethod("Name of a conditional item, leave it empty if there is no such one required",
                r.getConditionalItem())).thenReturn("newConditionalItem");
        when(SimpleUserInput.editMethod("DefaultItemLocation", r.getDefaultItemLocation()))
                .thenReturn("newDefaultItemLocation");
        when(SimpleUserInput.storeDialogue("Room")).thenReturn(Decision.SAVE);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.editRoom(r);

        // then
        assertEquals(false, feedback);
        verify(r).setName("newName");
        verify(r).setDescription("newDescription");
        verify(r).setDefaultItemLocation("newDefaultItemLocation");
        verify(r).setIsDark(false);
        verify(r).setConditionalItem("newConditionalItem");
        verify(editor).setChanged();
    }

    @Test
    public void test28editRoomAgain() {
        // before
        PowerMockito.mockStatic(SimpleUserInput.class);
        Room r = mock(Room.class);
        when(r.getName()).thenReturn("testRoomName");
        when(r.getDescription()).thenReturn("testRoomDescription");
        when(r.isDark()).thenReturn(true);
        when(r.getConditionalItem()).thenReturn("testConditionalItem");
        when(r.getDescription()).thenReturn("testDefaultItemLocation");
        when(SimpleUserInput.editMethod("Name", r.getName())).thenReturn("newName");
        when(SimpleUserInput.editMethod("Description", r.getDescription())).thenReturn("newDescription");
        when(SimpleUserInput.editMethod("Darkness", r.isDark())).thenReturn(false);
        when(SimpleUserInput.editMethod("Name of a conditional item, leave it empty if there is no such one required",
                r.getConditionalItem())).thenReturn("newConditionalItem");
        when(SimpleUserInput.editMethod("DefaultItemLocation", r.getDefaultItemLocation()))
                .thenReturn("newDefaultItemLocation");
        when(SimpleUserInput.storeDialogue("Room")).thenReturn(Decision.AGAIN);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.editRoom(r);

        // then
        assertEquals(true, feedback);
        verify(r, never()).setName(anyString());
        verify(r, never()).setDescription(anyString());
        verify(r, never()).setDefaultItemLocation(anyString());
        verify(r, never()).setIsDark(anyBoolean());
        verify(r, never()).setConditionalItem(anyString());
    }

    @Test
    public void test29editRoomAbbort() {
        // before
        PowerMockito.mockStatic(SimpleUserInput.class);
        Room r = mock(Room.class);
        when(r.getName()).thenReturn("testRoomName");
        when(r.getDescription()).thenReturn("testRoomDescription");
        when(r.isDark()).thenReturn(true);
        when(r.getConditionalItem()).thenReturn("testConditionalItem");
        when(r.getDescription()).thenReturn("testDefaultItemLocation");
        when(SimpleUserInput.editMethod("Name", r.getName())).thenReturn("newName");
        when(SimpleUserInput.editMethod("Description", r.getDescription())).thenReturn("newDescription");
        when(SimpleUserInput.editMethod("Darkness", r.isDark())).thenReturn(false);
        when(SimpleUserInput.editMethod("Name of a conditional item, leave it empty if there is no such one required",
                r.getConditionalItem())).thenReturn("newConditionalItem");
        when(SimpleUserInput.editMethod("DefaultItemLocation", r.getDefaultItemLocation()))
                .thenReturn("newDefaultItemLocation");
        when(SimpleUserInput.storeDialogue("Room")).thenReturn(Decision.CANCEL);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.editRoom(r);

        // then
        assertEquals(false, feedback);
        verify(r, never()).setName(anyString());
        verify(r, never()).setDescription(anyString());
        verify(r, never()).setDefaultItemLocation(anyString());
        verify(r, never()).setIsDark(anyBoolean());
        verify(r, never()).setConditionalItem(anyString());
    }

    @Test
    public void test30AddRoomSAVE() {
        // before
        List<Room> rooms = new ArrayList<>();
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.addMethod("Name")).thenReturn("newName");
        when(SimpleUserInput.addMethod("Description")).thenReturn("newDescription");
        when(SimpleUserInput.addMethodBoolean("is dark")).thenReturn(true);
        when(SimpleUserInput.addMethod("Name of a conditional item, leave it empty if there is no such one required"))
                .thenReturn("newConditionalItem");
        when(SimpleUserInput.addMethod("DefaultItemLocation")).thenReturn("newDefaultItemLocation");
        when(SimpleUserInput.storeDialogue("room")).thenReturn(Decision.SAVE);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.addRoom(rooms);

        // then
        assertEquals(false, feedback);
        assertEquals(1, rooms.size());
        verify(editor).setChanged();
    }

    @Test
    public void test31AddRoomAGAIN() {
        // before
        List<Room> rooms = new ArrayList<>();
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.addMethod("Name")).thenReturn("newName");
        when(SimpleUserInput.addMethod("Description")).thenReturn("newDescription");
        when(SimpleUserInput.addMethodBoolean("is dark")).thenReturn(true);
        when(SimpleUserInput.addMethod("Name of a conditional item, leave it empty if there is no such one required"))
                .thenReturn("newConditionalItem");
        when(SimpleUserInput.addMethod("DefaultItemLocation")).thenReturn("newDefaultItemLocation");
        when(SimpleUserInput.storeDialogue("room")).thenReturn(Decision.AGAIN);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.addRoom(rooms);

        // then
        assertEquals(true, feedback);
        verify(game, never()).addRoom(any());
    }

    @Test
    public void test32AddRoomABBORT() {
        // before
        List<Room> rooms = new ArrayList<>();
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.addMethod("Name")).thenReturn("newName");
        when(SimpleUserInput.addMethod("Description")).thenReturn("newDescription");
        when(SimpleUserInput.addMethodBoolean("is dark")).thenReturn(true);
        when(SimpleUserInput.addMethod("Name of a conditional item, leave it empty if there is no such one required"))
                .thenReturn("newConditionalItem");
        when(SimpleUserInput.addMethod("DefaultItemLocation")).thenReturn("newDefaultItemLocation");
        when(SimpleUserInput.storeDialogue("room")).thenReturn(Decision.CANCEL);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.addRoom(rooms);

        // then
        assertEquals(false, feedback);
        verify(game, never()).addRoom(any());
    }

    @Test
    public void test48InspectOutputAndQuit() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "quit";
        Room room = mock(Room.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(room.getName()).thenReturn("roomName");
        when(room.getDescription()).thenReturn("roomDescription");
        when(room.isDark()).thenReturn(true);
        when(room.getConditionalItem()).thenReturn("testConditionalItem");
        when(room.getDescription()).thenReturn("testDefaultItemLocation");
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.inspect(rooms, room);

        // then
        assertEquals(false, feedback);
        verify(out).println("name: " + room.getName());
        verify(out).println("description: " + room.getDescription());
        verify(out).println("Is dark: " + room.isDark());
        verify(out).println("Conditionalitem: " + room.getConditionalItem());
        verify(out).println("defaultItemLocation: " + room.getDefaultItemLocation());
        verify(out).println(
            "Enter 'edit' to edit the room, 'way' to edit the ways outgoing from this room, 'item' to edit its items, 'friends' to edit the friends' or 'enemy' to edit the enemies:");

    }

    @Test
    public void test49InspectEdit() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "edit";
        Room room = mock(Room.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(false).when(spyRoomEditor).editRoom(room);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.inspect(rooms, room);

        // then
        assertEquals(true, feedback);
        verify(spyRoomEditor).editRoom(room);
    }

    @Test
    public void test50InspectItem() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "item";
        Room room = mock(Room.class);
        ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(room.getRoomsItemList()).thenReturn(liste);
        doReturn(itemListEditor).when(editor).getItemListEditor();
        when(itemListEditor.editItemList(liste)).thenReturn(false);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.inspect(rooms, room);

        // then
        assertEquals(true, feedback);
        verify(itemListEditor).startItemListEditor(room.getRoomsItemList());
    }

    @Test
    public void test51InspectWay() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "way";
        Room room = mock(Room.class);
        ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        //doReturn(false).when(editor).waysMenu(room, game);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.inspect(rooms, room);

        // then
        assertEquals(true, feedback);
    verify(editor).waysMenu(room, rooms);
    }

    @Test
    public void test52InspectFailure() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "golem";
        Room room = mock(Room.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(spyRoomEditor.getEditor()).thenReturn(editor);
        when(editor.getFriendEditor()).thenReturn(friendEditor);
        when(editor.getEnemyEditor()).thenReturn(enemyEditor);

        // when
        boolean feedback = spyRoomEditor.inspect(rooms, room);

        // then
        assertEquals(true, feedback);
    }

    @Test
    public void test53EditRoomsInspect() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "inspect";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(SimpleUserInput.addMethodRoom("name of the room you want to inspect", rooms)).thenReturn(room);
        doReturn(false).when(spyRoomEditor).inspect(rooms, room);

        // when
        boolean feedback = spyRoomEditor.editRooms(rooms);

        // then
        assertEquals(true, feedback);
        verify(out, never()).println("There is no room available with the name '" + input + "'.");
        verify(spyRoomEditor).inspect(rooms, room);
    }

    @Test
    public void test54EditRoomsAdd() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "add";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        doReturn(false).when(spyRoomEditor).addRoom(rooms);

        // when
        boolean feedback = spyRoomEditor.editRooms(rooms);

        // then
        assertEquals(true, feedback);
        verify(spyRoomEditor).editRooms(rooms);
    }

    @Test
    public void test55EditRoomsDelete() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "delete";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        // when
        boolean feedback = spyRoomEditor.editRooms(rooms);

        // then
        assertEquals(true, feedback);
        verify(spyRoomEditor).deleteRoom(rooms);
    }

    @Test
    public void test56EditRoomsList() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "list";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        // when
        boolean feedback = spyRoomEditor.editRooms(rooms);

        // then
        assertEquals(true, feedback);
        verify(spyRoomEditor).listRooms(rooms);
        verify(out).println("There are the following rooms:");
    }

    @Test
    public void test57EditRoomsQuit() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "quit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        // when
        boolean feedback = spyRoomEditor.editRooms(rooms);

        // then
        assertEquals(false, feedback);
    }

    @Test
    public void test58EditRoomsFailure() {
        // before
        List<Room> rooms = new ArrayList<>();
        String input = "blödsinn";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(input);

        // when
        boolean feedback = spyRoomEditor.editRooms(rooms);

        // then
        assertEquals(true, feedback);
    }
}
