package de.dhbw.project;

import de.dhbw.project.item.Item;
import de.dhbw.project.item.Tool;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Quest.class)
public class QuestTest {
    @Test
    public void test1_shouldCreateQuest() throws Exception {
        //before
        String name = "name";
        String textStart = "Start Text!";
        String textAccept = "Accept Text!";
        String textMid = "Mid Text!";
        String textEnd = "End Text!";
        boolean completed = true;
        boolean accepted = true;
        boolean talkedOnce = true;
        Item reward = mock(Item.class);
        List<Item> questReward = Arrays.asList(reward);
        Item fulfillmentItems = mock(Item.class);
        List<Item> requiredItems = Arrays.asList(fulfillmentItems);


        //when
        //Quest q = new Quest(questName,questTextStart,questCompleted,questReward);
        Quest q = new Quest(name,textStart,textAccept,textMid,textEnd,completed,questReward,requiredItems,accepted,talkedOnce);

        //then
        assertEquals(q.getName(),name);
        assertEquals(q.getTextStart(),textStart);
        assertEquals(q.getTextAccept(),textAccept);
        assertEquals(q.getTextMid(),textMid);
        assertEquals(q.getTextEnd(),textEnd);
        assertEquals(q.isCompleted(),completed);
        assertEquals(q.isAccepted(),accepted);
        assertEquals(q.isTalkedOnce(),talkedOnce);
        assertEquals(q.getReward(),questReward);
        assertTrue(q.getReward().size() == 1);
        assertEquals(q.getFulfillmentItems(),requiredItems);
        assertTrue(q.getFulfillmentItems().size() == 1);



    }

    @Test
    public void test1_shouldCompleteQuest() throws Exception {
        //before
        String name = "name";
        String textStart = "Start Text!";
        String textAccept = "Accept Text!";
        String textMid = "Mid Text!";
        String textEnd = "End Text!";
        boolean completed = false;
        boolean accepted = true;
        boolean talkedOnce = true;
        Item reward = mock(Item.class);
        ArrayList<Item> questReward = new ArrayList<>();
        questReward.add(reward);
        //Arrays.asList(reward);
        Item fulfillmentItems = mock(Item.class);
        ArrayList<Item> requiredItems = new ArrayList<>();
        //requiredItems.add(fulfillmentItems); // muss glaub auskommentiert werden -> ziemlich sicher
        //Arrays.asList(fulfillmentItems);

        Player player = new Player();
        Tool itemFulfill1 = new Tool("item1", "desc_item1", State.ACTIVE, 1);
        Tool itemFullfill2 = new Tool("item2", "desc_item2", State.ACTIVE, 2);
        player.addItem(itemFulfill1);
        player.addItem(itemFullfill2);
        requiredItems.add(itemFulfill1);
        requiredItems.add(itemFullfill2);



        //when
        //Quest q = new Quest(questName,questTextStart,questCompleted,questReward);
        Quest q = new Quest(name,textStart,textAccept,textMid,textEnd,completed,questReward,requiredItems,accepted,talkedOnce);
        if(q.checkCompleted(player)){
            q.setCompleted(true);
        }

        //then
        assertEquals(q.getName(),name);
        assertEquals(q.getTextStart(),textStart);
        assertEquals(q.getTextAccept(),textAccept);
        assertEquals(q.getTextMid(),textMid);
        assertEquals(q.getTextEnd(),textEnd);
        assertEquals(q.isCompleted(),true);
        assertEquals(q.isAccepted(),accepted);
        assertEquals(q.isTalkedOnce(),talkedOnce);
        assertEquals(q.getReward(),questReward);
        assertTrue(q.getReward().size() == 1);
        assertEquals(q.getFulfillmentItems(),requiredItems);
        assertTrue(q.getFulfillmentItems().size() == player.getInventory().size());



    }
}
