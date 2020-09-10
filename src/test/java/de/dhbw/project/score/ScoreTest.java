package de.dhbw.project.score;

import de.dhbw.project.character.Character;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Score.class)
public class ScoreTest {

    @Test
    public void test1_shouldCreateScore() throws Exception {
        //before
        String playerName = "pName";
        Integer points = Integer.valueOf(10);
        Long time = Long.valueOf(10000);

        //when
        Score s = new Score(playerName,points,time);

        //then
        assertEquals(s.getPlayerName(),playerName);
        assertEquals(s.getPoints(), points);
        assertEquals(s.getTime(),time);
    }

    @Test
    public void test2_shouldPrintScore() throws Exception {
        //before
        String playerName = "pName";
        Integer points = Integer.valueOf(10);
        Long time = Long.valueOf(0);
        Score s = new Score(playerName,points,time);

        //when
        String output = s.toString();

        //then
        assertEquals(output,"player:pName, points:10, time:00:00:00");
    }

}
