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
        QuestItem reward = mock(QuestItem.class);
        List<QuestItem> questReward = Arrays.asList(reward);
        QuestItem fulfillmentItems = mock(QuestItem.class);
        String fulfillmentKill="killMe";
        List<QuestItem> requiredItems = Arrays.asList(fulfillmentItems);
        boolean autoComplete = false;


        //when
        //Quest q = new Quest(questName,questTextStart,questCompleted,questReward);
        Quest q = new Quest(name,textStart,textAccept,textMid,textEnd,completed,questReward,requiredItems,fulfillmentKill,accepted,talkedOnce,autoComplete);

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
        assertEquals(q.getFulfillmentKill(),fulfillmentKill);
        assertTrue(q.getFulfillmentItems().size() == 1);
        assertEquals(q.isAutoComplete(),autoComplete);
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
        QuestItem reward = mock(QuestItem.class);
        ArrayList<QuestItem> questReward = new ArrayList<>();
        questReward.add(reward);
        Item fulfillmentItems = mock(Item.class);
        ArrayList<QuestItem> requiredItems = new ArrayList<>();
        String fulfillmentKill = "killMe";
        boolean autoComplete = false;

        Player player = new Player();
        QuestItem itemFulfill1 = new QuestItem("item1","desc_item1", State.ACTIVE, 1, EquipmentType.SHOES, "cloth");
        QuestItem itemFulfill2 = new QuestItem("item2", "desc_item2", State.INACTIVE,  2, EquipmentType.SHOES, "cloth");
                //new Tool("item2", "desc_item2", State.ACTIVE, 2);
        player.addItem(itemFulfill1.questItemToItem());
        player.addItem(itemFulfill2.questItemToItem());
        requiredItems.add(itemFulfill1);
        requiredItems.add(itemFulfill2);



        //when
        //Quest q = new Quest(questName,questTextStart,questCompleted,questReward);
        Quest q = new Quest(name,textStart,textAccept,textMid,textEnd,completed,questReward,requiredItems,fulfillmentKill,accepted,talkedOnce,autoComplete);
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
        assertEquals(q.getFulfillmentKill(),fulfillmentKill);
        assertEquals(q.isAutoComplete(),autoComplete);
    }
}
