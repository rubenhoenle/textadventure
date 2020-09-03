package de.dhbw.project;

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
import java.util.List;

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
        Clothing c = new Clothing("TestClothing", "TestClothing", ItemState.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", ItemState.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", ItemState.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", ItemState.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", ItemState.NOT_USABLE, 99);


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
        
        Clothing c = new Clothing("TestClothing", "TestClothing", ItemState.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", ItemState.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", ItemState.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", ItemState.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", ItemState.NOT_USABLE, 99);
        List<String> bookpages = Arrays.asList("Test\n\nTest Test","Test test test");
        Book b = new Book("TestBook", "TestBook", ItemState.NOT_USABLE, 99, bookpages);

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

        Clothing c = new Clothing("TestClothing", "TestClothing", ItemState.NOT_USABLE, 99, EquipmentType.LOWER_BODY);
        Food f = new Food("TestFood", "TestFood", ItemState.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", ItemState.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", ItemState.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", ItemState.NOT_USABLE, 99);
        List<String> bookpages = Arrays.asList("Test\n\nTest Test","Test test test");
        Book b = new Book("TestBook", "TestBook", ItemState.NOT_USABLE, 99, bookpages);

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
        Item i = new Resource("TestItem", "TestItem", ItemState.NOT_USABLE, 99);
        p.addItem(i);

        //when
        Item result = p.getItem("FooBar");

        //then
        assertNull(result);
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
    	p.setStrength(5);
        p.setHealth(20);
        p.addEquipment(new Weapon("name", "description", ItemState.ACTIVE, 10, EquipmentType.WEAPON));
        
        Room r = mock(Room.class);
        
        ItemList list = new ItemList();
        Item i = new Tool("tool", "", ItemState.ACTIVE, 0);
        list.addItem(i);
        
        Enemy e = new Enemy("Yoda", "", 12, 5, "start", "killed", false, list, false);
        
        //when
        p.fight(e, r);

        //then
        verify(out).println("You fight with " + e.getName() + "!");
        verify(out).println(e.getName() + ": " + e.getStartStatement());
        verify(out).println(e.getName() + ": " + e.getKillStatement());
        verify(out).println("You win the fight against " + e.getName() + "!");
        verify(r).addItem(i);
        verify(out).println("Yoda drops '" + i.getName() +"'");
    }
    
    @Test
    public void test8_shouldFightLose(){
        //before
    	p.setStrength(5);
        p.setHealth(10);
        p.addEquipment(new Weapon("name", "description", ItemState.ACTIVE, 10, EquipmentType.WEAPON));
        
        Room r = mock(Room.class);
        
        Enemy e = new Enemy("Yoda", "", 20, 50, "start", "killed", false, null, false);
        
        PowerMockito.mockStatic(Zork.class);
        
        //when
        p.fight(e, r);

        //then
        verify(out).println("You lose the fight against " + e.getName() + "! You faint!");
        verify(out).println("----------");
        verify(out).println("Last save game will be loaded! \n");
    }
    
    @Test
    public void test9_shouldFightIndecided(){
        //before
    	p.setStrength(5);
        p.setHealth(20);
        p.addEquipment(new Weapon("name", "description", ItemState.ACTIVE, 10, EquipmentType.WEAPON));
        
        Room r = mock(Room.class);
        
        Enemy e = new Enemy("Yoda", "", 20, 5, "start", "killed", false, null, false);
        
        PowerMockito.mockStatic(Zork.class);
        
        //when
        p.fight(e, r);

        //then
        verify(out).println("Your opponent is bruised, but you also got a few scratches.");
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
