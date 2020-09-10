package de.dhbw.project.interactive;

import de.dhbw.project.Quest;
import de.dhbw.project.item.ItemList;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.Weapon;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InteractiveObjectTest {

    @Test
    public void test1_createInteractiveObject() {
        //before
        String name = "name";
        String description = "description";
        String place = "place";
        Quest quest = new Quest();
        ItemList requiredItem = new ItemList();
        Weapon w = new Weapon("axe","dangerous weapon", ItemState.ACTIVE, 99);
        requiredItem.addItem(w);
        boolean removeRequiredItem = false;
        String wayName= "wayName";
        String hint="hint";

        //when
        InteractiveObject io = new InteractiveObject(name,description,place,quest,requiredItem,removeRequiredItem,wayName,hint);

        //then
        assertEquals(io.getName(),name);
        assertEquals(io.getDescription(),description);
        assertEquals(io.getPlace(),place);
        assertEquals(io.getQuest(),quest);
        assertEquals(io.getRequiredItem(),w);
        assertEquals(io.getWayName(),wayName);
        assertEquals(io.getHint(),hint);
    }
}