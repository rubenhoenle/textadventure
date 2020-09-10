package de.dhbw.project.score;

import de.dhbw.project.Game;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(ScoreBoard.class)
public class ScoreBoardTest {

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
    }

    @Test
    public void test1_shouldCreateScoreBoard() throws Exception {
        //before

        //when
        ScoreBoard sb = new ScoreBoard();

        //then
        assertTrue(sb.getScores().size() == 0);
    }

    @Test
    public void test2_shouldPrintScoreBoard() throws Exception {
        //before
        ScoreBoard sb = new ScoreBoard();
        Score s1 = new Score("p1", 100, 0);
        Score s2 = new Score("p2", 100, 1000);
        Score s3 = new Score("p3", 99, 0);
        Score s4 = new Score("p4", 99, 1000);
        sb.getScores().addAll(Arrays.asList(s4,s2,s3,s1));

        //when
        sb.printScoreBoard();

        //then
        verify(out).println("1. player:p1, points:100, time:00:00:00");
        verify(out).println("2. player:p2, points:100, time:00:00:01");
        verify(out).println("3. player:p3, points:99, time:00:00:00");
        verify(out).println("4. player:p4, points:99, time:00:00:01");
    }

    @Test
    public void test3_shouldPrintEmptyScoreBoard() throws Exception {
        //before
        ScoreBoard sb = new ScoreBoard();

        //when
        sb.printScoreBoard();

        //then
        verify(out).println("There are no scores to show!");
    }

}
