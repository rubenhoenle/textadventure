package de.dhbw.project;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

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
        String condition = "condition";

        //when
        Way w = new Way(name,description,direction,from,to, state,hint, condition);

        //then
        assertEquals(w.getName(),name);
        assertEquals(w.getDescription(), description);
        assertEquals(w.getDirection(),direction);
        assertEquals(w.getFrom(), from);
        assertEquals(w.getTo(),to);
        assertEquals(w.getState(),state);
        assertEquals(w.getHint(),hint);
        assertEquals(w.getConditionalItem(),condition);
    }
}
