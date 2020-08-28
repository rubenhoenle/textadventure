package de.dhbw.project;

import de.dhbw.project.interactive.Createable;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.interactive.Material;
import de.dhbw.project.item.Food;
import de.dhbw.project.item.Item;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InteractiveObjectTest {

    @Test
    public void test1_createItemWhenPlayerHasAllMaterials() {
        //before
        InteractiveCraftingObject interactiveCraftingObject = new InteractiveCraftingObject("name", "desc", "state", "place", "usage", "action");
        Player player = new Player();
        player.addItem(new Food("item1", "desc", State.NOT_USABLE, 0));
        player.addItem(new Food("item1", "desc", State.NOT_USABLE, 0));
        player.addItem(new Food("item2", "desc", State.NOT_USABLE, 0));
        List<Material> materials = new ArrayList<>();
        materials.add(new Material("item1", "desc", 2));
        materials.add(new Material("item2", "desc", 1));
        Createable createable = new Createable("createable1", "desc", 0, "food", materials);

        //when
        String s = interactiveCraftingObject.createItem(player, createable);

        //then
        assertEquals("Congratulations. You created a " + createable.getName() + ".", s);
    }

    @Test
    public void test2_createItemWhenPlayerHasNoMaterials() {
        //before
        InteractiveCraftingObject interactiveCraftingObject = new InteractiveCraftingObject("name", "desc", "state", "place", "usage", "action");
        Player player = new Player();
        List<Material> materials = new ArrayList<>();
        materials.add(new Material("item1", "desc", 2));
        materials.add(new Material("item2", "desc", 1));
        Createable createable = new Createable("createable1", "desc", 0, "food", materials);

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
        player.addItem(new Food("item1", "desc", State.NOT_USABLE, 0));
        player.addItem(new Food("item2", "desc", State.NOT_USABLE, 0));
        List<Material> materials = new ArrayList<>();
        materials.add(new Material("item1", "desc", 2));
        materials.add(new Material("item2", "desc", 1));
        Createable createable = new Createable("createable1", "desc", 0, "food", materials);

        //when
        String s = interactiveCraftingObject.createItem(player, createable);

        //then
        assertEquals("You don't have all needed materials for this in your inventory.", s);
    }

    @Test
    public void test4_createItemWhenPlayerHasOnlySomeMaterials() {
        Player player = new Player();

        Item item = new Food("item1", "desc", State.NOT_USABLE, 0);

        player.removeItem(item);
    }

}