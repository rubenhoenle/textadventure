package de.dhbw.project;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoomTest {

    @Test
    public void test1_shouldAddItem(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Item i = new Item("TestItem", "TestItem", "TestState", 99);

        //when
        r.addItem(i);

        //then
        assertTrue(r.getRoomItemList().size() == 1);
    }

    @Test
    public void test2_shouldRemoveItem(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Item i = new Item("TestItem", "TestItem", "TestState", 99);
        r.addItem(i);

        //when
        r.removeItem(i);

        //then
        assertTrue(r.getRoomItemList().size() == 0);
    }

    @Test
    public void test3_shouldRemoveNonExistingItem(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Item i = new Item("TestItem", "TestItem", "TestState", 99);

        //when
        r.removeItem(i);

        //then
        assertTrue(r.getRoomItemList().size() == 0);
    }

    @Test
    public void test4_shouldListRoomItems(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Item i1 = new Item("TestItem1", "TestItem1", "TestState1", 99);
        Item i2 = new Item("TestItem2", "TestItem2", "TestState2", 99);
        r.addItem(i1);
        r.addItem(i2);

        //when
        List<String> roomItems = r.getRoomItemNameList();

        //then
        assertTrue(roomItems.size() == 2);
        assertTrue(roomItems.contains(i1.getName()));
        assertTrue(roomItems.contains(i2.getName()));
    }


}
