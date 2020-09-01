package de.dhbw.project;

import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.item.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest({Player.class,Zork.class})
public class PlayerTest {

    Player p = PowerMockito.spy(new Player());

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test1_shouldAddItem(){
        //before
        Player p = new Player();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);


        //when
        p.addItem(c);
        p.addItem(f);
        p.addItem(r);
        p.addItem(t);
        p.addItem(w);

        //then
        assertEquals(5, p.getInventory().size());
    }

    @Test
    public void test2_shouldRemoveItem(){
        //before
        Player p = new Player();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);
        Book b = new Book("TestBook", "TestBook", State.NOT_USABLE, 99);
        p.addItem(c);
        p.addItem(f);
        p.addItem(r);
        p.addItem(t);
        p.addItem(w);
        p.addItem(b);

        //when
        p.removeItem(c);
        p.removeItem(f);
        p.removeItem(r);
        p.removeItem(t);
        p.removeItem(w);
        p.removeItem(b);

        //then
        assertEquals(0, p.getInventory().size());
    }

    @Test
    public void test3_shouldGetItem(){
        //before
        Player p = new Player();
        Clothing c = new Clothing("TestClothing", "TestClothing", State.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", State.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", State.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", State.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", State.NOT_USABLE, 99);
        Book b = new Book("TestBook", "TestBook", State.NOT_USABLE, 99);
        p.addItem(c);
        p.addItem(f);
        p.addItem(r);
        p.addItem(t);
        p.addItem(w);
        p.addItem(b);

        //when
        Item resultC = p.getItem(c.getName());
        Item resultF = p.getItem(f.getName());
        Item resultR = p.getItem(r.getName());
        Item resultT = p.getItem(t.getName());
        Item resultW = p.getItem(w.getName());
        Item resultB = p.getItem(b.getName());

        //then
        assertEquals(c, resultC);
        assertEquals(f, resultF);
        assertEquals(r, resultR);
        assertEquals(t, resultT);
        assertEquals(w, resultW);
        assertEquals(b, resultB);
    }
    @Test
    public void test4_shouldNotGetItem(){
        //before
        Player p = new Player();
        Item i = new Resource("TestItem", "TestItem", State.NOT_USABLE, 99);
        p.addItem(i);

        //when
        Item result = p.getItem("FooBar");

        //then
        assertNull(result);
    }

    @Test
    public void test5_WinsFight(){
        //before
        Player p = new Player();
        Character c = new Character();
        Player p1= new Player();
        Character c1= new Character();
        Player p2= new Player();
        Character c2 = new Character();
        p.setStrength(20);
        c.setStrength(10);
        p1.setStrength(20);
        c1.setStrength(20);
        p2.setStrength(10);
        c2.setStrength(20);

        //when
        boolean r = p.winsFight(c);
        boolean r1 = p1.winsFight(c1);
        boolean r2 = p2.winsFight(c2);

        //then
        assertTrue(r);
        assertTrue(r1);
        assertFalse(r2);
    }

    @Test
    public void test5_shouldBeAttacked(){
        //before
        Game g = mock(Game.class);
        Room r = mock(Room.class);
        Enemy e = mock(Enemy.class);

        when(g.getCurrentRoom()).thenReturn(r);
        when(r.getEnemyList()).thenReturn(Arrays.asList(e));
        when(r.getName()).thenReturn("foo");
        when(e.isKilled()).thenReturn(false);
        when(e.isAutoAttack()).thenReturn(true);
        when(e.getName()).thenReturn("bar");

        //when
        p.isAttacked(g);

        //then
        verify(p).fight(e, r);
        verify(out).println( "Just after you entered foo you are attacked by bar!");
    }

    @Test
    public void test6_shouldNotBeAttacked(){
        //before
        Game g = mock(Game.class);
        Room r = mock(Room.class);
        Enemy e = mock(Enemy.class);

        when(g.getCurrentRoom()).thenReturn(r);
        when(r.getEnemyList()).thenReturn(Arrays.asList(e));
        when(r.getName()).thenReturn("foo");
        when(e.isKilled()).thenReturn(true);
        when(e.isAutoAttack()).thenReturn(true);
        when(e.getName()).thenReturn("bar");

        //when
        p.isAttacked(g);

        //then
        verify(p,never()).fight(e, r);
    }

    @Test
    public void test7_shouldFightWin(){
        //before
        Room r = mock(Room.class);
        Enemy e = mock(Enemy.class);
        Item i = mock(Item.class);

        when(e.isKilled()).thenReturn(false);
        when(e.getDropItemListElements()).thenReturn(Arrays.asList(i));
        when(e.getName()).thenReturn("Yoda");
        when(i.getName()).thenReturn("lightsaber");

        //when
        p.fight(e, r);

        //then
        verify(e, times(5)).getName();
        verify(e).getStartStatement();
        verify(p).winsFight(e);
        verify(e).getKillStatement();
        verify(r).addItem(i);
        verify(e).setKilled(true);
        verify(out).println("Yoda drops lightsaber");
    }

    @Test
    public void test8_shouldFightLose(){
        //before
        Room r = mock(Room.class);
        Enemy e = mock(Enemy.class);

        PowerMockito.mockStatic(Zork.class);
        when(e.isKilled()).thenReturn(false);
        doReturn(false).when(p).winsFight(e);


        //when
        p.fight(e, r);

        //then
        verify(e, times(3)).getName();
        verify(e).getStartStatement();
        verify(p).winsFight(e);
        verify(out).println ("Last save game will be loaded! \n");
    }

    @Test
    public void test9_shouldReturnItemListWithZeoElements(){
        //before
        Player p = new Player();
        
        //when
        ItemList list = p.getItemlist();

        //then
        assertEquals(0, list.getAllItemList().size());
    }

    @Test
    public void test10_shouldReturnItemList(){
        //before
        Player p = new Player();
        Weapon w = mock(Weapon.class);
        Book b = mock(Book.class);
        Food f = mock(Food.class);
        p.addItem(w);
        p.addItem(b);
        p.addItem(f);

        //when
        ItemList list = p.getItemlist();

        //then
        assertEquals(3, list.getAllItemList().size());

    }
}
