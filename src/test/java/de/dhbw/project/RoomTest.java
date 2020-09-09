package de.dhbw.project;

import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
import de.dhbw.project.character.RoamingEnemy;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoomTest {

    @Test
    public void test1_shouldAddItem() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Clothing c = new Clothing("TestClothing", "TestClothing", ItemState.NOT_USABLE, 99, EquipmentType.LOWER_BODY,0);
        Food f = new Food("TestFood", "TestFood", ItemState.NOT_USABLE, 99);
        Resource re = new Resource("TestResource", "TestResource", ItemState.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", ItemState.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", ItemState.NOT_USABLE, 99);

        //when
        r.addItem(c);
        r.addItem(f);
        r.addItem(re);
        r.addItem(t);
        r.addItem(w);


        //then
        assertTrue(r.getRoomItemList().size() == 5);
    }

    @Test
    public void test2_shouldRemoveItem() {
        //before
        Room room = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Clothing c = new Clothing("TestClothing", "TestClothing", ItemState.NOT_USABLE, 99, EquipmentType.LOWER_BODY,0);
        Food f = new Food("TestFood", "TestFood", ItemState.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", ItemState.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", ItemState.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", ItemState.NOT_USABLE, 99);
        room.addItem(c);
        room.addItem(f);
        room.addItem(r);
        room.addItem(t);
        room.addItem(w);


        //when
        room.removeItem(c);
        room.removeItem(f);
        room.removeItem(r);
        room.removeItem(t);
        room.removeItem(w);

        //then
        assertTrue(room.getRoomItemList().size() == 0);
    }

    @Test
    public void test3_shouldRemoveNonExistingItem() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Item i = new Resource("TestItem", "TestItem", ItemState.NOT_USABLE, 99);

        //when
        r.removeItem(i);

        //then
        assertTrue(r.getRoomItemList().size() == 0);
    }

    @Test
    public void test4_shouldListRoomItems() {
        //before
        Room room = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Clothing c = new Clothing("TestClothing", "TestClothing", ItemState.NOT_USABLE, 99, EquipmentType.LOWER_BODY,0);
        Food f = new Food("TestFood", "TestFood", ItemState.NOT_USABLE, 99);
        Resource r = new Resource("TestResource", "TestResource", ItemState.NOT_USABLE, 99);
        Tool t = new Tool("TestTool", "TestTool", ItemState.NOT_USABLE, 99);
        Weapon w = new Weapon("TestWeapon", "TestWeapon", ItemState.NOT_USABLE, 99);
        room.addItem(c);
        room.addItem(f);
        room.addItem(r);
        room.addItem(t);
        room.addItem(w);

        //when
        List<String> roomItems = room.getRoomItemLowerNameList();

        //then
        assertTrue(roomItems.size() == 5);
        assertTrue(roomItems.contains(c.getName().toLowerCase()));
        assertTrue(roomItems.contains(f.getName().toLowerCase()));
        assertTrue(roomItems.contains(r.getName().toLowerCase()));
        assertTrue(roomItems.contains(t.getName().toLowerCase()));
        assertTrue(roomItems.contains(w.getName().toLowerCase()));
    }

    @Test
    public void test5_shouldGetRoomInteractiveCraftingObjectsNameList() {
        //before
        List<InteractiveCraftingObject> craftingObjects = new ArrayList<>();
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject1", "desc", "state", "place", "usage", "action"));
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject2", "desc", "state", "place", "usage", "action"));
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", craftingObjects, null, null,false);

        //when
        List<String> names = r.getRoomInteractiveCraftingObjectsNameList();

        //then
        assertEquals("interactiveCraftingObject1", names.get(0));
        assertEquals("interactiveCraftingObject2", names.get(1));
    }

    @Test
    public void test6_shouldGetRoomInteractiveCraftingObjectByName() {
        //before
        List<InteractiveCraftingObject> craftingObjects = new ArrayList<>();
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject1", "desc", "state", "place", "usage", "action"));
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject2", "desc", "state", "place", "usage", "action"));
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", craftingObjects, null, null,false);

        //when
        InteractiveCraftingObject interactiveCraftingObject = r.getRoomInteractiveCraftingObjectByName("interactiveCraftingObject1");

        //then
        assertEquals("interactiveCraftingObject1", interactiveCraftingObject.getName());
    }

    @Test
    public void test7_shouldListRoomCharacter() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Enemy e = new Enemy();
        r.setEnemyList(Arrays.asList(e));

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains(e));
    }

    @Test
    public void test8_shouldListRoomCharacter() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void test9_shouldListRoomCharacter() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);

        //when
        List<String> result = r.getCharacterLowerNameList();

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void test10_shouldListRoomCharacter() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Enemy e = new Enemy();
        e.setName("foo");
        r.setEnemyList(Arrays.asList(e));

        //when
        List<String> result = r.getCharacterLowerNameList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains("foo"));
    }

    @Test
    public void test11_shouldListRoomCharacter() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Enemy e = new Enemy();
        RoamingEnemy re = new RoamingEnemy();
        Friend f = new Friend();
        r.setEnemyList(Arrays.asList(e));
        r.setFriendList(Arrays.asList(f));
        r.addRoamingEnemy(re);

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.size() == 3);
        assertTrue(result.contains(e));
        assertTrue(result.contains(f));
        assertTrue(result.contains(re));
    }

    @Test
    public void test12_shouldListRoomCharacter() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Friend f = new Friend();
        r.setFriendList(Arrays.asList(f));

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains(f));
    }

    @Test
    public void test13_shouldGetRoomInteractiveObjectsList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        InteractiveObject io = mock(InteractiveObject.class);
        r.setRoomInteractiveObjectsList(Arrays.asList(io));

        //when
        List<InteractiveObject> result = r.getRoomInteractiveObjectsList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains(io));
    }

    @Test
    public void test14_shouldGetRoomInteractiveObjectsNameList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        InteractiveObject io = new InteractiveObject("name", "description", "place", null, null, true, "wayName", "hint");
        r.setRoomInteractiveObjectsList(Arrays.asList(io));

        //when
        List<String> result = r.getRoomInteractiveObjectsLowerNameList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains("name"));
    }

    @Test
    public void test15_shouldgetRoomInteractiveObjectByName() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        InteractiveObject io1 = new InteractiveObject("name", "description", "place", null, null, true, "wayName", "hint");
        InteractiveObject io2 = new InteractiveObject("name2", "description2", "place2", null, null, true, "wayName2", "hint2");
        r.setRoomInteractiveObjectsList(Arrays.asList(io1, io2));

        //when
        InteractiveObject result = r.getRoomInteractiveObjectByName("name");

        //then
        assertEquals(result.getName(), "name");
    }

    @Test
    public void test16_shouldAddWay() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Way w = new Way("TestWay", "TestWayDescription", "W", r.getName(), "AnotherRoom", WayState.ACTIVE, "TestHint");

        //when
        r.addWay(w);

        //Then
        assertEquals(w, r.getWay("W"));
        assertEquals(1, r.getRoomWayNameList().size());
        assertEquals(1, r.getRoomWayList().size());
    }

    @Test
    public void test17_shouldDeleteWay() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Way w = new Way("TestWay", "TestWayDescription", "W", r.getName(), "AnotherRoom", WayState.ACTIVE, "TestHint");

        //when
        r.deleteWay(w);

        //then
        assertEquals(0, r.getRoomWayList().size());
        assertEquals(0, r.getRoomWayNameList().size());
    }

    @Test
    public void test18_addRoamingEnemy() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        RoamingEnemy re = new RoamingEnemy();

        //when
        r.addRoamingEnemy(re);

        //Then
        assertTrue(r.getRoamingEnemyList().contains(re));
        assertEquals(1, r.getRoamingEnemyList().size());
    }

    @Test
    public void test19_removeRoamingEnemy() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        RoamingEnemy re = new RoamingEnemy();
        r.addRoamingEnemy(re);

        //when
        r.removeRoamingEnemy(re);

        //Then
        assertTrue(r.getRoamingEnemyList().isEmpty());
    }

    @Test
    public void test20_getRoamingEnemyList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);

        //when
        List<RoamingEnemy> res = r.getRoamingEnemyList();

        //Then
        assertTrue(res.isEmpty());
    }

    @Test
    public void test21_getEnemyList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);

        //when
        List<Enemy> res = r.getEnemyList();

        //Then
        assertTrue(res.isEmpty());
    }

    @Test
    public void test22_getEnemyList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        Enemy e = new Enemy();
        r.setEnemyList(Arrays.asList(e));

        //when
        List<Enemy> res = r.getEnemyList();

        //Then
        assertTrue(res.contains(e));
        assertEquals(res.size(), 1);
    }

    @Test
    public void test23_getEnemyList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        RoamingEnemy e = new RoamingEnemy();
        r.addRoamingEnemy(e);

        //when
        List<Enemy> res = r.getEnemyList();

        //Then
        assertTrue(res.contains(e));
        assertEquals(res.size(), 1);
    }

    @Test
    public void test24_getEnemyList() {
        //before
        Room r = new Room("Test", "TestRoom", "true", "itemLocation", null, null, null,false);
        RoamingEnemy re = new RoamingEnemy();
        Enemy e = new Enemy();
        r.addRoamingEnemy(re);
        r.setEnemyList(Arrays.asList(e));

        //when
        List<Enemy> res = r.getEnemyList();

        //Then
        assertTrue(res.contains(e));
        assertTrue(res.contains(re));
        assertEquals(res.size(), 2);
    }
}
