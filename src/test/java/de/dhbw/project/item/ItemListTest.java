package de.dhbw.project.item;

import de.dhbw.project.EquipmentType;
import de.dhbw.project.State;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemListTest {
    
    @Test
    public void test1_shouldAddItem(){
        //before
        ItemList itemList = new ItemList();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);
        Book b = new Book("TestBook", "TestBook", State.NOT_USABLE, 99);

        //when
        itemList.addItem(c);
        itemList.addItem(f);
        itemList.addItem(r);
        itemList.addItem(t);
        itemList.addItem(w);
        itemList.addItem(b);

        //then
        assertEquals(6, itemList.getAllItemList().size());
        
    }

    @Test
    public void test2_shouldRemoveItem(){
        //before
        ItemList itemList = new ItemList();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);
        Book b = new Book("TestBook", "TestBook", State.NOT_USABLE, 99);
        itemList.addItem(c);
        itemList.addItem(f);
        itemList.addItem(r);
        itemList.addItem(t);
        itemList.addItem(w);
        itemList.addItem(b);

        //when
        itemList.removeItem(c);
        itemList.removeItem(f);
        itemList.removeItem(r);
        itemList.removeItem(t);
        itemList.removeItem(w);
        itemList.removeItem(b);

        //then
        assertEquals(0, itemList.getAllItemList().size());

    }

    @Test
    public void test3_shouldGetItem(){
        //before
        ItemList itemList = new ItemList();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);
        Book b = new Book("TestBook", "TestBook", State.NOT_USABLE, 99);
        itemList.addItem(c);
        itemList.addItem(f);
        itemList.addItem(r);
        itemList.addItem(t);
        itemList.addItem(w);
        itemList.addItem(b);

        //when
        Item resultC = itemList.getItem(c.getName());
        Item resultF = itemList.getItem(f.getName());
        Item resultR = itemList.getItem(r.getName());
        Item resultT = itemList.getItem(t.getName());
        Item resultW = itemList.getItem(w.getName());
        Item resultB = itemList.getItem(b.getName());

        //then
        assertEquals(c, resultC);
        assertEquals(f, resultF);
        assertEquals(r, resultR);
        assertEquals(t, resultT);
        assertEquals(w, resultW);
        assertEquals(b, resultB);
    }

    public void test4_shouldGetNumberOfItem(){
        //before
        ItemList itemList = new ItemList();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);
        Book b = new Book("TestBook", "TestBook", State.NOT_USABLE, 99);
        for (int i=0; i<=10; i++){
            itemList.addItem(c);
            itemList.addItem(f);
            itemList.addItem(r);
            itemList.addItem(t);
            itemList.addItem(w);
            itemList.addItem(b);
        }

        //when
        int iB = itemList.getNumberOfItem(b.getName());
        int iC = itemList.getNumberOfItem(c.getName());
        int iF = itemList.getNumberOfItem(f.getName());
        int iR = itemList.getNumberOfItem(r.getName());
        int iT = itemList.getNumberOfItem(t.getName());
        int iW = itemList.getNumberOfItem(w.getName());
        
        //then
        assertEquals(10, iB);
        assertEquals(10, iC);
        assertEquals(10, iF);
        assertEquals(10, iR);
        assertEquals(10, iT);
        assertEquals(10, iW);
    }
}