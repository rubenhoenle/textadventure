package de.dhbw.project;

import de.dhbw.project.interactive.Createable;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.interactive.Material;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InteractiveObjectTest {

    @Test
    public void test1_createItemWhenPlayerHasAllMaterials() {
        //before
        InteractiveCraftingObject interactiveCraftingObject = new InteractiveCraftingObject("name", "desc", "state", "place", "usage", "action");
        Player player = new Player();
        player.addItem(new Item("item1", "desc", "state", 0));
        player.addItem(new Item("item1", "desc", "state", 0));
        player.addItem(new Item("item2", "desc", "state", 0));
        List<Material> materials = new ArrayList<>();
        materials.add(new Material("item1", "desc", 2));
        materials.add(new Material("item2", "desc", 1));
        Createable createable = new Createable("createable1", "desc", materials);

        //when
        String s = interactiveCraftingObject.createItem(player, createable);

        //then
        assertEquals(s, "Congratulations. You created a " + createable.getName() + ".");
    }

    @Test
    public void test2_createItemWhenPlayerHasNoMaterials() {
        //before
        InteractiveCraftingObject interactiveCraftingObject = new InteractiveCraftingObject("name", "desc", "state", "place", "usage", "action");
        Player player = new Player();
        List<Material> materials = new ArrayList<>();
        materials.add(new Material("item1", "desc", 2));
        materials.add(new Material("item2", "desc", 1));
        Createable createable = new Createable("createable1", "desc", materials);

        //when
        String s = interactiveCraftingObject.createItem(player, createable);

        //then
        assertEquals("You don't have all needed materials for this in your inventory.", s);
    }

    @Test
    public void test3_createItemWhenPlayerHasOnlySomeMaterials() {
        //before
        InteractiveCraftingObject interactiveCraftingObject = new InteractiveCraftingObject("name", "desc", "state", "place", "usage", "action");
        Player player = new Player();
        player.addItem(new Item("item1", "desc", "state", 0));
        player.addItem(new Item("item2", "desc", "state", 0));
        List<Material> materials = new ArrayList<>();
        materials.add(new Material("item1", "desc", 2));
        materials.add(new Material("item2", "desc", 1));
        Createable createable = new Createable("createable1", "desc", materials);

        //when
        String s = interactiveCraftingObject.createItem(player, createable);

        //then
        assertEquals("You don't have all needed materials for this in your inventory.", s);
    }

    @Test
    public void test4_createItemWhenPlayerHasOnlySomeMaterials() {
        Player player = new Player();

        Item item = new Item("item1", "desc", "state", 0);

        player.removeItem(item);
    }

}