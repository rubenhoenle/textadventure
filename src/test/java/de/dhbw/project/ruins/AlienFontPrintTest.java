package de.dhbw.project.ruins;

import de.dhbw.project.regions.ruins.AlienFont;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.PrintStream;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(AlienFont.class)
public class AlienFontPrintTest {

    @Mock
    PrintStream out;

    @Before
    public void prepareTest() {
        System.setOut(out);
    }
    
    //tests base alien font display function
    @Test
    public void test1_testOutputAlienText() throws Exception {
        //before
        
        
        //when
    	AlienFont.toAlien("abcdef");
    	AlienFont.toAlien("Abc\ndef");
    	AlienFont.toAlien("abc", true);
    	
        //then
        
    	verify(out, times(8)).println();
    	verify(out).println("     ###  ###  ###       ###  ");
    	verify(out).println("          #         ###   #   ");
    	verify(out).println("     ###  ##    ##  ###   #   ");
    	verify(out).println("###  #     #   ##   ###  ##   ");
    	//verify(out).println();
    	
    	//verify(out).println();
    	verify(out,times(2)).println("     ###  ###  ");
    	verify(out,times(2)).println("          #    ");
    	verify(out,times(2)).println("     ###  ##   ");
    	verify(out,times(2)).println("###  #     #   ");
    	//verify(out).println();
    	//verify(out).println();
    	verify(out).println("###       ###  ");
    	verify(out).println("     ###   #   ");
    	verify(out).println(" ##  ###   #   ");
    	verify(out).println("##   ###  ##   ");
    	//verify(out).println();
    	
    	//verify(out).println();
    	//verify(out).println(" A    B    C   ");
    	//verify(out).println("     ###  ###  ");
    	//verify(out).println("          #    ");
    	//verify(out).println("     ###  ##   ");
    	//verify(out).println("###  #     #   ");
    	//verify(out).println();
    	
    }
    
    //tests combined display of alien font and normal text
    @Test
    public void test2_testMixedAlienText() throws Exception {
        //before
        
        
        //when
    	AlienFont.mixedDisplay("Nothing special here.", true);
    	AlienFont.mixedDisplay("abba[ALFONT]cba[ALFONT]normal[ALFONT]", true);
    	
        //then
    	
    	verify(out).println("Nothing special here.");
    	
    	
    	verify(out).println("abba");
    	verify(out, times(2)).println();
    	verify(out).println(" C    B    A   ");
    	verify(out).println("###  ###       ");
    	verify(out).println("#              ");
    	verify(out).println("##   ###       ");
    	verify(out).println(" #   #    ###  ");
    	//verify(out).println();
    	verify(out).println("normal");
    }

}
