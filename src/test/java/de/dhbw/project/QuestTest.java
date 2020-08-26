package de.dhbw.project;

import de.dhbw.project.character.Character;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

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
        String questName = "name";
        String questText = "text";
        boolean questCompleted = true;
        Item rewardItem = mock(Item.class);
        List<Item> questReward = Arrays.asList(rewardItem);

        //when
        Quest q = new Quest(questName,questText,questCompleted,questReward);

        //then
        assertEquals(q.getName(),questName);
        assertEquals(q.getQuestText(),questText);
        assertEquals(q.isCompleted(),questCompleted);
        assertEquals(q.getReward(),questReward);
        assertTrue(q.getReward().size() == 1);
    }

}
