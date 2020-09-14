package de.dhbw.project;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
@PrepareForTest(Zork.class)
public class LabyrinthTest {
	
    Game game = Mockito.mock(Game.class);
	
	@Before
    public void prepareTest() throws Exception {
		setFinalStatic(Zork.class.getDeclaredField("game"), game);
    }
	
	//@Test
    public void test1_shouldGenerateLabyrinth() throws Exception {
		//before
		String entryString = "low branches of maple tree";
		Room room = new Room(entryString, "", "", "", null, null, null, false, null, false);
		when(game.getRooms()).thenReturn(Arrays.asList(room));
		
		//when
		List<Room> labyrinth = Zork.generateGenericLabyrinth();
		
		//then
		assertTrue(labyrinth.size() == 5);
		for (int i = 0; i < 5; i++) {
			Room labRoom = labyrinth.get(i);
			assertTrue(labRoom.getName().equals("labyrinth" + i));
			if(i < 4) {
				assertTrue(labRoom.getRoomWayList().size() == 2);
				assertTrue(Constants.DIRECTIONS.contains(labRoom.getRoomWayList().get(1).getDirection()));
			} else {
				assertTrue(labRoom.getRoomWayList().size() == 1);
			}
			assertTrue(Constants.DIRECTIONS.contains(labRoom.getRoomWayList().get(0).getDirection()));
		}		
	}
	
	private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);        
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
}
