package de.dhbw.project;

import de.dhbw.project.character.Friend;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.Tool;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
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
        boolean mainQuest = false;
        QuestItem reward = mock(QuestItem.class);
        List<QuestItem> questReward = Arrays.asList(reward);
        QuestItem fulfillmentItems = mock(QuestItem.class);
        String fulfillmentKill="killMe";
        List<QuestItem> requiredItems = Arrays.asList(fulfillmentItems);
        boolean autoComplete = false;


        //when
        //Quest q = new Quest(questName,questTextStart,questCompleted,questReward);
        Quest q = new Quest(name,textStart,textAccept,textMid,textEnd,completed,questReward,requiredItems,accepted,talkedOnce,mainQuest,fulfillmentKill,autoComplete);

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
    public void test2_shouldCompleteQuest() throws Exception {
        //before
        String name = "name";
        String textStart = "Start Text!";
        String textAccept = "Accept Text!";
        String textMid = "Mid Text!";
        String textEnd = "End Text!";
        boolean completed = false;
        boolean accepted = true;
        boolean talkedOnce = true;
        boolean mainQuest = false;
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
        Quest q = new Quest(name,textStart,textAccept,textMid,textEnd,completed,questReward,requiredItems,accepted,talkedOnce,mainQuest,fulfillmentKill,autoComplete);
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
    @Test
    public void test3_shouldFinishGame() throws Exception {
        //before
        Game game = PowerMockito.spy(new Game());

        ArrayList<QuestItem> rewards1 = new ArrayList<>();
        rewards1.add(new QuestItem("reward1","desc_item1", State.INACTIVE, 1, EquipmentType.SHOES, "cloth"));
        ArrayList<QuestItem> fulfillment1 = new ArrayList<>();
        rewards1.add(new QuestItem("fulfill1","desc_item1", State.INACTIVE, 1, EquipmentType.SHOES, "cloth"));
        ArrayList<QuestItem> rewards2 = new ArrayList<>();
        rewards1.add(new QuestItem("reward2","desc_item1", State.INACTIVE, 1, EquipmentType.SHOES, "cloth"));
        ArrayList<QuestItem> fulfillment2 = new ArrayList<>();
        rewards1.add(new QuestItem("fulfill2","desc_item1", State.INACTIVE, 1, EquipmentType.SHOES, "cloth"));
        ArrayList<QuestItem> rewards3 = new ArrayList<>();
        rewards1.add(new QuestItem("reward3","desc_item1", State.INACTIVE, 1, EquipmentType.SHOES, "cloth"));
        ArrayList<QuestItem> fulfillment3 = new ArrayList<>();
        rewards1.add(new QuestItem("fulfill3","desc_item1", State.INACTIVE, 1, EquipmentType.SHOES, "cloth"));

        ArrayList<Quest> quests = new ArrayList<>();
        quests.add(new Quest("name1","start","accept","mid","end",false,rewards1,fulfillment1,false,true,false,"ahhh",false));
        quests.add(new Quest("name1","start","accept","mid","end",false,rewards2,fulfillment2,false,true,true,"ahh",false));
        quests.add(new Quest("name1","start","accept","mid","end",false,rewards3,fulfillment3,false,true,true,"ahhh",false));
        Friend friend = new Friend("friend_name","place",100,100,"start","kill",false,quests);
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(friend);
        Room room = new Room("room_name","room_desc",friends);
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);


        //when
        game.setRooms(rooms);
        friends.get(0).getQuests().get(0).setAccepted(true);
        friends.get(0).getQuests().get(1).setAccepted(true);
        friends.get(0).getQuests().get(2).setAccepted(true);
        friends.get(0).getQuests().get(0).setCompleted(true);
        friends.get(0).getQuests().get(1).setCompleted(true);
        friends.get(0).getQuests().get(2).setCompleted(true);

        if(game.getMainQuestNumber() == 2){
            game.setGameEnd(true);
        }

        //then
        assertEquals(game.isGameEnd(),true);
    }

}
