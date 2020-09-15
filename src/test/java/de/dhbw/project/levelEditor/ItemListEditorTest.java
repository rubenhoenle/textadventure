package de.dhbw.project.levelEditor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

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
import de.dhbw.project.item.Book;
import de.dhbw.project.item.Clothing;
import de.dhbw.project.item.Food;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;
import de.dhbw.project.item.Resource;
import de.dhbw.project.item.Tool;
import de.dhbw.project.item.Weapon;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({SimpleUserInput.class, Book.class, Clothing.class, Food.class, Resource.class, Tool.class, Tool.class, Weapon.class})
public class ItemListEditorTest {
    @Mock
    PrintStream out;

    @Mock
    Game game;

    @Mock
    Room room;

    @Mock
    Player player;

    @Mock
    Editor editor;

    @Spy
    ItemListEditor spyItemListEditor = new ItemListEditor(editor);

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test12EditItemListList() {
        // before
        final String testInput = "list";
        final ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doNothing().when(spyItemListEditor).listItems(liste);

        // when
        final boolean feedback = spyItemListEditor.editItemList(liste);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
        verify(out).println("All items:");
        verify(spyItemListEditor).listItems(liste);
    }

    @Test
    public void test13EditItemListAdd() {
        // before
        final String testInput = "add";
        final ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doReturn(false).when(spyItemListEditor).addItemToItemList(liste);

        // when
        final boolean feedback = spyItemListEditor.editItemList(liste);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
        verify(spyItemListEditor).addItemToItemList(liste);
    }

    @Test
    public void test14EditItemListEdit() {
        // before
        final String testInput = "edit";
        final ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doNothing().when(spyItemListEditor).editItem(liste);

        // when
        final boolean feedback = spyItemListEditor.editItemList(liste);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
        verify(spyItemListEditor).editItem(liste);
    }

    @Test
    public void test15EditItemListDelete() {
        // before
        final String testInput = "delete";
        final ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);
        doNothing().when(spyItemListEditor).deleteItem(liste);

        // when
        final boolean feedback = spyItemListEditor.editItemList(liste);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
        verify(spyItemListEditor).deleteItem(liste);
    }

    @Test
    public void test16EditItemListQuit() {
        // before
        final String testInput = "quit";
        final ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);

        // when
        final boolean feedback = spyItemListEditor.editItemList(liste);

        // then
        assertEquals(false, feedback);
        verify(out).println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
    }

    @Test
    public void test17EditItemListFailure() {
        // before
        final String testInput = "golem";
        final ItemList liste = mock(ItemList.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        when(SimpleUserInput.scan()).thenReturn(testInput);

        // when
        final boolean feedback = spyItemListEditor.editItemList(liste);

        // then
        assertEquals(true, feedback);
        verify(out).println(
                "Please enter 'list' to list all items, 'add' to add an item, 'edit' to edit an item or 'delete' to delete an item:");
    }
    

    @Test
    public void test62ListItemsNoList(){
        //before

        //when
        spyItemListEditor.listItems(null);

        //then
        verify(out).println("Error: no ItemList found!!");
    }

    @Test
    public void test63ListItemsEmptyList(){
        //before
        final ItemList itemList = mock(ItemList.class);
        when(itemList.getAllItemList()).thenReturn(new ArrayList<Item>());

        //when
        spyItemListEditor.listItems(itemList);

        //then
        verify(itemList).getAllItemList();
        verify(out).println("There are no items in this list!");
    }

    @Test
    public void test64ListItemsSuccess(){
        //before
        final ItemList itemList = mock(ItemList.class);
        final Item item1 = mock(Item.class);
        final Item item2 = mock(Item.class);
        when(item1.getName()).thenReturn("name1");
        when(item2.getName()).thenReturn("name2");
        when(itemList.getAllItemList()).thenReturn(Arrays.asList(item1,item2));

        //when
        spyItemListEditor.listItems(itemList);

        //then
        verify(itemList).getAllItemList();
        verify(out).println("  -"+item1.getName());
        verify(out).println("  -"+item2.getName());
    }

    @Test
    public void test65additemToItemListBook(){
        //before
        final String input = "Book";
        final Book book = mock(Book.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Book.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(Book.create()).thenReturn(book);
        when(spyItemListEditor.getEditor()).thenReturn(editor);

        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor).setChanged();;
        verify(itemList).addItem(book);
    }

    @Test
    public void test66additemToItemListClothing(){
        //before
        final String input = "Clothing";
        final Clothing object = mock(Clothing.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Clothing.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(Clothing.create()).thenReturn(object);
        when(spyItemListEditor.getEditor()).thenReturn(editor);

        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor).setChanged();
        verify(itemList).addItem(object);
    }

    @Test
    public void test66additemToItemListFood(){
        //before
        final String input = "Food";
        final Food object = mock(Food.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Food.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(Food.create()).thenReturn(object);
        when(spyItemListEditor.getEditor()).thenReturn(editor);

        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor).setChanged();
        verify(itemList).addItem(object);
    }

    @Test
    public void test67additemToItemListResource(){
        //before
        final String input = "Resource";
        final Resource object = mock(Resource.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Resource.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(Resource.create()).thenReturn(object);
        when(spyItemListEditor.getEditor()).thenReturn(editor);

        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor).setChanged();
        verify(itemList).addItem(object);
    }

    @Test
    public void test68additemToItemListTool(){
        //before
        final String input = "Tool";
        final Tool object = mock(Tool.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Tool.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(Tool.create()).thenReturn(object);
        when(spyItemListEditor.getEditor()).thenReturn(editor);

        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor).setChanged();
        verify(itemList).addItem(object);
    }

    @Test
    public void test69additemToItemListWeapon(){
        //before
        final String input = "Weapon";
        final Weapon object = mock(Weapon.class);
        PowerMockito.mockStatic(SimpleUserInput.class);
        PowerMockito.mockStatic(Weapon.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);
        when(Weapon.create()).thenReturn(object);
        when(spyItemListEditor.getEditor()).thenReturn(editor);


        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor).setChanged();
        verify(itemList).addItem(object);
    }

    @Test
    public void test70additemToItemListQuit(){
        //before
        final String input = "quit";
        PowerMockito.mockStatic(SimpleUserInput.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);


        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(false, feedback);
        verify(editor,never()).setChanged();
    }

    @Test
    public void test71additemToItemListFailure(){
        //before
        final String input = "quitsfsadf";
        PowerMockito.mockStatic(SimpleUserInput.class);
        final ItemList itemList = mock(ItemList.class);
        when(SimpleUserInput.scan()).thenReturn(input);


        //when
        final boolean feedback = spyItemListEditor.addItemToItemList(itemList);

        //then
        assertEquals(true, feedback);
        verify(editor,never()).setChanged();
    }
}
