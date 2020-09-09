package de.dhbw.project;

import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
import de.dhbw.project.item.Item;
import de.dhbw.project.item.ItemState;
import de.dhbw.project.item.Tool;
import de.dhbw.project.nls.commands.UseCommand;
import org.junit.Before;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Quest.class)
public class QuestTest {

    @Mock
    Game game;

    Quest q = PowerMockito.spy(new Quest());

    @Mock
    Player player;

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
        game.player = player;
    }

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
        Quest q = new Quest(name, textStart, textAccept, textMid, textEnd,  completed,
                questReward, requiredItems,  accepted,  talkedOnce, mainQuest, fulfillmentKill, autoComplete,false) ;

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
    public void test2_shouldCompleteQuestWithItem() throws Exception {

        //before
        QuestItem qi = mock(QuestItem.class);
        Whitebox.setInternalState(q, "fulfillmentItems", Arrays.asList(qi));
        when(qi.getName()).thenReturn("qi");
        when(game.player.getItemFromEquipment("qi")).thenReturn(null);
        when(game.player.getItem("qi")).thenReturn(qi);

        Whitebox.setInternalState(q, "fulfillmentKill", "");

        //when
        boolean returnValue = q.checkCompleted(game);

        //then
        assertTrue(returnValue);
    }

    @Test
    public void test3_shouldCompleteQuestWithKill() throws Exception {

        //before
        Whitebox.setInternalState(q, "fulfillmentItems", Arrays.asList());
        Whitebox.setInternalState(q, "fulfillmentKill", "John Wick");
        Enemy e = mock(Enemy.class);
        when(game.getCharacter("John Wick")).thenReturn(e);
        when(e.isKilled()).thenReturn(true);


        //when
        boolean returnValue = q.checkCompleted(game);

        //then
        assertTrue(returnValue);
    }

    @Test
    public void test4_shouldFinishGame() throws Exception {
        //before
        Game game = PowerMockito.spy(new Game());

        ArrayList<QuestItem> rewards1 = new ArrayList<>();
        rewards1.add(new QuestItem("reward1","desc_item1", ItemState.INACTIVE, 1, EquipmentType.SHOES, "cloth",0));
        ArrayList<QuestItem> fulfillment1 = new ArrayList<>();
        rewards1.add(new QuestItem("fulfill1","desc_item1", ItemState.INACTIVE, 1, EquipmentType.SHOES, "cloth",0));
        ArrayList<QuestItem> rewards2 = new ArrayList<>();
        rewards1.add(new QuestItem("reward2","desc_item1", ItemState.INACTIVE, 1, EquipmentType.SHOES, "cloth",0));
        ArrayList<QuestItem> fulfillment2 = new ArrayList<>();
        rewards1.add(new QuestItem("fulfill2","desc_item1", ItemState.INACTIVE, 1, EquipmentType.SHOES, "cloth",0));
        ArrayList<QuestItem> rewards3 = new ArrayList<>();
        rewards1.add(new QuestItem("reward3","desc_item1", ItemState.INACTIVE, 1, EquipmentType.SHOES, "cloth",0));
        ArrayList<QuestItem> fulfillment3 = new ArrayList<>();
        rewards1.add(new QuestItem("fulfill3","desc_item1", ItemState.INACTIVE, 1, EquipmentType.SHOES, "cloth",0));

        ArrayList<Quest> quests = new ArrayList<>();
        quests.add(new Quest("name1","start","accept","mid","end",false,rewards1,fulfillment1,false,true,false,"ahhh",false,true));
        quests.add(new Quest("name1","start","accept","mid","end",false,rewards2,fulfillment2,false,true,true,"ahh",false,true));
        quests.add(new Quest("name1","start","accept","mid","end",false,rewards3,fulfillment3,false,true,true,"ahhh",false,true));
        Friend friend = new Friend("friend_name","place",100,100,"start","kill",false,quests);
        ArrayList<Friend> friends = new ArrayList<>();
        friends.add(friend);
        Room room = new Room("room_name","room_desc","", "", null, friends, null, false);
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
