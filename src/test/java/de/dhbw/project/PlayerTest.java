package de.dhbw.project;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerTest {

    @Test
    public void test1_shouldAddItem(){
        //before
        Player p = new Player();
        Item i = new Item("TestItem", "TestItem", "TestState", 99);

        //when
        p.addItem(i);

        //then
        assertEquals(1, p.getInventory().size());
    }

    @Test
    public void test2_shouldRemoveItem(){
        //before
        Player p = new Player();
        Item i = new Item("TestItem", "TestItem", "TestState", 99);
        p.addItem(i);

        //when
        p.removeItem(i);

        //then
        assertEquals(0, p.getInventory().size());
    }

    @Test
    public void test3_shouldGetItem(){
        //before
        Player p = new Player();
        Item i = new Item("TestItem", "TestItem", "TestState", 99);
        p.addItem(i);

        //when
        Item result = p.getItem(i.getName());

        //then
        assertEquals(i, result);
    }
    @Test
    public void test4_shouldNotGetItem(){
        //before
        Player p = new Player();
        Item i = new Item("TestItem", "TestItem", "TestState", 99);
        p.addItem(i);

        //when
        Item result = p.getItem("FooBar");

        //then
        assertNull(result);
    }
}
