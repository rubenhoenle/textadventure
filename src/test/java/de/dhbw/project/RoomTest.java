package de.dhbw.project;

import de.dhbw.project.character.Character;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

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

    @Test
    public void test5_shouldGetRoomInteractiveCraftingObjectsNameList(){
        //before
        List<InteractiveCraftingObject> craftingObjects = new ArrayList<>();
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject1", "desc", "state", "place", "usage", "action"));
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject2", "desc", "state", "place", "usage", "action"));
        Room r = new Room("Test", "TestRoom", "true", craftingObjects);

        //when
        List<String> names = r.getRoomInteractiveCraftingObjectsNameList();

        //then
        assertEquals("interactiveCraftingObject1", names.get(0));
        assertEquals("interactiveCraftingObject2", names.get(1));
    }

    @Test
    public void test6_shouldGetRoomInteractiveCraftingObjectByName(){
        //before
        List<InteractiveCraftingObject> craftingObjects = new ArrayList<>();
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject1", "desc", "state", "place", "usage", "action"));
        craftingObjects.add(new InteractiveCraftingObject("interactiveCraftingObject2", "desc", "state", "place", "usage", "action"));
        Room r = new Room("Test", "TestRoom", "true", craftingObjects);

        //when
        InteractiveCraftingObject interactiveCraftingObject = r.getRoomInteractiveCraftingObjectByName("interactiveCraftingObject1");

        //then
        assertEquals("interactiveCraftingObject1", interactiveCraftingObject.getName());
    }

    @Test
    public void test7_shouldListRoomCharacter(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Enemy e = new Enemy();
        r.setEnemyList(Arrays.asList(e));

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains(e));
    }

    @Test
    public void test8_shouldListRoomCharacter(){
        //before
        Room r = new Room("Test", "TestRoom", "true");

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void test9_shouldListRoomCharacter(){
        //before
        Room r = new Room("Test", "TestRoom", "true");

        //when
        List<String> result = r.getCharacterNameList();

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void test10_shouldListRoomCharacter(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Enemy e = new Enemy();
        e.setName("foo");
        r.setEnemyList(Arrays.asList(e));

        //when
        List<String> result = r.getCharacterNameList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains("foo"));
    }

    @Test
    public void test11_shouldListRoomCharacter(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Enemy e = new Enemy();
        Friend f = new Friend();
        r.setEnemyList(Arrays.asList(e));
        r.setFriendList(Arrays.asList(f));

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.size() == 2);
        assertTrue(result.contains(e));
        assertTrue(result.contains(f));
    }

    @Test
    public void test12_shouldListRoomCharacter(){
        //before
        Room r = new Room("Test", "TestRoom", "true");
        Friend f = new Friend();
        r.setFriendList(Arrays.asList(f));

        //when
        List<Character> result = r.getCharacterList();

        //then
        assertTrue(result.size() == 1);
        assertTrue(result.contains(f));
    }
}
