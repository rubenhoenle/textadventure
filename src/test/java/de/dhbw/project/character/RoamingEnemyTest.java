package de.dhbw.project.character;

import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemList;
import de.dhbw.project.item.Resource;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(RoamingEnemy.class)
public class RoamingEnemyTest {

    @Test
    public void test1_shouldCreateRoamingEnemy() throws Exception {
        //before
        String characterName = "name";
        String characterWhere = "where";
        int characterHealth = 99;
        int characterStrength = 99;
        int characterPoints = 99;
        String characterStartStatement = "startStatement";
        String characterKillStatement = "killStatement";
        boolean characterKilled = false;
        Item dropItem = mock(Resource.class);
        ItemList characterDropItems = new ItemList();
        characterDropItems.addItem(dropItem);
        boolean characterAutoAttack = true;
        List<String> path = Arrays.asList("foo");

        //when
        RoamingEnemy e = new RoamingEnemy(characterName, characterWhere, characterHealth, characterStrength, characterStartStatement, characterKillStatement, characterKilled, characterPoints, characterDropItems, path);

        //then
        assertEquals(e.getName(), characterName);
        assertEquals(e.getWhere(), characterWhere);
        assertEquals(e.getHealth(), characterHealth);
        assertEquals(e.getStrength(), characterStrength);
        assertEquals(e.getStartStatement(), characterStartStatement);
        assertEquals(e.getKillStatement(), characterKillStatement);
        assertFalse(e.isKilled());
        assertEquals(e.getPoints(),characterPoints);
        assertEquals(e.getDropItemList(), characterDropItems);
        assertEquals(e.getTravelPath(), path);
        assertTrue(e.getDropItemListElements().size() == 1);
        assertTrue(e.isAutoAttack());
    }

    @Test
    public void test2_nextRoomNull() throws Exception {
        //before
        RoamingEnemy e = new RoamingEnemy(
                "name", "where", 10, 10, "start",
                "kill", false, 10, new ItemList(),null );
        //when
        String res = e.getNextRoom("foo");
        //then
        assertNull(res);
    }

    @Test
    public void test3_nextRoom() throws Exception {
        //before
        RoamingEnemy e = new RoamingEnemy(
                "name", "where", 10, 10, "start",
                "kill", false, 10, new ItemList(), Arrays.asList("foo", "bar") );
        //when
        String res = e.getNextRoom("foo");
        //then
        assertEquals(res, "bar");
    }

    @Test
    public void test4_nextRoom() throws Exception {
        //before
        RoamingEnemy e = new RoamingEnemy(
                "name", "where", 10, 10, "start",
                "kill", false, 10, new ItemList(), Arrays.asList("foo", "bar") );
        //when
        String res = e.getNextRoom("bar");
        //then
        assertEquals(res, "foo");
    }

    @Test
    public void test5_nextRoom() throws Exception {
        //before
        RoamingEnemy e = new RoamingEnemy(
                "name", "where", 10, 10, "start",
                "kill", false, 10, new ItemList(), Arrays.asList("foo") );
        //when
        String res = e.getNextRoom("foo");
        //then
        assertEquals(res, "foo");
    }

    @Test
    public void test6_nextRoom() throws Exception {
        //before
        RoamingEnemy e = new RoamingEnemy(
                "name", "where", 10, 10, "start",
                "kill", false, 10, new ItemList(), Arrays.asList("foo", "bar") );
        //when
        String res = e.getNextRoom("baz");
        //then
        assertNull(res);
    }
}