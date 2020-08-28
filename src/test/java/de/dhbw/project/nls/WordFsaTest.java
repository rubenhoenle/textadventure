package de.dhbw.project.nls;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * WordFsa test
 */
public class WordFsaTest {
	class DummyIndexConsumer implements IConsumeIndex {
		@Override
		public void setMatchEndIndex(int index) {
		}
	}
	
	DummyIndexConsumer dummyIndexConsumer = new DummyIndexConsumer();
	
    /**
     * @throws Exception 
     */
    @Test
    public void matchWord() {
    	String input = "testword\0";
    	WordFsa fsa = new WordFsa("testword", dummyIndexConsumer);
    	
    	for (int i = 0; i < input.length(); i++) {
    		fsa.processChar(input.charAt(i), i);
    	}
    	
    	assertTrue(fsa.getState() == FSA.State.MATCH);
    }
    
    @Test
    public void dontMatchSimilarWord() {
    	String input = "testwordabc\0";
    	WordFsa fsa = new WordFsa("testword", dummyIndexConsumer);
    	
    	for (int i = 0; i < input.length() && fsa.getState() == FSA.State.PENDING; i++) {
    		fsa.processChar(input.charAt(i), i);
    	}
    	
    	assertTrue(fsa.getState() == FSA.State.NO_MATCH);
    }
    
    @Test
    public void dontMatchWord() {
    	String input = "wasandres\0";
    	WordFsa fsa = new WordFsa("testword", dummyIndexConsumer);
    	
    	for (int i = 0; i < input.length(); i++) {
    		fsa.processChar(input.charAt(i), i);
    	}
    	
    	assertTrue(fsa.getState() == FSA.State.NO_MATCH);
    }
}
