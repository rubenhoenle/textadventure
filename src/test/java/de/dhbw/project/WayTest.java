package de.dhbw.project;

import com.google.gson.annotations.SerializedName;
import de.dhbw.project.character.Character;
import de.dhbw.project.character.Enemy;
import de.dhbw.project.character.Friend;
import de.dhbw.project.interactive.InteractiveCraftingObject;
import de.dhbw.project.interactive.InteractiveObject;
import de.dhbw.project.item.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WayTest {

    @Test
    public void test1_shouldCreateWay() {
        //before
        String name="name";
        String description="description";
        String direction="direction";
        String from="from";
        String to="to";
        WayState state = WayState.ACTIVE;
        String hint="hint";

        //when
        Way w = new Way(name,description,direction,from,to, state,hint);

        //then
        assertEquals(w.getName(),name);
        assertEquals(w.getDescription(), description);
        assertEquals(w.getDirection(),direction);
        assertEquals(w.getFrom(), from);
        assertEquals(w.getTo(),to);
        assertEquals(w.getState(),state);
        assertEquals(w.getHint(),hint);

    }
}
