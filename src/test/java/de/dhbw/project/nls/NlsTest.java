package de.dhbw.project.nls;

import de.dhbw.project.Game;
import de.dhbw.project.nls.commands.Command;
import de.dhbw.project.nls.commands.MoveCommand;
import de.dhbw.project.nls.commands.PutCommand;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * Natural language support test
 */
public class NlsTest {
	/**
     * @throws Exception 
     */

	private LanguageProcessor processor = new LanguageProcessor();
	private HashMap<FSA, Command> commands = new HashMap<FSA, Command>();
    
    
    @Test
    public void testAnd() {
    	LanguageProcessor p = new LanguageProcessor();
    	AndCompositeFsa comp = new AndCompositeFsa(p);
    	comp.add(new WordFsa("AB", (IConsumeIndex) comp));
    	comp.add(new WhitespaceFsa((IConsumeIndex) comp));
    	comp.add(new WordFsa("CD", (IConsumeIndex) comp));
    	comp.add(new TerminatingCharFsa((IConsumeIndex) comp));
    	p.setFsa(comp);
    	
    	String input = "AB CD\0";
    	
    	Optional<DataStorage> data = p.getCommandFrom(input);
    	
    	assertTrue(data.isPresent());
    }
    
    @Test
    public void testOr() {
    	LanguageProcessor p = new LanguageProcessor();
    	OrCompositeFsa comp = OrCompositeFsa.create(p);
    	new WordFsa("AB", comp);
    	new WordFsa("CD", comp);
    	p.setFsa(comp);
    	
    	String input = "AB\0";
    	
    	Optional<DataStorage> data = p.getCommandFrom(input);
    	
    	assertTrue(data.isPresent());
    }
    
    @Test
    public void testBoth() {
    	LanguageProcessor p = new LanguageProcessor();
    	AndCompositeFsa and = new AndCompositeFsa(p);
    	OrCompositeFsa or = OrCompositeFsa.create(and);
    	new WordFsa("AB", or);
    	new WordFsa("CD", or);
    	new TerminatingCharFsa(and);
    	p.setFsa(and);
    	
    	String input = "AB\0";
    	
    	Optional<DataStorage> data = p.getCommandFrom(input);
    	
    	assertTrue(data.isPresent());
    }
    
    private LazyCompositeFsa createLazyOr(CompositeFsa parent) {
    	return new LazyCompositeFsa(p -> {
    		AndCompositeFsa and2 = new AndCompositeFsa((IConsumeIndex) p);
    		new WhitespaceFsa(and2);
    		OrCompositeFsa or = OrCompositeFsa.create(and2);
    		
    		new WordFsa("on", or);
    		
    		AndCompositeFsa and = new AndCompositeFsa(or);
    		new IdentifierFsa("test", and);
    		createLazyOr(and);
    		
    		return and2;
    	}, parent);
    }
    
    @Test
    public void multipleItems() {
    	// "<item+> on"
    	LanguageProcessor p = new LanguageProcessor();
    	AndCompositeFsa and = new AndCompositeFsa(p);
    	new IdentifierFsa("test", and);
    	createLazyOr(and);
    	new TerminatingCharFsa(and);
    	
    	p.setFsa(and);
    	
    	Optional<DataStorage> data = p.getCommandFrom("te on\0");
    	
    	assertTrue(data.isPresent());
    }
    
    @Test
    public void noneOrMore() {
    	LanguageProcessor p = new LanguageProcessor();
    	AndCompositeFsa and = new AndCompositeFsa(p);
    	new IdentifierFsa("item", and);
    	new NoneOrMoreFsa(and, par -> {
    		AndCompositeFsa and2 = new AndCompositeFsa(par);
    		new WhitespaceFsa(and2);
    		new IdentifierFsa("item+", and2);
    	}, par -> {
    		AndCompositeFsa and2 = new AndCompositeFsa(par);
    		new WhitespaceFsa(and2);
    		new WordFsa("on", and2);
    		new TerminatingCharFsa(and2);
    	});
    	
    	p.setFsa(and);
    	
    	Optional<DataStorage> data = p.getCommandFrom("ta test on\0");
    	
    	assertTrue(data.isPresent());
    	assertTrue(data.get().get("item").contains("ta"));
    	assertTrue(data.get().get("item+").contains("test"));
    }
      
    @Test
    public void fullCommand() {
    	LanguageProcessor p = new LanguageProcessor();
    	AndCompositeFsa and = new AndCompositeFsa(p);
    	new WordFsa("put", and);
    	new WhitespaceFsa(and);
    	new IdentifierFsa("item", and);
    	new NoneOrMoreFsa(and, par -> {
    		AndCompositeFsa and2 = new AndCompositeFsa(par);
    		new WhitespaceFsa(and2);
    		new IdentifierFsa("item", and2);
    	}, par -> {
    		AndCompositeFsa and2 = new AndCompositeFsa(par);
    		new WhitespaceFsa(and2);
    		new WordFsa("on", and2);
    	});
    	new WhitespaceFsa(and);
    	new IdentifierFsa("place", and);
    	new NoneOrMoreFsa(and, par -> {
    		AndCompositeFsa and2 = new AndCompositeFsa(par);
    		new WhitespaceFsa(and2);
    		new IdentifierFsa("place", and2);
    	}, par -> {
    		AndCompositeFsa and2 = new AndCompositeFsa(par);
        	new TerminatingCharFsa(and2);
    	});
    	    	
    	p.setFsa(and);
    	
    	Optional<DataStorage> data = p.getCommandFrom("put x y z on old desk\0");
    	
    	assertTrue(data.isPresent());
    	assertTrue(data.get().get("item").contains("x"));
    	assertTrue(data.get().get("place").contains("desk"));
    }
    
    
    public void register(Command command) {
		String[] patterns = command.getPattern();
		OrCompositeFsa topLevelFsa = OrCompositeFsa.create(processor);
		for (String pattern : patterns) {
			CompositeFsa.buildCompositeFsaFrom(pattern, topLevelFsa);
		}
		commands.put(topLevelFsa, command);
	}

    @Test
    public void testMatchMove() {
    	String input = "do move north\0";
    	register(new MoveCommand(new Game()));
    	
		for (Entry<FSA, Command> entry : commands.entrySet()) {
			processor.setFsa(entry.getKey());
			Optional<DataStorage> result = processor.getCommandFrom(input);
			if (result.isPresent()) {
				entry.getKey().reset();
				assertTrue(result.get().get("direction").contains("north"));
				break;
			}
			entry.getKey().reset();
		}
    }
    
    @Test
    public void testMatchPut() {
    	String input = "i want to put xx y on old desk please\0";
    	register(new PutCommand(new Game()));
    	
		for (Entry<FSA, Command> entry : commands.entrySet()) {
			processor.setFsa(entry.getKey());
			Optional<DataStorage> result = processor.getCommandFrom(input);
			if (result.isPresent()) {
				entry.getKey().reset();
				assertTrue(result.get().get("item").contains("xx"));
				assertTrue(result.get().get("target").contains("old"));
				assertTrue(result.get().get("target").contains("desk"));
				break;
			}
			entry.getKey().reset();
		}
    }
}
