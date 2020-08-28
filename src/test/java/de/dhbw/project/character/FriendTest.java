package de.dhbw.project.character;

import de.dhbw.project.Quest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Friend.class)
public class FriendTest {

    @Test
    public void test1_shouldCreateFriend() throws Exception {
        //before
        String characterName = "name";
        String characterWhere = "where";
        int characterHealth = 99;
        int characterStrength = 99;
        String characterStartStatement = "startStatement";
        String characterKillStatement = "killStatement";
        boolean characterKilled = false;
        Quest characterQuest = mock(Quest.class);
        List<Quest> characterQuests = Arrays.asList(characterQuest);

        //when
        Friend f = new Friend(characterName,characterWhere, characterHealth, characterStrength, characterStartStatement, characterKillStatement, characterKilled, characterQuests);

        //then
        assertEquals(f.getName(),characterName);
        assertEquals(f.getWhere(),characterWhere);
        assertEquals(f.getHealth(),characterHealth);
        assertEquals(f.getStrength(),characterStrength);
        assertEquals(f.getStartStatement(), characterStartStatement);
        assertEquals(f.getKillStatement(), characterKillStatement);
        assertEquals(f.isKilled(),characterKilled);
        assertEquals(f.getQuests(),characterQuests);
    }

}
