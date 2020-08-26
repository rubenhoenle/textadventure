package de.dhbw.project.character;

import de.dhbw.project.Item;
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
@PrepareForTest(Enemy.class)
public class EnemyTest {

    @Test
    public void test1_shouldCreateEnemy() throws Exception {
        //before
        String characterName = "name";
        String characterWhere = "where";
        int characterHealth = 99;
        int characterStrength = 99;
        String characterStartStatement = "startStatement";
        String characterKillStatement = "killStatement";
        boolean characterKilled = false;
        Item dropItem = mock(Item.class);
        List<Item> characterDropItems = Arrays.asList(dropItem);
        boolean characterAutoAttack = true;

        //when
        Enemy e = new Enemy(characterName,characterWhere, characterHealth, characterStrength, characterStartStatement, characterKillStatement, characterKilled, characterDropItems, characterAutoAttack);

        //then
        assertEquals(e.getName(),characterName);
        assertEquals(e.getWhere(),characterWhere);
        assertEquals(e.getHealth(),characterHealth);
        assertEquals(e.getStrength(),characterStrength);
        assertEquals(e.getStartStatement(), characterStartStatement);
        assertEquals(e.getKillStatement(), characterKillStatement);
        assertEquals(e.isKilled(),characterKilled);
        assertEquals(e.getDropItemList(),characterDropItems);
        assertTrue(e.getDropItemList().size()==1);
        assertEquals(e.isAutoAttack(), characterAutoAttack);
    }

}
