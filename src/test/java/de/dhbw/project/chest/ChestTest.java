package de.dhbw.project.chest;

import de.dhbw.project.Constants;
import de.dhbw.project.Game;
import de.dhbw.project.item.Food;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class ChestTest {

    @Test
    public void test1_shouldGiveMysteryChestSize() throws Exception {
        //before
        Chest mysteryChest = new Chest("name", "desc", "place", null, 42, true);

        //when
        int size = mysteryChest.getChestSize();

        //then
        assertEquals(size, Constants.MYSTERY_CHEST_SIZE);
    }

    @Test
    public void test2_shouldGiveChoosenChestSize() throws Exception {
        //before
        int choosenChestSize = 42;
        Chest chest = new Chest("name", "desc", "place", null, choosenChestSize, false);

        //when
        int size = chest.getChestSize();

        //then
        assertEquals(size, choosenChestSize);
    }

    @Test
    public void test3_addItemToMysteryChestAndRemoveItFromOtherMysteryChest() throws Exception {
        //before
        Game game = new Game();
        Chest mysteryChest1 = new Chest("name1", "desc1", "place1", null, 30, true);
        Chest mysteryChest2 = new Chest("name2", "desc2", "place2", null, 30, true);
        Item item = new Food("apple", "desc", ItemState.NOT_USABLE, 1);

        //when
        mysteryChest1.addItem(game, item);
        Item takeItem = mysteryChest2.getAllItems(game).get(0);

        //then
        assertEquals(item, takeItem);
    }

}

