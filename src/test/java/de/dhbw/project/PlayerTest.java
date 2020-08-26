package de.dhbw.project;

import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.nls.commands.AttackCommand;
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
import static org.powermock.api.mockito.PowerMockito.when;

import static org.mockito.Mockito.*;

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
        when(e.getDropItemList()).thenReturn(Arrays.asList(i));
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
}
