package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.ArrayList;
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
import de.dhbw.project.item.ItemList;
import de.dhbw.project.levelEditor.SimpleUserInput.Decision;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ SimpleUserInput.class, Zork.class, Way.class })
public class PlayerEditorTest {
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
    Editor editor;

    @Spy
    PlayerEditor spyPlayerEditor = new PlayerEditor(editor);

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test06EditPlayerView() {
        // before
        String testInput = "view";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doNothing().when(spyPlayerEditor).printPlayerAttributes(player);

        // when
        Boolean feedback = spyPlayerEditor.editPlayer(player);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
        verify(out).println("Player-Attributes:");
        verify(spyPlayerEditor).printPlayerAttributes(player);
    }

    @Test
    public void test07EditPlayerEdit() {
        // before
        String testInput = "edit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doReturn(false).when(spyPlayerEditor).editPlayerAttributes(player);

        // when
        boolean feedback = spyPlayerEditor.editPlayer(player);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
        verify(spyPlayerEditor).editPlayerAttributes(player);
    }

    @Test
    public void test08EditPlayerItem() {
        // before
        String testInput = "item";
        ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(game.getPlayer()).thenReturn(player);
        when(player.getItemlist()).thenReturn(liste);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doReturn(itemListEditor).when(editor).getItemListEditor();
        doReturn(false).when(itemListEditor).editItemList(liste);
        doReturn(editor).when(spyPlayerEditor).getEditor();

        // when
        boolean feedback = spyPlayerEditor.editPlayer(player);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
        verify(itemListEditor).startItemListEditor(liste);
    }

    @Test
    public void test09EditPlayerEquipment() {
        // before
        String testInput = "equipment";
        ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(game.getPlayer()).thenReturn(player);
        when(player.getEquipmentItemList()).thenReturn(liste);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doReturn(itemListEditor).when(editor).getItemListEditor();
        when(itemListEditor.editItemList(liste)).thenReturn(false);
        doReturn(editor).when(spyPlayerEditor).getEditor();

        // when
        boolean feedback = spyPlayerEditor.editPlayer(player);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
        verify(itemListEditor).startItemListEditor(liste);
    }

    @Test
    public void test10EditPlayerQuit() {
        // before
        String testInput = "quit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);

        // when
        boolean feedback = spyPlayerEditor.editPlayer(player);

        // then
        assertEquals(false, feedback);
        verify(out).println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
    }

    @Test
    public void test11EditPlayerFailure() {
        // before
        String testInput = "golem";
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);

        // when
        boolean feedback = spyPlayerEditor.editPlayer(player);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Enter 'view' to view his attributes, 'edit' to edit them, 'item' to edit his items and 'equipment' to edit his equipment:");
    }

    @Test
    public void test59EditPlayerAttributesSAVE(){
        //before
        List<Room> rooms = new ArrayList<>();
        String newName = "newName";
        String oldName = "oldName";
        String newRoomName = "newRoomName";
        String oldRoomName = "oldRoomName";
        int newPoints = 77;
        int oldPoints = 9;
        int newStrength = 99;
        int oldStrength = 37;
        int newHealth = 111;
        int oldHealth = 91;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(player.getName()).thenReturn(oldName);
        when(player.getRoomName()).thenReturn(oldRoomName);
        when(player.getPoints()).thenReturn(oldPoints);
        when(player.getStrength()).thenReturn(oldStrength);
        when(player.getHealth()).thenReturn(oldHealth);
        when(SimpleUserInput.storeDialogue("Player")).thenReturn(Decision.SAVE);
        when(game.getPlayer()).thenReturn(player);
        when(SimpleUserInput.editMethod("Name", player.getName())).thenReturn(newName);
        when(SimpleUserInput.editMethod("Points", player.getPoints())).thenReturn(newPoints);
        when(SimpleUserInput.editMethod("Strength", player.getStrength())).thenReturn(newStrength);
        when(SimpleUserInput.editMethod("Health", player.getHealth())).thenReturn(newHealth);
        when(SimpleUserInput.editMethodRoomName("Room", player.getRoomName(), rooms)).thenReturn(newRoomName);
        doReturn(editor).when(spyPlayerEditor).getEditor();
        when(editor.getGame()).thenReturn(game);
        when(game.getRooms()).thenReturn(rooms);

        //when
        boolean feedback = spyPlayerEditor.editPlayerAttributes(player);

        //then
        assertEquals(false, feedback);
        verify(editor).setChanged();
        verify(player).setName(newName);
        verify(player).setPoints(newPoints);
        verify(player).setStrength(newStrength);
        verify(player).setRoomName(newRoomName);
        verify(player).setHealth(newHealth);
    }

    @Test
    public void test60EditPlayerAttributesAGAIN(){
        //before
        List<Room> rooms = new ArrayList<>();
        String newName = "newName";
        String oldName = "oldName";
        String newRoomName = "newRoomName";
        String oldRoomName = "oldRoomName";
        int newPoints = 77;
        int oldPoints = 9;
        int newStrength = 99;
        int oldStrength = 37;
        int newHealth = 111;
        int oldHealth = 91;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(player.getName()).thenReturn(oldName);
        when(player.getRoomName()).thenReturn(oldRoomName);
        when(player.getPoints()).thenReturn(oldPoints);
        when(player.getStrength()).thenReturn(oldStrength);
        when(player.getHealth()).thenReturn(oldHealth);
        when(SimpleUserInput.storeDialogue("Player")).thenReturn(Decision.AGAIN);
        when(game.getPlayer()).thenReturn(player);
        when(SimpleUserInput.editMethod("Name", player.getName())).thenReturn(newName);
        when(SimpleUserInput.editMethod("Points", player.getPoints())).thenReturn(newPoints);
        when(SimpleUserInput.editMethod("Strength", player.getStrength())).thenReturn(newStrength);
        when(SimpleUserInput.editMethod("Health", player.getHealth())).thenReturn(newHealth);
        when(SimpleUserInput.editMethodRoomName("Room", player.getRoomName(), rooms)).thenReturn(newRoomName);
        doReturn(editor).when(spyPlayerEditor).getEditor();
        when(editor.getGame()).thenReturn(game);
        when(game.getRooms()).thenReturn(rooms);

        //when
        boolean feedback = spyPlayerEditor.editPlayerAttributes(player);

        //then
        assertEquals(true, feedback);
        verify(editor, never()).setChanged();
    }

    @Test
    public void test61EditPlayerAttributesABBORT(){
        //before
        List<Room> rooms = new ArrayList<>();
        String newName = "newName";
        String oldName = "oldName";
        String newRoomName = "newRoomName";
        String oldRoomName = "oldRoomName";
        int newPoints = 77;
        int oldPoints = 9;
        int newStrength = 99;
        int oldStrength = 37;
        int newHealth = 111;
        int oldHealth = 91;
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(player.getName()).thenReturn(oldName);
        when(player.getRoomName()).thenReturn(oldRoomName);
        when(player.getPoints()).thenReturn(oldPoints);
        when(player.getStrength()).thenReturn(oldStrength);
        when(player.getHealth()).thenReturn(oldHealth);
        when(SimpleUserInput.storeDialogue("Player")).thenReturn(Decision.ABBORT);
        when(game.getPlayer()).thenReturn(player);
        when(SimpleUserInput.editMethod("Name", player.getName())).thenReturn(newName);
        when(SimpleUserInput.editMethod("Points", player.getPoints())).thenReturn(newPoints);
        when(SimpleUserInput.editMethod("Strength", player.getStrength())).thenReturn(newStrength);
        when(SimpleUserInput.editMethod("Health", player.getHealth())).thenReturn(newHealth);
        when(SimpleUserInput.editMethodRoomName("Room", player.getRoomName(), rooms)).thenReturn(newRoomName);
        doReturn(editor).when(spyPlayerEditor).getEditor();
        when(editor.getGame()).thenReturn(game);
        when(game.getRooms()).thenReturn(rooms);

        //when
        boolean feedback = spyPlayerEditor.editPlayerAttributes(player);

        //then
        assertEquals(false, feedback);
        verify(editor, never()).setChanged();
    }


}
