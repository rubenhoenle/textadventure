package de.dhbw.project.item;

import de.dhbw.project.Constants;
import de.dhbw.project.Player;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)

public class LampStateTest {

    @Test
    public void test1_LampStateShouldBeOn() throws Exception {
        //before
        Player player = new Player();
        String lampname = Constants.LAMP_NAMES.get(0);
        player.addItem(new Resource(lampname, "desc", ItemState.INACTIVE, 0));
        player.getItem(lampname).setItemstate(ItemState.ACTIVE);

        //when
        LampState state = player.getLampState();

        //then
        assertEquals(LampState.ON, state);
    }

    @Test
    public void test2_LampStateShouldBeOff() throws Exception {
        //before
        Player player = new Player();
        String lampname = Constants.LAMP_NAMES.get(0);
        player.addItem(new Resource(lampname, "desc", ItemState.ACTIVE, 0));
        player.getItem(lampname).setItemstate(ItemState.INACTIVE);

        //when
        LampState state = player.getLampState();

        //then
        assertEquals(LampState.OFF, state);
    }

    @Test
    public void test3_LampStateShouldBe_HasNoLamp() throws Exception {
        //before
        Player player = new Player();
        String lampname = Constants.LAMP_NAMES.get(0);

        //when
        LampState state = player.getLampState();

        //then
        assertEquals(LampState.HAS_NO_LAMP, state);
    }

}
