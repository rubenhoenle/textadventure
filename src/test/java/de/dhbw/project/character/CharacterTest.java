package de.dhbw.project.character;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Character.class)
public class CharacterTest {

    @Test
    public void test1_shouldCreateCharacter() throws Exception {
        //before
        String characterName = "name";
        String characterWhere = "where";
        int characterHealth = 99;
        int characterStrength = 99;
        String characterStartStatement = "startStatement";
        String characterKillStatement = "killStatement";
        boolean characterKilled = false;

        //when
        Character c = new Character(characterName,characterWhere, characterHealth, characterStrength, characterStartStatement, characterKillStatement, characterKilled);

        //then
        assertEquals(c.getName(),characterName);
        assertEquals(c.getWhere(),characterWhere);
        assertEquals(c.getHealth(),characterHealth);
        assertEquals(c.getStrength(),characterStrength);
        assertEquals(c.getStartStatement(), characterStartStatement);
        assertEquals(c.getKillStatement(), characterKillStatement);
        assertEquals(c.isKilled(),characterKilled);
    }

}
